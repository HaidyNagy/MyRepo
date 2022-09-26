package SIG.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InvoiceHeader {
    private int number;
    private String customer;
    private Date invoiceDate;
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    private ArrayList<InvoiceItem> lines;

    public InvoiceHeader(int number, String customer, Date invoiceDate) {
        this.number = number;
        this.customer = customer;
        this.invoiceDate = invoiceDate;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public ArrayList<InvoiceItem> getLines() {
        if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public void setLines(ArrayList<InvoiceItem> lines) {
        this.lines = lines;
    }
    
    public double getInvoiceTotal() {
        double total = 0.0;
        
        for (int i = 0; i < getLines().size(); i++) {
            total += getLines().get(i).getItemTotal();
        }
        
        return total;
    }

    @Override
    public String toString() {
        return number + "," + df.format(invoiceDate) + "," + customer;
    }
    
}

