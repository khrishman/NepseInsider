package view;

import controller.AdminController;
import controller.StockController;
import model.Stock;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * MarketsPanel - Shows market overview and indices
 */
public class MarketsPanel extends JPanel {
    
    private MainFrame mainFrame;
    private AdminController adminController;
    private StockController stockController;
    
    public MarketsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.adminController = AdminController.getInstance();
        this.stockController = StockController.getInstance();
        
        setBackground(UIConstants.BACKGROUND_DARK);
        setLayout(new BorderLayout());
        
        initComponents();
    }
    
    private void initComponents() {
        add(new NavigationBar(mainFrame, MainFrame.MARKETS), BorderLayout.NORTH);
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UIConstants.BACKGROUND_DARK);
        content.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        // Title
        JLabel title = new JLabel("Market Overview");
        title.setForeground(UIConstants.GOLD);
        title.setFont(UIConstants.FONT_HEADING);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(title);
        content.add(Box.createVerticalStrut(30));
        
        // Market indices cards
        JPanel indicesPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        indicesPanel.setOpaque(false);
        indicesPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        indicesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        AdminController.MarketData nepse = adminController.getPrimaryMarket();
        if (nepse != null) {
            indicesPanel.add(createIndexCard("NEPSE Index", nepse.indexValue, nepse.change, nepse.changePercent));
        }
        indicesPanel.add(createIndexCard("Sensitive Index", 458.32, 6.22, 1.38));
        indicesPanel.add(createIndexCard("Float Index", 142.56, -0.72, -0.50));
        
        content.add(indicesPanel);
        content.add(Box.createVerticalStrut(30));
        
        // Market summary
        JLabel summaryTitle = new JLabel("Market Summary");
        summaryTitle.setForeground(UIConstants.TEXT_PRIMARY);
        summaryTitle.setFont(UIConstants.FONT_SUBHEADING);
        summaryTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(summaryTitle);
        content.add(Box.createVerticalStrut(15));
        
        // Summary stats
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setOpaque(false);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        statsPanel.add(createSummaryCard("Total Turnover", "Rs. 2.5 Billion"));
        statsPanel.add(createSummaryCard("Total Traded Shares", "5.25 Million"));
        statsPanel.add(createSummaryCard("Total Transactions", "42,856"));
        statsPanel.add(createSummaryCard("Market Status", "OPEN"));
        
        content.add(statsPanel);
        content.add(Box.createVerticalStrut(30));
        
        // Sector performance
        JLabel sectorTitle = new JLabel("Sector Performance");
        sectorTitle.setForeground(UIConstants.TEXT_PRIMARY);
        sectorTitle.setFont(UIConstants.FONT_SUBHEADING);
        sectorTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(sectorTitle);
        content.add(Box.createVerticalStrut(15));
        
        JPanel sectorPanel = createSectorTable();
        sectorPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(sectorPanel);
        
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBackground(UIConstants.BACKGROUND_DARK);
        scrollPane.getViewport().setBackground(UIConstants.BACKGROUND_DARK);
        scrollPane.setBorder(null);
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createIndexCard(String name, double value, double change, double changePercent) {
        JPanel card = new JPanel();
        card.setBackground(UIConstants.BACKGROUND_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setForeground(UIConstants.TEXT_SECONDARY);
        nameLabel.setFont(UIConstants.FONT_BODY);
        
        JLabel valueLabel = new JLabel(String.format("%.2f", value));
        valueLabel.setForeground(UIConstants.TEXT_PRIMARY);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        
        JLabel changeLabel = new JLabel(String.format("%+.2f (%+.2f%%)", change, changePercent));
        changeLabel.setForeground(change >= 0 ? UIConstants.SUCCESS : UIConstants.DANGER);
        changeLabel.setFont(UIConstants.FONT_BODY_BOLD);
        
        card.add(nameLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(changeLabel);
        
        return card;
    }
    
    private JPanel createSummaryCard(String label, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(UIConstants.BACKGROUND_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel labelComp = new JLabel(label);
        labelComp.setForeground(UIConstants.TEXT_SECONDARY);
        labelComp.setFont(UIConstants.FONT_SMALL);
        
        JLabel valueComp = new JLabel(value);
        valueComp.setForeground(UIConstants.TEXT_PRIMARY);
        valueComp.setFont(UIConstants.FONT_BODY_BOLD);
        
        card.add(labelComp, BorderLayout.NORTH);
        card.add(valueComp, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createSectorTable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350));
        
        String[] columns = {"Sector", "Companies", "Turnover", "Change %"};
        
        Map<String, Integer> sectorMap = stockController.getStocksBySector();
        Object[][] data = new Object[sectorMap.size()][4];
        
        int i = 0;
        double[] changes = {1.25, -0.85, 2.10, 0.55, -1.20, 1.80};
        for (Map.Entry<String, Integer> entry : sectorMap.entrySet()) {
            data[i][0] = entry.getKey();
            data[i][1] = entry.getValue();
            data[i][2] = "Rs. " + (Math.random() * 500 + 100) + " M";
            data[i][3] = String.format("%+.2f%%", changes[i % changes.length]);
            i++;
        }
        
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        UIConstants.styleTable(table);
        
        JScrollPane scrollPane = UIConstants.createScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(0, 300));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    public void refresh() {
        removeAll();
        initComponents();
        revalidate();
        repaint();
    }
}
