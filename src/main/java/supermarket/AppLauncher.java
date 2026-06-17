/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

public class AppLauncher {
    public static void main(String[] args) {
        // By adding 'ui.' before Form, we force it to look at the right file
        // which fixes the "non-static variable this" error.
        java.awt.EventQueue.invokeLater(() -> {
            new Form().setVisible(true);
        });
    }
}