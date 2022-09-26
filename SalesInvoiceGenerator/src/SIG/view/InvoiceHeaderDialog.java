package SIG.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InvoiceHeaderDialog extends JDialog {
    private JTextField customerName;
    private JTextField invoiceDate;
    private JLabel customerNameLbl;
    private JLabel invoiceDateLbl;
    private JButton okBtn;
    private JButton cancelBtn;

    public InvoiceHeaderDialog(InvoiceFrame frame) {
        customerNameLbl = new JLabel("Customer Name:");
        customerName = new JTextField(20);
        invoiceDateLbl = new JLabel("Invoice Date:");
        invoiceDate = new JTextField(20);
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        okBtn.setActionCommand("createInvoice");
        cancelBtn.setActionCommand("cancelInvoice");
        okBtn.addActionListener(frame.getActionListener());
        cancelBtn.addActionListener(frame.getActionListener());
        setLayout(new GridLayout(4, 3));
        add(customerNameLbl);
        add(customerName);
        add(invoiceDateLbl);
        add(invoiceDate);
        add(okBtn);
        add(cancelBtn);    
        pack();
        
    }

    public JTextField getCustomerName() {
        return customerName;
    }

    public JTextField getInvoiceDate() {
        return invoiceDate;
    }
    
}
