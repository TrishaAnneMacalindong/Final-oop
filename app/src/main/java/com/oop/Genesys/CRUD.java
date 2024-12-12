package com.oop.Genesys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Customer;
import models.Customer2;
import models.SQLquery;

public class CRUD {
    private static final String DB_URL = "jdbc:mysql://database-management.crcukwsq0ap7.ap-southeast-2.rds.amazonaws.com:3306/laundry_ops?useSSL=false&serverTimezone=UTC";
    private static final String USER = "admin";
    private static final String PASSWORD = "August_172024";

    private Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            System.err.println("Connection failed!" + e);
        }
        return connection;
    }

    public void createPriceTable() {
        String query = "CREATE TABLE IF NOT EXISTS price ("
                     + "id INT AUTO_INCREMENT PRIMARY KEY, "
                     + "item VARCHAR(255) NOT NULL, "
                     + "quantity INT NOT NULL, "
                     + "price DECIMAL(10, 2) NOT NULL"
                     + ");";
        try (Connection connection = this.connect(); 
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.executeUpdate();
            System.out.println("Price table created successfully!");
        } catch (SQLException e) {
            System.err.println("Error during table creation: " + e.getMessage());
        }
    }

    public boolean createPrice(SQLquery sql) {
        String query = "INSERT INTO price (item, quantity, price) VALUES (?, ?, ?)";
        try (Connection connection = this.connect(); 
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, sql.name); 
            statement.setInt(2, sql.number);
            statement.setDouble(3, sql.price);
            
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new price record was inserted successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error during CREATE operation for price: " + e.getMessage());
        }
        return false;
    }

    public List<SQLquery> readPrice() {
        String query = "SELECT * FROM price";
        
        List<SQLquery> items = new ArrayList<>();
        
        try (Connection connection = this.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int price = resultSet.getInt("price");
                int quantity = resultSet.getInt("quantity");

                SQLquery item = new SQLquery(0, "Item", quantity, price);
                items.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Error during READ operation: " + e.getMessage());
        }
        return items;
    }

    public boolean updatePrice(SQLquery sql) {
        String query = "UPDATE price SET item = ?, quantity = ?, price = ? WHERE id = ?";
        try (Connection connection = this.connect(); 
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, sql.name);  // Updated item name
            statement.setInt(2, sql.number);  // Updated quantity
            statement.setDouble(3, sql.price); // Updated price
            statement.setInt(4, sql.id);       // ID of the record
            
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Price record with ID " + sql.id + " was updated successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error during UPDATE operation for price: " + e.getMessage());
        }
        return false;
    }

    public boolean deletePrice(int id) {
        String query = "DELETE FROM price WHERE id = ?";
        try (Connection connection = this.connect(); 
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, id);
            
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Price record with ID " + id + " was deleted successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error during DELETE operation for price: " + e.getMessage());
        }
        return false;
    }
    
    public void createCustomerTable() {
        String query = "CREATE TABLE IF NOT EXISTS customer ("
                     + "id INT AUTO_INCREMENT PRIMARY KEY, "
                     + "name VARCHAR(255) NOT NULL, "
                     + "number VARCHAR(255) NOT NULL, "
                     + "address VARCHAR(255) NOT NULL, "
                     + "itemName VARCHAR(255) NOT NULL, "
                     + "itemPrice INT NOT NULL, "
                     + "itemNumber INT NOT NULL, "
                     + "confirmed BOOLEAN NOT NULL"
                     + ");";
        try (Connection connection = this.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
            System.out.println("Customer table created successfully!");
        } catch (SQLException e) {
            System.err.println("Error during table creation: " + e.getMessage());
        }
    }

    public boolean createCustomer(Customer customer) {
        String query = "INSERT INTO customer (name, number, address, itemName, itemPrice, itemNumber, confirmed) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = this.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.name);
            statement.setString(2, customer.number);
            statement.setString(3, customer.address);
            statement.setString(4, customer.itemName);
            statement.setInt(5, customer.itemPrice);
            statement.setInt(6, customer.itemNumber);
            statement.setBoolean(7, customer.confirmed);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new customer record was inserted successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error during CREATE operation for customer: " + e.getMessage());
        }
        return false;
    }

    public List<Customer2> readCustomers() {
        String query = "SELECT * FROM customer";
        List<Customer2> customers = new ArrayList<>();
        
        try (Connection connection = this.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String number = resultSet.getString("number");
                String address = resultSet.getString("address");
                String itemName = resultSet.getString("itemName");
                int itemPrice = resultSet.getInt("itemPrice");
                int itemNumber = resultSet.getInt("itemNumber");
                boolean confirmed = resultSet.getBoolean("confirmed");

                Customer2 customer = new Customer2(id, name, number, address, itemName, itemPrice, itemNumber, confirmed);
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.err.println("Error during READ operation: " + e.getMessage());
        }
        return customers;
    }

    public boolean updateCustomer(Customer2 customer) {
        String query = "UPDATE customer SET name = ?, number = ?, address = ?, itemName = ?, itemPrice = ?, itemNumber = ?, confirmed = ? WHERE id = ?";
        try (Connection connection = this.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.name);
            statement.setString(2, customer.number);
            statement.setString(3, customer.address);
            statement.setString(4, customer.itemName);
            statement.setInt(5, customer.itemPrice);
            statement.setInt(6, customer.itemNumber);
            statement.setBoolean(7, customer.confirmed);
            statement.setInt(8, customer.id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Customer record with ID " + customer.id + " was updated successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error during UPDATE operation for customer: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteCustomer(int id) {
        String query = "DELETE FROM customer WHERE id = ?";
        try (Connection connection = this.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Customer record with ID " + id + " was deleted successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error during DELETE operation for customer: " + e.getMessage());
        }
        return false;
    }
}