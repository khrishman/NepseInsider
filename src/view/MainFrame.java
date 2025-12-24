package view;

import controller.UserController;

import javax.swing.*;
import java.awt.*;

/**
 * MainFrame - Main application window with navigation
 */
public class MainFrame extends JFrame {
    
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private UserController userController;
    
    public static final String HOME = "HOME";
    public static final String LOGIN = "LOGIN";
    public static final String MARKETS = "MARKETS";
    public static final String STOCKS = "STOCKS";
    public static final String PORTFOLIO = "PORTFOLIO";
    public static final String ADMIN = "ADMIN";
    public static final String USER_DASHBOARD = "USER_DASHBOARD";
    
    private HomePanel homePanel;
    private LoginPanel loginPanel;
    private MarketsPanel marketsPanel;
    private StocksPanel stocksPanel;
    private PortfolioPanel portfolioPanel;
    private AdminDashboard adminDashboard;
    private UserDashboard userDashboard;
    
    public MainFrame() {
        userController = UserController.getInstance();
        
        setTitle("NepseInsider - Stock Market Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1280, 800));
        setLocationRelativeTo(null);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        initComponents();
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
    
    private void initComponents() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(UIConstants.BACKGROUND_DARK);
        
        // Initialize all panels
        homePanel = new HomePanel(this);
        loginPanel = new LoginPanel(this);
        marketsPanel = new MarketsPanel(this);
        stocksPanel = new StocksPanel(this);
        portfolioPanel = new PortfolioPanel(this);
        adminDashboard = new AdminDashboard(this);
        userDashboard = new UserDashboard(this);
        
        // Add panels
        mainPanel.add(homePanel, HOME);
        mainPanel.add(loginPanel, LOGIN);
        mainPanel.add(marketsPanel, MARKETS);
        mainPanel.add(stocksPanel, STOCKS);
        mainPanel.add(portfolioPanel, PORTFOLIO);
        mainPanel.add(adminDashboard, ADMIN);
        mainPanel.add(userDashboard, USER_DASHBOARD);
        
        add(mainPanel);
        
        showHome();
    }
    
    public void showHome() {
        homePanel.refresh();
        cardLayout.show(mainPanel, HOME);
    }
    
    public void showLogin() {
        loginPanel.reset();
        cardLayout.show(mainPanel, LOGIN);
    }
    
    public void showMarkets() {
        marketsPanel.refresh();
        cardLayout.show(mainPanel, MARKETS);
    }
    
    public void showStocks() {
        stocksPanel.refresh();
        cardLayout.show(mainPanel, STOCKS);
    }
    
    public void showPortfolio() {
        if (!userController.isLoggedIn()) {
            showLogin();
            return;
        }
        portfolioPanel.refresh();
        cardLayout.show(mainPanel, PORTFOLIO);
    }
    
    public void showAdminDashboard() {
        // Refresh admin dashboard
        mainPanel.remove(adminDashboard);
        adminDashboard = new AdminDashboard(this);
        mainPanel.add(adminDashboard, ADMIN);
        cardLayout.show(mainPanel, ADMIN);
    }
    
    public void showUserDashboard() {
        mainPanel.remove(userDashboard);
        userDashboard = new UserDashboard(this);
        mainPanel.add(userDashboard, USER_DASHBOARD);
        cardLayout.show(mainPanel, USER_DASHBOARD);
    }
    
    public void logout() {
        userController.logout();
        showHome();
    }
}
