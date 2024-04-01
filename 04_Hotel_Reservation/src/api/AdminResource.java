package api;

import interfaces.IRoom;
import model.Customer;
import model.RoomType;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;

/**
 * The type Admin resource. API class to handle admin functionality
 */
public class AdminResource {

    private final static CustomerService CUSTOMER_SERVICE = CustomerService.getInstance();
    private final static ReservationService RESERVATION_SERVICE = ReservationService.getInstance();
    /**
     * Gets customer.
     *
     * @param email the email
     * @return the customer
     */
    public static Customer getCustomer(String email) {
        return CUSTOMER_SERVICE.getCustomer(email);
    }

    /**
     * Adds room.
     *
     * @param roomNumber the room number
     * @param price      the price
     * @param roomType   the room type
     */
    public static void addRoom(String roomNumber, Double price, RoomType roomType) {
        if (price == 0.0)
            RESERVATION_SERVICE.createFreeRoom(roomNumber, price, roomType);
        else
            RESERVATION_SERVICE.createRoom(roomNumber, price, roomType);
    }


    public static IRoom getRoom(String roomNumber) {
        return RESERVATION_SERVICE.getARoom(roomNumber);
    }
    /**
     * Gets all rooms.
     *
     * @return the all rooms collection
     */
    public static Collection<IRoom> getAllRooms() {
        return RESERVATION_SERVICE.getAllRooms();
    }

    /**
     * Gets all customers.
     *
     * @return the all customers collection
     */
    public static Collection<Customer> getAllCustomers() {
        return CUSTOMER_SERVICE.getAllCustomers();
    }

    /**
     * Display all reservations.
     */
    public static void displayAllReservations() {
        RESERVATION_SERVICE.printAllReservations();
    }

    /**
     * Display all rooms.
     */
    public static void displayAllRooms() {
        RESERVATION_SERVICE.printAllRooms();
    }

    /**
     * Display all customers.
     */
    public static void displayAllCustomers() {
        CUSTOMER_SERVICE.printAllCustomers();
    }
}
