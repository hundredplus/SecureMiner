
package ppdm.test;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Oct 16, 2008
 */
public class PPDMTableModel extends DefaultTableModel {

    public PPDMTableModel() {
        super();
    }

    public PPDMTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    public PPDMTableModel(Vector data, Vector columnNames) {
        super(data, columnNames);
    }

    public PPDMTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public PPDMTableModel(Vector columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public PPDMTableModel(int rowCount, int columnCount) {
        super(rowCount, columnCount);
    }
    
    public void addListener(){
        
    }
}
