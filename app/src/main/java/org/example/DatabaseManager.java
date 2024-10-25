package org.example;

import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/library_db";
    private static final String USER = ""; // Write your username here
    private static final String PASSWORD = ""; // Write the user's password here

    private Connection connection;

    public void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the PostgreSQL database successfully.");
        } catch (SQLException e) {
            System.err.println("Connection to PostgreSQL failed: " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the PostgreSQL database.");
            }
        } catch (SQLException e) {
            System.err.println("Error disconnecting: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void addBook(String title, String author, Date publishedDate, String isbn) throws SQLException {
        String sql = "INSERT INTO books (title, author, published_date, isbn) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setDate(3, new java.sql.Date(publishedDate.getTime()));
            stmt.setString(4, isbn);
            stmt.executeUpdate();
            System.out.println("The book has been added to the database.");
        }
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDate("published_date"),
                        rs.getString("isbn")
                ));
            }
        }
        return books;
    }

    public Book findBookByTitle(String title) throws SQLException {
        String sql = "SELECT * FROM books WHERE title = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getDate("published_date"),
                            rs.getString("isbn")
                    );
                }
            }
        }
        return null;
    }

    public void deleteBook(int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Book deleted from database.");
        }
    }

    public void addReader(String name, String email) throws SQLException {
        String sql = "INSERT INTO readers (name, email) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();
            System.out.println("Reader added to database.");
        }
    }

    public List<Reader> getAllReaders() throws SQLException {
        List<Reader> readers = new ArrayList<>();
        String sql = "SELECT * FROM readers";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                readers.add(new Reader(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                ));
            }
        }
        return readers;
    }

    public Reader findReaderByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM readers WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Reader(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email")
                    );
                }
            }
        }
        return null;
    }

    public void deleteReader(int id) throws SQLException {
        String sql = "DELETE FROM readers WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Reader deleted from database.");
        }
    }
}
