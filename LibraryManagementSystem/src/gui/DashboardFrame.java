package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DashboardFrame extends JFrame {

    private JButton addBookButton;
    private JButton viewBooksButton;
    private JButton issueBookButton;
    private JButton returnBookButton;
    private JButton logoutButton;

    public DashboardFrame() {
        setTitle("Library Management System - Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome to Library Dashboard");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBounds(100, 20, 300, 30);
        add(welcomeLabel);

        addBookButton = new JButton("Add Book");
        addBookButton.setBounds(150, 80, 200, 40);
        add(addBookButton);

        viewBooksButton = new JButton("View Books");
        viewBooksButton.setBounds(150, 130, 200, 40);
        add(viewBooksButton);

        issueBookButton = new JButton("Issue Book");
        issueBookButton.setBounds(150, 180, 200, 40);
        add(issueBookButton);

        returnBookButton = new JButton("Return Book");
        returnBookButton.setBounds(150, 230, 200, 40);
        add(returnBookButton);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(150, 280, 200, 40);
        add(logoutButton);

        // Button actions
        addBookButton.addActionListener(e -> {
            AddBookFrame addBookFrame = new AddBookFrame();
            addBookFrame.setVisible(true);
        });

        viewBooksButton.addActionListener(e -> {
            ViewBooksFrame viewBooksFrame = new ViewBooksFrame();
            viewBooksFrame.setVisible(true);
        });

        issueBookButton.addActionListener(e -> {
            IssueBookFrame issueBookFrame = new IssueBookFrame();
            issueBookFrame.setVisible(true);
        });

        returnBookButton.addActionListener(e -> {
            ReturnBookFrame returnBookFrame = new ReturnBookFrame();
            returnBookFrame.setVisible(true);
        });

        logoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logged out successfully!");
            this.dispose();
            new LoginFrame().setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardFrame().setVisible(true));
    }
}
