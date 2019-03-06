package Client;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        // TODO: Terminal ui
        System.out.println("Welcome to the best internet casino.");

        // TODO: move login and account creation somewhere else
        System.out.println("Enter username (or leave blank to create a new account):");

        Scanner sc = new Scanner(System.in);
        String uName = sc.nextLine();

        if (uName.equals("")){
            System.out.println("Creating a new account.");
            System.out.print("Select an username: ");
            // TODO: check with server that an account with the same name has not yet been created

            uName = sc.nextLine();

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
            String pw;
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
                    pw = pw1;
                    break;
                }
                System.out.println("The passwords do not match. Please try again.");
            }

            System.out.println("Account successfully created");
        }
    }
}
