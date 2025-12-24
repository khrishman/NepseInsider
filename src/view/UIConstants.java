package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * UIConstants - Centralized UI styling constants
 * Dark blue theme with white text and green accents
 */
public class UIConstants {
    
    // ==================== Color Palette ====================
    
    // Primary Colors (Dark Blue Theme)
    public static final Color BACKGROUND_DARK = new Color(15, 23, 42);        // Dark navy blue
    public static final Color BACKGROUND_SECONDARY = new Color(30, 41, 59);   // Slate blue
    public static final Color BACKGROUND_TERTIARY = new Color(51, 65, 85);    // Lighter slate
    public static final Color BACKGROUND_CARD = new Color(30, 41, 59);        // Card backgrounds
    public static final Color BACKGROUND_SIDEBAR = new Color(15, 23, 42);     // Sidebar
    
    // Accent Colors (Green Theme)
    public static final Color PRIMARY_GREEN = new Color(34, 197, 94);         // Emerald green
    public static final Color PRIMARY_GREEN_DARK = new Color(22, 163, 74);    // Darker green
    public static final Color PRIMARY_GREEN_LIGHT = new Color(74, 222, 128);  // Lighter green
    public static final Color ACCENT_CYAN = new Color(6, 182, 212);           // Cyan accent
    
    // Golden/Yellow
    public static final Color GOLD = new Color(250, 204, 21);                 // Amber/Gold
    public static final Color GOLD_LIGHT = new Color(253, 224, 71);           // Light gold
    
    // Text Colors - Using #fff as requested
    public static final Color TEXT_PRIMARY = new Color(255, 255, 255);        // Pure white #fff
    public static final Color TEXT_SECONDARY = new Color(203, 213, 225);      // Light gray
    public static final Color TEXT_MUTED = new Color(148, 163, 184);          // Muted
    public static final Color TEXT_DARK = new Color(30, 41, 59);              // Dark text for light bg
    
    // Status Colors
    public static final Color SUCCESS = new Color(34, 197, 94);               // Green
    public static final Color DANGER = new Color(239, 68, 68);                // Red
    public static final Color WARNING = new Color(245, 158, 11);              // Orange
    public static final Color INFO = new Color(59, 130, 246);                 // Blue
    
    // Border Colors
    public static final Color BORDER_COLOR = new Color(51, 65, 85);
    public static final Color BORDER_LIGHT = new Color(71, 85, 105);
    
    // ==================== Fonts ====================
    
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 36);
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font FONT_SUBHEADING = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BODY_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_TABLE_HEADER = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_TABLE = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_LOGO = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_MENU = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_STAT_VALUE = new Font("Segoe UI", Font.BOLD, 32);
    
    // ==================== Dimensions ====================
    
    public static final int PADDING_SMALL = 8;
    public static final int PADDING_MEDIUM = 16;
    public static final int PADDING_LARGE = 24;
    public static final int PADDING_XLARGE = 32;
    
    public static final int BUTTON_HEIGHT = 42;
    public static final int FIELD_HEIGHT = 42;
    public static final int NAVBAR_HEIGHT = 60;
    public static final int SIDEBAR_WIDTH = 250;
    
    // ==================== Factory Methods ====================
    
    public static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(PRIMARY_GREEN);
        button.setForeground(Color.WHITE);
        button.setFont(FONT_BUTTON);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(140, BUTTON_HEIGHT));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalColor = PRIMARY_GREEN;
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(PRIMARY_GREEN_LIGHT);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(originalColor);
            }
        });
        
        return button;
    }
    
    public static JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(BACKGROUND_TERTIARY);
        button.setForeground(TEXT_PRIMARY);
        button.setFont(FONT_BUTTON);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(BORDER_LIGHT, 1));
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, BUTTON_HEIGHT));
        
        return button;
    }
    
    public static JButton createDangerButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(DANGER);
        button.setForeground(Color.WHITE);
        button.setFont(FONT_BUTTON);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, BUTTON_HEIGHT));
        
        return button;
    }
    
    public static JTextField createTextField() {
        JTextField field = new JTextField();
        field.setBackground(BACKGROUND_TERTIARY);
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setFont(FONT_BODY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        field.setPreferredSize(new Dimension(250, FIELD_HEIGHT));
        return field;
    }
    
    public static JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setBackground(BACKGROUND_TERTIARY);
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setFont(FONT_BODY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        field.setPreferredSize(new Dimension(250, FIELD_HEIGHT));
        return field;
    }
    
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_PRIMARY);
        label.setFont(FONT_BODY);
        return label;
    }
    
    public static JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_DARK);
        return panel;
    }
    
    public static void styleTable(JTable table) {
        table.setBackground(BACKGROUND_CARD);
        table.setForeground(TEXT_PRIMARY);
        table.setFont(FONT_TABLE);
        table.setGridColor(BORDER_COLOR);
        table.setSelectionBackground(PRIMARY_GREEN);
        table.setSelectionForeground(Color.WHITE);
        table.setRowHeight(50);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setBorder(null);
        
        // Style header
        JTableHeader header = table.getTableHeader();
        header.setBackground(BACKGROUND_TERTIARY);
        header.setForeground(GOLD);
        header.setFont(FONT_TABLE_HEADER);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY_GREEN));
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
    }
    
    public static JScrollPane createScrollPane(Component view) {
        JScrollPane scrollPane = new JScrollPane(view);
        scrollPane.setBackground(BACKGROUND_DARK);
        scrollPane.getViewport().setBackground(BACKGROUND_DARK);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        scrollPane.getVerticalScrollBar().setBackground(BACKGROUND_SECONDARY);
        scrollPane.getHorizontalScrollBar().setBackground(BACKGROUND_SECONDARY);
        return scrollPane;
    }
    
    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void showSuccess(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static boolean showConfirm(Component parent, String message) {
        int result = JOptionPane.showConfirmDialog(parent, message, "Confirm", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }
    
    public static String formatCurrency(double amount) {
        return String.format("Rs. %,.2f", amount);
    }
    
    public static String formatPercent(double percent) {
        return String.format("%.2f%%", percent);
    }
    
    public static String formatLargeNumber(double number) {
        if (number >= 10000000) {
            return String.format("%.2f Cr", number / 10000000.0);
        } else if (number >= 100000) {
            return String.format("%.2f L", number / 100000.0);
        } else if (number >= 1000) {
            return String.format("%.1f K", number / 1000.0);
        }
        return String.format("%.0f", number);
    }
}
