package SIG.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InvoiceItemDialog extends JDialog{
    private JTextField itemName;
    private JTextField itemQuantity;
    private JTextField itemPrice;
    private JLabel itemNameLbl;
    private JLabel itemQuantityLbl;
    private JLabel itemPriceLbl;
    private JButton okBtn;
    private JButton cancelBtn;
    
    public InvoiceItemDialog(InvoiceFrame frame) {
        itemName = new JTextField(25);
        itemNameLbl = new JLabel("Item Name");
        
        itemQuantity = new JTextField(25);
        itemQuantityLbl = new JLabel("Item Quantity");
        
        itemPrice = new JTextField(25);
        itemPriceLbl = new JLabel("Item Price");
        
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        
        okBtn.setActionCommand("newLineOK");
        cancelBtn.setActionCommand("newLineCancel");
        
        okBtn.addActionListener(frame.getActionListener());
        cancelBtn.addActionListener(frame.getActionListener());
        setLayout(new GridLayout(5, 3));
        
        add(itemNameLbl);
        add(itemName);
        add(itemQuantityLbl);
        add(itemQuantity);
        add(itemPriceLbl);
        add(itemPrice);
        add(okBtn);
        add(cancelBtn);
        
        pack();
    }

    public JTextField getItemName() {
        return itemName;
    }

    public JTextField getItemQuantity() {
        return itemQuantity;
    }

    public JTextField getItemPrice() {
        return itemPrice;
    }
}
