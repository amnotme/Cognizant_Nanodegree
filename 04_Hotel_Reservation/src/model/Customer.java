package model;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Customer. Customer model class
 */
public class Customer {

    /**
     * The Email patter string.
     * <p>
     * This regular expression refers to a pattern which must start with “_A-Za-z0-9-\+” ,
     * optional follow by “.[_A-Za-z0-9-]”, and end with a “@” symbol.
     * The email’s domain name must start with “A-Za-z0-9-“, follow by first level Tld (.com, .net)
     * “.[A-Za-z0-9]” and optional follow by a second level Tld (.com.au, .com.my) “\.[A-Za-z]{2,}”,
     * where second level Tld must start with a dot “.” and length must equal or more than 2 characters.
     */
    private final static String EMAIL_PATTER_STRING =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    /**
     * The First name.
     */
    private final String firstName;

    /**
     * The Last name.
     */
    private final String lastName;

    /**
     * The Email.
     */
    private final String email;

    /**
     * Instantiates a new Customer.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email
     */
    public Customer(
            String firstName,
            String lastName,
            String email
    ) {
        this.firstName = firstName;
        this.lastName = lastName;

        this.email = email;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Is email valid string.
     *
     * @param email the email to validate
     * @return the validated email.
     */
    private String isEmailValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTER_STRING);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches())
            throw new IllegalArgumentException("[" + email + "] " + "That's an invalid email format. Please use the following format: (email@address.com)");
        return email;
    }

    @Override
    public String toString() {
        return "Customer Name: " + this.getFirstName() + " " + this.getLastName() + ", Email: " + getEmail();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return firstName.equals(customer.firstName) && lastName.equals(customer.lastName) && email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }
}
