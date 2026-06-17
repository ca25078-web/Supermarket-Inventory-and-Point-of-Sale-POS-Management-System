/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;


public class ProfitUI {
    private ProfitController controller = new ProfitController();

    public void displayProfitAnalysis() {
        System.out.println("\n--- PROFIT ANALYSIS REPORT ---");
        ProfitMetrics metrics = controller.requestProfitData();
        
        System.out.println("Total Sales Revenue: $" + metrics.getTotalRevenue());
        System.out.println("Total Cost of Goods: $" + metrics.getTotalCost());
        System.out.println("Net Profit Margin:   $" + metrics.getNetProfit());
    }
}