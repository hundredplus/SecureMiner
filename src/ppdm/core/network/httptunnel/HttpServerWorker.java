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
 * Implements the Worker interface for the HttpServer
 */
public class HttpServerWorker implements Worker
{

	/**
	 * Invoked by the Pool when a job comes in for the Worker
	 * @param data Worker data
	 * @return void
	 */
	public void run(Object data)
	{
		Socket socket = (Socket)((Hashtable)data).get ("Socket");
		HttpServer server = (HttpServer)((Hashtable)data).get ("HttpServer");
		try
		{
			DataInputStream input = new DataInputStream (new BufferedInputStream(socket.getInputStream()));
			String line = input.readLine();
			if ( line.toUpperCase().startsWith ("POST") )
			{
				for ( ; (line=input.readLine()).length() > 0; )
					; 
				
				int type = input.readInt();
				switch (type)
				{
					case HttpClient.DATA :
					{
						int length = input.readInt();
						byte buffer[] = new byte[length];
						input.readFully (buffer);
						ByteArrayOutputStream dataOut = new ByteArrayOutputStream();
						server.notifyListener (new ByteArrayInputStream (buffer), dataOut);
						DataOutputStream output = new DataOutputStream (new BufferedOutputStream(socket.getOutputStream()));
						server.writeResponse (output);
						output.writeInt (HttpClient.DATA);
						output.writeInt (dataOut.toByteArray().length);
						output.write (dataOut.toByteArray());
						output.flush();

						input.close();
						output.close();
						socket.close();
						break;
					}
					case HttpClient.PENDING :
					{
						// DON'T CLOSE THE SOCKET!

						server.addClient (socket);
						break;
					}
					default :
					{
						System.err.println ("Invalid type: " + type);
					}
				}
			}
			else
			{
				System.err.println ("Invalid HTTP request: " + line);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
