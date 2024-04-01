package service;

import interfaces.IRoom;
import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.*;

import helper.*;
import view.MainMenu;

/**
 * The type Reservation service. Reservation service class which hold all business logic.
 */
public final class ReservationService {

    /**
     * Instantiates a new Reservation service.
     */
    private ReservationService() {}

    /**
     * The constant INSTANCE.
     */
    private static ReservationService INSTANCE;

    /**
     * Gets instance.
     *
     * @return the instance of ReservationService
     */
    public static ReservationService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReservationService();
        }
        return INSTANCE;
    }

    /**
     * The constant reservations.
     */
    public Collection<Reservation> reservations = new ArrayList<Reservation>();

    /**
     * The constant rooms.
     */
    public Collection<IRoom> rooms = new ArrayList<IRoom>();

    /**
     * Add room.
     *
     * @param room the room
     */
    void addRoom(IRoom room) {
        rooms.add(room);
    }

    /**
     * Create room.
     *
     * @param roomNumber the room number
     * @param price      the price
     * @param roomType   the room type
     */
    public void createRoom(String roomNumber, Double price, RoomType roomType) {
        Room room = new Room(roomNumber, price, roomType);
        addRoom(room);
    }

    /**
     * Create free room.
     *
     * @param roomNumber the room number
     * @param price      the price
     * @param roomType   the room type
     */
    public void createFreeRoom(String roomNumber, Double price, RoomType roomType) {
        FreeRoom room = new FreeRoom(roomNumber, price, roomType);
        addRoom(room);
    }

    /**
     * Gets a room.
     *
     * @param roomId the room id
     * @return the room
     */
    public IRoom getARoom(String roomId) {
        for (IRoom room : rooms)
            if (room.getRoomNumber().equals(roomId))
                return room;
        return null;
    }

    /**
     * Make a room reservation.
     *
     * @param customer     the customer
     * @param room         the room
     * @param checkInDate  the check in date
     * @param checkOutDate the check out date
     * @return the reservation
     */
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation tempReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(tempReservation);

        return tempReservation;
    }

    /**
     * Is bookable functions takes the core logic for finding a bookable date and returns a boolean.
     *
     * @param roomNumber   the room number
     * @param checkInDate  the check in date
     * @param checkOutDate the check-out date
     * @return the boolean
     */
    private boolean isBookable(String roomNumber, Date checkInDate, Date checkOutDate) {
        boolean bookable = true;

        for (Reservation reservation : reservations) {
            if (reservation.getRoom().getRoomNumber().equals(roomNumber)) {
                // 1st case.  same start time                 or same end time
                if (reservation.getCheckInDate().equals(checkInDate) || reservation.getCheckOutDate().equals(checkOutDate)) {
                    bookable = false;
                    break ;
                    // 2nd case.  new start is before start        and new end is after end
                } else if (checkInDate.before(reservation.getCheckInDate()) && checkOutDate.after(reservation.getCheckOutDate())) {
                    bookable = false;
                    break ;
                    // 3rd case.  new start is after start        and new start is before end
                } else if (checkInDate.after(reservation.getCheckInDate()) && checkInDate.before(reservation.getCheckOutDate())) {
                    bookable = false;
                    break ;
                    // 4th case. new end is after start         and new end is before end
                } else if (checkOutDate.after(reservation.getCheckInDate()) && checkOutDate.before(reservation.getCheckOutDate())) {
                    bookable = false;
                    break ;
                }
            }
        }
        return bookable;
    }

    /**
     * Find rooms. Main logic to get booking times
     *
     * @param checkInDate  the check in date
     * @param checkOutDate the check out date
     * @return the collection
     */
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> tempRooms = new ArrayList<IRoom>();

        for (IRoom room : rooms) {
            String roomNumber = room.getRoomNumber();
            boolean bookable = true;

            bookable = isBookable(roomNumber, checkInDate, checkOutDate);

            if (bookable)
                tempRooms.add(room);
        }
        return tempRooms;
    }

    /**
     * Gets customer reservation.
     *
     * @param customer the customer
     * @return the customer reservation
     */
    public Collection<Reservation> getCustomerReservation(Customer customer) {
        Collection<Reservation> tempReservations = new ArrayList<Reservation>();

        for (Reservation reservation : reservations)
            if (reservation.getCustomer().equals(customer))
                tempReservations.add(reservation);
        return tempReservations;
    }

    /**
     * Gets all rooms.
     *
     * @return the all rooms
     */
    public Collection<IRoom> getAllRooms() {
        return rooms;
    }

    /**
     * Print all reservations.
     */
    public void printAllReservations() {
        for (Reservation reservation : reservations)
            System.out.println(Color.colorText(Color.WHITE_BOLD_BRIGHT, reservation.toString()));
    }

    /**
     * Print all reservations tied to a specific customer
     *
     * @param reservations The reservations to display.
     */
    public void printCustomerReservations(Collection<Reservation> reservations) {
        for (Reservation reservation : reservations)
            System.out.println(Color.colorText(Color.WHITE_BOLD_BRIGHT, reservation.toString()));
    }

    /**
     * Print all rooms.
     */
     public void printAllRooms() {
        for (IRoom room : rooms)
            System.out.println(Color.colorText(Color.YELLOW_BOLD_BRIGHT, room.toString()));
    }

    /**
     * Print rooms selected
     *
     * @param rooms the selected rooms
     */
    public void printSelectedRooms(Collection<IRoom> rooms) {
        if (rooms.isEmpty()) {
            System.out.println(Color.colorText(Color.YELLOW_BOLD_BRIGHT, MainMenu.ERROR_MAP.get("noRooms")));
        } else {
            for (IRoom room : rooms)
                System.out.println(Color.colorText(Color.YELLOW_BOLD_BRIGHT, room.toString()));
        }
    }
}
