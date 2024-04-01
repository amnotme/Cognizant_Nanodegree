package view;

import api.AdminResource;
import helper.*;
import model.RoomType;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The type Admin menu. Menu class that extends from MainMenu
 */
public class AdminMenu extends MainMenu {

    /**
     * The constant ADMIN_MENU_LIST.
     */
    private final static String ADMIN_MENU_LIST =
        "------------------------------------\n" +
        "1. See all Customers. \n" +
        "2. See all Rooms. \n" +
        "3. See all Reservations. \n" +
        "4. Add a room. \n" +
        "5. Back to Main Menu \n" +
        "------------------------------------\n";

    /**
     * The constant ADD_ROOM_PROMPTS.
     */
    private final static HashMap<String, String> ADD_ROOM_PROMPTS = new HashMap<>() {{
       put("roomNumber", "What room number would you like to add? ");
       put("roomPrice", "Enter the price per night: ");
       put("roomType", "Enter the room type: SINGLE (1) or DOUBLE (2)? ");
       put("oneMore", "Would you like to add another room?");
    }};

    /**
     * Is room number valid string.
     *
     * @param roomNumber the room number
     * @return the string
     * @throws IllegalArgumentException the illegal argument exception
     */
    private static String isRoomNumberValid(String roomNumber) throws IllegalArgumentException{
        if (AdminResource.getRoom(roomNumber) != null)
            throw new IllegalArgumentException(ERROR_MAP.get("roomExists"));
        return roomNumber;
    }

    /**
     * Is room price valid double.
     *
     * @param price the price
     * @return the double
     * @throws NumberFormatException the number format exception
     */
    private static Double isRoomPriceValid(Double price) throws NumberFormatException {
        if (price < 0.0)
            throw new NumberFormatException(ERROR_MAP.get("roomPriceLessThanZero"));
        return price;
    }

    /**
     * Is room type valid room type.
     *
     * @param roomType the room type
     * @return the room type
     * @throws IllegalArgumentException the illegal argument exception
     */
    private static RoomType isRoomTypeValid(String roomType) throws IllegalArgumentException {
        if (roomType.equals("1"))
            return RoomType.SINGLE;
        if (roomType.equals("2"))
            return RoomType.DOUBLE;
        throw new NumberFormatException(ERROR_MAP.get("incorrectRoomType"));
    }

    /**
     * Add a room.
     *
     * @param scan the scan
     */
    private static void addARoom(Scanner scan) {
        boolean returnToAdminMenu = false;
        String roomNumber;
        Double roomPrice;
        RoomType roomType;
        String response;

        while (!returnToAdminMenu) {
            try {
                System.out.println(Color.colorText(Color.CYAN_BOLD_BRIGHT, ADD_ROOM_PROMPTS.get("roomNumber")));
                roomNumber = isRoomNumberValid(scan.next());
                System.out.println(Color.colorText(Color.CYAN_BOLD_BRIGHT, ADD_ROOM_PROMPTS.get("roomPrice")));
                roomPrice = isRoomPriceValid(scan.nextDouble());
                System.out.println(Color.colorText(Color.CYAN_BOLD_BRIGHT, ADD_ROOM_PROMPTS.get("roomType")));
                roomType = isRoomTypeValid(scan.next());
                AdminResource.addRoom(roomNumber, roomPrice, roomType);
                System.out.println(Color.colorText(Color.CYAN_BOLD_BRIGHT, ADD_ROOM_PROMPTS.get("oneMore")));
                response = scan.next();
                while (!isValidYesNo(response)) {
                    System.out.println(Color.colorText(Color.CYAN_BOLD_BRIGHT, ERROR_MAP.get("yesNo")));
                    response = scan.next().toLowerCase();
                }
                if (response.equals(OPTIONS[2]) || response.equals(OPTIONS[3]))
                    returnToAdminMenu = true;
            } catch (IllegalArgumentException ex) {
                System.out.println(Color.colorText(Color.RED_BOLD_BRIGHT, ex.getLocalizedMessage()));
            } catch (InputMismatchException ex) {
                System.out.println(Color.colorText(Color.RED_BOLD_BRIGHT, ERROR_MAP.get("incorrectPriceType") + " You entered: " + scan.next()));
            }
        }
    }

    /**
     * Admin menu.
     *
     * @param scan the scanner
     */
    public static void adminMenu(Scanner scan) {
        boolean returnToMainMenu = false;
        String input = "";

        while (!returnToMainMenu) {
            printMenu(Color.CYAN_BOLD_BRIGHT, ADMIN_MENU_LIST);
            System.out.println(Color.colorText(Color.CYAN_BOLD_BRIGHT, MENU_INPUT.get("option")));
            input = scan.next().toLowerCase();
            switch (input) {
                case "1": // See all customers
                    AdminResource.displayAllCustomers();
                    break ;
                case "2": // See all rooms
                    AdminResource.displayAllRooms();
                    break ;
                case "3": // See all reservations
                    AdminResource.displayAllReservations();
                    break ;
                case "4": // Add a room
                    addARoom(scan);
                    break ;
                case "5": // Back to main menu
                    returnToMainMenu = true;
                    System.out.println(Color.colorText(Color.CYAN_BOLD_BRIGHT, MENU_INPUT.get("mainMenu")));
                    break ;
                default:
                    System.out.println(Color.colorText(Color.RED_BOLD_BRIGHT, ERROR_MAP.get("option")));
            }
        }
    }
}
