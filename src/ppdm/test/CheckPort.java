
package ppdm.test;

import javax.net.ServerSocketFactory;

public class CheckPort {

    public final static int PORT_TO_CHECK = 6868;
   
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        try {
            ServerSocketFactory.getDefault().createServerSocket(PORT_TO_CHECK);
        } catch (Exception e) {
            System.out.println("Port " + PORT_TO_CHECK + " is in use.");
        }
        System.out.println("Check duration :" + (System.currentTimeMillis() - startTime) + "ms.");
    }
}