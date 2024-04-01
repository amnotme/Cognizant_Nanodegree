package helper;

import api.AdminResource;
import api.HotelResource;
import model.RoomType;

/**
 * The type Seed app. This is a helper class to seed application when run.
 */
public class SeedApp {

    /**
     * Seed customers. Start program with some Customers.
     */
    public static void seedCustomers() {
        HotelResource.createACustomer("leo@gmail.com", "Leo", "Hernandez");
        HotelResource.createACustomer("john.smith@gmail.com", "John", "Smith");
        HotelResource.createACustomer("gabby.diaz@hotmail.com", "Gabby", "Diaz");
        HotelResource.createACustomer("mark.walowitz@bigbank.com", "Mark", "Walowitz");
        HotelResource.createACustomer("polcia03@gmail.co.po", "Paulina", "Sprawka");
        HotelResource.createACustomer("hector.hdz@gmail.com", "Hector", "Hernandez");
        HotelResource.createACustomer("pe.t@gmail.com", "Peter", "Griffin");
        HotelResource.createACustomer("m.ruffalo@mcu.com", "Mark", "Ruffalo");
        HotelResource.createACustomer("oyeah@gmail.com", "Kool", "Aide");
        HotelResource.createACustomer("maxh@gmail.com", "Maximiliano", "Hernandez");
    }

    /**
     * Seed rooms. Start program with some rooms.
     */
    public static void seedRooms() {
        AdminResource.addRoom("101", 105.50, RoomType.SINGLE);
        AdminResource.addRoom("102", 125.50, RoomType.SINGLE);
        AdminResource.addRoom("103", 135.50, RoomType.SINGLE);
        AdminResource.addRoom("104", 145.50, RoomType.SINGLE);
        AdminResource.addRoom("105", 155.50, RoomType.SINGLE);

        AdminResource.addRoom("201", 205.50, RoomType.DOUBLE);
        AdminResource.addRoom("202", 225.50, RoomType.DOUBLE);
        AdminResource.addRoom("203", 235.50, RoomType.DOUBLE);
        AdminResource.addRoom("204", 245.50, RoomType.DOUBLE);
        AdminResource.addRoom("205", 255.50, RoomType.DOUBLE);

        AdminResource.addRoom("001", 0.0, RoomType.SINGLE);
        AdminResource.addRoom("002", 0.0, RoomType.SINGLE);
        AdminResource.addRoom("003", 0.0, RoomType.SINGLE);
        AdminResource.addRoom("004", 0.0, RoomType.SINGLE);
        AdminResource.addRoom("005", 0.0, RoomType.SINGLE);
    }

    /**
     * Seed reservations. Start program with some reservations.
     */
    public static void seedReservations() {
        HotelResource.bookARoom("leo@gmail.com", HotelResource.getRoom("101"), Time.createDate("01/01/2022"), Time.createDate("01/02/2022"));
        HotelResource.bookARoom("john.smith@gmail.com", HotelResource.getRoom("201"), Time.createDate("02/01/2022"), Time.createDate("02/02/2022"));
        HotelResource.bookARoom("oyeah@gmail.com", HotelResource.getRoom("001"), Time.createDate("03/01/2022"), Time.createDate("03/02/2022"));

    }
}
