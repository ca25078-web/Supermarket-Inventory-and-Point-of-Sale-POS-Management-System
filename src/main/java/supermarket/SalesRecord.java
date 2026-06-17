/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;
import java.util.Date;

public class SalesRecord {
    private String transactionID;
    private String productId;
    private int quantity;
    private double totalPrice;
    private Date saleDate;

    public SalesRecord(String transactionID, String productId, int quantity, double totalPrice, Date saleDate) {
        this.transactionID = transactionID;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.saleDate = saleDate;
    }

    public double getTotalPrice() { return totalPrice; }
}