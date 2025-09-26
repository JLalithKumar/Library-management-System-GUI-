package gui;

import database.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewBooksFrame extends JFrame {

    private JTable table;

    public ViewBooksFrame() {
        setTitle("View Books");
        setSize(600, 400);
        setLocationRelativeTo(null);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        loadBooks();
    }

    private void loadBooks() {
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");

            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Title", "Author", "Quantity"}, 0);
            while (rs.next()) {
                int id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int qty = rs.getInt("quantity");
                model.addRow(new Object[]{id, title, author, qty});
            }

            table.setModel(model);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
