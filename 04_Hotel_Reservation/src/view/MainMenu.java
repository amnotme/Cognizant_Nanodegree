package view;

import api.HotelResource;
import helper.*;
import interfaces.IRoom;
import model.Reservation;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Main menu. Main menu class and point of entry for the main run of the program.
 */
public class MainMenu {

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
     * The constant WELCOME_STRING.
     */
    private final static String WELCOME_STRING =
        "--------------------------------------------\n" +
        "Welcome to the Hotel Reservation Application\n" +
        "--------------------------------------------\n";

    /**
     * The constant MAIN_MENU_LIST.
     */
    private final static String MAIN_MENU_LIST =
        "------------------------------------\n" +
        "1. Find and reserve a room. \n" +
        "2. See my reservations. \n" +
        "3. Create an account. \n" +
        "4. Admin. \n" +
        "5. Exit \n" +
        "------------------------------------\n";

    /**
     * The Options.
     */
    protected final static String[] OPTIONS = {
        "yes", "y", "no", "n"
    };

    /**
     * The constant FIND_A_ROOM_PROMPTS.
     */
    private final static HashMap<String, String> FIND_A_ROOM_PROMPTS = new HashMap<>() {{
       put("checkIn", "Enter CheckIn Date (mm/dd/yyyy) example: 01/24/2022");
       put("checkOut", "Enter CheckOut Date (mm/dd/yyyy) example: 01/25/2022");
       put("roomsAvailable", "These are the rooms available for your dates");
       put("book", "Would you like to book a room with us?");
       put("account", "Do you have an account with us?");
       put("noAccount", "Please create an account before continuing.");
       put("email", "Enter your email address: (email@address.com)");
       put("room", "What room number would you like to reserve?");
       put("lookingFurther", "It seems that we were unable to find rooms for those dates. We will try with the following: ");
       put("differentDates", "Would you like to try different dates? Yes (y) / No (n): ");
       put("reservations", "Thanks for booking.  These are your reservations.");
    }};


    /**
     * The constant ACCOUNT_PROMPTS.
     */
    private final static HashMap<String, String> ACCOUNT_PROMPTS = new HashMap<>() {{
        put("email", "Please enter your email address (email@address.com): ");
        put("firstName", "Please enter a first name: ");
        put("lastName", "Please enter a last name: ");
        put("oneMore", "Would you like to add another customer? Yes (y) / No (n): ");
    }};

    /**
     * The constant MENU_INPUT.
     */
    protected final static HashMap<String, String> MENU_INPUT = new HashMap<>() {{
       put("option", "Please enter a number from the menu above (1 - 5): ");
       put("exitProgram", "Thanks and good bye!");
       put("mainMenu", "Returning to Main Menu");
    }};

    /**
     * The constant ERROR_MAP.
     */
    public final static HashMap<String, String> ERROR_MAP = new HashMap<>() {{
        put("yesNo", "Sorry, Is that Yes (y) or No (n)?");
        put("email", "That's an invalid email format. Please use the following format: (email@address.com)");
        put("emailExists", "Sorry, this email already tied to another user. Please try again");
        put("option", "That's not a valid option. Please try again");
        put("noRooms", "Sorry but we don't have rooms for those dates or a week after");
        put("roomUnavailable", "That room is not available or doesn't exist");
        put("roomExists", "That room already exists. Please try again.");
        put("incorrectEmail", "That email doesn't belong to a user. Try again");
        put("roomPriceLessThanZero", "The room price should not be less than zero. Please try again.");
        put("incorrectPriceType", "That's not a valid price. Please try again.");
        put("incorrectRoomType", "That's not a valid room type. Please enter 1 for SINGLE or 2 for DOUBLE.");
        put("date", "That's not a valid date (mm/dd/yyyy). Please try again");
        put("invalidDateRange", "Check in time cannot be after Check-out time. Please try again.");
    }};

    /**
     * The constant MY_RESERVATION_PROMPTS.
     */
    public final static HashMap<String, String> MY_RESERVATION_PROMPTS = new HashMap<>() {{
       put("customer", "Please enter the email address associated with the reservations (email@address.com): ");
       put("reservations", "These are the reservations we have found in our system: ");
       put("noReservations", "Oops! There doesn't seem to be any reservations for that customer or customer doesn't exist.");
       put("lookUpMore", "Would you like to look at a different customers' reservations? Yes (y) / No (n) ?");
    }};

    /**
     * Main menu print my reservations.
     *
     * @param scan the scan
     */
    public static void mainMenuPrintMyReservations(Scanner scan) {
        String response;
        boolean backToMenu = false;

        while (!backToMenu) {
            System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, MY_RESERVATION_PROMPTS.get("customer")));
            response = scan.next();

