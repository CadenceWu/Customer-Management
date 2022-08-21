package customermanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
//View. Provide UI. Display the database

public class CustomerManagement {

    public static void main(String[] args) {
        CustomerDAO.reCreateDatabase();

        ArrayList<Customer> customers = CustomerDAO.getAllCustomers();
        displayCustomer(customers);
        System.out.println();

        boolean askAgain = false;

        do {
            CustomerDAO.askActions();
            customers = CustomerDAO.getAllCustomers();
            displayCustomer(customers);
        } while (askAgain = true);
    }

    // Get all customers data and show them
    private static void displayCustomer(ArrayList<Customer> customers) {
        if (customers.isEmpty()) {
            System.out.println("!!! No Customer Found!!!");
            return;
        }
        System.out.println("\n###########################");
        System.out.println("All customers' information");
        System.out.println("############################");
        System.out.printf(" %-10s %-15s %-20s %5s\n", "Id", "Name", "Email", "Mobile");
        for (Customer c : customers) {
            int id = c.getId();
            String name = c.getName();
            String email = c.getEmail();
            String mobile = c.getMobile();

            System.out.printf("%-1d   %-13s   %-20s %1s\n", id, name, email, mobile);
        }

    }

}
