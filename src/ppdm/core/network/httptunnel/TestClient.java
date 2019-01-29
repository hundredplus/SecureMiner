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

public class TestClient implements HttpListener, Runnable
{
	private HttpClient _client;
	private String _data;
	private TextArea _area;
	private TextArea _area2;
	private long _timeout;
	private int _count1;
	private int _count2;

	public TestClient (String url, long timeout) throws Exception
	{
		_timeout = timeout;
		Frame f = new Frame ("TestClient: Output");
		f.addWindowListener (
			new WindowAdapter()
			{
				public void windowClosing (WindowEvent event)
				{
					System.exit(0);
				}
			}
		);
		_area = new TextArea (10,20);
		_area.setEditable (false);
		_area2 = new TextArea (10,20);
		_area2.setEditable (false);
		f.setLayout (new FlowLayout());
		f.add (_area);
		f.add (_area2);
		f.pack();
		f.show();	
		_data = new String("hello\n");
		_client = new HttpClient (url);
		_client.addHttpListener (this);
		new Thread(this).start();
	}

	public void service (InputStream data)
	{
		try
		{
			DataInputStream input = new DataInputStream (data);
			String s = input.readLine();
			_area.append ((_count2++)+"TestServer Sent: " + s + "\n");
			input.close();
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
				String s = new String (_client.send (_data.getBytes()));
				_area2.append ((_count1++)+"TestServer response: " + s + "\n");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	static public void main (String argv[]) throws Exception
	{
		if ( argv.length == 2 )
		{
			new TestClient (argv[0], Long.parseLong (argv[1]));
		}
		else
		{
                        new TestClient ("http://localhost:8888", 100);
//			System.out.println ("Usage: java TestClient <url> <send timeout>");
//			System.exit(1);
		}
	}
}
