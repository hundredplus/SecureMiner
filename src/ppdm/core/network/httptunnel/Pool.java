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
import java.util.*;

/**
 * Implements Thread Pooling. Thread Pool simply keeps a 
 * bunch of suspended threads around to do some work.
 */
public class Pool 
{
	/**
	 * Handler class for perform work
	 * requested by the Pool.
	 */
	class WorkerThread extends Thread
	{
		private Worker _worker;
		private Object _data;

		/**
		 * Creates a new WorkerThread
		 * @param id Thread ID
		 * @param worker Worker instance associated with the WorkerThread
		 */
		WorkerThread(String id, Worker worker)
		{
			super(id);
			_worker = worker;
			_data = null;
		}
		
		/**
		 * Wakes the thread and does some work
		 * @param data Data to send to the Worker
		 * @return void
		 */
		synchronized void wake (Object data)
		{
			_data = data;
			notify();
		}

		/**
		 * WorkerThread's thread routine
		 */
		synchronized public void run()
		{
			boolean stop = false;
			while (!stop)
			{
				if ( _data == null )
				{
					try
					{
						wait();
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
						continue;
					}
				}
				if ( _data != null )
				{
					_worker.run(_data);
				}
				_data = null;
				stop = !(_push (this));
			}
		}
	};

	private Stack _waiting;
	private int _max;
	private Class _workerClass;

	/**
	 * Creates a new Pool instance
	 * @param max Max number of handler threads
	 * @param workerClass Name of Worker implementation
	 * @throws Exception
	 */
	public Pool (int max, Class workerClass) throws Exception
	{
		_max = max;
		_waiting = new Stack();
		_workerClass = workerClass;
		Worker worker;
		WorkerThread w;
		for ( int i = 0; i < _max; i++ )
		{
			worker = (Worker)_workerClass.newInstance();
			w = new WorkerThread ("Worker#"+i, worker);
			w.start();
			_waiting.push (w);
		}
	}

	/**
	 * Request the Pool to perform some work. 
	 * @param data Data to give to the Worker
	 * @return void
	 * @throws InstantiationException Thrown if additional worker can't be created
	 */
	public void performWork (Object data) throws InstantiationException
	{
		WorkerThread w = null;
		synchronized (_waiting)
		{
			if ( _waiting.empty() )
			{
				try
				{
					w = new WorkerThread ("additional worker", (Worker)_workerClass.newInstance());
					w.start();
				}
				catch (Exception e)
				{
					throw new InstantiationException ("Problem creating instance of Worker.class: " + e.getMessage());
				}
			}
			else
			{
				w = (WorkerThread)_waiting.pop();
			}
		}
		w.wake (data);
	}

	/**
	 * Convience method used by WorkerThread
	 * to put Thread back on the stack
	 * @param w WorkerThread to push
	 * @return boolean True if pushed, false otherwise
	 */
	private boolean _push (WorkerThread w)
	{
		boolean stayAround = false;
		synchronized (_waiting)
		{
			if ( _waiting.size() < _max )
			{
				stayAround = true;
				_waiting.push (w);
			}
		}
		return stayAround;
	}
}
	

