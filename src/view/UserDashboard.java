package view;

import controller.UserController;
import controller.StockController;
import controller.AdminController;
import model.User;
import model.Stock;
import model.Portfolio.PortfolioItem;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

/**
 * UserDashboard - Enhanced Dashboard with Buy, Sell, Deposit, Withdraw
 */
public class UserDashboard extends JPanel {
    
    private MainFrame mainFrame;
    private UserController userController;
    private StockController stockController;
    private AdminController adminController;
    
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private String currentMenu = "Dashboard";
    private Map<String, JPanel> menuItems = new HashMap<>();
    
    private List<PortfolioItem> userHoldings = new ArrayList<>();
    private List<TransactionRecord> transactionHistory = new ArrayList<>();
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    private static final Color SIDEBAR_BG = new Color(20, 27, 45);
    private static final Color SIDEBAR_HOVER = new Color(30, 40, 65);
    private static final Color SIDEBAR_ACTIVE = new Color(34, 197, 94);
    private static final Color CONTENT_BG = new Color(15, 23, 42);
    private static final Color CARD_BG = new Color(30, 41, 59);
    private static final Color TEXT_WHITE = new Color(255, 255, 255);
    private static final Color TEXT_GRAY = new Color(148, 163, 184);
    private static final Color TEXT_MUTED = new Color(100, 116, 139);
    private static final Color BORDER_COLOR = new Color(51, 65, 85);
    private static final Color GREEN = new Color(34, 197, 94);
    private static final Color GOLD = new Color(250, 204, 21);
    private static final Color RED = new Color(239, 68, 68);
    private static final Color BLUE = new Color(59, 130, 246);
    private static final Color ORANGE = new Color(245, 158, 11);
    
    public UserDashboard(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = UserController.getInstance();
        this.stockController = StockController.getInstance();
        this.adminController = AdminController.getInstance();
        initializeSamplePortfolio();
        setBackground(CONTENT_BG);
        setLayout(new BorderLayout());
        initComponents();
    }
    
    private void initializeSamplePortfolio() {
        User user = userController.getCurrentUser();
        if (user != null) {
            Stock nabil = stockController.getStock("NABIL");
            Stock nica = stockController.getStock("NICA");
            Stock upper = stockController.getStock("UPPER");
            if (nabil != null) userHoldings.add(new PortfolioItem(nabil, 50, 1200.00));
            if (nica != null) userHoldings.add(new PortfolioItem(nica, 100, 820.00));
            if (upper != null) userHoldings.add(new PortfolioItem(upper, 75, 560.00));
            transactionHistory.add(new TransactionRecord(1, "BUY", "NABIL", 50, 1200.00, LocalDateTime.now().minusDays(5)));
            transactionHistory.add(new TransactionRecord(2, "BUY", "NICA", 100, 820.00, LocalDateTime.now().minusDays(3)));
        }
    }
    
