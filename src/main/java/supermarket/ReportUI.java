/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

import java.util.Date;

public class ReportUI {
    private ReportController controller = new ReportController();

    public void displaySalesReport() {
        System.out.println("\n--- GENERATING SALES REPORT ---");
        // Simulating user date input parameters from sequence diagram
        ReportSummary summary = controller.generateReport(new Date(), new Date());
        
        System.out.println("Total Revenue Generated: $" + summary.getTotalRevenue());
        System.out.println("Total Transactions Processed: " + summary.getUnitsSold());
        System.out.println("Top Performing Product: " + summary.getTopProducts().get(0));
        
        // Simulating the optional export use case flow
        System.out.println(controller.exportData("PDF"));
    }
}