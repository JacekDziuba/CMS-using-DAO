package pl.coderslab;

import java.sql.*;
import java.util.ArrayList;

public class Solution {
	private int id;
	private int exercise;
	private int users_id;
	private Timestamp created;
	private String updated;
	private String description;
	
	public Solution() {
	}

	public Solution(int exercise, int users_id, Timestamp created, String updated, String description) {
		this.exercise = exercise;
		this.users_id = users_id;
		this.created = created;
		this.updated = updated;
		this.description = description;
	}

	
	//GETTERS
	public int getId() {return id;}
	public int getExercise() {return exercise;}
	public int getUsers_id() {return users_id;}
	public Timestamp getCreated() {return created;}
	public String getUpdated() {return updated;}
	public String getDescription() {return description;}
	//SETTERS
	public void setExercise(int exercise) {this.exercise = exercise;}
	public void setUsers_id(int users_id) {this.users_id = users_id;}
	public void setCreated(Timestamp created) {this.created = created;}
	public void setUpdated(String updated) {this.updated = updated;}
	public void setDescription(String description) {this.description = description;}

	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO Solution(exercise, users_id, created, updated, description) VALUES (?, ?, ?, ?, ?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setInt(1, this.exercise);
			preparedStatement.setInt(2, this.users_id);
			preparedStatement.setTimestamp(3, this.created);
			preparedStatement.setString(4, this.updated);
			preparedStatement.setString(5, this.description);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		} else {
			String sql = "UPDATE Solution SET exercise=?, users_id=?, created=?, updated=?, description=? where users_id = ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.exercise);
			preparedStatement.setInt(2, this.users_id);
			preparedStatement.setTimestamp(3, this.created);
			preparedStatement.setString(4, this.updated);
			preparedStatement.setString(5, this.description);
			preparedStatement.setInt(4, this.id);
			preparedStatement.executeUpdate();
		}
	}
	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM Solution WHERE user_id= ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}
	static public Solution loadSolutionById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM Solution where id=?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.exercise = resultSet.getInt("exercise");
			loadedSolution.users_id = resultSet.getInt("users_id");
			loadedSolution.created = resultSet.getTimestamp("created");
			loadedSolution.updated = resultSet.getString("updated");
			loadedSolution.description = resultSet.getString("description");
			return loadedSolution;
		}
		return null;
	}
	static public Solution[] loadAllSolutions(Connection conn) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM Solution";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.exercise = resultSet.getInt("exercise");
			loadedSolution.users_id = resultSet.getInt("users_id");
			loadedSolution.created = resultSet.getTimestamp("created");
			loadedSolution.updated = resultSet.getString("updated");
			loadedSolution.description = resultSet.getString("description");
			solutions.add(loadedSolution);
		}
		Solution[] uArray = new Solution[solutions.size()];
		uArray = solutions.toArray(uArray);
		return uArray;
	}
	static public Solution[] loadAllSolutionsByUserId(Connection conn, int user_id) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM Solution where users_id=?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, user_id);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.exercise = resultSet.getInt("exercise");
			loadedSolution.users_id = resultSet.getInt("users_id");
			loadedSolution.created = resultSet.getTimestamp("created");
			loadedSolution.updated = resultSet.getString("updated");
			loadedSolution.description = resultSet.getString("description");
			solutions.add(loadedSolution);
		}
		Solution[] uArray = new Solution[solutions.size()];
		uArray = solutions.toArray(uArray);
		return uArray;
	}
	static public Solution[] loadUsersSolutionsByUserId(Connection conn, int user_id) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM Solution where users_id=? AND description IS NULL";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, user_id);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.exercise = resultSet.getInt("exercise");
			loadedSolution.users_id = resultSet.getInt("users_id");
			loadedSolution.created = resultSet.getTimestamp("created");
			loadedSolution.updated = resultSet.getString("updated");
			loadedSolution.description = resultSet.getString("description");
			solutions.add(loadedSolution);
		}
		Solution[] uArray = new Solution[solutions.size()];
		uArray = solutions.toArray(uArray);
		return uArray;
	}
	static public Solution[] loadAllByExerciseId(Connection conn, int exercise_id) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM Solution where exercise=? ORDER BY created";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, exercise_id);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.exercise = resultSet.getInt("exercise");
			loadedSolution.users_id = resultSet.getInt("users_id");
			loadedSolution.created = resultSet.getTimestamp("created");
			loadedSolution.updated = resultSet.getString("updated");
			loadedSolution.description = resultSet.getString("description");
			solutions.add(loadedSolution);
		}
		Solution[] uArray = new Solution[solutions.size()];
		uArray = solutions.toArray(uArray);
		return uArray;
	}}


