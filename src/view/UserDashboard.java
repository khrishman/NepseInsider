package view;

import controller.UserController;
import controller.StockController;
import model.User;
import model.Stock;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.Cursor;
/**
 * UserDashboard - Dashboard for regular users
 */
public class UserDashboard extends JPanel {
    
    private MainFrame mainFrame;
    private UserController userController;
    private StockController stockController;
    
    public UserDashboard(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = UserController.getInstance();
        this.stockController = StockController.getInstance();
        
        setBackground(UIConstants.BACKGROUND_DARK);
        setLayout(new BorderLayout());
        
        initComponents();
    }
    
    private void initComponents() {
        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(UIConstants.BACKGROUND_SECONDARY);
        topBar.setPreferredSize(new Dimension(0, 60));
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UIConstants.BORDER_COLOR));
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        leftPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("User Dashboard");
        titleLabel.setForeground(UIConstants.GOLD);
        titleLabel.setFont(UIConstants.FONT_SUBHEADING);
        leftPanel.add(titleLabel);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        rightPanel.setOpaque(false);
        
        User user = userController.getCurrentUser();
        JLabel userLabel = new JLabel("Welcome, " + (user != null ? user.getFullName() : "User"));
        userLabel.setForeground(UIConstants.TEXT_PRIMARY);
        userLabel.setFont(UIConstants.FONT_BODY);

        JButton homeBtn = new JButton("Home");
        homeBtn.setBackground(Color.WHITE);
        homeBtn.setForeground(Color.BLACK);
        homeBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        homeBtn.setFocusPainted(false);
        homeBtn.setBorderPainted(false);
        homeBtn.setOpaque(true);
        homeBtn.setContentAreaFilled(true);
        homeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeBtn.setPreferredSize(new Dimension(80, 36));
        homeBtn.addActionListener(e -> mainFrame.showHome());
        
        JButton logoutBtn = UIConstants.createDangerButton("Logout");
        logoutBtn.setPreferredSize(new Dimension(80, 36));
        logoutBtn.addActionListener(e -> mainFrame.logout());
        
        rightPanel.add(userLabel);
        rightPanel.add(homeBtn);
        rightPanel.add(logoutBtn);
        
        topBar.add(leftPanel, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);
        
        add(topBar, BorderLayout.NORTH);
        
        // Main content
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UIConstants.BACKGROUND_DARK);
        content.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        // Welcome section
        JLabel welcomeLabel = new JLabel("Welcome back, " + (user != null ? user.getFullName() : "User") + "!");
        welcomeLabel.setForeground(UIConstants.TEXT_PRIMARY);
        welcomeLabel.setFont(UIConstants.FONT_HEADING);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(welcomeLabel);
        content.add(Box.createVerticalStrut(30));
        
        // Quick stats
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        statsPanel.setOpaque(false);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        double balance = user != null ? user.getBalance() : 0;
        statsPanel.add(createStatCard("Account Balance", UIConstants.formatCurrency(balance), UIConstants.PRIMARY_GREEN));
        statsPanel.add(createStatCard("Portfolio Value", UIConstants.formatCurrency(82500), UIConstants.INFO));
        statsPanel.add(createStatCard("Today's P/L", "+Rs. 1,250", UIConstants.SUCCESS));
        statsPanel.add(createStatCard("Holdings", "3 Stocks", UIConstants.GOLD));
        
        content.add(statsPanel);
        content.add(Box.createVerticalStrut(30));
        
        // Quick actions
        JLabel actionsTitle = new JLabel("Quick Actions");
        actionsTitle.setForeground(UIConstants.TEXT_PRIMARY);
        actionsTitle.setFont(UIConstants.FONT_SUBHEADING);
        actionsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(actionsTitle);
        content.add(Box.createVerticalStrut(15));
        
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        actionsPanel.setOpaque(false);
        actionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton viewPortfolio = UIConstants.createPrimaryButton("View Portfolio");
        viewPortfolio.addActionListener(e -> mainFrame.showPortfolio());

        // Browse Stocks button - white bg, black text
        JButton browseStocks = new JButton("Browse Stocks");
        browseStocks.setBackground(Color.WHITE);
        browseStocks.setForeground(Color.BLACK);
        browseStocks.setFont(new Font("Segoe UI", Font.BOLD, 14));
        browseStocks.setFocusPainted(false);
        browseStocks.setBorderPainted(false);
        browseStocks.setOpaque(true);
        browseStocks.setContentAreaFilled(true);
        browseStocks.setCursor(new Cursor(Cursor.HAND_CURSOR));
        browseStocks.setPreferredSize(new Dimension(140, 42));
        browseStocks.addActionListener(e -> mainFrame.showStocks());

        // View Markets button - white bg, black text
        JButton viewMarkets = new JButton("View Markets");
        viewMarkets.setBackground(Color.WHITE);
        viewMarkets.setForeground(Color.BLACK);
        viewMarkets.setFont(new Font("Segoe UI", Font.BOLD, 14));
        viewMarkets.setFocusPainted(false);
        viewMarkets.setBorderPainted(false);
        viewMarkets.setOpaque(true);
        viewMarkets.setContentAreaFilled(true);
        viewMarkets.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewMarkets.setPreferredSize(new Dimension(140, 42));
        viewMarkets.addActionListener(e -> mainFrame.showMarkets());

        actionsPanel.add(viewPortfolio);
        actionsPanel.add(browseStocks);
        actionsPanel.add(viewMarkets);
        
        content.add(actionsPanel);
        content.add(Box.createVerticalStrut(30));
        
        // Market overview
        JLabel marketTitle = new JLabel("Market Overview");
        marketTitle.setForeground(UIConstants.TEXT_PRIMARY);
        marketTitle.setFont(UIConstants.FONT_SUBHEADING);
        marketTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(marketTitle);
        content.add(Box.createVerticalStrut(15));
        
        JPanel marketPanel = new JPanel(new GridLayout(1, 5, 15, 0));
        marketPanel.setOpaque(false);
        marketPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        marketPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        List<Stock> topStocks = stockController.getTopGainers(5);
        for (Stock stock : topStocks) {
            marketPanel.add(createStockCard(stock));
        }
        
        content.add(marketPanel);
        
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBackground(UIConstants.BACKGROUND_DARK);
        scrollPane.getViewport().setBackground(UIConstants.BACKGROUND_DARK);
        scrollPane.setBorder(null);
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createStatCard(String label, String value, Color accent) {
        JPanel card = new JPanel();
        card.setBackground(UIConstants.BACKGROUND_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, accent),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        
        JLabel labelComp = new JLabel(label);
        labelComp.setForeground(UIConstants.TEXT_SECONDARY);
        labelComp.setFont(UIConstants.FONT_SMALL);
        
        JLabel valueComp = new JLabel(value);
        valueComp.setForeground(UIConstants.TEXT_PRIMARY);
        valueComp.setFont(UIConstants.FONT_SUBHEADING);
        
        card.add(labelComp);
        card.add(Box.createVerticalStrut(10));
        card.add(valueComp);
        
        return card;
    }
    
    private JPanel createStockCard(Stock stock) {
        JPanel card = new JPanel();
        card.setBackground(UIConstants.BACKGROUND_CARD);
        card.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        
        JLabel symbol = new JLabel(stock.getSymbol());
        symbol.setForeground(UIConstants.PRIMARY_GREEN);
        symbol.setFont(UIConstants.FONT_BODY_BOLD);
        
        JLabel price = new JLabel(UIConstants.formatCurrency(stock.getCurrentPrice()));
        price.setForeground(UIConstants.TEXT_PRIMARY);
        price.setFont(UIConstants.FONT_SMALL);
        
        double change = stock.getChangePercent();
        JLabel changeLabel = new JLabel(String.format("%+.2f%%", change));
        changeLabel.setForeground(change >= 0 ? UIConstants.SUCCESS : UIConstants.DANGER);
        changeLabel.setFont(UIConstants.FONT_SMALL);
        
        card.add(symbol);
        card.add(price);
        card.add(changeLabel);
        
        return card;
    }
}
