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
import java.awt.*;
import java.awt.event.*;

public class TestServer implements HttpServerListener, Runnable
{
	private HttpServer _server;
	private String _data;
	private TextArea _area;
	private long _timeout;
	private int _count;

	public TestServer (int port, long timeout, int pool) throws Exception
	{
		_timeout = timeout;
		Frame f = new Frame ("TestServer");
		f.addWindowListener (
			new WindowAdapter()
			{
				public void windowClosing (WindowEvent event)
				{
					System.exit(0);
				}
			}
		);
		_area = new TextArea (10, 20);
		_area.setEditable (false);
		f.add ("Center", _area);
		f.pack();
		f.show();
		_server = new HttpServer (port, pool);
		_data = new String ("What's Up\n");
		_server.addHttpServerListener (this);
		new Thread(this).start();
	}

	public void service (InputStream data, OutputStream out)
	{
		try
		{
			DataInputStream input = new DataInputStream (data);
			String s = input.readLine();
			_area.append ((_count++)+"TestClient sent: " + s + "\n");
			input.close();

			DataOutputStream dataOut = new DataOutputStream(out);
			dataOut.writeBytes ("Hi");
			dataOut.flush();
			dataOut.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		while (true)
		{
			try
			{
				Thread.sleep (_timeout);
				_server.send (_data.getBytes());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	static public void main (String argv[]) throws Exception
	{
		if ( argv.length == 3)
		{
			new TestServer (Integer.parseInt(argv[0]), Long.parseLong (argv[1]), Integer.parseInt(argv[2]));
		}
		else
		{
                        new TestServer (80, 1000, 100);
//			System.out.println ("Usage: java TestServer <port> <send timeout> <pool size>");
//			System.exit(1);
		}
	}
}

