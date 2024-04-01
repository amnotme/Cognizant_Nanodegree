package api;

import helper.Color;
import interfaces.IRoom;
import model.Customer;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;
import view.MainMenu;

import java.util.Collection;
import java.util.Date;

/**
 * The type Hotel resource. API class to handle Hotel functionality
 */
public class HotelResource {

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
     * Create a customer.
     *
     * @param email     the email
     * @param firstName the first name
     * @param lastName  the last name
     */
    public static void createACustomer(String email, String firstName, String lastName) {
        CUSTOMER_SERVICE.addCustomer(email, firstName, lastName);
    }

    /**
     * Gets room.
     *
     * @param roomNumber the room number
     * @return the room
     */
    public static IRoom getRoom(String roomNumber) {
        return RESERVATION_SERVICE.getARoom(roomNumber);
    }

    /**
     * Book a room reservation.
     *
     * @param customerEmail the customer email
     * @param room          the room
     * @param checkInDate   the check in date
     * @param checkOutDate  the check-out date
     * @return the reservation
     */
    public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return RESERVATION_SERVICE.reserveARoom(CUSTOMER_SERVICE.getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    /**
     * Gets customer reservations.
     *
     * @param customerEmail the customer email
     * @return the customer reservations
     */
    public static Collection<Reservation> getCustomerReservations(String customerEmail) {
        return RESERVATION_SERVICE.getCustomerReservation(getCustomer(customerEmail));
    }

    /**
     * Find a room collection.
     *
     * @param checkIn  the check in
     * @param checkOut the check-out
     * @return the collection
     */
    public static Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return RESERVATION_SERVICE.findRooms(checkIn, checkOut);
    }

    /**
     * Display all reservations tied to a specific customer
     *
     * @param customerEmail The customer email.
     */
    public static void displayCustomerReservations(String customerEmail) {
        if ((getCustomerReservations(customerEmail)).isEmpty())
            System.out.println(Color.colorText(Color.RED_BOLD_BRIGHT, MainMenu.MY_RESERVATION_PROMPTS.get("noReservations")));
        else
            RESERVATION_SERVICE.printCustomerReservations(getCustomerReservations(customerEmail));
    }

    /**
     * Display all selected rooms.
     *
     * @param rooms The rooms to be displayed.
     */
    public static void displaySelectedRooms(Collection<IRoom> rooms) {
        RESERVATION_SERVICE.printSelectedRooms(rooms);
    }
}
