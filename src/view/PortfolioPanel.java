package view;

import controller.UserController;
import controller.StockController;
import model.User;
import model.Stock;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

/**
 * PortfolioPanel - User portfolio view
 */
public class PortfolioPanel extends JPanel {
    
    private MainFrame mainFrame;
    private UserController userController;
    private StockController stockController;
    
    public PortfolioPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = UserController.getInstance();
        this.stockController = StockController.getInstance();
        
        setBackground(UIConstants.BACKGROUND_DARK);
        setLayout(new BorderLayout());
        
        initComponents();
    }
    
    private void initComponents() {
        add(new NavigationBar(mainFrame, MainFrame.PORTFOLIO), BorderLayout.NORTH);
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UIConstants.BACKGROUND_DARK);
        content.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        User user = userController.getCurrentUser();
        
        // Title
        JLabel title = new JLabel("My Portfolio");
        title.setForeground(UIConstants.GOLD);
        title.setFont(UIConstants.FONT_HEADING);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(title);
        content.add(Box.createVerticalStrut(30));
        
        // Portfolio summary cards
        JPanel summaryPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        summaryPanel.setOpaque(false);
        summaryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        summaryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        double balance = user != null ? user.getBalance() : 0;
        summaryPanel.add(createSummaryCard("Available Balance", UIConstants.formatCurrency(balance), UIConstants.PRIMARY_GREEN));
        summaryPanel.add(createSummaryCard("Invested Amount", UIConstants.formatCurrency(75000), UIConstants.INFO));
        summaryPanel.add(createSummaryCard("Current Value", UIConstants.formatCurrency(82500), UIConstants.SUCCESS));
        summaryPanel.add(createSummaryCard("Total Profit/Loss", "+Rs. 7,500 (10%)", UIConstants.SUCCESS));
        
        content.add(summaryPanel);
        content.add(Box.createVerticalStrut(30));
        
        // Holdings section
        JLabel holdingsTitle = new JLabel("My Holdings");
        holdingsTitle.setForeground(UIConstants.TEXT_PRIMARY);
        holdingsTitle.setFont(UIConstants.FONT_SUBHEADING);
        holdingsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(holdingsTitle);
        content.add(Box.createVerticalStrut(15));
        
        // Holdings table
        String[] columns = {"Symbol", "Company", "Quantity", "Avg. Price", "Current Price", "Value", "P/L %"};
        Object[][] data = {
            {"NABIL", "Nabil Bank Limited", 50, "Rs. 1,200.00", "Rs. 1,250.00", "Rs. 62,500", "+4.17%"},
            {"NICA", "NIC Asia Bank", 100, "Rs. 900.00", "Rs. 850.00", "Rs. 85,000", "-5.56%"},
            {"CHCL", "Chilime Hydropower", 30, "Rs. 680.00", "Rs. 720.00", "Rs. 21,600", "+5.88%"}
        };
        
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        UIConstants.styleTable(table);
        
        // Custom renderer for P/L column
        table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null) {
                    String val = value.toString();
                    if (val.startsWith("+")) {
                        c.setForeground(UIConstants.SUCCESS);
                    } else if (val.startsWith("-")) {
                        c.setForeground(UIConstants.DANGER);
                    }
                }
                if (!isSelected) {
                    c.setBackground(UIConstants.BACKGROUND_CARD);
                }
                return c;
            }
        });
        
        JScrollPane scrollPane = UIConstants.createScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(0, 250));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        content.add(scrollPane);
        content.add(Box.createVerticalStrut(30));
        
        // Watchlist section
        JLabel watchlistTitle = new JLabel("Watchlist");
        watchlistTitle.setForeground(UIConstants.TEXT_PRIMARY);
        watchlistTitle.setFont(UIConstants.FONT_SUBHEADING);
        watchlistTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(watchlistTitle);
        content.add(Box.createVerticalStrut(15));
        
        JPanel watchlistPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        watchlistPanel.setOpaque(false);
        watchlistPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        watchlistPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        List<Stock> stocks = stockController.getAllStocks();
        for (int i = 0; i < Math.min(4, stocks.size()); i++) {
            Stock s = stocks.get(i);
            watchlistPanel.add(createWatchlistCard(s));
        }
        
        content.add(watchlistPanel);
        
        JScrollPane mainScroll = new JScrollPane(content);
        mainScroll.setBackground(UIConstants.BACKGROUND_DARK);
        mainScroll.getViewport().setBackground(UIConstants.BACKGROUND_DARK);
        mainScroll.setBorder(null);
        
        add(mainScroll, BorderLayout.CENTER);
    }
    
    private JPanel createSummaryCard(String label, String value, Color accent) {
        JPanel card = new JPanel();
        card.setBackground(UIConstants.BACKGROUND_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, accent),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        
        JLabel labelComp = new JLabel(label);
        labelComp.setForeground(UIConstants.TEXT_SECONDARY);
        labelComp.setFont(UIConstants.FONT_SMALL);
        
        JLabel valueComp = new JLabel(value);
        valueComp.setForeground(UIConstants.TEXT_PRIMARY);
        valueComp.setFont(UIConstants.FONT_SUBHEADING);
        
        card.add(labelComp);
        card.add(Box.createVerticalStrut(8));
        card.add(valueComp);
        
        return card;
    }
    
    private JPanel createWatchlistCard(Stock stock) {
        JPanel card = new JPanel();
        card.setBackground(UIConstants.BACKGROUND_CARD);
        card.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel symbol = new JLabel(stock.getSymbol());
        symbol.setForeground(UIConstants.PRIMARY_GREEN);
        symbol.setFont(UIConstants.FONT_BODY_BOLD);
        
        JLabel price = new JLabel(UIConstants.formatCurrency(stock.getCurrentPrice()));
        price.setForeground(UIConstants.TEXT_PRIMARY);
        price.setFont(UIConstants.FONT_BODY);
        
        double change = stock.getChangePercent();
        JLabel changeLabel = new JLabel(String.format("%+.2f%%", change));
        changeLabel.setForeground(change >= 0 ? UIConstants.SUCCESS : UIConstants.DANGER);
        changeLabel.setFont(UIConstants.FONT_SMALL);
        
        card.add(symbol);
        card.add(Box.createVerticalStrut(5));
        card.add(price);
        card.add(Box.createVerticalStrut(3));
        card.add(changeLabel);
        
        return card;
    }
    
    public void refresh() {
        removeAll();
        initComponents();
        revalidate();
        repaint();
    }
}
