package SIG.controller;


import java.util.Date;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import SIG.view.InvoiceFrame;
import SIG.model.InvoiceHeader;
import SIG.model.InvoiceItem;
import SIG.model.InvoiceHeaderTable;
import SIG.view.InvoiceHeaderDialog;
import SIG.model.InvoiceItemsTable;
import SIG.view.InvoiceItemDialog;

public class InvoiceActionListener implements ActionListener {

    private InvoiceFrame frame;
    private InvoiceHeaderDialog headerDialog;
    private InvoiceItemDialog lineDialog;

    public InvoiceActionListener(InvoiceFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Save Files":
                saveFiles();
                break;

            case "Load Files":
                loadFiles();
                break;

            case "New Invoice":
                createNewInvoice();
                break;

            case "Delete Invoice":
                deleteInvoice();
                break;

            case "New Item":
                createItem();
                break;

            case "Delete Item":
                deleteItem();
                break;

            case "createInvoice":
                newInvoiceDialogOK();
                break;

            case "cancelInvoice":
                newInvoiceDialogCancel();
                break;

            case "newLineCancel":
                newLineDialogCancel();
                break;

            case "newLineOK":
                newLineDialogOK();
                break;
        }
    }

    private void loadFiles() {
        JFileChooser FC = new JFileChooser();
        try {
            int output = FC.showOpenDialog(frame);
            if (output == JFileChooser.APPROVE_OPTION) {
                File hFile = FC.getSelectedFile();
                Path hPath = Paths.get(hFile.getAbsolutePath());
                List<String> hLines = Files.readAllLines(hPath);
                ArrayList<InvoiceHeader> invoiceHeaders = new ArrayList<>();
                for (String hLine : hLines) {
                    String[] array = hLine.split(",");
                    String string1 = array[0];
                    String string2 = array[1];
                    String string3 = array[2];
                    int code = Integer.parseInt(string1);
                    Date invoiceDate = InvoiceFrame.dateFormat.parse(string2);
                    InvoiceHeader header = new InvoiceHeader(code, string3, invoiceDate);
                    invoiceHeaders.add(header);
                }
                frame.setInvoicesArray(invoiceHeaders);

                output = FC.showOpenDialog(frame);
                if (output == JFileChooser.APPROVE_OPTION) {
                    File lFile = FC.getSelectedFile();
                    Path linePath = Paths.get(lFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath);
                    for (String lineLine : lineLines) {
                        String[] array = lineLine.split(",");
                        String string1 = array[0];
                        String string2 = array[1];
                        String string3 = array[2];
                        String string4 = array[3];
                        int invoiceID = Integer.parseInt(string1);
                        double price = Double.parseDouble(string3);
                        int quantity = Integer.parseInt(string4);
                        InvoiceHeader invoice = frame.getInvoiceObject(invoiceID);
                        InvoiceItem line = new InvoiceItem(string2, price, quantity, invoice);
                        invoice.getLines().add(line);
                    }
                }
                InvoiceHeaderTable headerTableModel = new InvoiceHeaderTable(invoiceHeaders);
                frame.setHeaderTable(headerTableModel);
                frame.getInvoiceHeaderTbl().setModel(headerTableModel);
            }

        } catch (IOException | ParseException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFiles() {
        ArrayList<InvoiceHeader> invoicesArray = frame.getInvoicesArray();
        JFileChooser fc = new JFileChooser();
        String headers = "";
        String lines = "";
        try {
            int result = fc.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File hFile = fc.getSelectedFile();
                FileWriter hfilewriter = new FileWriter(hFile);
                for (InvoiceHeader invoice : invoicesArray) {
                    headers += invoice.toString();
                    headers += "\n";
                    for (InvoiceItem line : invoice.getLines()) {
                        lines += line.toString();
                        lines += "\n";
                    }
                }
                headers = headers.substring(0, headers.length() - 1);
                lines = lines.substring(0, lines.length() - 1);
                result = fc.showSaveDialog(frame);
                File lineFile = fc.getSelectedFile();
                FileWriter lfilewriter = new FileWriter(lineFile);
                hfilewriter.write(headers);
                lfilewriter.write(lines);
                hfilewriter.close();
                lfilewriter.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createNewInvoice() {
        headerDialog = new InvoiceHeaderDialog(frame);
        headerDialog.setVisible(true);
    }

    private void newInvoiceDialogOK() {
        headerDialog.setVisible(false);

        String custName = headerDialog.getCustomerName().getText();
        String str = headerDialog.getInvoiceDate().getText();
        Date d = new Date();
        try {
            d = InvoiceFrame.dateFormat.parse(str);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, "Cannot parse date, resetting to today.", "Invalid date format", JOptionPane.ERROR_MESSAGE);
        }

        int invoiceNumber = 0;
        for (InvoiceHeader invoice : frame.getInvoicesArray()) {
            if (invoice.getNumber() > invoiceNumber) {
                invoiceNumber = invoice.getNumber();
            }
        }
        invoiceNumber++;
        InvoiceHeader newInv = new InvoiceHeader(invoiceNumber, custName, d);
        frame.getInvoicesArray().add(newInv);
        frame.getHeaderTable().fireTableDataChanged();
        headerDialog.dispose();
        headerDialog = null;
    }

    private void newInvoiceDialogCancel() {
        headerDialog.setVisible(false);
        headerDialog.dispose();
        headerDialog = null;
    }

    private void deleteInvoice() {
        int selectedInvoiceIndex = frame.getInvoiceHeaderTbl().getSelectedRow();
        if (selectedInvoiceIndex != -1) {
            frame.getInvoicesArray().remove(selectedInvoiceIndex);
            frame.getHeaderTable().fireTableDataChanged();

            frame.getInvoiceItemTbl().setModel(new InvoiceItemsTable(null));
            frame.setItemsArray(null);
            frame.getCustomerNameLbl().setText("");
            frame.getInvoiceNumberLbl().setText("");
            frame.getInvoiceTotalIbl().setText("");
            frame.getInvoiceDateLbl().setText("");
        }
    }

    private void createItem() {
        lineDialog = new InvoiceItemDialog(frame);
        lineDialog.setVisible(true);
    }

    private void newLineDialogOK() {
        lineDialog.setVisible(false);
        String name = lineDialog.getItemName().getText();
        String str1 = lineDialog.getItemQuantity().getText();
        String str2 = lineDialog.getItemPrice().getText();
        int count = 1;
        double price = 1;
        try {
            count = Integer.parseInt(str1);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Cannot convert quantity number", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }

        try {
            price = Double.parseDouble(str2);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Price cannot be converted", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }
        int selectedInvHeader = frame.getInvoiceHeaderTbl().getSelectedRow();
        if (selectedInvHeader != -1) {
            InvoiceHeader invHeader = frame.getInvoicesArray().get(selectedInvHeader);
            InvoiceItem line = new InvoiceItem(name, price, count, invHeader);
            //invHeader.getLines().add(line);
            frame.getitemsArray().add(line);
            InvoiceItemsTable lineTableModel = (InvoiceItemsTable) frame.getInvoiceItemTbl().getModel();
            lineTableModel.fireTableDataChanged();
            frame.getHeaderTable().fireTableDataChanged();
        }
        frame.getInvoiceHeaderTbl().setRowSelectionInterval(selectedInvHeader, selectedInvHeader);
        lineDialog.dispose();
        lineDialog = null;
    }

    private void newLineDialogCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

    private void deleteItem() {
        int selectedLineIndex = frame.getInvoiceItemTbl().getSelectedRow();
        int selectedInvoiceIndex = frame.getInvoiceHeaderTbl().getSelectedRow();
        if (selectedLineIndex != -1) {
            frame.getitemsArray().remove(selectedLineIndex);
            InvoiceItemsTable lineTableModel = (InvoiceItemsTable) frame.getInvoiceItemTbl().getModel();
            lineTableModel.fireTableDataChanged();
            frame.getInvoiceTotalIbl().setText("" + frame.getInvoicesArray().get(selectedInvoiceIndex).getInvoiceTotal());
            frame.getHeaderTable().fireTableDataChanged();
            frame.getInvoiceHeaderTbl().setRowSelectionInterval(selectedInvoiceIndex, selectedInvoiceIndex);
        }
    }

}
