package pl.coderslab.Classes;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;

public class User {

	// == fields ==

	private int id;
	private int user_group_id;
	private String username;
	private String password;
	private String email;

	// == constructors ==

	public User() {}

	public User(int user_group_id, String username, String email, String password) {
		this.user_group_id = user_group_id;
		this.username = username;
		this.email = email;
		this.setPassword(password);
	}

    // == constants ==

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_GROUP_ID = "user_group_id";
    public static final String COLUMN_USER_USERNAME = "username";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_EMAIL = "email";

    public static final String INSERT_INTO_USERS = "INSERT INTO " + TABLE_USERS + "(" +
            COLUMN_USER_USERNAME + ", " +
            COLUMN_USER_EMAIL + ", " +
            COLUMN_USER_PASSWORD + ", " +
            COLUMN_USER_GROUP_ID + ") VALUES (?, ?, ?, ?)";

    // == fields ==

    private PreparedStatement insertUser;

	//GETTERS
	public int getId() {return id;}
	public String getUsername() {return username;}
	public String getPassword() {return password;}
	public String getEmail() {return email;}
	public int getUser_group_id() {return user_group_id;}

	//SETTERS
	public void setUsername(String username) {this.username = username;}
	public void setEmail(String email) {this.email = email;}
	public void setPassword(String password) {this.password = BCrypt.hashpw(password, BCrypt.gensalt());}
	public void setUser_group_id(int user_group_id) {this.user_group_id = user_group_id;}

	// == methods ==

	public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = INSERT_INTO_USERS;

            insertUser = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insertUser.setString(1, this.username);
            insertUser.setString(2, this.email);
            insertUser.setString(3, this.password);
            insertUser.setInt(4, this.user_group_id);
            int affectedRows = insertUser.executeUpdate();

            ResultSet rs = insertUser.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }

		} else {
			String sql = "UPDATE Users SET username=?, email=?, password=?, user_group_id=? where id=?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, this.username);
			preparedStatement.setString(2, this.email);
			preparedStatement.setString(3, this.password);
			preparedStatement.setInt(4, this.user_group_id);
			preparedStatement.setInt(5, this.id);
			preparedStatement.executeUpdate();
		}
	}

	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM Users WHERE id= ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}

	static public User loadUserById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM Users where id=?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			User loadedUser = new User();
			loadedUser.id = resultSet.getInt("id");
			loadedUser.username = resultSet.getString("username");
			loadedUser.password = resultSet.getString("password");
			loadedUser.email = resultSet.getString("email");
			loadedUser.user_group_id = resultSet.getInt("user_group_id");
			return loadedUser;
		}
		return null;
	}

	static public User[] loadAllUsers(Connection conn) throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM Users";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			User loadedUser = new User();
			loadedUser.id = resultSet.getInt("id");
			loadedUser.username = resultSet.getString("username");
			loadedUser.password = resultSet.getString("password");
			loadedUser.email = resultSet.getString("email");
			loadedUser.user_group_id = resultSet.getInt("user_group_id");
			users.add(loadedUser);
		}
		User[] uArray = new User[users.size()];
		uArray = users.toArray(uArray);
		return uArray;
	}

	static public User[] loadAllByGrupId(Connection conn, int group_id) throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM users WHERE user_group_id=? ";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, group_id);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			User loadedUser = new User();
			loadedUser.id = resultSet.getInt("id");
			loadedUser.username = resultSet.getString("username");
			loadedUser.password = resultSet.getString("password");
			loadedUser.email = resultSet.getString("email");
			loadedUser.user_group_id = resultSet.getInt("user_group_id");
			users.add(loadedUser);
		}
		User[] uArray = new User[users.size()];
		uArray = users.toArray(uArray);
		return uArray;
	}
}

