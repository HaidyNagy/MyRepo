
package SIG.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class InvoiceItemsTable extends AbstractTableModel {

    private ArrayList<InvoiceItem> itemsArray;
    private String[] columns = {"Item Name", "Unit Price", "Quantity", "Item Total"};

    public InvoiceItemsTable(ArrayList<InvoiceItem> itemsArray) {
        this.itemsArray = itemsArray;
    }

    @Override
    public int getRowCount() {
        return itemsArray == null ? 0 : itemsArray.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (itemsArray == null) {
            return "";
        } else {
            InvoiceItem item = itemsArray.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return item.getItem();
                case 1:
                    return item.getPrice();
                case 2:
                    return item.getQuantity();
                case 3:
                    return item.getItemTotal();
                default:
                    return "";
            }
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

}

