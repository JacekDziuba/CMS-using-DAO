package pl.coderslab.Classes;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exercise {

    // == constants ==

    public static final String TABLE_EXERCISES = "exercises";
    public static final String COLUMN_EXERCISE_ID = "id";
    public static final String COLUMN_EXERCISE_TITLE = "title";
    public static final String COLUMN_EXERCISE_DESCRIPTION = "description";

    public static final String INSERT_INTO_EXERCISE = "INSERT INTO " + TABLE_EXERCISES +
            "( " +
            COLUMN_EXERCISE_TITLE + ", " +
            COLUMN_EXERCISE_DESCRIPTION +
            ") VALUES (?, ?)";
    public static final String UPDATE_EXERCISE = "UPDATE " + TABLE_EXERCISES + " SET " +
            COLUMN_EXERCISE_TITLE + "=?" + ", " +
            COLUMN_EXERCISE_DESCRIPTION + "=?" +
            " WHERE " + COLUMN_EXERCISE_ID + "=?";
    public static final String DELETE_FROM_EXERCISE = "DELETE FROM " + TABLE_EXERCISES + " WHERE " + COLUMN_EXERCISE_ID + "=?";
    public static final String LOAD_EXERCISE_BY_ID = "SELECT * FROM " + TABLE_EXERCISES + " WHERE " + COLUMN_EXERCISE_ID + "=?";
    public static final String SELECT_ALL_EXERCISES = "SELECT * FROM " + TABLE_EXERCISES;

    // == fields ==

	private int id;
	private String title;
	private String description;

    private PreparedStatement insertExercise;
    private PreparedStatement updateExercise;
    private PreparedStatement deleteExercise;

    // == constructors ==
	
	public Exercise() {}

    public Exercise(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // == getters ==
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    // == setters ==
    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // == methods ==

	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
            insertExercise = conn.prepareStatement(INSERT_INTO_EXERCISE, Statement.RETURN_GENERATED_KEYS);
            insertExercise.setString(1, this.title);
            insertExercise.setString(2, this.description);

            int affectedRows = insertExercise.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Could not add the exercise");
            }

            ResultSet rs = insertExercise.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}

		} else {
            updateExercise = conn.prepareStatement(UPDATE_EXERCISE);
            updateExercise.setString(1, this.title);
            updateExercise.setString(2, this.description);
            updateExercise.setInt(3, this.id);

            int affectedRows = updateExercise.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Could not update the exercise");
            }
		}
	}

	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
            deleteExercise = conn.prepareStatement(DELETE_FROM_EXERCISE);
            deleteExercise.setInt(1, this.id);

            int affectedRows = deleteExercise.executeUpdate();
            if (affectedRows == 1) {
                System.out.println("Exercise: " + this.id + " deleted.");
                this.id = 0;
            } else {
                throw new SQLException("Could not delete the exercise");
            }
		}
	}

	static public Exercise loadExerciseById(Connection conn, int id) throws SQLException {
		PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(LOAD_EXERCISE_BY_ID);
		preparedStatement.setInt(1, id);

		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Exercise exercise = new Exercise();
            exercise.id = resultSet.getInt(COLUMN_EXERCISE_ID);
            exercise.title = resultSet.getString(COLUMN_EXERCISE_TITLE);
            exercise.description = resultSet.getString(COLUMN_EXERCISE_DESCRIPTION);
			return exercise;
		}
		return null;
	}

    static public Exercise[] loadAllExercise(Connection conn) throws SQLException {
        ArrayList<Exercise> exerciseArrayList = new ArrayList<Exercise>();

		PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(SELECT_ALL_EXERCISES);

		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
            Exercise newExercise = new Exercise();
            newExercise.id = resultSet.getInt(COLUMN_EXERCISE_ID);
            newExercise.title = resultSet.getString(COLUMN_EXERCISE_TITLE);
            newExercise.description = resultSet.getString(COLUMN_EXERCISE_DESCRIPTION);
            exerciseArrayList.add(newExercise);
        }
        Exercise[] array = new Exercise[exerciseArrayList.size()];
        array = exerciseArrayList.toArray(array);
        return array;
    }
}