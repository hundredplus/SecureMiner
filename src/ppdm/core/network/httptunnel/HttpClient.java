package ppdm.core.network.httptunnel;

/**
 * Copyright (c) 1996,1997,1998 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL purposes and without
 * fee is hereby granted provided that this copyright notice
 * appears in all copies. Please refer to the file "copyright.html"
 * for further important copyright and licensing information.
 *
 * The Java source code is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You shall
 * not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sun.
 *
 * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
import java.net.*;
import java.io.*;
import java.util.*;


/**
 * Implementation of a basic HTTP client for Firewall
 * tunneling. The client supports both Client->Server and
 * Server->Client communication.
 */
public class HttpClient implements Runnable
{
	// Type of message: also used in HttpServerWorker
	final static public int PENDING = 1;
	final static public int DATA = 2;

	// The Server URL
	private String _url;

	// Pending connection Thread
	private Thread _pendingTID;
	private boolean _stop;

	// Listeners for incoming messages
	private HttpListener _listener;

	/**
	 * Creates a new HttpClient instance configured
	 * to connect to the server specified in the url
	 * parameter.
	 * @param url URL of the server, example http://bogus:9000
	 */
	public HttpClient (String url)
	{
		_url = url;
		_pendingTID = new Thread(this);
		_pendingTID.setPriority (Thread.MAX_PRIORITY);
		_listener = null;
	}

	/**
	 * Removes the HttpListener. The pending connection
	 * is stopped, so the client won't receive any more
	 * messages from the server
	 * @param l HttpListener to remove
	 * @return void
	 */
	public void removeHttpListener (HttpListener l) 
	{
		if ( _listener != null )
		{
			synchronized (this)
			{
				_stop = true;
			}
			_pendingTID.destroy();
			_listener = null;
		}
	}

	/**
	 * Adds the HttpListener. The pending connection
	 * is started, so the client will receive 
	 * messages from the server. 
	 * @param l HttpListener to remove
	 * @return void
	 * @throws TooManyListenersException Thrown if more then one
	 * HttpListener is added.
	 */
	public void addHttpListener (HttpListener l) throws TooManyListenersException
	{
		if ( _listener == null )
		{
			_listener = l;
			synchronized (this)
			{
				_stop = false;
			}
			_pendingTID.start();
		}
		else
		{
			throw new TooManyListenersException();
		}
	}

	/**
	 * Convience method to send the PENDING connection to 
	 * the server
	 * @return void
	 * @throws IOException Thrown if a problem occurs while setting
	 * up the pending message.
	 */
	private URLConnection _sendPending () throws IOException
	{
		URL url = new URL (_url);
		URLConnection connection = url.openConnection();
		connection.setUseCaches(false);
		connection.setDoOutput (true);
		return connection;
	}

	/**
	 * Thread used to maintain the pending connection
	 * @return void
	 */
	public void run()
	{
		boolean s;
		do
		{
			URLConnection connection;
			int length;
			try
			{
				connection = _sendPending();
				DataOutputStream output = new DataOutputStream (connection.getOutputStream());
				output.writeInt (PENDING);
				output.flush();
				output.close();
				output = null;

				// Check our response code
				int code = ((HttpURLConnection)connection).getResponseCode();
				switch (code)
				{
					case 200: // HTTP_OK
					{
						DataInputStream input = new DataInputStream (connection.getInputStream());
						length = input.readInt();
						byte buffer[] = new byte[length];
						if ( length >= 0 )
						{
							input.readFully (buffer);
							if ( buffer.length == length )
							{
								if ( _listener != null )
								{
									_listener.service ( new ByteArrayInputStream (buffer));
								}
							}
							else
							{
								System.err.println ("Short read");
							}
							buffer = null;
						}
						else
						{
							System.err.println ("Invalid length: " + length);
						}
						input.close();
						input = null;
					}
					case 504: //HTTP_GATEWAY_TIMEOUT
					{
						connection = _sendPending();
						break;
					}
					default: //OTHER HTTP_SERVER ERRORS
					{
						System.out.println ("Invalid code:"+code);
						synchronized (this)
						{
							_stop = true;
						}
					}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
				synchronized (this)
				{
					_stop = true;
				}
			}
			synchronized (this)
			{
				s = _stop;
			}
		}while ( s == false );

	}

	/**
	 * Sends a message to the server
	 * @param data Array of bytes to send
	 * @return void
	 * @throws IOException Thrown if a problem occurs while sending
	 * the message
	 */
	synchronized public byte[] send (byte data[]) throws IOException
	{
		byte buffer[];

		// Establish a connection
		URL url = new URL (_url);
		URLConnection connection = url.openConnection();
		connection.setUseCaches (false);
		connection.setDoOutput(true);
		
		// Write out the data
		DataOutputStream dataOut = new DataOutputStream (new BufferedOutputStream (connection.getOutputStream()));
		dataOut.writeInt (HttpClient.DATA);
		dataOut.writeInt (data.length);
		dataOut.write (data);
		dataOut.flush();
		dataOut.close();

		int length;
		DataInputStream input = new DataInputStream (new BufferedInputStream (connection.getInputStream()));
		int type = input.readInt();
		if ( type == HttpClient.DATA )
		{
			length = input.readInt();
			buffer = new byte[length];
			input.readFully (buffer);
		}
		else
		{
			buffer = null;
			throw new IOException ("Unknown Response Type");
		}
		input.close();
		return buffer;
	}
}


			
