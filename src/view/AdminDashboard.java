package view;

import controller.*;
import model.*;
import util.ValidationUtils.ValidationResult;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class AdminDashboard extends JPanel {
    
    private MainFrame mainFrame;
    private UserController userController;
    private StockController stockController;
    private AdminController adminController;
    
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private String currentMenu = "Dashboard";
    private Map<String, JPanel> menuItems = new HashMap<>();
    
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
    private static final Color CYAN = new Color(6, 182, 212);
    private static final Color ORANGE = new Color(245, 158, 11);
    
    public AdminDashboard(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = UserController.getInstance();
        this.stockController = StockController.getInstance();
        this.adminController = AdminController.getInstance();
        setBackground(CONTENT_BG);
        setLayout(new BorderLayout());
        initComponents();
    }
    
    private void initComponents() {
        add(createTopBar(), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);
        
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(CONTENT_BG);
        
        contentPanel.add(createDashboardPanel(), "Dashboard");
        contentPanel.add(createCategoriesPanel(), "Categories");
        contentPanel.add(createSubCategoriesPanel(), "SubCategories");
        contentPanel.add(createMarketsPanel(), "Markets");
        contentPanel.add(createUpcomingMarketsPanel(), "Upcoming Markets");
        contentPanel.add(createLiveMarketsPanel(), "Live Markets");
        contentPanel.add(createUsersPanel(), "Manage Users");
        contentPanel.add(createStocksPanel(), "Manage Stocks");
        contentPanel.add(createDepositsPanel(), "Deposits");
        contentPanel.add(createWithdrawalsPanel(), "Withdrawals");
        contentPanel.add(createCommentsPanel(), "Comments");
        contentPanel.add(createTicketsPanel(), "Support Tickets");
        contentPanel.add(createReportsPanel(), "Reports");
        contentPanel.add(createSubscribersPanel(), "Subscribers");
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(CARD_BG);
        topBar.setPreferredSize(new Dimension(0, 55));
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 12));
        leftPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setForeground(GOLD);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        leftPanel.add(titleLabel);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        rightPanel.setOpaque(false);
        User user = userController.getCurrentUser();
        JLabel userLabel = new JLabel("Welcome, " + (user != null ? user.getUsername() : "Admin"));
        userLabel.setForeground(TEXT_WHITE);
        
        JButton homeBtn = createTopButton("Home", BLUE);
        homeBtn.addActionListener(e -> mainFrame.showHome());
        JButton logoutBtn = createTopButton("Logout", RED);
        logoutBtn.addActionListener(e -> mainFrame.logout());
        
        rightPanel.add(userLabel);
        rightPanel.add(Box.createHorizontalStrut(15));
        rightPanel.add(homeBtn);
        rightPanel.add(logoutBtn);
        
        topBar.add(leftPanel, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);
        return topBar;
    }
    
    private JButton createTopButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(80, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(220, 0));
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
        
        addMenuSection(sidebar, "MAIN", new String[]{"Dashboard"});
        addMenuSection(sidebar, "MANAGEMENT", new String[]{"Categories", "SubCategories", "Markets", "Upcoming Markets", "Live Markets"});
        addMenuSection(sidebar, "USERS & STOCKS", new String[]{"Manage Users", "Manage Stocks"});
        addMenuSection(sidebar, "TRANSACTIONS", new String[]{"Deposits", "Withdrawals"});
        addMenuSection(sidebar, "CONTENT", new String[]{"Comments", "Support Tickets", "Reports", "Subscribers"});
        
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
        
        JLabel header = new JLabel("Dashboard Overview");
        header.setForeground(GOLD);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(header);
        content.add(Box.createVerticalStrut(25));
        
        JPanel stats1 = new JPanel(new GridLayout(1, 4, 15, 0));
        stats1.setOpaque(false);
        stats1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        stats1.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        Map<String, Object> stats = adminController.getDashboardStats();
        stats1.add(createStatCard("Total Users", String.valueOf(userController.getTotalUsers()), BLUE));
        stats1.add(createStatCard("Total Stocks", String.valueOf(stockController.getTotalStocks()), GREEN));
        stats1.add(createStatCard("Categories", String.valueOf(stats.get("totalCategories")), GOLD));
        stats1.add(createStatCard("Subscribers", String.valueOf(stats.get("totalSubscribers")), CYAN));
        content.add(stats1);
        content.add(Box.createVerticalStrut(15));
        
        JPanel stats2 = new JPanel(new GridLayout(1, 4, 15, 0));
        stats2.setOpaque(false);
        stats2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        stats2.setAlignmentX(Component.LEFT_ALIGNMENT);
        stats2.add(createStatCard("Pending Deposits", String.valueOf(stats.get("pendingDeposits")), ORANGE));
        stats2.add(createStatCard("Pending Withdrawals", String.valueOf(stats.get("pendingWithdrawals")), RED));
        stats2.add(createStatCard("Open Tickets", String.valueOf(stats.get("openTickets")), BLUE));
        stats2.add(createStatCard("Pending Comments", String.valueOf(stats.get("pendingComments")), TEXT_GRAY));
        content.add(stats2);
        
        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.setBackground(CONTENT_BG);
        scroll.getViewport().setBackground(CONTENT_BG);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createStatCard(String title, String value, Color valueColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_COLOR), BorderFactory.createEmptyBorder(15, 20, 15, 20)));
        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(valueColor);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(TEXT_GRAY);
        card.add(valueLabel, BorderLayout.CENTER);
        card.add(titleLabel, BorderLayout.SOUTH);
        return card;
    }
    
    private JButton createActionButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(100, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private void styleTable(JTable table) {
        table.setBackground(CARD_BG);
        table.setForeground(TEXT_WHITE);
        table.setGridColor(BORDER_COLOR);
        table.setSelectionBackground(GREEN);
        table.setSelectionForeground(Color.WHITE);
        table.setRowHeight(45);
        JTableHeader header = table.getTableHeader();
        header.setBackground(SIDEBAR_BG);
        header.setForeground(GOLD);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
    }
    
    private JPanel createManagementPanel(String title, String[] columns, Object[][] data, JButton[] buttons) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CONTENT_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(GOLD);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        for (JButton btn : buttons) buttonPanel.add(btn);
        
        JButton refreshBtn = createActionButton("Refresh", ORANGE);
        refreshBtn.addActionListener(e -> { mainFrame.showAdminDashboard(); showSuccess("Refreshed!"); });
        buttonPanel.add(refreshBtn);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        
        JTable table = new JTable(new DefaultTableModel(data, columns) {
            public boolean isCellEditable(int r, int c) { return false; }
        });
        styleTable(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(CARD_BG);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createCategoriesPanel() {
        List<AdminController.Category> cats = adminController.getAllCategories();
        Object[][] data = new Object[cats.size()][4];
        for (int i = 0; i < cats.size(); i++) {
            AdminController.Category c = cats.get(i);
            data[i] = new Object[]{c.id, c.name, c.description, c.active ? "Active" : "Inactive"};
        }
        
        JButton addBtn = createActionButton("+ Add", GREEN);
        addBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Category Name:");
            if (name != null && !name.isEmpty()) {
                adminController.addCategory(name, JOptionPane.showInputDialog(this, "Description:"));
                showSuccess("Added!"); mainFrame.showAdminDashboard();
            }
        });
        
        JButton editBtn = createActionButton("Edit", BLUE);
        editBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Category ID to edit:"));
                String name = JOptionPane.showInputDialog(this, "New Name:");
                String desc = JOptionPane.showInputDialog(this, "New Description:");
                adminController.updateCategory(id, name, desc, true);
                showSuccess("Updated!"); mainFrame.showAdminDashboard();
            } catch (Exception ex) { showError("Invalid input"); }
        });
        
        JButton deleteBtn = createActionButton("Delete", RED);
        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Category ID to delete:"));
                if (JOptionPane.showConfirmDialog(this, "Delete?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    adminController.deleteCategory(id);
                    showSuccess("Deleted!"); mainFrame.showAdminDashboard();
                }
            } catch (Exception ex) { showError("Invalid ID"); }
        });
        
        return createManagementPanel("Categories Management", new String[]{"ID", "Name", "Description", "Status"}, data, new JButton[]{addBtn, editBtn, deleteBtn});
    }
    
    private JPanel createSubCategoriesPanel() {
        List<AdminController.SubCategory> subs = adminController.getAllSubCategories();
        Object[][] data = new Object[subs.size()][4];
        for (int i = 0; i < subs.size(); i++) {
            AdminController.SubCategory s = subs.get(i);
            data[i] = new Object[]{s.id, s.categoryId, s.name, s.description};
        }
        
        JButton addBtn = createActionButton("+ Add", GREEN);
        addBtn.addActionListener(e -> {
            try {
                int catId = Integer.parseInt(JOptionPane.showInputDialog(this, "Category ID:"));
                adminController.addSubCategory(catId, JOptionPane.showInputDialog(this, "Name:"), JOptionPane.showInputDialog(this, "Description:"));
                showSuccess("Added!"); mainFrame.showAdminDashboard();
            } catch (Exception ex) { showError("Invalid input"); }
        });
        
        JButton deleteBtn = createActionButton("Delete", RED);
        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "SubCategory ID:"));
                if (JOptionPane.showConfirmDialog(this, "Delete?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    adminController.deleteSubCategory(id);
                    showSuccess("Deleted!"); mainFrame.showAdminDashboard();
                }
            } catch (Exception ex) { showError("Invalid ID"); }
        });
        
        return createManagementPanel("SubCategories", new String[]{"ID", "Category ID", "Name", "Description"}, data, new JButton[]{addBtn, deleteBtn});
    }
    
    private JPanel createMarketsPanel() {
        List<AdminController.MarketData> markets = adminController.getAllMarkets();
        Object[][] data = new Object[markets.size()][5];
        for (int i = 0; i < markets.size(); i++) {
            AdminController.MarketData m = markets.get(i);
            data[i] = new Object[]{m.id, m.name, String.format("%.2f", m.indexValue), String.format("%+.2f%%", m.changePercent), m.status};
        }
        
        JButton updateBtn = createActionButton("Update", BLUE);
        updateBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Market ID:"));
                double val = Double.parseDouble(JOptionPane.showInputDialog(this, "New Index Value:"));
                double change = Double.parseDouble(JOptionPane.showInputDialog(this, "Change:"));
                adminController.updateMarketIndex(id, val, change);
                showSuccess("Updated!"); mainFrame.showAdminDashboard();
            } catch (Exception ex) { showError("Invalid input"); }
        });
        
        return createManagementPanel("Markets", new String[]{"ID", "Name", "Index", "Change %", "Status"}, data, new JButton[]{updateBtn});
    }
    
    private JPanel createUpcomingMarketsPanel() {
        List<AdminController.UpcomingMarket> list = adminController.getAllUpcomingMarkets();
        Object[][] data = new Object[list.size()][6];
        for (int i = 0; i < list.size(); i++) {
            AdminController.UpcomingMarket u = list.get(i);
            data[i] = new Object[]{u.id, u.companyName, u.symbol, u.type, u.eventDate, u.status};
        }
        
        JButton addBtn = createActionButton("+ Add", GREEN);
        addBtn.addActionListener(e -> {
            adminController.addUpcomingMarket(JOptionPane.showInputDialog(this, "Company:"), JOptionPane.showInputDialog(this, "Symbol:"),
                JOptionPane.showInputDialog(this, "Type:"), JOptionPane.showInputDialog(this, "Date:"), 100, 100000);
            showSuccess("Added!"); mainFrame.showAdminDashboard();
        });
        
        JButton deleteBtn = createActionButton("Delete", RED);
        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "ID to delete:"));
                if (JOptionPane.showConfirmDialog(this, "Delete?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    adminController.deleteUpcomingMarket(id);
                    showSuccess("Deleted!"); mainFrame.showAdminDashboard();
                }
            } catch (Exception ex) { showError("Invalid ID"); }
        });
        
        return createManagementPanel("Upcoming IPO/FPO", new String[]{"ID", "Company", "Symbol", "Type", "Date", "Status"}, data, new JButton[]{addBtn, deleteBtn});
    }
    
    private JPanel createLiveMarketsPanel() {
        AdminController.MarketData m = adminController.getPrimaryMarket();
        Object[][] data = m != null ? new Object[][]{{m.id, m.name, String.format("%.2f", m.indexValue), m.volume, "LIVE"}} : new Object[][]{};
        return createManagementPanel("Live Markets", new String[]{"ID", "Name", "Index", "Volume", "Status"}, data, new JButton[]{});
    }
    
    private JPanel createUsersPanel() {
        List<User> users = userController.getAllUsers();
        Object[][] data = new Object[users.size()][6];
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            data[i] = new Object[]{u.getId(), u.getUsername(), u.getEmail() != null ? u.getEmail() : "-", u.getRole(), u.getStatus(), String.format("Rs. %,.2f", u.getBalance())};
        }
        
        JButton addBtn = createActionButton("+ Add", GREEN);
        addBtn.addActionListener(e -> {
            userController.addUser(JOptionPane.showInputDialog(this, "Username:"), JOptionPane.showInputDialog(this, "Password:"),
                JOptionPane.showInputDialog(this, "Email:"), JOptionPane.showInputDialog(this, "Full Name:"), User.UserRole.USER);
            showSuccess("Added!"); mainFrame.showAdminDashboard();
        });
        
        JButton editBtn = createActionButton("Edit Status", BLUE);
        editBtn.addActionListener(e -> {
            String un = JOptionPane.showInputDialog(this, "Username:");
            String[] opts = {"ACTIVE", "INACTIVE", "SUSPENDED"};
            String status = (String) JOptionPane.showInputDialog(this, "Status:", "Edit", JOptionPane.PLAIN_MESSAGE, null, opts, opts[0]);
            if (status != null) {
                userController.updateUserStatus(un, User.UserStatus.valueOf(status));
                showSuccess("Updated!"); mainFrame.showAdminDashboard();
            }
        });
        
        JButton deleteBtn = createActionButton("Delete", RED);
        deleteBtn.addActionListener(e -> {
            String un = JOptionPane.showInputDialog(this, "Username to delete:");
            if (un != null && JOptionPane.showConfirmDialog(this, "Delete " + un + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                userController.deleteUser(un);
                showSuccess("Deleted!"); mainFrame.showAdminDashboard();
            }
        });
        
        return createManagementPanel("User Management", new String[]{"ID", "Username", "Email", "Role", "Status", "Balance"}, data, new JButton[]{addBtn, editBtn, deleteBtn});
    }
    
    private JPanel createStocksPanel() {
        List<Stock> stocks = stockController.getAllStocks();
        Object[][] data = new Object[stocks.size()][6];
        for (int i = 0; i < stocks.size(); i++) {
            Stock s = stocks.get(i);
            data[i] = new Object[]{s.getId(), s.getSymbol(), s.getCompanyName(), s.getSector(), String.format("Rs. %,.2f", s.getCurrentPrice()), String.format("%+.2f%%", s.getChangePercent())};
        }
        
        JButton addBtn = createActionButton("+ Add", GREEN);
        addBtn.addActionListener(e -> {
            try {
                stockController.addStock(JOptionPane.showInputDialog(this, "Symbol:"), JOptionPane.showInputDialog(this, "Company:"),
                    JOptionPane.showInputDialog(this, "Sector:"), Double.parseDouble(JOptionPane.showInputDialog(this, "Price:")), 0, 0, 2020);
                showSuccess("Added!"); mainFrame.showAdminDashboard();
            } catch (Exception ex) { showError("Invalid input"); }
        });
        
        JButton editBtn = createActionButton("Edit", BLUE);
        editBtn.addActionListener(e -> {
            try {
                String sym = JOptionPane.showInputDialog(this, "Symbol to edit:");
                stockController.updateStock(sym, JOptionPane.showInputDialog(this, "Company:"), JOptionPane.showInputDialog(this, "Sector:"),
                    Double.parseDouble(JOptionPane.showInputDialog(this, "New Price:")), 0, 0, 2020);
                showSuccess("Updated!"); mainFrame.showAdminDashboard();
            } catch (Exception ex) { showError("Invalid input"); }
        });
        
        JButton deleteBtn = createActionButton("Delete", RED);
        deleteBtn.addActionListener(e -> {
            String sym = JOptionPane.showInputDialog(this, "Symbol to delete:");
            if (sym != null && JOptionPane.showConfirmDialog(this, "Delete " + sym + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                stockController.deleteStock(sym);
                showSuccess("Deleted!"); mainFrame.showAdminDashboard();
            }
        });
        
        return createManagementPanel("Stock Management", new String[]{"ID", "Symbol", "Company", "Sector", "Price", "Change"}, data, new JButton[]{addBtn, editBtn, deleteBtn});
    }
    
    private JPanel createDepositsPanel() {
        List<AdminController.DepositTransaction> deps = adminController.getAllDeposits();
        Object[][] data = new Object[deps.size()][6];
        for (int i = 0; i < deps.size(); i++) {
            AdminController.DepositTransaction d = deps.get(i);
            data[i] = new Object[]{d.id, d.username, String.format("Rs. %,.2f", d.amount), d.paymentMethod, d.status, d.createdDate.format(DATE_FORMAT)};
        }
        
        JButton approveBtn = createActionButton("Approve", GREEN);
        approveBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Deposit ID to approve:"));
                adminController.approveDeposit(id);
                showSuccess("Approved! Balance credited."); mainFrame.showAdminDashboard();
            } catch (Exception ex) { showError("Invalid ID"); }
        });
        
        JButton rejectBtn = createActionButton("Reject", ORANGE);
        rejectBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Deposit ID to reject:"));
                String reason = JOptionPane.showInputDialog(this, "Reason:");
                adminController.rejectDeposit(id, reason);
                showSuccess("Rejected!"); mainFrame.showAdminDashboard();
            } catch (Exception ex) { showError("Invalid ID"); }
        });
        
        JButton deleteBtn = createActionButton("Delete", RED);
        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Deposit ID to delete:"));
                if (JOptionPane.showConfirmDialog(this, "Delete?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    adminController.deleteDeposit(id);
                    showSuccess("Deleted!"); mainFrame.showAdminDashboard();
                }
            } catch (Exception ex) { showError("Invalid ID"); }
        });
        
        return createManagementPanel("Deposits", new String[]{"ID", "User", "Amount", "Method", "Status", "Date"}, data, new JButton[]{approveBtn, rejectBtn, deleteBtn});
    }
    
    private JPanel createWithdrawalsPanel() {
        List<AdminController.WithdrawalTransaction> wits = adminController.getAllWithdrawals();
        Object[][] data = new Object[wits.size()][6];
        for (int i = 0; i < wits.size(); i++) {
            AdminController.WithdrawalTransaction w = wits.get(i);
            data[i] = new Object[]{w.id, w.username, String.format("Rs. %,.2f", w.amount), w.bankName, w.status, w.createdDate.format(DATE_FORMAT)};
        }
        
        JButton approveBtn = createActionButton("Approve", GREEN);
        approveBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Withdrawal ID to approve:"));
                adminController.approveWithdrawal(id);
                showSuccess("Approved! Balance deducted."); mainFrame.showAdminDashboard();
            } catch (Exception ex) { showError("Invalid ID"); }
        });
        
        JButton rejectBtn = createActionButton("Reject", ORANGE);
        rejectBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Withdrawal ID to reject:"));
                String reason = JOptionPane.showInputDialog(this, "Reason:");
                adminController.rejectWithdrawal(id, reason);
                showSuccess("Rejected!"); mainFrame.showAdminDashboard();
            } catch (Exception ex) { showError("Invalid ID"); }
        });
        
        JButton deleteBtn = createActionButton("Delete", RED);
        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Withdrawal ID to delete:"));
                if (JOptionPane.showConfirmDialog(this, "Delete?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    adminController.deleteWithdrawal(id);
                    showSuccess("Deleted!"); mainFrame.showAdminDashboard();
                }
            } catch (Exception ex) { showError("Invalid ID"); }
        });
        
        return createManagementPanel("Withdrawals", new String[]{"ID", "User", "Amount", "Bank", "Status", "Date"}, data, new JButton[]{approveBtn, rejectBtn, deleteBtn});
    }
    
    private JPanel createCommentsPanel() {
        List<AdminController.CommentData> comms = adminController.getAllComments();
        Object[][] data = new Object[comms.size()][5];
        for (int i = 0; i < comms.size(); i++) {
            AdminController.CommentData c = comms.get(i);
            data[i] = new Object[]{c.id, c.username, c.stockSymbol, c.content, c.status};
        }
        
        JButton approveBtn = createActionButton("Approve", GREEN);
        approveBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Comment ID to approve:"));
                adminController.approveComment(id);
                showSuccess("Approved!"); mainFrame.showAdminDashboard();
            } catch (Exception ex) { showError("Invalid ID"); }
        });
        
        JButton rejectBtn = createActionButton("Reject", ORANGE);
        rejectBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Comment ID to reject:"));
                adminController.rejectComment(id);
                showSuccess("Rejected!"); mainFrame.showAdminDashboard();
            } catch (Exception ex) { showError("Invalid ID"); }
        });
        
        JButton deleteBtn = createActionButton("Delete", RED);
        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Comment ID to delete:"));
                if (JOptionPane.showConfirmDialog(this, "Delete comment?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    adminController.deleteComment(id);
                    showSuccess("Deleted!"); mainFrame.showAdminDashboard();
                }
            } catch (Exception ex) { showError("Invalid ID"); }
        });
        
        return createManagementPanel("Comments", new String[]{"ID", "User", "Stock", "Comment", "Status"}, data, new JButton[]{approveBtn, rejectBtn, deleteBtn});
    }
    
    private JPanel createTicketsPanel() {
        List<AdminController.SupportTicket> ticks = adminController.getAllTickets();
        Object[][] data = new Object[ticks.size()][5];
        for (int i = 0; i < ticks.size(); i++) {
            AdminController.SupportTicket t = ticks.get(i);
            data[i] = new Object[]{t.id, t.username, t.subject, t.status, t.createdDate.format(DATE_FORMAT)};
        }
        
        JButton replyBtn = createActionButton("Reply", BLUE);
        replyBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Ticket ID:"));
                String reply = JOptionPane.showInputDialog(this, "Your Reply:");
                adminController.replyToTicket(id, reply);
                showSuccess("Reply sent!"); mainFrame.showAdminDashboard();
            } catch (Exception ex) { showError("Invalid input"); }
        });
        
        JButton deleteBtn = createActionButton("Delete", RED);
        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Ticket ID to delete:"));
                if (JOptionPane.showConfirmDialog(this, "Delete ticket?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    adminController.deleteTicket(id);
                    showSuccess("Deleted!"); mainFrame.showAdminDashboard();
                }
            } catch (Exception ex) { showError("Invalid ID"); }
        });
        
        return createManagementPanel("Support Tickets", new String[]{"ID", "User", "Subject", "Status", "Date"}, data, new JButton[]{replyBtn, deleteBtn});
    }
    
    private JPanel createReportsPanel() {
        Object[][] data = {{"1", "Daily Summary", "System", "2025-12-24", "Complete"}, {"2", "Weekly Report", "Admin", "2025-12-20", "Complete"}};
        JButton genBtn = createActionButton("Generate", GREEN);
        genBtn.addActionListener(e -> showSuccess("Report generation started!"));
        return createManagementPanel("Reports", new String[]{"ID", "Type", "By", "Date", "Status"}, data, new JButton[]{genBtn});
    }
    
    private JPanel createSubscribersPanel() {
        List<AdminController.Subscriber> subs = adminController.getAllSubscribers();
        Object[][] data = new Object[subs.size()][4];
        for (int i = 0; i < subs.size(); i++) {
            AdminController.Subscriber s = subs.get(i);
            data[i] = new Object[]{s.id, s.email, s.active ? "Active" : "Inactive", s.subscribedDate.format(DATE_FORMAT)};
        }
        
        JButton addBtn = createActionButton("+ Add", GREEN);
        addBtn.addActionListener(e -> {
            String email = JOptionPane.showInputDialog(this, "Email:");
            if (email != null) {
                adminController.addSubscriber(email);
                showSuccess("Added!"); mainFrame.showAdminDashboard();
            }
        });
        
        JButton deleteBtn = createActionButton("Delete", RED);
        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Subscriber ID to remove:"));
                if (JOptionPane.showConfirmDialog(this, "Remove subscriber?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    adminController.removeSubscriber(id);
                    showSuccess("Removed!"); mainFrame.showAdminDashboard();
                }
            } catch (Exception ex) { showError("Invalid ID"); }
        });
        
        return createManagementPanel("Subscribers", new String[]{"ID", "Email", "Status", "Date"}, data, new JButton[]{addBtn, deleteBtn});
    }
    
    private void showSuccess(String msg) { JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE); }
    private void showError(String msg) { JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE); }
}