            System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, MY_RESERVATION_PROMPTS.get("reservations")));
            HotelResource.displayCustomerReservations(response);
            System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, MY_RESERVATION_PROMPTS.get("lookUpMore")));

            response = scan.next().toLowerCase();
            while (!isValidYesNo(response)) {
                System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, ERROR_MAP.get("yesNo")));
                response = scan.next().toLowerCase();
            }
            if (response.equals(OPTIONS[2]) || response.equals(OPTIONS[3]))
                backToMenu = true;
        }
    }

    /**
     * Print menu.
     *
     * @param color the color
     * @param menu  the menu
     */
    public static void printMenu(String color, String menu) {
        System.out.println(Color.colorText(color, menu));
    }

    /**
     * Print welcome main menu.
     */
    public static void printWelcomeMainMenu() {
        System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, WELCOME_STRING));
    }

    /**
     * Is valid yes no boolean.
     *
     * @param response the response
     * @return the boolean
     */
    public static boolean isValidYesNo(String response) {
        for (String option : OPTIONS)
            if (response.equals(option))
                return true;
        return false;
    }

    /**
     * Is email valid string.
     *
     * @param emailToValidate the email to validate
     * @return the string
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static String isEmailValid(String emailToValidate) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile(EMAIL_PATTER_STRING);
        Matcher matcher = pattern.matcher(emailToValidate);
        if (!matcher.matches())
            throw new IllegalArgumentException("[" + emailToValidate + "] " + ERROR_MAP.get("email"));
        if (isEmailTaken(emailToValidate))
            throw new IllegalArgumentException("[" + emailToValidate + "] " + ERROR_MAP.get("emailExists"));
        return emailToValidate;
    }

    /**
     * Is email taken. Determines if an email has been taken by another user.
     *
     * @param emailToValidate the email to validate
     * @return the boolean
     */
    public static boolean isEmailTaken(String emailToValidate) {
        return HotelResource.getCustomer(emailToValidate) != null;
    }

    /**
     * Create customer account.
     *
     * @param scan the scan
     */
    private static void createCustomerAccount(Scanner scan) {
        boolean returnToMainMenu = false;
        String customerEmail;
        String customerFirstName;
        String customerLastName;
        String response;

        while (!returnToMainMenu) {
            try {
                System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, ACCOUNT_PROMPTS.get("email")));
                customerEmail = isEmailValid(scan.next());
                System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, ACCOUNT_PROMPTS.get("firstName")));
                customerFirstName = scan.next();
                System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, ACCOUNT_PROMPTS.get("lastName")));
                customerLastName = scan.next();

                HotelResource.createACustomer(customerEmail, customerFirstName, customerLastName);

                System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, ACCOUNT_PROMPTS.get("oneMore")));
                response = scan.next();
                while (!isValidYesNo(response)) {
                    System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, ERROR_MAP.get("yesNo")));
                    response = scan.next().toLowerCase();
                }
                if (response.equals(OPTIONS[2]) || response.equals(OPTIONS[3]))
                    returnToMainMenu = true;
            } catch (IllegalArgumentException ex) {
                System.out.println(Color.colorText(Color.RED_BOLD_BRIGHT, ex.getLocalizedMessage()));
            }
        }
    }

    /**
     * Is valid date range.
     *
     * @param checkIn  the check in
     * @param checkOut the check out
     * @throws Exception the exception
     */
    private static void isValidDateRange(Date checkIn, Date checkOut) throws Exception {
         if (checkIn.after(checkOut))
             throw new Exception(ERROR_MAP.get("invalidDateRange"));
    }

    /**
     * Find a room.
     *
     * @param scan the scan
     */
    private static void findARoom(Scanner scan) {
        boolean returnToMainMenu = false;
        Date checkIn;
        Date checkOut;
        Collection<IRoom> rooms;
        String response;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();

        while (!returnToMainMenu) {
            try {
                System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, FIND_A_ROOM_PROMPTS.get("checkIn")));
                checkIn = Time.createDate(scan.next());
                System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, FIND_A_ROOM_PROMPTS.get("checkOut")));
                checkOut = Time.createDate(scan.next());
                isValidDateRange(checkIn, checkOut);
                rooms = HotelResource.findARoom(checkIn, checkOut);
                if (rooms.isEmpty()) {
                    checkIn.setDate(checkIn.getDate() + 7);
                    checkOut.setDate(checkOut.getDate() + 7);
                    System.out.println(Color.colorText(Color.YELLOW_BOLD_BRIGHT, FIND_A_ROOM_PROMPTS.get("lookingFurther") + formatter.format(checkIn) + " - " + formatter.format(checkOut)));
                    rooms = HotelResource.findARoom(checkIn, checkOut);
                }
                System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, FIND_A_ROOM_PROMPTS.get("roomsAvailable")));
                HotelResource.displaySelectedRooms(rooms);

                if (!rooms.isEmpty()) {
                    returnToMainMenu = reserveARoom(scan, checkIn, checkOut, rooms);
                } else {
                    System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, FIND_A_ROOM_PROMPTS.get("differentDates")));
                    response = scan.next();
                    while (!isValidYesNo(response)) {
                        System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, ERROR_MAP.get("yesNo")));
                        response = scan.next().toLowerCase();
                    }
                    if (response.equals(OPTIONS[2]) || response.equals(OPTIONS[3]))
                        returnToMainMenu = true;
                }

            } catch (InputMismatchException ex) {
                System.out.println(Color.colorText(Color.RED_BOLD_BRIGHT, ex.getLocalizedMessage()));
            } catch (NumberFormatException ex) {
                System.out.println(Color.colorText(Color.RED_BOLD_BRIGHT, "[" + ex.getLocalizedMessage() + "]: " + ERROR_MAP.get("date")));
            } catch (Exception ex) {
                System.out.println(Color.colorText(Color.RED_BOLD_BRIGHT, ex.getLocalizedMessage()));
            }
        }
    }

    /**
     * Check if room is available from a collection of given rooms.
     *
     * @param rooms list of available rooms.
     * @param roomNumber room number we are looking to find if available
     * @return true or false.
     */
    private static boolean isRoomAvailableToBook(Collection<IRoom> rooms, String roomNumber) {
        for (IRoom room : rooms)
            if (room.getRoomNumber().equals(roomNumber))
                return true;
        return false;
    }
    /**
     * Reserve a room boolean.
     *
     * @param scan         the scan
     * @param checkInDate  the check in date
     * @param checkOutDate the check out date
     * @param rooms        the rooms
     * @return the boolean
     */
    private static boolean reserveARoom(Scanner scan, Date checkInDate, Date checkOutDate, Collection<IRoom> rooms) {
        boolean returnToMainMenu = false;
        String response;
        String roomNumber;
        String customerEmail;
        Reservation reservation;

        while (!returnToMainMenu) {
            try {
                System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, FIND_A_ROOM_PROMPTS.get("account")));
                response = scan.next();
                while (!isValidYesNo(response)) {
                    System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, ERROR_MAP.get("yesNo")));
                    response = scan.next().toLowerCase();
                }
                if (response.equals(OPTIONS[2]) || response.equals(OPTIONS[3])) {
                    System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, FIND_A_ROOM_PROMPTS.get("noAccount")));
                    returnToMainMenu = true;
                    break ;
                }

                System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, FIND_A_ROOM_PROMPTS.get("email")));
                customerEmail = scan.next();

                while (!isEmailTaken(customerEmail)) {
                    System.out.println(Color.colorText(Color.RED_BOLD_BRIGHT, ERROR_MAP.get("incorrectEmail")));
                    System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, FIND_A_ROOM_PROMPTS.get("email")));
                    customerEmail = scan.next();
                }

                System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, FIND_A_ROOM_PROMPTS.get("room")));
                roomNumber = scan.next();

                while (!isRoomAvailableToBook(rooms, roomNumber) || HotelResource.getRoom(roomNumber) == null) {
                    System.out.println(Color.colorText(Color.RED_BOLD_BRIGHT, ERROR_MAP.get("roomUnavailable")));
                    System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, FIND_A_ROOM_PROMPTS.get("roomsAvailable")));
                    HotelResource.displaySelectedRooms(rooms);
                    System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, FIND_A_ROOM_PROMPTS.get("room")));
                    roomNumber = scan.next();
                }

                reservation = HotelResource.bookARoom(customerEmail, HotelResource.getRoom(roomNumber), checkInDate, checkOutDate);
                System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, FIND_A_ROOM_PROMPTS.get("reservations")));
                HotelResource.displayCustomerReservations(customerEmail);
                returnToMainMenu = true;

            } catch (IllegalArgumentException ex) {
                System.out.println(Color.colorText(Color.RED_BOLD_BRIGHT, ex.getLocalizedMessage()));
            }
        }
        return returnToMainMenu;
    }

    /**
     * Main menu. Main entry point for the program.
     *
     * @param scan the scan
     */
    public static void mainMenu(Scanner scan) {
        boolean exitProgram = false;
        String input = "";

        while (!exitProgram) {
            printMenu(Color.GREEN_BOLD_BRIGHT, MAIN_MENU_LIST);
            System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, MENU_INPUT.get("option")));
            input = scan.next();

            switch (input) {
                case "1": // Find and reserve a room
                    findARoom(scan);
                    break ;
                case "2": // See my reservations
                    mainMenuPrintMyReservations(scan);
                    break ;
                case "3": // Create an account
                    createCustomerAccount(scan);
                    break ;
                case "4": // Admin
                    AdminMenu.adminMenu(scan);
                    break ;
                case "5": // Exit
                    exitProgram = true;
                    System.out.println(Color.colorText(Color.GREEN_BOLD_BRIGHT, MENU_INPUT.get("exitProgram")));
                    return ;
                default:
                    System.out.println(Color.colorText(Color.RED_BOLD_BRIGHT, ERROR_MAP.get("option")));

            }
        }
    }
}
