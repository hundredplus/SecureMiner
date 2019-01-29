
package ppdm.core.network;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Nov 15, 2008
 */
import javax.net.ServerSocketFactory;
import ppdm.core.ErrorController;

public class PortCheck {

    public static boolean isAvailable(int port) {
//        long startTime = System.currentTimeMillis();
        try {
            ServerSocketFactory.getDefault().createServerSocket(port);
            return true;
        } catch (Exception e) {
            ErrorController.show(null, "Port " + port + " is in use.");
            return false;
//            System.out.println("Port " + PORT_TO_CHECK + " is in use.");
        }
//        System.out.println("Check duration :" + (System.currentTimeMillis() - startTime) + "ms.");
    }
}
