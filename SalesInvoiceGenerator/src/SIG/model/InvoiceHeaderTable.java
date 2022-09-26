package SIG.model;

import SIG.view.InvoiceFrame;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class InvoiceHeaderTable extends AbstractTableModel {

    private ArrayList<InvoiceHeader> invoicesArray;
    private String[] columns = {"Invoice Number", "Invoice Date", "Customer Name", "Invoice Total"};
    
    public InvoiceHeaderTable(ArrayList<InvoiceHeader> invoicesArray) {
        this.invoicesArray = invoicesArray;
    }

    @Override
    public int getRowCount() {
        return invoicesArray.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeader inv = invoicesArray.get(rowIndex);
        switch (columnIndex) {
            case 0: return inv.getNumber();
            case 1: return InvoiceFrame.dateFormat.format(inv.getInvoiceDate());
            case 2: return inv.getCustomer();
            case 3: return inv.getInvoiceTotal();
        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}