    private void initComponents() {
        add(createTopBar(), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);
        
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(CONTENT_BG);
        
        contentPanel.add(createDashboardPanel(), "Dashboard");
        contentPanel.add(createPortfolioPanel(), "My Portfolio");
        contentPanel.add(createBuyStockPanel(), "Buy Stock");
        contentPanel.add(createSellStockPanel(), "Sell Stock");
        contentPanel.add(createDepositPanel(), "Deposit");
        contentPanel.add(createWithdrawPanel(), "Withdraw");
        contentPanel.add(createTransactionsPanel(), "Transactions");
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(CARD_BG);
        topBar.setPreferredSize(new Dimension(0, 55));
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 12));
        leftPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("User Dashboard");
        titleLabel.setForeground(GOLD);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        leftPanel.add(titleLabel);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        rightPanel.setOpaque(false);
        User user = userController.getCurrentUser();
        
        JLabel balanceLabel = new JLabel("Balance: " + UIConstants.formatCurrency(user != null ? user.getBalance() : 0));
        balanceLabel.setForeground(GREEN);
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        JLabel userLabel = new JLabel("Welcome, " + (user != null ? user.getFullName() : "User"));
        userLabel.setForeground(TEXT_WHITE);
        
        JButton homeBtn = createButton("Home", BLUE);
        homeBtn.addActionListener(e -> mainFrame.showHome());
        JButton logoutBtn = createButton("Logout", RED);
        logoutBtn.addActionListener(e -> mainFrame.logout());
        
        rightPanel.add(balanceLabel);
        rightPanel.add(Box.createHorizontalStrut(15));
        rightPanel.add(userLabel);
        rightPanel.add(Box.createHorizontalStrut(15));
        rightPanel.add(homeBtn);
        rightPanel.add(logoutBtn);
        
        topBar.add(leftPanel, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);
        return topBar;
    }
    
    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER_COLOR));
        
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 12));
        logoPanel.setOpaque(false);
        logoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        JLabel logoText = new JLabel("NepseInsider");
        logoText.setForeground(GREEN);
        logoText.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logoPanel.add(logoText);
        sidebar.add(logoPanel);
        sidebar.add(Box.createVerticalStrut(10));
        
        addMenuSection(sidebar, "OVERVIEW", new String[]{"Dashboard", "My Portfolio"});
        addMenuSection(sidebar, "TRADING", new String[]{"Buy Stock", "Sell Stock"});
        addMenuSection(sidebar, "FUNDS", new String[]{"Deposit", "Withdraw"});
        addMenuSection(sidebar, "HISTORY", new String[]{"Transactions"});
        
        sidebar.add(Box.createVerticalGlue());
        return sidebar;
    }
    
    private void addMenuSection(JPanel sidebar, String title, String[] items) {
        JLabel sectionLabel = new JLabel("  " + title);
        sectionLabel.setForeground(TEXT_MUTED);
        sectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        sectionLabel.setBorder(BorderFactory.createEmptyBorder(12, 10, 5, 0));
        sectionLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        sidebar.add(sectionLabel);
        for (String item : items) sidebar.add(createMenuItem(item));
    }
    
    private JPanel createMenuItem(String text) {
        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(text.equals("Dashboard") ? SIDEBAR_ACTIVE : SIDEBAR_BG);
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JPanel indicator = new JPanel();
        indicator.setPreferredSize(new Dimension(3, 38));
        indicator.setBackground(text.equals("Dashboard") ? GREEN : SIDEBAR_BG);
        
        JLabel label = new JLabel("  " + text);
        label.setForeground(text.equals("Dashboard") ? Color.WHITE : TEXT_GRAY);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        item.add(indicator, BorderLayout.WEST);
        item.add(label, BorderLayout.CENTER);
        menuItems.put(text, item);
        
        item.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { selectMenuItem(text); }
            public void mouseEntered(MouseEvent e) { if (!text.equals(currentMenu)) item.setBackground(SIDEBAR_HOVER); }
            public void mouseExited(MouseEvent e) { if (!text.equals(currentMenu)) item.setBackground(SIDEBAR_BG); }
        });
        return item;
    }
    
    private void selectMenuItem(String menu) {
        JPanel prevItem = menuItems.get(currentMenu);
        if (prevItem != null) {
            prevItem.setBackground(SIDEBAR_BG);
            ((JPanel)prevItem.getComponent(0)).setBackground(SIDEBAR_BG);
            ((JLabel)prevItem.getComponent(1)).setForeground(TEXT_GRAY);
        }
        currentMenu = menu;
        JPanel newItem = menuItems.get(menu);
        if (newItem != null) {
            newItem.setBackground(SIDEBAR_ACTIVE);
            ((JPanel)newItem.getComponent(0)).setBackground(GREEN);
            ((JLabel)newItem.getComponent(1)).setForeground(Color.WHITE);
        }
        cardLayout.show(contentPanel, menu);
    }
    
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CONTENT_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        
        User user = userController.getCurrentUser();
        JLabel header = new JLabel("Welcome back, " + (user != null ? user.getFullName() : "User") + "!");
        header.setForeground(GOLD);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(header);
        content.add(Box.createVerticalStrut(25));
        
        // Stats
        JPanel stats = new JPanel(new GridLayout(1, 4, 15, 0));
        stats.setOpaque(false);
        stats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        stats.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        double balance = user != null ? user.getBalance() : 0;
        double portfolioValue = userHoldings.stream().mapToDouble(PortfolioItem::getCurrentValue).sum();
        double todayPL = userHoldings.stream().mapToDouble(PortfolioItem::getProfitLoss).sum();
        
        stats.add(createStatCard("Account Balance", UIConstants.formatCurrency(balance), BLUE));
        stats.add(createStatCard("Portfolio Value", UIConstants.formatCurrency(portfolioValue), GREEN));
        stats.add(createStatCard("Today's P/L", String.format("%s%.2f", todayPL >= 0 ? "+" : "", todayPL), todayPL >= 0 ? GREEN : RED));
        stats.add(createStatCard("Holdings", userHoldings.size() + " Stocks", GOLD));
        content.add(stats);
        content.add(Box.createVerticalStrut(25));
        
        // Quick Actions
        JLabel actionsTitle = new JLabel("Quick Actions");
        actionsTitle.setForeground(GOLD);
        actionsTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        actionsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(actionsTitle);
        content.add(Box.createVerticalStrut(15));
        
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        actionsPanel.setOpaque(false);
        actionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JButton buyBtn = createActionButton("Buy Stock", GREEN);
        buyBtn.addActionListener(e -> selectMenuItem("Buy Stock"));
        JButton sellBtn = createActionButton("Sell Stock", ORANGE);
        sellBtn.addActionListener(e -> selectMenuItem("Sell Stock"));
        JButton depositBtn = createActionButton("Deposit", BLUE);
        depositBtn.addActionListener(e -> selectMenuItem("Deposit"));
        JButton withdrawBtn = createActionButton("Withdraw", RED);
        withdrawBtn.addActionListener(e -> selectMenuItem("Withdraw"));
        
        actionsPanel.add(buyBtn);
        actionsPanel.add(sellBtn);
        actionsPanel.add(depositBtn);
        actionsPanel.add(withdrawBtn);
        content.add(actionsPanel);
        
        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.setBackground(CONTENT_BG);
        scroll.getViewport().setBackground(CONTENT_BG);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createPortfolioPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CONTENT_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        JLabel titleLabel = new JLabel("My Portfolio");
        titleLabel.setForeground(GOLD);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        Object[][] data = new Object[userHoldings.size()][7];
        for (int i = 0; i < userHoldings.size(); i++) {
            PortfolioItem item = userHoldings.get(i);
            Stock stock = item.getStock();
            double pl = item.getProfitLoss();
            data[i] = new Object[]{stock.getSymbol(), stock.getCompanyName(), item.getQuantity(),
                String.format("Rs. %,.2f", item.getBuyPrice()), String.format("Rs. %,.2f", stock.getCurrentPrice()),
                String.format("Rs. %,.2f", item.getCurrentValue()), String.format("%s%.2f", pl >= 0 ? "+" : "", pl)};
        }
        
        JTable table = new JTable(new DefaultTableModel(data, new String[]{"Symbol", "Company", "Qty", "Buy Price", "Current", "Value", "P/L"}) {
            public boolean isCellEditable(int r, int c) { return false; }
        });
        styleTable(table);
        
        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(CARD_BG);
        
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(titleLabel, BorderLayout.NORTH);
        wrapper.add(sp, BorderLayout.CENTER);
        panel.add(wrapper, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createBuyStockPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CONTENT_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        JLabel titleLabel = new JLabel("Buy Stock");
        titleLabel.setForeground(GOLD);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(CARD_BG);
        formCard.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_COLOR), BorderFactory.createEmptyBorder(25, 25, 25, 25)));
        formCard.setMaximumSize(new Dimension(500, 400));
        
        List<Stock> allStocks = stockController.getAllStocks();
        String[] stockOptions = new String[allStocks.size()];
        for (int i = 0; i < allStocks.size(); i++) {
            Stock s = allStocks.get(i);
            stockOptions[i] = s.getSymbol() + " - " + s.getCompanyName() + " (Rs. " + s.getCurrentPrice() + ")";
        }
        
        JComboBox<String> stockCombo = new JComboBox<>(stockOptions);
        JTextField qtyField = new JTextField();
        qtyField.setBackground(SIDEBAR_BG);
        qtyField.setForeground(TEXT_WHITE);
        qtyField.setCaretColor(TEXT_WHITE);
        
        JLabel totalLabel = new JLabel("Total: Rs. 0.00");
        totalLabel.setForeground(GREEN);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        qtyField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                try {
                    int idx = stockCombo.getSelectedIndex();
                    int qty = Integer.parseInt(qtyField.getText().trim());
                    double price = allStocks.get(idx).getCurrentPrice();
                    totalLabel.setText("Total: Rs. " + String.format("%,.2f", price * qty));
                } catch (Exception ex) { totalLabel.setText("Total: Rs. 0.00"); }
            }
        });
        
        formCard.add(new JLabel("Select Stock:") {{ setForeground(TEXT_GRAY); }});
        formCard.add(stockCombo);
        formCard.add(Box.createVerticalStrut(15));
        formCard.add(new JLabel("Quantity:") {{ setForeground(TEXT_GRAY); }});
        formCard.add(qtyField);
        formCard.add(Box.createVerticalStrut(15));
        formCard.add(totalLabel);
        formCard.add(Box.createVerticalStrut(20));
        
        JButton buyBtn = createActionButton("Buy Now", GREEN);
        buyBtn.addActionListener(e -> {
            try {
                int idx = stockCombo.getSelectedIndex();
                int qty = Integer.parseInt(qtyField.getText().trim());
                if (qty <= 0) { showError("Quantity must be positive"); return; }
                Stock selected = allStocks.get(idx);
                double total = selected.getCurrentPrice() * qty;
                User user = userController.getCurrentUser();
                if (user.getBalance() < total) { showError("Insufficient balance! Need Rs. " + String.format("%,.2f", total)); return; }
                
                user.withdraw(total);
                boolean found = false;
                for (PortfolioItem item : userHoldings) {
                    if (item.getStock().getSymbol().equals(selected.getSymbol())) {
                        int newQty = item.getQuantity() + qty;
                        double avgPrice = ((item.getBuyPrice() * item.getQuantity()) + total) / newQty;
                        item.setQuantity(newQty);
                        item.setBuyPrice(avgPrice);
                        found = true;
                        break;
                    }
                }
                if (!found) userHoldings.add(new PortfolioItem(selected, qty, selected.getCurrentPrice()));
                transactionHistory.add(new TransactionRecord(transactionHistory.size() + 1, "BUY", selected.getSymbol(), qty, selected.getCurrentPrice(), LocalDateTime.now()));
                showSuccess("Successfully bought " + qty + " shares of " + selected.getSymbol() + "!");
                qtyField.setText("");
            } catch (NumberFormatException ex) { showError("Enter a valid quantity"); }
        });
        formCard.add(buyBtn);
        
        JPanel wrapper = new JPanel(new BorderLayout(0, 20));
        wrapper.setOpaque(false);
        wrapper.add(titleLabel, BorderLayout.NORTH);
        JPanel formWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formWrapper.setOpaque(false);
        formWrapper.add(formCard);
        wrapper.add(formWrapper, BorderLayout.CENTER);
        panel.add(wrapper, BorderLayout.NORTH);
        return panel;
    }
    
    private JPanel createSellStockPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CONTENT_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        JLabel titleLabel = new JLabel("Sell Stock");
        titleLabel.setForeground(GOLD);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(CARD_BG);
        formCard.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_COLOR), BorderFactory.createEmptyBorder(25, 25, 25, 25)));
        formCard.setMaximumSize(new Dimension(500, 400));
        
        String[] holdingOptions = new String[userHoldings.size()];
        for (int i = 0; i < userHoldings.size(); i++) {
            PortfolioItem item = userHoldings.get(i);
            holdingOptions[i] = item.getStock().getSymbol() + " - " + item.getQuantity() + " shares @ Rs. " + String.format("%.2f", item.getStock().getCurrentPrice());
        }
        
        JComboBox<String> stockCombo = new JComboBox<>(holdingOptions);
        JTextField qtyField = new JTextField();
        qtyField.setBackground(SIDEBAR_BG);
        qtyField.setForeground(TEXT_WHITE);
        qtyField.setCaretColor(TEXT_WHITE);
        
        JLabel totalLabel = new JLabel("Total: Rs. 0.00");
        totalLabel.setForeground(GREEN);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        qtyField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                try {
                    int idx = stockCombo.getSelectedIndex();
                    if (idx >= 0 && idx < userHoldings.size()) {
                        int qty = Integer.parseInt(qtyField.getText().trim());
                        double price = userHoldings.get(idx).getStock().getCurrentPrice();
                        totalLabel.setText("Total: Rs. " + String.format("%,.2f", price * qty));
                    }
                } catch (Exception ex) { totalLabel.setText("Total: Rs. 0.00"); }
            }
        });
        
        formCard.add(new JLabel("Select Stock:") {{ setForeground(TEXT_GRAY); }});
        formCard.add(stockCombo);
        formCard.add(Box.createVerticalStrut(15));
        formCard.add(new JLabel("Quantity to Sell:") {{ setForeground(TEXT_GRAY); }});
        formCard.add(qtyField);
        formCard.add(Box.createVerticalStrut(15));
        formCard.add(totalLabel);
        formCard.add(Box.createVerticalStrut(20));
        
        JButton sellBtn = createActionButton("Sell Now", ORANGE);
        sellBtn.addActionListener(e -> {
            try {
                if (userHoldings.isEmpty()) { showError("No stocks to sell"); return; }
                int idx = stockCombo.getSelectedIndex();
                int qty = Integer.parseInt(qtyField.getText().trim());
                if (qty <= 0) { showError("Quantity must be positive"); return; }
                PortfolioItem item = userHoldings.get(idx);
                if (qty > item.getQuantity()) { showError("You only have " + item.getQuantity() + " shares"); return; }
                
                double total = item.getStock().getCurrentPrice() * qty;
                User user = userController.getCurrentUser();
                user.deposit(total);
                String symbol = item.getStock().getSymbol();
                if (qty == item.getQuantity()) userHoldings.remove(idx);
                else item.setQuantity(item.getQuantity() - qty);
                
                transactionHistory.add(new TransactionRecord(transactionHistory.size() + 1, "SELL", symbol, qty, item.getStock().getCurrentPrice(), LocalDateTime.now()));
                showSuccess("Sold " + qty + " shares for Rs. " + String.format("%,.2f", total));
                qtyField.setText("");
                
                // Refresh combo
                stockCombo.removeAllItems();
                for (PortfolioItem h : userHoldings) {
                    stockCombo.addItem(h.getStock().getSymbol() + " - " + h.getQuantity() + " shares @ Rs. " + String.format("%.2f", h.getStock().getCurrentPrice()));
                }
            } catch (NumberFormatException ex) { showError("Enter a valid quantity"); }
        });
        formCard.add(sellBtn);
        
        JPanel wrapper = new JPanel(new BorderLayout(0, 20));
        wrapper.setOpaque(false);
        wrapper.add(titleLabel, BorderLayout.NORTH);
        JPanel formWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formWrapper.setOpaque(false);
        formWrapper.add(formCard);
        wrapper.add(formWrapper, BorderLayout.CENTER);
        panel.add(wrapper, BorderLayout.NORTH);
        return panel;
    }
    
    private JPanel createDepositPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CONTENT_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        JLabel titleLabel = new JLabel("Deposit Funds");
        titleLabel.setForeground(GOLD);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(CARD_BG);
        formCard.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_COLOR), BorderFactory.createEmptyBorder(25, 25, 25, 25)));
        formCard.setMaximumSize(new Dimension(500, 350));
        
        User user = userController.getCurrentUser();
        JLabel balanceInfo = new JLabel("Current Balance: " + UIConstants.formatCurrency(user != null ? user.getBalance() : 0));
        balanceInfo.setForeground(GREEN);
        balanceInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JTextField amtField = new JTextField();
        amtField.setBackground(SIDEBAR_BG);
        amtField.setForeground(TEXT_WHITE);
        amtField.setCaretColor(TEXT_WHITE);
        
        JComboBox<String> methodCombo = new JComboBox<>(new String[]{"Bank Transfer", "eSewa", "Khalti", "IME Pay", "Connect IPS"});
        
        formCard.add(balanceInfo);
        formCard.add(Box.createVerticalStrut(20));
        formCard.add(new JLabel("Amount (Rs.):") {{ setForeground(TEXT_GRAY); }});
        formCard.add(amtField);
        formCard.add(Box.createVerticalStrut(15));
        formCard.add(new JLabel("Payment Method:") {{ setForeground(TEXT_GRAY); }});
        formCard.add(methodCombo);
        formCard.add(Box.createVerticalStrut(20));
        
        JButton depositBtn = createActionButton("Request Deposit", BLUE);
        depositBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amtField.getText().trim());
                if (amount < 100) { showError("Minimum deposit is Rs. 100"); return; }
                if (amount > 1000000) { showError("Maximum deposit is Rs. 10,00,000"); return; }
                adminController.createDeposit(user.getUsername(), amount, (String) methodCombo.getSelectedItem());
                showSuccess("Deposit request of Rs. " + String.format("%,.2f", amount) + " submitted!\nAwaiting admin approval.");
                amtField.setText("");
            } catch (NumberFormatException ex) { showError("Enter a valid amount"); }
        });
        formCard.add(depositBtn);
        
        JPanel wrapper = new JPanel(new BorderLayout(0, 20));
        wrapper.setOpaque(false);
        wrapper.add(titleLabel, BorderLayout.NORTH);
        JPanel formWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formWrapper.setOpaque(false);
        formWrapper.add(formCard);
        wrapper.add(formWrapper, BorderLayout.CENTER);
        panel.add(wrapper, BorderLayout.NORTH);
        return panel;
    }
    
    private JPanel createWithdrawPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CONTENT_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        JLabel titleLabel = new JLabel("Withdraw Funds");
        titleLabel.setForeground(GOLD);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(CARD_BG);
        formCard.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_COLOR), BorderFactory.createEmptyBorder(25, 25, 25, 25)));
        formCard.setMaximumSize(new Dimension(500, 450));
        
        User user = userController.getCurrentUser();
        JLabel balanceInfo = new JLabel("Available Balance: " + UIConstants.formatCurrency(user != null ? user.getBalance() : 0));
        balanceInfo.setForeground(GREEN);
        balanceInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JTextField amtField = new JTextField();
        amtField.setBackground(SIDEBAR_BG);
        amtField.setForeground(TEXT_WHITE);
        amtField.setCaretColor(TEXT_WHITE);
        
        JComboBox<String> bankCombo = new JComboBox<>(new String[]{"Nabil Bank", "NIC Asia Bank", "Global IME Bank", "Sanima Bank", "Siddhartha Bank", "Himalayan Bank"});
        
        JTextField accField = new JTextField();
        accField.setBackground(SIDEBAR_BG);
        accField.setForeground(TEXT_WHITE);
        accField.setCaretColor(TEXT_WHITE);
        
        formCard.add(balanceInfo);
        formCard.add(Box.createVerticalStrut(20));
        formCard.add(new JLabel("Amount (Rs.):") {{ setForeground(TEXT_GRAY); }});
        formCard.add(amtField);
        formCard.add(Box.createVerticalStrut(15));
        formCard.add(new JLabel("Bank Name:") {{ setForeground(TEXT_GRAY); }});
        formCard.add(bankCombo);
        formCard.add(Box.createVerticalStrut(15));
        formCard.add(new JLabel("Account Number:") {{ setForeground(TEXT_GRAY); }});
        formCard.add(accField);
        formCard.add(Box.createVerticalStrut(20));
        
        JButton withdrawBtn = createActionButton("Request Withdrawal", RED);
        withdrawBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amtField.getText().trim());
                String accNumber = accField.getText().trim();
                if (amount < 500) { showError("Minimum withdrawal is Rs. 500"); return; }
                if (user.getBalance() < amount) { showError("Insufficient balance!"); return; }
                if (accNumber.length() < 10) { showError("Enter valid account number (10+ digits)"); return; }
                adminController.createWithdrawal(user.getUsername(), amount, (String) bankCombo.getSelectedItem(), accNumber);
                showSuccess("Withdrawal request of Rs. " + String.format("%,.2f", amount) + " submitted!\nAwaiting admin approval.");
                amtField.setText("");
                accField.setText("");
            } catch (NumberFormatException ex) { showError("Enter a valid amount"); }
        });
        formCard.add(withdrawBtn);
        
        JPanel wrapper = new JPanel(new BorderLayout(0, 20));
        wrapper.setOpaque(false);
        wrapper.add(titleLabel, BorderLayout.NORTH);
        JPanel formWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formWrapper.setOpaque(false);
        formWrapper.add(formCard);
        wrapper.add(formWrapper, BorderLayout.CENTER);
        panel.add(wrapper, BorderLayout.NORTH);
        return panel;
    }
    
    private JPanel createTransactionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CONTENT_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        JLabel titleLabel = new JLabel("Transaction History");
        titleLabel.setForeground(GOLD);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        Object[][] data = new Object[transactionHistory.size()][6];
        for (int i = 0; i < transactionHistory.size(); i++) {
            TransactionRecord t = transactionHistory.get(i);
            data[i] = new Object[]{t.id, t.type, t.symbol, t.quantity, String.format("Rs. %,.2f", t.price), t.date.format(DATE_FORMAT)};
        }
        
        JTable table = new JTable(new DefaultTableModel(data, new String[]{"ID", "Type", "Symbol", "Qty", "Price", "Date"}) {
            public boolean isCellEditable(int r, int c) { return false; }
        });
        styleTable(table);
        
        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(CARD_BG);
        
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(titleLabel, BorderLayout.NORTH);
        wrapper.add(sp, BorderLayout.CENTER);
        panel.add(wrapper, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createStatCard(String title, String value, Color valueColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_COLOR), BorderFactory.createEmptyBorder(15, 20, 15, 20)));
        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(valueColor);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(TEXT_GRAY);
        card.add(valueLabel, BorderLayout.CENTER);
        card.add(titleLabel, BorderLayout.SOUTH);
        return card;
    }
    
    private JButton createActionButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(140, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private void styleTable(JTable table) {
        table.setBackground(CARD_BG);
        table.setForeground(TEXT_WHITE);
        table.setGridColor(BORDER_COLOR);
        table.setSelectionBackground(GREEN);
        table.setRowHeight(40);
        JTableHeader header = table.getTableHeader();
        header.setBackground(SIDEBAR_BG);
        header.setForeground(GOLD);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
    }
    
    private void showSuccess(String msg) { JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE); }
    private void showError(String msg) { JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE); }
    
    private static class TransactionRecord {
        int id; String type; String symbol; int quantity; double price; LocalDateTime date;
        TransactionRecord(int id, String type, String symbol, int qty, double price, LocalDateTime date) {
            this.id = id; this.type = type; this.symbol = symbol; this.quantity = qty; this.price = price; this.date = date;
        }
    }
}
