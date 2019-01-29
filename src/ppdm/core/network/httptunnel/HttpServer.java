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
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Implementation of a basic HTTP server for Firewall
 * tunneling. The server supports both Client->Server and
 * Server->Client communication.
 */
public class HttpServer implements Runnable
{
	// Accept socket
	private ServerSocket _serverSocket;

	// Server Listener
	private HttpServerListener _listener;

	// Thread accepting connections
	private Thread _acceptTID;

	// Handler threads
	private Pool _pool;

	// Client sockets
	private Vector _clients;

	// Default HTTP Response
	private String _httpResponse; 

	/**
	 * Creates a new HttpServer instance
	 * @param port Port to listen on
	 * @param poolSize Number of handler threads
	 * @throws IOException Thrown if the accept socket cannot be opended
	 */
	public HttpServer (int port, int poolSize) throws IOException
	{
		_serverSocket = new ServerSocket (port);
		_httpResponse = "HTTP/1.0 200 MyServer \nCache-Control: no-cache\nPragma: no-cache \r\n\r\n";
		try
		{
			_pool = new Pool (poolSize, HttpServerWorker.class);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new InternalError (e.getMessage());
		}
		_clients = new Vector();
		_acceptTID = new Thread(this);
		_acceptTID.start();
	}

	/**
	 * Adds a new client 
	 * @param s Socket
	 * @return void
	 */
	synchronized void addClient (Socket s)
	{
		_clients.addElement(s);
	}

	/**
	 * Adds a new HttpServerListener. Only one listener can
	 * be added
	 * @param l HttpServerListener
	 * @return void
	 * @throws TooManyListenersException Thrown if more then one listener is added
	 */
	public void addHttpServerListener (HttpServerListener l) throws TooManyListenersException
	{
		if ( _listener == null )
		{
			_listener = l;
		}
		else
		{
			throw new TooManyListenersException();
		}
	}

	/**
	 * Removes a new HttpServerListener. 
	 * be added
	 * @param l HttpServerListener
	 * @return void
	 */
	public void removeHttpServerListener (HttpServerListener l)
	{
		_listener = null;
	}

	/**
	 * Notifies the listener when a message arrives
	 * @param data Message data
	 * @param out Stream to write results too
	 * @return void
	 */
	synchronized void notifyListener (InputStream data, OutputStream out)
	{
		if ( _listener != null )
		{
			_listener.service (data, out);
		}
	}

	/**
	 * Simple implementation that sends data to all clients
	 * @param data Array of bytes containing data to send
	 * @return void
	 */
	synchronized public void send (byte data[])
	{
		Enumeration elements = _clients.elements();
		while ( elements.hasMoreElements() )
		{
			Socket s = (Socket)elements.nextElement();
			try
			{
				DataOutputStream output = new DataOutputStream(new BufferedOutputStream (s.getOutputStream()));
				int length;
				writeResponse (output);
				output.writeInt (data.length);
				output.write (data);
				output.flush();
				output.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					s.close();
				}
				catch (IOException e)
				{
				}
			}
		}
		_clients.removeAllElements();
	}
	
	/**
	 * Thread to accept connections
	 * @return void
	 */
	public void run()
	{
		while (true)
		{
			try
			{
				Socket s = _serverSocket.accept();
				Hashtable data = new Hashtable();
				data.put ("Socket", s);
				data.put ("HttpServer", this);
				_pool.performWork (data);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Convience method to write the HTTP Response header
	 * @param out Stream to write the response too
	 * @return void
	 * @throws IOException Thrown if response can't be written
	 */
	void writeResponse (DataOutputStream out) throws IOException 
	{
		out.writeBytes (_httpResponse);
	}
}

