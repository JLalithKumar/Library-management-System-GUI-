package gui;

import database.DBConnection;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IssueBookFrame extends JFrame {
    private JTextField userIdField, bookIdField;
    private JButton issueButton;

    public IssueBookFrame() {
        setTitle("Issue Book");
        setSize(400, 250);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel userLabel = new JLabel("User ID:");
        userLabel.setBounds(50, 50, 80, 25);
        add(userLabel);

        userIdField = new JTextField();
        userIdField.setBounds(150, 50, 165, 25);
        add(userIdField);

        JLabel bookLabel = new JLabel("Book ID:");
        bookLabel.setBounds(50, 90, 80, 25);
        add(bookLabel);

        bookIdField = new JTextField();
        bookIdField.setBounds(150, 90, 165, 25);
        add(bookIdField);

        issueButton = new JButton("Issue Book");
        issueButton.setBounds(150, 140, 120, 30);
        add(issueButton);

        issueButton.addActionListener(e -> issueBook());
    }

    private void issueBook() {
        int userId, bookId;

        try {
            userId = Integer.parseInt(userIdField.getText());
            bookId = Integer.parseInt(bookIdField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "IDs must be numbers!");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Check if book exists and quantity > 0
            PreparedStatement pst1 = conn.prepareStatement("SELECT quantity FROM books WHERE book_id=?");
            pst1.setInt(1, bookId);
            ResultSet rs = pst1.executeQuery();

            if (rs.next() && rs.getInt("quantity") > 0) {
                // Insert into issued_books
                PreparedStatement pst2 = conn.prepareStatement(
                        "INSERT INTO issued_books (user_id, book_id, issue_date) VALUES (?, ?, CURDATE())");
                pst2.setInt(1, userId);
                pst2.setInt(2, bookId);
                pst2.executeUpdate();

                // Decrease quantity
                PreparedStatement pst3 = conn.prepareStatement("UPDATE books SET quantity=quantity-1 WHERE book_id=?");
                pst3.setInt(1, bookId);
                pst3.executeUpdate();

                JOptionPane.showMessageDialog(this, "Book issued successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Book not available!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
