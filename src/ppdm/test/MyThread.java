
package ppdm.test;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Oct 14, 2008
 */
public class MyThread extends Thread {
    private volatile Thread blinker;
    
    public void run() {
        Thread thisThread = Thread.currentThread();
        while (blinker == thisThread) {
            try {
                thisThread.sleep(12);
            } catch (InterruptedException e){
            }
        }
    }

}

