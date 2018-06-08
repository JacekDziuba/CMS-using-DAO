package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Scanner;

public class UserInterface {

	public static void main(String[] args) {

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/users?useSSL=false", "root", "coderslab");

			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			System.out.println("What is your ID?");
			int userId = scanner.nextInt();
			scanner.nextLine();
			printOptions();

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
					Solution[] usersSolutions = Solution.loadUsersSolutionsByUserId(conn, userId);
					for (Solution solution : usersSolutions) {
						System.out.println("Your solutions: " + solution.getId() + ". " + solution.getDescription());
					}
					System.out.println("Solution to which exercise do you want to add?");
					int action2 = scanner.nextInt();
					scanner.nextLine();
					System.out.println("What is your solution?");
					String solution = scanner.nextLine();
					String sql = "UPDATE Solution SET updated=?, description=? where id = ?";
					Calendar cal = Calendar.getInstance();
					java.sql.Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
					PreparedStatement preparedStatement;
					preparedStatement = conn.prepareStatement(sql);
					preparedStatement.setTimestamp(1, timestamp);
					preparedStatement.setString(2, solution);
					preparedStatement.setInt(3, action2);
					preparedStatement.executeUpdate();
					break;
				case 2:
					Solution[] solutions = Solution.loadAllSolutionsByUserId(conn, userId);
					for (Solution solution1 : solutions) {
						System.out.println("Your solutions: " + solution1.getId() + ". " + solution1.getDescription());
					}
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
		System.out.println("0  - to shutdown\n" + "1  - add your solutions\n" + "2  - view your solutions\n"
				+ "3  - to print a list of available actions.");
		System.out.println("Choose your action: ");
	}
}
