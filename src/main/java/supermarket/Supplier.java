/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

/**
 *
 * @author Edwin
 */
public class Supplier {
    private int supplierID;
    private String supplierName;
    private String contactInfo;

    public Supplier(int supplierID, String supplierName, String contactInfo) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.contactInfo = contactInfo;
    }

    public int getSupplierID() { return supplierID; }
    public String getSupplierName() { return supplierName; }
    public String getContactInfo() { return contactInfo; }
}
