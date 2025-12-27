import view.MainFrame;
import javax.swing.*;
import java.awt.*;

/**
 * NepseInsiderApp - Main entry point for the application
 * NepseInsider Stock Market Platform
 * 
 * Features:
 * - MVC Architecture
 * - Admin dashboard with full management capabilities
 * - User dashboard for regular users
 * - Stock tracking with search and sort algorithms
 * - Data structures: Stack, Queue, ArrayList, LinkedList, HashMap
 * 
 * Admin Login: username: admin, password: admin
 * User Login: username: khrishman, password: khadka
 */
public class NepseInsiderApp {
    
    public static void main(String[] args) {
        // Set system properties for better rendering
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
        // Run on EDT
        SwingUtilities.invokeLater(() -> {
            try {
                // Use system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                // Set custom UI properties for dark theme
                UIManager.put("Panel.background", new Color(15, 23, 42));

                // OptionPane (dialogs) - use light theme for readability
                UIManager.put("OptionPane.background", new Color(240, 240, 240));
                UIManager.put("OptionPane.messageForeground", Color.BLACK);
                UIManager.put("Panel.background", new Color(240, 240, 240));

                // TextField in dialogs - white bg, black text
                UIManager.put("TextField.background", Color.WHITE);
                UIManager.put("TextField.foreground", Color.BLACK);
                UIManager.put("TextField.caretForeground", Color.BLACK);

                // ComboBox - white bg, black text
                UIManager.put("ComboBox.background", Color.WHITE);
                UIManager.put("ComboBox.foreground", Color.BLACK);

                // Buttons in dialogs - white bg, black text
                UIManager.put("Button.background", Color.WHITE);
                UIManager.put("Button.foreground", Color.BLACK);
                UIManager.put("Button.focus", new Color(200, 200, 200));

                // Labels
                UIManager.put("Label.foreground", Color.BLACK);

            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Create and show main frame
            new MainFrame();
        });
    }
}
