
package SIG.controller;

import SIG.model.InvoiceHeader;
import SIG.model.InvoiceItem;
import SIG.model.InvoiceItemsTable;
import SIG.view.InvoiceFrame;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TableSelectionListener implements ListSelectionListener {

    private InvoiceFrame frame;

    public TableSelectionListener(InvoiceFrame frame) {
        this.frame = frame;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedInvoiceIndex = frame.getInvoiceHeaderTbl().getSelectedRow();
        if (selectedInvoiceIndex != -1) {
            InvoiceHeader selectedInvoice = frame.getInvoicesArray().get(selectedInvoiceIndex);
            ArrayList<InvoiceItem> items = selectedInvoice.getLines();
            InvoiceItemsTable itemTableModel = new InvoiceItemsTable(items);
            frame.setItemsArray(items);
            frame.getInvoiceItemTbl().setModel(itemTableModel);
            frame.getCustomerNameLbl().setText(selectedInvoice.getCustomer());
           frame.getInvoiceNumberLbl().setText(""+ selectedInvoice.getNumber());
            frame.getInvoiceTotalIbl().setText(""+selectedInvoice.getInvoiceTotal());
            frame.getInvoiceDateLbl().setText(InvoiceFrame.dateFormat.format(selectedInvoice.getInvoiceDate()));
        }
    }

}

