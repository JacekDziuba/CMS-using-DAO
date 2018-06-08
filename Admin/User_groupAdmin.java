package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class User_groupAdmin {

	public static void main(String[] args) {

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/users?useSSL=false", "root", "coderslab");

			displayAllGroups(conn);
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
					displayAllGroups(conn);
					break;
				case 2:
					System.out.println("Enter group's data: \n");
					System.out.println("Enter group's name: \n");
					String newName = scanner.nextLine();
					User_group newGroup = new User_group(newName);
					newGroup.saveToDB(conn);
					break;
				case 3:
					System.out.println("Enter group's data: \n");
					System.out.println("Enter group's ID: \n");
					int groupId = scanner.nextInt();
					scanner.nextLine();
					System.out.println("Enter group's name: \n");
					String newName1 = scanner.nextLine();
					User_group newGroup1 = User_group.loadUserGroupById(conn, groupId);
					newGroup1.setName(newName1);
					newGroup1.saveToDB(conn);
					break;
				case 4:
					System.out.println("Enter group's ID: \n");
					int groupId2 = scanner.nextInt();
					User_group newGroup2 = User_group.loadUserGroupById(conn, groupId2);
					newGroup2.delete(conn);
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
					+ "1  - to print groups\n" 
					+ "2  - to add a new group\n" 
					+ "3  - to update existing an existing group\n" 
					+ "4  - to remove an existing group\n"
					+ "5  - to print a list of available actions.");
		System.out.println("Choose your action: ");
	}
	public static void displayAllGroups(Connection conn) throws SQLException {
		System.out.println("Groups' list: ");
		User_group[] array = User_group.loadAllUserGroups(conn);
		for (int i = 0; i < array.length; i++) {
			System.out.println("Group ID: " + array[i].getId());
			System.out.println("Group Name: " + array[i].getName());
		}
	}

// CREATE TABLE User_group (id int(11) AUTO_INCREMENT PRIMARY KEY, name varchar(255));
// CREATE TABLE Users (id bigint(20) AUTO_INCREMENT PRIMARY KEY, username varchar(255), email varchar(255) UNIQUE, password varchar(245), user_group_id int(11), FOREIGN KEY(user_group_id) REFERENCES User_group(id));
// CREATE TABLE Solution(id int(11) AUTO_INCREMENT PRIMARY KEY, created DATETIME, updated DATETIME, description TEXT, exercise int(11), users_id bigint(20), FOREIGN KEY(exercise) REFERENCES Exercise(id), FOREIGN KEY(users_id) REFERENCES Users(id));
// CREATE TABLE Exercise(id int(11) AUTO_INCREMENT PRIMARY KEY, title varchar(255), description TEXT);

}
