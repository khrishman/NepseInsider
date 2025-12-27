# NepseInsider - Stock Market Platform

A comprehensive Java Swing application for tracking and managing NEPSE (Nepal Stock Exchange) stocks. Built with MVC architecture, featuring a modern dark theme with green accents and golden text.

## 🚀 Features

### User Features

- **Home Page**: Landing page with market overview, statistics, and recent stocks
- **Market Ticker**: Real-time market index display
- **Stock Tracking**: View all listed stocks with search and sort capabilities
- **Top Gainers/Losers**: Quick view of best and worst performing stocks
- **Recently Added**: Carousel showing last 5 added stocks using Queue data structure

### Admin Features

- **Categories Management**: Create, update, delete stock categories
- **SubCategories Management**: Manage sub-categories within categories
- **Markets Management**: Manage market indices and data
- **Upcoming Markets Management**: Handle IPOs and upcoming events
- **Live Markets Management**: Real-time market data management
- **Manage Users**: User CRUD operations, status management
- **Manage Stocks**: Full stock management with all details
- **Deposits Management**: Process and approve/reject deposits
- **Withdrawals Management**: Handle withdrawal requests
- **Comments Management**: Moderate user comments
- **Support Ticket Management**: Handle user support requests
- **Report Management**: Generate and view system reports
- **Subscribers Management**: Manage newsletter subscribers
- **System Settings**: Core system configuration
- **General Settings**: Application-wide settings
- **Logo and Favicon Management**: Upload and manage branding
- **System Configuration**: Advanced system config
- **Notification Settings**: Configure notifications

## 🔐 Login Credentials

**Admin Access:**

- Username: `admin`
- Password: `admin`

**Sample Users:**

- john / john123
- ram / ram123
- sita / sita123

## 🛠️ Technical Implementation

### MVC Architecture

```
src/
├── model/           # Data models
│   ├── Stock.java
│   ├── User.java
│   ├── Portfolio.java
│   ├── Category.java
│   ├── Market.java
│   ├── Transaction.java
│   ├── SupportTicket.java
│   └── SystemSettings.java
├── view/            # UI components
│   ├── MainFrame.java
│   ├── HomePanel.java
│   ├── LoginPanel.java
│   ├── AdminDashboard.java
│   ├── BackgroundPanel.java
│   └── UIConstants.java
├── controller/      # Business logic
│   ├── StockController.java
│   ├── UserController.java
│   └── AdminController.java
├── util/            # Utilities
│   ├── DataStructures.java
│   ├── SearchAlgorithms.java
│   ├── SortAlgorithms.java
│   └── ValidationUtils.java
└── NepseInsiderApp.java  # Entry point
```

### Data Structures Used

1. **ArrayList**: Main stock list storage
2. **LinkedList**: Transaction history, queue implementation
3. **HashMap**: Stock lookup by symbol, user lookup by username
4. **Stack**: Price change history, undo operations
5. **Queue**: Recently added stocks (FIFO - last 5 stocks)

### Algorithms Implemented

1. **Binary Search**: O(log n) search for sorted stock data

   - Search by symbol (exact match)
   - Search by price range
   - Search by year

2. **Linear Search**: O(n) search for flexible queries

   - Partial name matching
   - Sector search
   - Multi-criteria search

3. **QuickSort**: O(n log n) average sorting

   - Sort by price
   - Sort by market cap
   - Sort by volume
   - Sort by change percent

4. **MergeSort**: O(n log n) stable sorting
   - Sort by symbol
   - Sort by company name
   - Sort by year listed

### Validation & Exception Handling

- Input validation for all fields
- NumberFormatException handling for numeric inputs
- Duplicate stock symbol prevention
- Clear error messages for invalid inputs
- Year range validation (1937 - current)
- Email and phone format validation

## 🎨 UI Design

### Color Scheme

- **Background**: Dark blue-black (#0D1117)
- **Primary Accent**: Green (#2ECC71)
- **Text Highlight**: Gold (#FFD700)
- **Text Primary**: White/Light gray
- **Text Secondary**: Muted gray

### Typography

- Segoe UI font family
- Bold headings with golden color
- Clear hierarchy with size variations

### Components

- Custom painted buttons with gradients
- Rounded card panels
- Animated background with stock chart patterns
- Responsive sidebar navigation
- Styled tables with hover effects



## 📄 License

This project is created for educational purposes as part of the CS5003NI Data Structures and Specialist Programming coursework.

---

**Note**: This is a demonstration application. Stock data shown is sample/simulated data for educational purposes.
