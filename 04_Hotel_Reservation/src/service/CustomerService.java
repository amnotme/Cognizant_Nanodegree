package service;

import java.util.*;
import model.Customer;
import helper.*;

/**
 * The type Customer service. Logic for Customer service
 */
public final class CustomerService {

    /**
     * Instantiates a new Customer service.
     */
    private CustomerService() {}

    /**
     * The constant INSTANCE.
     */
    private static  CustomerService INSTANCE;

    /**
     * Gets instance.
     *
     * @return the instance of CustomerService
     */
    public static CustomerService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomerService();
        }
        return INSTANCE;
    }

    /**
     * The constant customers.
     */
    public Collection<Customer> customers = new ArrayList<Customer>();

    /**
     * Add customer.
     *
     * @param email     the email
     * @param firstName the first name
     * @param lastName  the last name
     */
    public void addCustomer(String email, String firstName, String lastName) {
        Customer tempCustomer = new Customer(firstName, lastName, email);
        customers.add(tempCustomer);
    }

    /**
     * Gets customer.
     *
     * @param customerEmail the customer email
     * @return the customer
     */
    public Customer getCustomer(String customerEmail) {
        for (Customer customer : customers) {
            if (customer.getEmail().equals(customerEmail))
                return customer;
        }
        return null;
    }

    /**
     * Gets all customers.
     *
     * @return the all customers
     */
    public Collection<Customer> getAllCustomers() {
        return customers;
    }

    /**
     * Print all customers.
     */
    public void printAllCustomers() {
        for (Customer customer : customers)
            System.out.println(Color.colorText(Color.BLUE_BOLD_BRIGHT, customer.toString()));
    }
}
