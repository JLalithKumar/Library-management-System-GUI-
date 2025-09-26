package gui;

import database.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddBookFrame extends JFrame {
    private JTextField titleField, authorField, quantityField;
    private JButton addButton;

    public AddBookFrame() {
        setTitle("Add Book");
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(50, 50, 80, 25);
        add(titleLabel);

        titleField = new JTextField();
        titleField.setBounds(150, 50, 165, 25);
        add(titleField);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(50, 90, 80, 25);
        add(authorLabel);

        authorField = new JTextField();
        authorField.setBounds(150, 90, 165, 25);
        add(authorField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(50, 130, 80, 25);
        add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setBounds(150, 130, 165, 25);
        add(quantityField);

        addButton = new JButton("Add Book");
        addButton.setBounds(150, 180, 120, 30);
        add(addButton);

        addButton.addActionListener(e -> addBook());
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        int quantity;

        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity must be a number!");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO books (title, author, quantity) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, title);
            pst.setString(2, author);
            pst.setInt(3, quantity);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book added successfully!");
            titleField.setText("");
            authorField.setText("");
            quantityField.setText("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
