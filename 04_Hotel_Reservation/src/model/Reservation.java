package model;

import interfaces.IRoom;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * The type Reservation. Reservation model class.
 */
public class Reservation {

    /**
     * The Date format string.
     */
    private final String DATE_FORMAT_STRING = "MM/dd/yyyy";

    /**
     * The Date formatter.
     */
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(this.DATE_FORMAT_STRING);

    /**
     * The Customer.
     */
    private final Customer customer;

    /**
     * The Room.
     */
    private final IRoom room;

    /**
     * The Check in date.
     */
    private final Date checkInDate;

    /**
     * The Check out date.
     */
    private final Date checkOutDate;

    /**
     * Instantiates a new Reservation.
     *
     * @param customer     the customer
     * @param room         the room
     * @param checkInDate  the check in date
     * @param checkOutDate the check out date
     */
    public Reservation(
            Customer customer,
            IRoom room,
            Date checkInDate,
            Date checkOutDate
    ) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    /**
     * Gets customer.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }


    /**
     * Gets room.
     *
     * @return the room
     */
    public IRoom getRoom() {
        return room;
    }


    /**
     * Gets check in date.
     *
     * @return the check in date
     */
    public Date getCheckInDate() {
        return checkInDate;
    }


    /**
     * Gets check out date.
     *
     * @return the check out date
     */
    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return DATE_FORMAT_STRING.equals(that.DATE_FORMAT_STRING) && dateFormatter.equals(that.dateFormatter) && customer.equals(that.customer) && Objects.equals(room, that.room) && checkInDate.equals(that.checkInDate) && checkOutDate.equals(that.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(DATE_FORMAT_STRING, dateFormatter, customer, room, checkInDate, checkOutDate);
    }

    @Override
    public String toString() {
        return "Reservation Info: \n"
                + getCustomer().toString() + "\n"
                + "Room number [" + this.getRoom().getRoomNumber() + "] - "
                + "Check-in: " + dateFormatter.format(this.getCheckInDate()) + " "
                + "Check-out: " + dateFormatter.format(this.getCheckOutDate()) + "\n"
                + "Price per night: $" + this.getRoom().getRoomPrice();
    }
}
