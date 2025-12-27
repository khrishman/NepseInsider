package view;

import controller.StockController;
import model.Stock;
import util.SortAlgorithms.SortOrder;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * StocksPanel - Browse and search stocks
 */
public class StocksPanel extends JPanel {
    
    private MainFrame mainFrame;
    private StockController stockController;
    private JTable stockTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> sectorFilter;
    private JComboBox<String> sortBy;
    
    public StocksPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.stockController = StockController.getInstance();
        
        setBackground(UIConstants.BACKGROUND_DARK);
        setLayout(new BorderLayout());
        
        initComponents();
    }
    
    private void initComponents() {
        add(new NavigationBar(mainFrame, MainFrame.STOCKS), BorderLayout.NORTH);
        
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UIConstants.BACKGROUND_DARK);
        content.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        // Header with title and controls
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JLabel title = new JLabel("All Stocks");
        title.setForeground(UIConstants.GOLD);
        title.setFont(UIConstants.FONT_HEADING);
        
        // Search and filter panel
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        controlsPanel.setOpaque(false);
        
        // Search field
        searchField = UIConstants.createTextField();
        searchField.setPreferredSize(new Dimension(200, 38));
        searchField.putClientProperty("JTextField.placeholderText", "Search stocks...");
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterStocks();
            }
        });

// Sector filter
        List<String> sectors = stockController.getAllSectors();
        String[] sectorArray = new String[sectors.size() + 1];
        sectorArray[0] = "All Sectors";
        for (int i = 0; i < sectors.size(); i++) {
            sectorArray[i + 1] = sectors.get(i);
        }
        sectorFilter = new JComboBox<>(sectorArray);
        sectorFilter.setBackground(Color.WHITE);
        sectorFilter.setForeground(Color.BLACK);
        sectorFilter.setPreferredSize(new Dimension(150, 38));
        sectorFilter.addActionListener(e -> filterStocks());

        // Sort by
        String[] sortOptions = {"Symbol", "Price (Low-High)", "Price (High-Low)", "Change %"};
        sortBy = new JComboBox<>(sortOptions);
        sortBy.setBackground(Color.WHITE);
        sortBy.setForeground(Color.BLACK);
        sortBy.setPreferredSize(new Dimension(150, 38));
        sortBy.addActionListener(e -> sortStocks());
        
        controlsPanel.add(new JLabel("Search:") {{ setForeground(UIConstants.TEXT_PRIMARY); }});
        controlsPanel.add(searchField);
        controlsPanel.add(new JLabel("Sector:") {{ setForeground(UIConstants.TEXT_PRIMARY); }});
        controlsPanel.add(sectorFilter);
        controlsPanel.add(new JLabel("Sort:") {{ setForeground(UIConstants.TEXT_PRIMARY); }});
        controlsPanel.add(sortBy);
        
        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(controlsPanel, BorderLayout.EAST);
        
        content.add(headerPanel, BorderLayout.NORTH);
        
        // Stock table
        String[] columns = {"Symbol", "Company Name", "Sector", "Price", "Change %", "Market Cap", "Volume"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        stockTable = new JTable(tableModel);
        UIConstants.styleTable(stockTable);
        
        // Custom renderer for change column
        stockTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null) {
                    String val = value.toString();
                    if (val.startsWith("+") || val.startsWith("▲")) {
                        c.setForeground(UIConstants.SUCCESS);
                    } else if (val.startsWith("-") || val.startsWith("▼")) {
                        c.setForeground(UIConstants.DANGER);
                    } else {
                        c.setForeground(UIConstants.TEXT_PRIMARY);
                    }
                }
                if (isSelected) {
                    c.setBackground(UIConstants.PRIMARY_GREEN);
                } else {
                    c.setBackground(UIConstants.BACKGROUND_CARD);
                }
                return c;
            }
        });
        
        loadStocks(stockController.getAllStocks());
        
        JScrollPane scrollPane = UIConstants.createScrollPane(stockTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        content.add(scrollPane, BorderLayout.CENTER);
        
        add(content, BorderLayout.CENTER);
    }
    
    private void loadStocks(List<Stock> stocks) {
        tableModel.setRowCount(0);
        
        for (Stock stock : stocks) {
            double change = stock.getChangePercent();
            String changeStr = String.format("%s%.2f%%", change >= 0 ? "▲ " : "▼ ", Math.abs(change));
            
            Object[] row = {
                stock.getSymbol(),
                stock.getCompanyName(),
                stock.getSector(),
                UIConstants.formatCurrency(stock.getCurrentPrice()),
                changeStr,
                UIConstants.formatLargeNumber(stock.getMarketCap()),
                String.format("%,d", stock.getVolume())
            };
            tableModel.addRow(row);
        }
    }
    
    private void filterStocks() {
        String searchText = searchField.getText().trim().toLowerCase();
        String selectedSector = (String) sectorFilter.getSelectedItem();
        
        List<Stock> allStocks = stockController.getAllStocks();
        List<Stock> filtered = new java.util.ArrayList<>();
        
        for (Stock stock : allStocks) {
            boolean matchesSearch = searchText.isEmpty() || 
                stock.getSymbol().toLowerCase().contains(searchText) ||
                stock.getCompanyName().toLowerCase().contains(searchText);
            
            boolean matchesSector = "All Sectors".equals(selectedSector) ||
                stock.getSector().equals(selectedSector);
            
            if (matchesSearch && matchesSector) {
                filtered.add(stock);
            }
        }
        
        loadStocks(filtered);
    }
    
    private void sortStocks() {
        String selected = (String) sortBy.getSelectedItem();
        List<Stock> stocks = stockController.getAllStocks();
        
        switch (selected) {
            case "Price (Low-High)":
                stockController.sortByPrice(SortOrder.ASCENDING);
                break;
            case "Price (High-Low)":
                stockController.sortByPrice(SortOrder.DESCENDING);
                break;
            case "Change %":
                stockController.sortByChangePercent(SortOrder.DESCENDING);
                break;
            default:
                // Symbol - default order
                break;
        }
        
        filterStocks();
    }
    
    public void refresh() {
        removeAll();
        initComponents();
        revalidate();
        repaint();
    }
}
