package model;

import interfaces.IRoom;

import java.util.Objects;

/**
 * The type Room. Room model class.
 */
public class Room implements IRoom {

    /**
     * The Room number.
     */
    protected final String roomNumber;

    /**
     * The Price.
     */
    protected final Double price;

    /**
     * The Enumeration.
     */
    protected final RoomType enumeration;

    /**
     * Instantiates a new Room.
     *
     * @param roomNumber  the room number
     * @param price       the price
     * @param enumeration the enumeration
     */
    public Room(
            String roomNumber,
            Double price,
            RoomType enumeration
    ) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public RoomType getRoomType() {
        return this.enumeration;
    }

    @Override
    public boolean isFree() {
        return this.getRoomPrice() == 0.0;
    }

    @Override
    public String toString() {
        return "Room Info: [" + this.getRoomNumber() + "] Type: " + this.getRoomType() + " Price: $" + this.getRoomPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber.equals(room.roomNumber) && price.equals(room.price) && enumeration == room.enumeration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, price, enumeration);
    }
}
