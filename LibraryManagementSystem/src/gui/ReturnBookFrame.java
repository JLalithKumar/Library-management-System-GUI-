package gui;

import database.DBConnection;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReturnBookFrame extends JFrame {
    private JTextField issueIdField;
    private JButton returnButton;

    public ReturnBookFrame() {
        setTitle("Return Book");
        setSize(400, 200);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel issueLabel = new JLabel("Issue ID:");
        issueLabel.setBounds(50, 50, 80, 25);
        add(issueLabel);

        issueIdField = new JTextField();
        issueIdField.setBounds(150, 50, 165, 25);
        add(issueIdField);

        returnButton = new JButton("Return Book");
        returnButton.setBounds(150, 100, 120, 30);
        add(returnButton);

        returnButton.addActionListener(e -> returnBook());
    }

    private void returnBook() {
        int issueId;

        try {
            issueId = Integer.parseInt(issueIdField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Issue ID must be a number!");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Get book_id for this issue
            PreparedStatement pst1 = conn.prepareStatement("SELECT book_id FROM issued_books WHERE issue_id=?");
            pst1.setInt(1, issueId);
            ResultSet rs = pst1.executeQuery();

            if (rs.next()) {
                int bookId = rs.getInt("book_id");

                // Delete from issued_books
                PreparedStatement pst2 = conn.prepareStatement("DELETE FROM issued_books WHERE issue_id=?");
                pst2.setInt(1, issueId);
                pst2.executeUpdate();

                // Increase quantity
                PreparedStatement pst3 = conn.prepareStatement("UPDATE books SET quantity=quantity+1 WHERE book_id=?");
                pst3.setInt(1, bookId);
                pst3.executeUpdate();

                JOptionPane.showMessageDialog(this, "Book returned successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Issue ID!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
