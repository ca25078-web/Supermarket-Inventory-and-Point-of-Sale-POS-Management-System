/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

public class ProfitMetrics {
    private double totalRevenue;
    private double totalCost;
    private double netProfit;

    public ProfitMetrics(double totalRevenue, double totalCost) {
        this.totalRevenue = totalRevenue;
        this.totalCost = totalCost;
        this.netProfit = totalRevenue - totalCost;
    }

    public double getTotalRevenue() { return totalRevenue; }
    public double getTotalCost() { return totalCost; }
    public double getNetProfit() { return netProfit; }
}