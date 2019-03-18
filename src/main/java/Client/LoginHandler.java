package main.java.Client;

import java.util.Scanner;

public class LoginHandler {
    public static User login(){
        User user;
        // TODO: check if credentials are already saved on disk.
        System.out.println("Enter username (or leave blank to create a new account):");

        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine();
        String password;

        if (username.equals("")){
            user = createAccount(sc);
        }else {
            System.out.println("Enter password");
            password = sc.nextLine();

            user = new User(username, password);
        }

        if (user.authenticate()){
            // Return the authenticated user
            return user;
        }else{
            return null;
        }
    }

    public static User createAccount(Scanner sc){
        System.out.println("Creating a new account.");
        System.out.print("Select an username: ");
        // TODO: check with server that an account with the same name has not yet been created

        String username = sc.nextLine();

        // Read the password
        // TODO: read the password without displaying it to the console. Below in the commented out code the console is always null when tested.

//            Console console = System.console();
//            if (console == null) {
//                System.out.println("Couldn't get Console instance");
//                System.exit(0);
//            }
//
//            console.printf("Testing password%n");
//            char passwordArray[] = console.readPassword("Enter your secret password: ");
//            String pw = new String(passwordArray);
//
//            System.out.println(pw);

        String password;
        // TODO: maybe test password security
        while (true){
            System.out.print("Type a password: ");
            String pw1 = sc.nextLine();
            if (pw1.equals("")){
                System.out.println("Password cannot be left blank. Please try again.");
                continue;
            }
            System.out.print("Retype the entered password: ");
            String pw2 = sc.nextLine();
            if (pw1.equals(pw2)){
                password = pw1;
                break;
            }
            System.out.println("The passwords do not match. Please try again.");
        }

        saveCredentials(username, password);

        System.out.println("Account successfully created");

        return new User(username, password);
    }

    public static void saveCredentials(String usernname, String password){
        // TODO: write data to disk

        // TODO: send credential data to server
    }

    public static User readCredentials(){
        // TODO: read credentials from disk
        return null;
    }
}
