package pl.coderslab.Admin;

import pl.coderslab.Classes.User;
import pl.coderslab.Solution;
import pl.coderslab.User_group;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Scanner;

public class SolutionAdmin {

	public static void main(String[] args) {

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/users?useSSL=false", "root", "coderslab");

			printOptions();

			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);

			boolean quit = false;
			while (!quit) {
				System.out.println("Enter action: (3 to show available actions)");
				int action = scanner.nextInt();
				scanner.nextLine();
				
				switch (action) {
				case 0:
					System.out.println("\nShutting down...");
					quit = true;
					break;
				case 1:
					User_groupAdmin.displayAllGroups(conn);
					System.out.println("Enter group ID: ");
					int groupId = scanner.nextInt();
					System.out.println(User_group.loadUserGroupById(conn, groupId).getName());
					User[] array = User.loadAllByGrupId(conn, groupId);
					for (User user : array) {
						System.out.println(user.getId() + ". " + user.getUsername());
					}
					System.out.println("Enter user ID to check his solutions: ");
					int userId = scanner.nextInt();
					Solution[] solutions = Solution.loadAllSolutionsByUserId(conn, userId);
					for (Solution solution : solutions) {
						System.out.println(solution.getId() + ". " + solution.getDescription());
					}
					break;
				case 2:
					User[] arrayUsers = User.loadAllUsers(conn);
					for (User user : arrayUsers) {
						System.out.println("User from group: " + user.getUser_group_id());
						System.out.println("ID: " + user.getId() + " Name: " + user.getUsername());
					}
					System.out.println("Enter user's ID: ");
					int userId1 = scanner.nextInt();
					ExerciseAdmin.displayAllExercises(conn);
					System.out.println("Enter exercise ID to assing to the user: ");
					int exerciseId = scanner.nextInt();
					Solution solution = new Solution();
					solution.setUsers_id(userId1);
					solution.setExercise(exerciseId);
					Calendar cal = Calendar.getInstance(); 
					java.sql.Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
					solution.setCreated(timestamp);
					solution.saveToDB(conn);
					break;
				case 3:
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
					+ "1  - to check user's solutions\n" 
					+ "2  - to add a new exercise to a user\n" 
					+ "3  - to print a list of available actions.");
		System.out.println("Choose your action: ");
	}
}
