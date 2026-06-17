/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;
import java.util.Scanner;

public class MainDashboardUI_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

       
        ConfigUI configUI = new ConfigUI();
        ReportUI reportUI = new ReportUI();
        ProfitUI profitUI = new ProfitUI();

        while (running) {
            System.out.println("\n=== SMART INVENTORY & SALES SYSTEM ===");
            System.out.println("Module 4: Reporting & Analytics Manager");
            System.out.println("1. Monitor Stock Levels");
            System.out.println("2. Configure Low-Stock Thresholds");
            System.out.println("3. Generate Sales Report");
            System.out.println("4. View Profit Analysis");
            System.out.println("5. Exit System");
            System.out.print("Select an option: ");
            
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("Stock Monitor has been upgraded to the GUI! Please use Form.java.");
                    break;
                case "2":
                    configUI.displayConfigMenu();
                    break;
                case "3":
                    reportUI.displaySalesReport();
                    break;
                case "4":
                    profitUI.displayProfitAnalysis();
                    break;
                case "5":
                    running = false;
                    System.out.println("Exiting System...");
                    break;
                default:
                    System.out.println("Invalid Option. Please enter a number between 1 and 5.");
            }
        }
        scanner.close();
    }
}