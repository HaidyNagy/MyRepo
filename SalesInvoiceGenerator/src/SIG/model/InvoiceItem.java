package SIG.model;

public class InvoiceItem {
    private String item;
    private double price;
    private int quantity;
    private InvoiceHeader header;

    public InvoiceItem() {
    }

    public InvoiceItem(String item, double price, int quantity, InvoiceHeader header) {
        this.item = item;
        this.price = price;
        this.quantity = quantity;
        this.header = header;
    }

    public InvoiceHeader getHeader() {
        return header;
    }

    public void setHeader(InvoiceHeader header) {
        this.header = header;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setCount(int quantity) {
        this.quantity = quantity;
    }
    
    public double getItemTotal() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return header.getNumber() + "," + item + "," + price + "," + quantity;
    }

    
    
}
