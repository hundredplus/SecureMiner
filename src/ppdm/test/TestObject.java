
package ppdm.test;

import java.io.Serializable;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Oct 3, 2008
 */
public class TestObject implements Serializable {
    String      send;
    double      rate;

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

}
