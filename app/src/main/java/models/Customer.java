package models;

public class Customer {
    public final String name;
    public final String number;
    public final String address;
    public final String itemName;
    public final int itemPrice;
    public final int itemNumber;
    public final boolean confirmed;

    public Customer(String name, String number, String address, String itemName, int itemPrice, int itemNumber, boolean confirmed) {
        this.name = name;
        this.number = number;
        this.address = address;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemNumber = itemNumber;
        this.confirmed = confirmed;
    }
}
