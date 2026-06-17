/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalesData {
    public List<SalesRecord> fetchSales(Date startDate, Date endDate) {
        List<SalesRecord> mockSales = new ArrayList<>();
        // Creating mock database transactions
        mockSales.add(new SalesRecord("TXN001", "ITM001", 2, 2400.0, new Date()));
        mockSales.add(new SalesRecord("TXN002", "ITM002", 5, 75.0, new Date()));
        return mockSales;
    }
}