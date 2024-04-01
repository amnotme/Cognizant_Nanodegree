package model;

/**
 * The type Free room. Free room model class.
 */
public class FreeRoom extends Room {

    /**
     * Instantiates a new Free room.
     *
     * @param roomNumber  the room number
     * @param price       the price
     * @param enumeration the enumeration
     */
    public FreeRoom(
            String roomNumber,
            Double price,
            RoomType enumeration
    ) {
        super(roomNumber, 0.0, enumeration);
    }

    @Override
    public String toString() {
        return "Room Info: [" + this.getRoomNumber() + "] Type: " + this.getRoomType() + " Price: $" + this.getRoomPrice();
    }

}
