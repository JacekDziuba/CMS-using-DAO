package pl.coderslab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exercise {
	private int id;
	private String title;
	private String description;
	
	public Exercise() {}
	public Exercise(String title, String description) {
		this.title = title;
		this.description = description;}
	
	//GETTERS
	public int getId() {
		return id;}
	public String getTitle() {
		return title;}
	public String getDescription() {
		return description;}
	//SETTERS
	public void setDescription(String description) {
		this.description = description;}
	public void setTitle(String title) {
		this.title = title;}
	
	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO Exercise(title, description) VALUES (?, ?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.title);
			preparedStatement.setString(2, this.description);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		} else {
			String sql = "UPDATE Exercise SET title=?, description=? where id = ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, this.title);
			preparedStatement.setString(2, this.description);
			preparedStatement.setInt(3, this.id);
			preparedStatement.executeUpdate();
		}
	}
	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM Exercise WHERE id= ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}
	static public Exercise loadExerciseById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM Exercise where id=?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Exercise exercise = new Exercise();
			exercise.id = resultSet.getInt("id");
			exercise.title = resultSet.getString("title");
			exercise.description = resultSet.getString("description");
			return exercise;
		}
		return null;
	}
	static public Exercise[] loadAllExercise(Connection conn) throws SQLException {
		ArrayList<Exercise> exercise = new ArrayList<Exercise>();
		String sql = "SELECT * FROM Exercise";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Exercise exercises = new Exercise();
			exercises.id = resultSet.getInt("id");
			exercises.title = resultSet.getString("title");
			exercises.description = resultSet.getString("description");
			exercise.add(exercises);
		}
		Exercise[] uArray = new Exercise[exercise.size()];
		uArray = exercise.toArray(uArray);
		return uArray;
	}}
	