package ppdm.gui;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Oct 14, 2008
 */
import java.awt.Component;
import javax.swing.*;
import javax.swing.table.*;

/**
 * This class acts as the Table Column model. This class contains methods to resize the sizes of the
 * table columns according to the length of the field names.
 *
  */
public class CustomTableColumnModel extends DefaultTableColumnModel {

    private String[] header;
    private JTable table;
    private int[] colWidth;

    public CustomTableColumnModel(){
        
    }
    /**
     * Constructs a CustomTableColumnModel with the field names as parameters.
     * @param header - Field names.
     */
    public CustomTableColumnModel(JTable table, String[] header) {
        this.table = table;
        this.header = header;
        colWidth = new int[header.length];
        calculateWidths();
    }

    /**
     * Adds a column to the table. Also, resizes the column according to the field names.
     */
    public void addColumn(TableColumn tc) {
        int index = tc.getModelIndex();
        tc.setPreferredWidth(colWidth[index]);
        super.addColumn(tc);
    }

    private void calculateWidths() {
        int totalWidth = 0;
        int temp = 0;
        for (int i = 0; i < header.length; i++) {
            totalWidth += header[i].length();
        }
        int tableWidth = table.getPreferredScrollableViewportSize().width;
        double ratio = tableWidth / totalWidth;
        for (int i = 0; i < header.length; i++) {
            if (i == (header.length - 1)) {
                colWidth[i] = tableWidth - temp;
            } else {
                colWidth[i] = (int) Math.round(ratio * header[i].length());
                temp += colWidth[i];
            }
        }
    }

    public static void calcColumnWidths(JTable table) {
        JTableHeader header = table.getTableHeader();

        TableCellRenderer defaultHeaderRenderer = null;

        if (header != null) {
            defaultHeaderRenderer = header.getDefaultRenderer();
        }
        TableColumnModel columns = table.getColumnModel();
        TableModel data = table.getModel();

        int margin = columns.getColumnMargin(); // only JDK1.3

        int rowCount = data.getRowCount();

        int totalWidth = 0;

        for (int i = columns.getColumnCount() - 1; i >= 0; --i) {
            TableColumn column = columns.getColumn(i);

            int columnIndex = column.getModelIndex();

            int width = -1;

            TableCellRenderer h = column.getHeaderRenderer();

            if (h == null) {
                h = defaultHeaderRenderer;
            }
            if (h != null) // Not explicitly impossible
            {
                Component c = h.getTableCellRendererComponent(table, column.getHeaderValue(),
                        false, false, -1, i);

                width = c.getPreferredSize().width;
            }

            for (int row = rowCount - 1; row >= 0; --row) {
                TableCellRenderer r = table.getCellRenderer(row, i);

                Component c = r.getTableCellRendererComponent(table,
                        data.getValueAt(row, columnIndex),
                        false, false, row, i);

                width = Math.max(width, c.getPreferredSize().width);
            }

            if (width >= 0) {
                column.setPreferredWidth(width + margin); // <1.3: without margin
            } else; // ???

            totalWidth += column.getPreferredWidth();
        }
    }
}