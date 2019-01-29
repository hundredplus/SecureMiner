package ppdm.core;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Oct 6, 2008
 */
public class ErrorController {

    private static Component frame;
//    static JDialog  dialog;

    public static void show(Object o, Exception ex) {
        //Save to log

        //and show
        show(o, ex.toString());
    }

    public static void show(Object o, String ex) {
      
        if (o != null) {
            ((JLabel) o).setText("<HTML>Error: " + ex + "</HTML>");
        }
        JOptionPane.showMessageDialog(frame, "<HTML>Error: " + ex + "</HTML>");
//        if (ex.getClass().equals(FileNotFoundException.class))
//            fileNotFound(o, ex.toString());
    }
    
    
    public static void show(Object o, String ex, String[] para) {
        if (o != null) {
            ((JLabel) o).setText("<HTML>" + ex.toString() + "</HTML>");
        }
        JOptionPane.showMessageDialog(frame, "<HTML>" + ex + "</HTML>");
//        if (ex.getClass().equals(FileNotFoundException.class))
//            fileNotFound(o, ex.toString());
    }

    public static void fileNotFound(Object o, String file) {
        ((JLabel) o).setText("<HTML><color=\"red\">ERR: File not found: <BR>" + file + "</color></HTML>");
    }

    public static void iOException(Object o, String err) {
        ((JLabel) o).setText("<HTML>ERR: IOException: <BR>" + err + "</HTML>");
    }
}
