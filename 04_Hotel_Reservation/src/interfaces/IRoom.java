package interfaces;

import model.RoomType;

/**
 * The interface Room. Interface that will be used for all other Room types.
 */
public interface IRoom {
    /**
     * Gets room number.
     *
     * @return the room number
     */
    default String getRoomNumber() {
        return null;
    }

    /**
     * Gets room price.
     *
     * @return the room price
     */
    Double getRoomPrice();

    /**
     * Gets room type.
     *
     * @return the room type
     */
    RoomType getRoomType();

    /**
     * Is free boolean.
     *
     * @return the boolean
     */
    boolean isFree();
}
