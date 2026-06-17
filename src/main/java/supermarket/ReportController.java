/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class ReportController {
       private SalesData salesData = new SalesData();

    public ReportSummary generateReport(Date startDate, Date endDate) {
        List<SalesRecord> records = salesData.fetchSales(startDate, endDate);
        double totalRevenue = 0;
        for (SalesRecord r : records) {
            totalRevenue += r.getTotalPrice();
        }
        List<String> topProducts = new ArrayList<>();
        topProducts.add("Laptop Pro");
        
        return new ReportSummary(totalRevenue, records.size(), topProducts);
    }

    public String exportData(String format) {
        return "SUCCESS: Report exported to sales_report." + format.toLowerCase();
    }
    
    public String getReportData() {
    // Replace this logic with whatever your current controller does
    // to format your report data into a readable String
    return "Report Content Goes Here..."; 
}
}
