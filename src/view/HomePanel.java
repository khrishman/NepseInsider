package view;

import controller.StockController;
import controller.AdminController;
import model.Stock;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.List;

/**
 * HomePanel - Landing page with modern design
 */
public class HomePanel extends JPanel {
    
    private MainFrame mainFrame;
    private StockController stockController;
    private AdminController adminController;
    
    public HomePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.stockController = StockController.getInstance();
        this.adminController = AdminController.getInstance();
        
        setBackground(UIConstants.BACKGROUND_DARK);
        setLayout(new BorderLayout());
        
        initComponents();
    }
    
    private void initComponents() {
        // Navigation bar
        add(new NavigationBar(mainFrame, MainFrame.HOME), BorderLayout.NORTH);
        
        // Main content
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UIConstants.BACKGROUND_DARK);
        
        content.add(createHeroSection());
        content.add(createMarketTicker());
        content.add(createStatsSection());
        content.add(createRecentStocksSection());
        content.add(createGainersLosersSection());
        
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBackground(UIConstants.BACKGROUND_DARK);
        scrollPane.getViewport().setBackground(UIConstants.BACKGROUND_DARK);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createHeroSection() {
        JPanel hero = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw gradient background
                GradientPaint gp = new GradientPaint(0, 0, UIConstants.BACKGROUND_DARK, 
                        getWidth(), getHeight(), new Color(20, 40, 60));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Draw decorative lines
                g2d.setColor(new Color(34, 197, 94, 30));
                g2d.setStroke(new BasicStroke(2));
                int[] points = {50, 120, 80, 150, 100, 90, 130, 160, 110, 140};
                for (int i = 0; i < getWidth(); i += 80) {
                    for (int j = 0; j < points.length - 1; j++) {
                        int x1 = i + j * 30;
                        int x2 = i + (j + 1) * 30;
                        g2d.drawLine(x1, points[j] + 100, x2, points[j + 1] + 100);
                    }
                }
            }
        };
        hero.setPreferredSize(new Dimension(0, 400));
        hero.setLayout(new GridBagLayout());
        
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        
        JLabel titleLine1 = new JLabel("Your Gateway to");
        titleLine1.setForeground(UIConstants.GOLD);
        titleLine1.setFont(new Font("Segoe UI", Font.BOLD, 48));
        titleLine1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLine2 = new JLabel("NEPSE Markets");
        titleLine2.setForeground(UIConstants.PRIMARY_GREEN);
        titleLine2.setFont(new Font("Segoe UI", Font.BOLD, 48));
        titleLine2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitle = new JLabel("<html><center>Real-time stock tracking, portfolio management, and market insights.<br>Make informed investment decisions with NepseInsider.</center></html>");
        subtitle.setForeground(UIConstants.TEXT_SECONDARY);
        subtitle.setFont(UIConstants.FONT_BODY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);

        JButton exploreBtn = UIConstants.createPrimaryButton("Explore Markets");
        exploreBtn.setPreferredSize(new Dimension(160, 45));
        exploreBtn.addActionListener(e -> mainFrame.showMarkets());

        // Login button with white background and black text
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(Color.WHITE);
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        loginBtn.setOpaque(true);
        loginBtn.setContentAreaFilled(true);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setPreferredSize(new Dimension(120, 45));
        loginBtn.addActionListener(e -> mainFrame.showLogin());

        // Hover effect
        loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                loginBtn.setBackground(new Color(230, 230, 230));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                loginBtn.setBackground(Color.WHITE);
            }
        });

        buttonPanel.add(exploreBtn);
        buttonPanel.add(loginBtn);
        
        textPanel.add(titleLine1);
        textPanel.add(titleLine2);
        textPanel.add(subtitle);
        textPanel.add(buttonPanel);
        
        hero.add(textPanel);
        
        return hero;
    }
    
    private JPanel createMarketTicker() {
        JPanel ticker = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 12));
        ticker.setBackground(UIConstants.BACKGROUND_SECONDARY);
        ticker.setPreferredSize(new Dimension(0, 50));
        
        AdminController.MarketData market = adminController.getPrimaryMarket();
        if (market != null) {
            ticker.add(createTickerItem("NEPSE", market.indexValue, market.changePercent));
        }
        ticker.add(createTickerItem("Sensitive", 458.32, 1.20));
        ticker.add(createTickerItem("Float", 142.56, -0.50));
        ticker.add(createTickerItem("Banking", 1245.80, 0.80));
        ticker.add(createTickerItem("Hydropower", 2156.40, 2.10));
        
        return ticker;
    }
    
    private JPanel createTickerItem(String name, double value, double change) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        item.setOpaque(false);
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setForeground(UIConstants.TEXT_MUTED);
        nameLabel.setFont(UIConstants.FONT_SMALL);
        
        JLabel valueLabel = new JLabel(String.format("%.2f", value));
        valueLabel.setForeground(UIConstants.TEXT_PRIMARY);
        valueLabel.setFont(UIConstants.FONT_BODY_BOLD);
        
        JLabel changeLabel = new JLabel(String.format("%+.2f%%", change));
        changeLabel.setForeground(change >= 0 ? UIConstants.SUCCESS : UIConstants.DANGER);
        changeLabel.setFont(UIConstants.FONT_SMALL);
        
        item.add(nameLabel);
        item.add(valueLabel);
        item.add(changeLabel);
        
        return item;
    }
    
    private JPanel createStatsSection() {
        JPanel section = new JPanel(new GridLayout(1, 4, 20, 0));
        section.setBackground(UIConstants.BACKGROUND_DARK);
        section.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
        
        section.add(createStatCard("Total Stocks", String.valueOf(stockController.getTotalStocks()), UIConstants.ACCENT_CYAN));
        section.add(createStatCard("Market Cap", UIConstants.formatLargeNumber(stockController.getTotalMarketCap()), UIConstants.PRIMARY_GREEN));
        section.add(createStatCard("Top Gainers", String.valueOf(stockController.getTopGainers(10).size()), UIConstants.SUCCESS));
        section.add(createStatCard("Active Sectors", String.valueOf(stockController.getAllSectors().size()), UIConstants.GOLD));
        
        return section;
    }
    
    private JPanel createStatCard(String title, String value, Color accent) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(UIConstants.BACKGROUND_CARD);
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2d.setColor(accent);
                g2d.fillRect(0, 0, 4, getHeight());
                g2d.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(UIConstants.TEXT_PRIMARY);
        valueLabel.setFont(UIConstants.FONT_STAT_VALUE);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(UIConstants.TEXT_SECONDARY);
        titleLabel.setFont(UIConstants.FONT_BODY);
        
        card.add(valueLabel, BorderLayout.CENTER);
        card.add(titleLabel, BorderLayout.SOUTH);
        
        return card;
    }
    
    private JPanel createRecentStocksSection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(UIConstants.BACKGROUND_DARK);
        section.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));
        
        JLabel title = new JLabel("Recently Added Stocks");
        title.setForeground(UIConstants.GOLD);
        title.setFont(UIConstants.FONT_SUBHEADING);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JPanel cardsPanel = new JPanel(new GridLayout(1, 5, 15, 0));
        cardsPanel.setOpaque(false);
        
        List<Stock> stocks = stockController.getAllStocks();
        for (int i = 0; i < Math.min(5, stocks.size()); i++) {
            cardsPanel.add(createStockCard(stocks.get(i)));
        }
        
        section.add(title, BorderLayout.NORTH);
        section.add(cardsPanel, BorderLayout.CENTER);
        
        return section;
    }
    
    private JPanel createStockCard(Stock stock) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(UIConstants.BACKGROUND_CARD);
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2d.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel symbol = new JLabel(stock.getSymbol());
        symbol.setForeground(UIConstants.PRIMARY_GREEN);
        symbol.setFont(UIConstants.FONT_BODY_BOLD);
        
        JLabel name = new JLabel(stock.getCompanyName());
        name.setForeground(UIConstants.TEXT_MUTED);
        name.setFont(UIConstants.FONT_SMALL);
        
        JLabel price = new JLabel(UIConstants.formatCurrency(stock.getCurrentPrice()));
        price.setForeground(UIConstants.TEXT_PRIMARY);
        price.setFont(UIConstants.FONT_BODY_BOLD);
        price.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        
        double change = stock.getChangePercent();
        JLabel changeLabel = new JLabel((change >= 0 ? "▲ " : "▼ ") + String.format("%.2f%%", Math.abs(change)));
        changeLabel.setForeground(change >= 0 ? UIConstants.SUCCESS : UIConstants.DANGER);
        changeLabel.setFont(UIConstants.FONT_SMALL);
        
        card.add(symbol);
        card.add(name);
        card.add(price);
        card.add(changeLabel);
        
        return card;
    }
    
    private JPanel createGainersLosersSection() {
        JPanel section = new JPanel(new GridLayout(1, 2, 30, 0));
        section.setBackground(UIConstants.BACKGROUND_DARK);
        section.setBorder(BorderFactory.createEmptyBorder(30, 80, 50, 80));
        
        section.add(createGLPanel("Top Gainers", stockController.getTopGainers(5), true));
        section.add(createGLPanel("Top Losers", stockController.getTopLosers(5), false));
        
        return section;
    }
    
    private JPanel createGLPanel(String title, List<Stock> stocks, boolean isGainers) {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(UIConstants.BACKGROUND_CARD);
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2d.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(isGainers ? UIConstants.SUCCESS : UIConstants.DANGER);
        titleLabel.setFont(UIConstants.FONT_SUBHEADING);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);
        
        for (Stock stock : stocks) {
            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);
            row.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
            
            JLabel symbolLabel = new JLabel(stock.getSymbol() + " - " + stock.getCompanyName());
            symbolLabel.setForeground(UIConstants.TEXT_PRIMARY);
            symbolLabel.setFont(UIConstants.FONT_BODY);
            
            JLabel changeLabel = new JLabel(String.format("%+.2f%%", stock.getChangePercent()));
            changeLabel.setForeground(isGainers ? UIConstants.SUCCESS : UIConstants.DANGER);
            changeLabel.setFont(UIConstants.FONT_BODY_BOLD);
            
            row.add(symbolLabel, BorderLayout.WEST);
            row.add(changeLabel, BorderLayout.EAST);
            
            listPanel.add(row);
        }
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(listPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    public void refresh() {
        removeAll();
        initComponents();
        revalidate();
        repaint();
    }
}
