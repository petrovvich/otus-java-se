package ru.petrovvich.study.H2;

import java.sql.*;

public class H2Demo {
    private static final String URL = "jdbc:h2:mem:";
    private final Connection connection;

    public H2Demo() throws SQLException, ClassNotFoundException {

        this.connection = DriverManager.getConnection(URL);
        this.connection.setAutoCommit(false);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        H2Demo demo = new H2Demo();
        demo.createTable();
        demo.insert(1, "Victor");
        demo.insert(2, "Alex");
        demo.insert(3, "Ibrahim");
        demo.select(1);
        demo.select(2);
        demo.select(3);
        demo.close();

    }

    private void createTable() throws SQLException {
        try (PreparedStatement p = connection.prepareStatement("CREATE TABLE Client (id INT, name varchar(255))")) {
            p.executeUpdate();
        }
    }

    private void insert(int id, String name) throws SQLException {
        try (PreparedStatement p = connection.prepareStatement("INSERT INTO Client (id, name) VALUES (?, ?)")) {
            Savepoint savepoint = this.connection.setSavepoint("beforeInsert");
            p.setInt(1, id);
            p.setString(2, name);
            try {
                int count = p.executeUpdate();
                this.connection.commit();
                System.out.println("Inserted count: " + count);
            } catch (SQLException e) {
                this.connection.rollback();
                System.out.println(e.getMessage());
            }
        }
    }

    private void select(int id) throws SQLException {
        try (PreparedStatement p = this.connection.prepareStatement("SELECT name FROM Client WHERE id = ?")){
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()){
                System.out.print("Name for id: " + id + " is: ");
                if (r.next()) {
                    System.out.println(r.getString("name"));
                }
            }
        }
    }

    public void close() throws SQLException {
        this.connection.close();
    }
}
