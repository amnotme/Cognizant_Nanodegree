package main;

import helper.SeedApp;
import view.MainMenu;

import java.util.Scanner;

/**
 * The type Hotel application.
 */
public class HotelApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        // These can be commented out if you would like to start with no seed data.
        SeedApp.seedCustomers();
        SeedApp.seedRooms();
        SeedApp.seedReservations();

        Scanner scan = new Scanner(System.in);
        MainMenu.printWelcomeMainMenu();
        MainMenu.mainMenu(scan);
    }
}
