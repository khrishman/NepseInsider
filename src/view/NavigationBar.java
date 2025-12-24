package view;

import controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * NavigationBar - Reusable navigation bar component
 */
public class NavigationBar extends JPanel {
    
    private MainFrame mainFrame;
    private UserController userController;
    private String currentPage;
    
    public NavigationBar(MainFrame mainFrame, String currentPage) {
        this.mainFrame = mainFrame;
        this.userController = UserController.getInstance();
        this.currentPage = currentPage;
        
        setBackground(UIConstants.BACKGROUND_SECONDARY);
        setPreferredSize(new Dimension(0, UIConstants.NAVBAR_HEIGHT));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UIConstants.BORDER_COLOR));
        setLayout(new BorderLayout());
        
        initComponents();
    }
    
    private void initComponents() {
        // Left - Logo
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 12));
        leftPanel.setOpaque(false);
        
        JLabel logoIcon = new JLabel("📈");
        logoIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        
        JLabel logoText = new JLabel("NepseInsider");
        logoText.setForeground(UIConstants.PRIMARY_GREEN);
        logoText.setFont(UIConstants.FONT_LOGO);
        logoText.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showHome();
            }
        });
        
        leftPanel.add(logoIcon);
        leftPanel.add(logoText);
        
        // Center - Navigation links
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        centerPanel.setOpaque(false);
        
        centerPanel.add(createNavLink("Home", MainFrame.HOME));
        centerPanel.add(createNavLink("Markets", MainFrame.MARKETS));
        centerPanel.add(createNavLink("Stocks", MainFrame.STOCKS));
        centerPanel.add(createNavLink("Portfolio", MainFrame.PORTFOLIO));
        centerPanel.add(createNavLink("About", "ABOUT"));
        
        // Right - Login/User info
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        rightPanel.setOpaque(false);
        
        if (userController.isLoggedIn()) {
            JLabel userLabel = new JLabel("Hello, " + userController.getCurrentUser().getUsername());
            userLabel.setForeground(UIConstants.TEXT_PRIMARY);
            userLabel.setFont(UIConstants.FONT_BODY);
            
            JButton dashboardBtn = createNavButton("Dashboard");
            dashboardBtn.addActionListener(e -> {
                if (userController.isAdmin()) {
                    mainFrame.showAdminDashboard();
                } else {
                    mainFrame.showUserDashboard();
                }
            });
            
            JButton logoutBtn = createNavButton("Logout");
            logoutBtn.setBackground(UIConstants.DANGER);
            logoutBtn.addActionListener(e -> mainFrame.logout());
            
            rightPanel.add(userLabel);
            rightPanel.add(dashboardBtn);
            rightPanel.add(logoutBtn);
        } else {
            JButton loginBtn = createNavButton("Login");
            loginBtn.addActionListener(e -> mainFrame.showLogin());
            rightPanel.add(loginBtn);
        }
        
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }
    
    private JLabel createNavLink(String text, String page) {
        JLabel label = new JLabel(text);
        label.setForeground(currentPage.equals(page) ? UIConstants.PRIMARY_GREEN : UIConstants.TEXT_PRIMARY);
        label.setFont(UIConstants.FONT_MENU);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (page) {
                    case MainFrame.HOME: mainFrame.showHome(); break;
                    case MainFrame.MARKETS: mainFrame.showMarkets(); break;
                    case MainFrame.STOCKS: mainFrame.showStocks(); break;
                    case MainFrame.PORTFOLIO: mainFrame.showPortfolio(); break;
                    case "ABOUT": JOptionPane.showMessageDialog(mainFrame, 
                        "NepseInsider v1.0\nStock Market Platform\n\nBuilt with Java Swing", 
                        "About", JOptionPane.INFORMATION_MESSAGE); break;
                }
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(UIConstants.PRIMARY_GREEN);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (!currentPage.equals(page)) {
                    label.setForeground(UIConstants.TEXT_PRIMARY);
                }
            }
        });
        
        return label;
    }
    
    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(UIConstants.PRIMARY_GREEN);
        button.setForeground(Color.WHITE);
        button.setFont(UIConstants.FONT_BUTTON);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 36));
        return button;
    }
}
