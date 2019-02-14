package pl.coderslab.Admin;

import pl.coderslab.Classes.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class UserAdmin {

	public static void main(String[] args) {

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/users?useSSL=false", "root", "coderslab");

			displayAllUsers(conn);
			printOptions();

			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);

			boolean quit = false;
			while (!quit) {
				System.out.println("Enter action: (5 to show available actions)");
				int action = scanner.nextInt();
				scanner.nextLine();
				
				switch (action) {
				case 0:
					System.out.println("\nShutting down...");
					quit = true;
					break;
				case 1:
					displayAllUsers(conn);
					break;
				case 2:
					System.out.println("Enter user's data: \n");
					System.out.println("Enter user's group ID: \n");
					int user_group_id = scanner.nextInt();
					scanner.nextLine();
					System.out.println("Enter user's name: \n");
					String newUsername = scanner.nextLine();
					System.out.println("Enter user's password: \n");
					String newPassword = scanner.nextLine();
					System.out.println("Enter user's email: \n");
					String newEmail = scanner.nextLine();
					User newUser = new User(user_group_id, newUsername, newEmail, newPassword);
					newUser.saveToDB(conn);
					break;
				case 3:
					System.out.println("Enter user's data: \n");
					System.out.println("Enter user's group ID: \n");
					int user_group_id1 = scanner.nextInt();
					System.out.println("Enter user's ID: \n");
					int id1 = scanner.nextInt();
					scanner.nextLine();
					System.out.println("Enter user's name: \n");
					String username1 = scanner.nextLine();
					System.out.println("Enter user's password: \n");
					String password1 = scanner.nextLine();
					System.out.println("Enter user's email: \n");
					String email1 = scanner.nextLine();
					User user1 = User.loadUserById(conn, id1);
					user1.setUser_group_id(user_group_id1);
					user1.setUsername(username1);
					user1.setEmail(email1);
					user1.setPassword(password1);
					user1.saveToDB(conn);
					break;
				case 4:
					System.out.println("Enter user's ID: \n");
					int id2 = scanner.nextInt();
					User user = User.loadUserById(conn, id2);
					user.delete(conn);
					break;
				case 5:
					printOptions();
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printOptions() {
		System.out.println("\nAvailable options. Press: ");
		System.out.println("0  - to shutdown\n" 
					+ "1  - to print users\n" 
					+ "2  - to add a new user\n" 
					+ "3  - to update existing an existing user\n" 
					+ "4  - to remove an existing user\n"
					+ "5  - to print a list of available actions.");
		System.out.println("Choose your action: ");
	}
	public static void displayAllUsers(Connection conn) throws SQLException {
		System.out.println("Users' list: ");
		User[] array = User.loadAllUsers(conn);
		for (int i = 0; i < array.length; i++) {
			System.out.println("User's ID: " + array[i].getId());
			System.out.println("Belongs to group: " + array[i].getUser_group_id());
			System.out.println("User's name: " + array[i].getUsername());
			System.out.println("User's email: " + array[i].getEmail());
			System.out.println("User's hashed password: " + array[i].getPassword());
		}
	}

// CREATE TABLE User_group (id int(11) AUTO_INCREMENT PRIMARY KEY, name varchar(255));
// CREATE TABLE Users (id bigint(20) AUTO_INCREMENT PRIMARY KEY, username varchar(255), email varchar(255) UNIQUE, password varchar(245), user_group_id int(11), FOREIGN KEY(user_group_id) REFERENCES User_group(id));
// CREATE TABLE Solution(id int(11) AUTO_INCREMENT PRIMARY KEY, created DATETIME, updated DATETIME, description TEXT, exercise int(11), users_id bigint(20), FOREIGN KEY(exercise) REFERENCES Exercise(id), FOREIGN KEY(users_id) REFERENCES Users(id));
// CREATE TABLE Exercise(id int(11) AUTO_INCREMENT PRIMARY KEY, title varchar(255), description TEXT);

}
