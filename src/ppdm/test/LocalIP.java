
package ppdm.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Sep 25, 2009
 */
public class LocalIP {

    public static void main(String[] args) {
    try {
        InetAddress addr = InetAddress.getLocalHost();

        System.out.println(addr);

        // Get IP Address
        byte[] ipAddr = addr.getAddress();

        System.out.println(ipAddr);

        String ip = addr.getHostAddress();

        System.out.println(ip);
        // Get hostname
        String hostname = addr.getHostName();
    } catch (UnknownHostException e) {
    }
    }

}
