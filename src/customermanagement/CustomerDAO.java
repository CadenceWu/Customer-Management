package customermanagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
//Controller
//CustomerDAO works as an interface between the main application and the customer objects.

public class CustomerDAO {

    //Give the user options to implement database actions
    public static void askActions() {
        Scanner sc = new Scanner(System.in);
        boolean askAgain = false;

        System.out.println();
        System.out.println("What action would you like to implement? (Enter the number as followings)");
        System.out.println("  1: Add a new customer");
        System.out.println("  2: Delete the customer.");
        System.out.println("  3: Search the customer.");
        System.out.println("  4: Re-create the database.");
        System.out.println("  5: Exit the system.");
        do {
            try {
                int askUserAction = sc.nextInt();
                switch (askUserAction) {
                    case 1:
                        insertCustomer();
                        break;
                    case 2:
                        deleteCustomerByID();
                        break;
                    case 3:
                        searchCustomerByID();
                        break;
                    case 4:
                        reCreateDatabase();
                        break;
                    case 5:
                        System.exit(0);
                    default:
                        System.out.println(">>> Enter only number 1-5");
                        continue;
                }
                break;
            } catch (InputMismatchException err) {
                System.out.println("*** Please enter an interger or an appropriate value ***");
                askAgain = true;
                sc.nextLine();
            }
        } while (askAgain = true);
    }

    //Allow the user to enter a new customer data to the database
    public static void insertCustomer() {
        Scanner sc = new Scanner(System.in);
        Customer cus = new Customer();
        boolean askAgain = false;

        System.out.println();
        System.out.println(">>> You would like to add a new customer. <<<");
        System.out.println();
        System.out.println("Please answer the following questions.");
        String enterGivenName;
        String enterSurName;

        do {
            try {
                System.out.println("what is customer's ID?");
                cus.setId(sc.nextInt());
                System.out.println("What is customer's givenName?");
                enterGivenName = sc.next();
                System.out.println("What is customer's surname?");
                enterSurName = sc.next();
                cus.setName(enterGivenName + " " + enterSurName);
                System.out.println("what is customer's email?");
                cus.setEmail(sc.next());
                System.out.println("what is customer's phone number?");
                cus.setMobile(sc.next());

                String insertSQL = String.format("INSERT INTO customer (id,name,email,mobile) VALUES ('%d','%s','%s','%s');",
                        cus.getId(), cus.getName(), cus.getEmail(), cus.getMobile());
                int count = DBUtilAss.executeUpdate(insertSQL);

                if (count == 0) {
                    System.out.println("!!! Failed to add a new customer!!!");
                } else {
                    System.out.println("\n>>> The information you've entered was successfully added <<<");
                }
                break;
            } catch (InputMismatchException err) {
                System.out.println("*** Please enter an interger or an appropriate value ***");
                System.out.println();
                askAgain = true;
                sc.nextLine();
            }
        } while (askAgain = true);
    }

    //Allow the user to delete the customer by entering the ID
    public static void deleteCustomerByID() {
        Scanner sc = new Scanner(System.in);
        Customer cus = new Customer();
        boolean askAgain = false;
        do {
            try {
                System.out.println("Which customer would you like to delete? (Enter the ID only)");
                cus.setId(sc.nextInt());
                String deleteSQL = "DELETE FROM customer WHERE ID='" + cus.getId() + "';";
                int count = DBUtilAss.executeUpdate(deleteSQL);

                if (count == 0) {
                    System.out.println();
                    System.out.println(">>> Customer NOT found <<<");
                } else {
                    System.out.println("\n>>> The customer was successfully deleted!!!");
                }
                break;
            } catch (InputMismatchException err) {
                System.out.println("*** Please enter an interger or an appropriate value ***");
                System.out.println();
                askAgain = true;
                sc.nextLine();
            }
        } while (askAgain = true);
    }

    //Allow the user to search the customer by the ID
    public static Customer searchCustomerByID() {
        Scanner sc = new Scanner(System.in);
        Customer cus = new Customer();
        boolean askAgain = false;
        Customer c = null;
        do {
            try {
                System.out.println("Which customer would you like to search for? (Enter the ID only)");
                cus.setId(sc.nextInt());
                String query = "SELECT * FROM customer WHERE ID=" + cus.getId() + ";";

                ResultSet rs = DBUtilAss.executeQuery(query);

                if (rs.next()) {
                    c = new Customer();
                    c.setId(rs.getInt("ID"));
                    c.setName(rs.getString("Name"));
                    c.setEmail(rs.getString("Email"));
                    c.setMobile(rs.getString("Mobile"));
                    System.out.println();
                    System.out.println(">>> Found the customer <<<");
                    System.out.printf("%-1d   %-13s   %-20s %1s\n", c.getId(), c.getName(), c.getEmail(), c.getMobile());
                } else {
                    System.out.println();
                    System.out.println(">>> Customer NOT found <<<");
                }
                break;
            } catch (SQLException ex) {
                System.out.println("SQLException on executeQuery: " + ex.getMessage());
            } catch (InputMismatchException err) {
                System.out.println("*** Please enter an interger or an appropriate value ***");
                System.out.println();
                askAgain = true;
                sc.nextLine();
            }
        } while (askAgain = true);
        return c;

    }

    //The option to re-creat the default database
    public static void reCreateDatabase() {
        System.out.println("\n#############################");
        System.out.println("A database has been created");
        System.out.println("#############################");
        CreateAssDatabase.createSmtbizDB();
    }

    // Get all customer information in a collection object ArrayList.
    public static ArrayList<Customer> getAllCustomers() {
        String query = "SELECT * FROM customer;";
        ArrayList<Customer> customers = new ArrayList<>();

        try {
            ResultSet rs = DBUtilAss.executeQuery(query);

            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("ID"));
                c.setName(rs.getString("Name"));
                c.setEmail(rs.getString("Email"));
                c.setMobile(rs.getString("Mobile"));
                customers.add(c);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException on executeQuery: " + ex.getMessage());
        }

        return customers;
    }
}
