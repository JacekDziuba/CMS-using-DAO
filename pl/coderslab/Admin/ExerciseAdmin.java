package pl.coderslab.Admin;

import pl.coderslab.Classes.Exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ExerciseAdmin {

	public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD)) {

			displayAllExercises(conn);
			printOptions();
            runProgram(conn);

        } catch (SQLException e) {
            System.out.println("Something went wrong " + e.getMessage());
            System.out.println(e.getStackTrace());
		}
	}

    // == constants ==

    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/warsztaty_2?useSSL=false";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "coderslab";

    // == methods ==

    private static void printOptions() {
		System.out.println("\nAvailable options. Press: ");
		System.out.println("0  - to shutdown\n" 
					+ "1  - to print exercises\n" 
					+ "2  - to add a new exercise\n" 
					+ "3  - to update existing an existing exercise\n" 
					+ "4  - to remove an existing exercise\n"
					+ "5  - to print a list of available actions.");
		System.out.println("Choose your action: ");
	}

    private static void runProgram(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean flag = false;

        while (!flag) {
            System.out.println("Enter action: (5 to show available actions)");
            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {

                case 0:
                    System.out.println("\nShutting down...");
                    flag = true;
                    break;

                case 1:
                    displayAllExercises(conn);
                    break;

                case 2:
                    System.out.println("Please add new exercise title: \n");
                    String newTitle = scanner.nextLine();

                    System.out.println("Please add new exercise description: \n");
                    String newDescription = scanner.nextLine();

                    Exercise newExercise = new Exercise(newTitle, newDescription);
                    newExercise.saveToDB(conn);
                    break;

                case 3:
                    System.out.println("Please enter exercise ID for update: \n");
                    int exerciseId = scanner.nextInt();

                    scanner.nextLine();
                    System.out.println("Please update the exercise title: \n");
                    String editedTitle = scanner.nextLine();

                    System.out.println("Please update the exercise description: \n");
                    String editedDescription = scanner.nextLine();

                    Exercise editedExercise = Exercise.loadExerciseById(conn, exerciseId);
                    editedExercise.setTitle(editedTitle);
                    editedExercise.setDescription(editedDescription);
                    editedExercise.saveToDB(conn);
                    break;

                case 4:
                    System.out.println("Please enter exercise ID for deletion: ");
                    int exerciseToDeleteId = scanner.nextInt();

                    Exercise exerciseToDelete = Exercise.loadExerciseById(conn, exerciseToDeleteId);
                    exerciseToDelete.delete(conn);
                    break;

                case 5:
                    printOptions();
                    break;
            }
        }

    }

	public static void displayAllExercises(Connection conn) throws SQLException {
		Exercise[] array = Exercise.loadAllExercise(conn);
        if (array.length == 0) {
            System.out.println("No exercises available.");
        } else {
            System.out.println("List of all exercises: ");
            for (int i = 0; i < array.length; i++) {
                System.out.println("Exercise ID: " + array[i].getId());
                System.out.println("Exercise title: " + array[i].getTitle());
                System.out.println("Exercise description: " + array[i].getDescription());
            }
        }
	}

// CREATE TABLE User_group (id int(11) AUTO_INCREMENT PRIMARY KEY, name varchar(255));
// CREATE TABLE Users (id bigint(20) AUTO_INCREMENT PRIMARY KEY, username varchar(255), email varchar(255) UNIQUE, password varchar(245), user_group_id int(11), FOREIGN KEY(user_group_id) REFERENCES User_group(id));
// CREATE TABLE Exercise(id int(11) AUTO_INCREMENT PRIMARY KEY, title varchar(255), description TEXT);
// CREATE TABLE Solution(id int(11) AUTO_INCREMENT PRIMARY KEY, created DATETIME, updated DATETIME, description TEXT, exercise int(11), users_id bigint(20), FOREIGN KEY(exercise) REFERENCES Exercise(id), FOREIGN KEY(users_id) REFERENCES Users(id));

}
