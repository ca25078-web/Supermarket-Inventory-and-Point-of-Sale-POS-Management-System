/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;
import java.util.List;

public class ReportSummary {
    private double totalRevenue;
    private int unitsSold;
    private List<String> topProducts;

    public ReportSummary(double totalRevenue, int unitsSold, List<String> topProducts) {
        this.totalRevenue = totalRevenue;
        this.unitsSold = unitsSold;
        this.topProducts = topProducts;
    }

    public double getTotalRevenue() { return totalRevenue; }
    public int getUnitsSold() { return unitsSold; }
    public List<String> getTopProducts() { return topProducts; }
}