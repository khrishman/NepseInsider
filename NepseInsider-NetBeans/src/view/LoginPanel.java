package view;

import controller.UserController;
import util.ValidationUtils.ValidationResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

/**
 * LoginPanel - Modern login screen
 */
public class LoginPanel extends JPanel {
    
    private MainFrame mainFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel errorLabel;
    private UserController userController;
    
    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = UserController.getInstance();
        
        setBackground(UIConstants.BACKGROUND_DARK);
        setLayout(new BorderLayout());
        
        initComponents();
    }
    
    private void initComponents() {
        // Navigation
        add(new NavigationBar(mainFrame, MainFrame.LOGIN), BorderLayout.NORTH);
        
        // Center panel with login form
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(UIConstants.BACKGROUND_DARK);
        
        JPanel loginCard = createLoginCard();
        centerPanel.add(loginCard);
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private JPanel createLoginCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(UIConstants.BACKGROUND_SECONDARY);
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));
                g2d.setColor(UIConstants.PRIMARY_GREEN);
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, 16, 16));
                g2d.dispose();
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(400, 480));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        // Logo
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setOpaque(false);
        JLabel logoIcon = new JLabel("📈");
        logoIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        JLabel logoText = new JLabel("NepseInsider");
        logoText.setForeground(UIConstants.PRIMARY_GREEN);
        logoText.setFont(UIConstants.FONT_LOGO);
        logoPanel.add(logoIcon);
        logoPanel.add(logoText);
        card.add(logoPanel);
        card.add(Box.createVerticalStrut(20));
        
        // Welcome text
        JLabel welcomeLabel = new JLabel("Welcome Back");
        welcomeLabel.setForeground(UIConstants.GOLD);
        welcomeLabel.setFont(UIConstants.FONT_HEADING);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(welcomeLabel);
        
        JLabel subtitleLabel = new JLabel("Sign in to continue");
        subtitleLabel.setForeground(UIConstants.TEXT_SECONDARY);
        subtitleLabel.setFont(UIConstants.FONT_BODY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(subtitleLabel);
        card.add(Box.createVerticalStrut(25));
        
        // Error label
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(UIConstants.DANGER);
        errorLabel.setFont(UIConstants.FONT_SMALL);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(errorLabel);
        card.add(Box.createVerticalStrut(10));
        
        // Username field
        JLabel userLabel = new JLabel("Username");
        userLabel.setForeground(UIConstants.TEXT_SECONDARY);
        userLabel.setFont(UIConstants.FONT_BODY);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(userLabel);
        card.add(Box.createVerticalStrut(8));
        
        usernameField = UIConstants.createTextField();
        usernameField.setMaximumSize(new Dimension(320, 42));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(usernameField);
        card.add(Box.createVerticalStrut(15));
        
        // Password field
        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(UIConstants.TEXT_SECONDARY);
        passLabel.setFont(UIConstants.FONT_BODY);
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(passLabel);
        card.add(Box.createVerticalStrut(8));
        
        passwordField = UIConstants.createPasswordField();
        passwordField.setMaximumSize(new Dimension(320, 42));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(passwordField);
        card.add(Box.createVerticalStrut(25));
        
        // Login button
        loginButton = UIConstants.createPrimaryButton("Sign In");
        loginButton.setMaximumSize(new Dimension(320, 45));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(e -> performLogin());
        card.add(loginButton);
        card.add(Box.createVerticalStrut(20));
        
        // Credentials hint
        JLabel hintLabel = new JLabel("<html><center>Admin: admin / admin<br>User: khrishman / khadka</center></html>");
        hintLabel.setForeground(UIConstants.TEXT_MUTED);
        hintLabel.setFont(UIConstants.FONT_SMALL);
        hintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(hintLabel);
        
        // Enter key listener
        KeyListener enterListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        };
        usernameField.addKeyListener(enterListener);
        passwordField.addKeyListener(enterListener);
        
        return card;
    }
    
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty()) {
            showError("Please enter your username");
            usernameField.requestFocus();
            return;
        }
        
        if (password.isEmpty()) {
            showError("Please enter your password");
            passwordField.requestFocus();
            return;
        }
        
        loginButton.setEnabled(false);
        loginButton.setText("Signing in...");
        
        Timer timer = new Timer(300, e -> {
            ValidationResult result = userController.login(username, password);
            
            if (result.isValid()) {
                errorLabel.setText(" ");
                if (userController.isAdmin()) {
                    mainFrame.showAdminDashboard();
                } else {
                    mainFrame.showUserDashboard();
                }
            } else {
                showError(result.getErrorMessage());
                passwordField.setText("");
                passwordField.requestFocus();
            }
            
            loginButton.setEnabled(true);
            loginButton.setText("Sign In");
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
    }
    
    public void reset() {
        usernameField.setText("");
        passwordField.setText("");
        errorLabel.setText(" ");
        usernameField.requestFocus();
    }
}
