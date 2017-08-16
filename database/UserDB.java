package database;

import java.io.IOException;
//import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import security.SecurePassword;

//import security.SecurePassword;

public class UserDB {

	private String securityTableName;
	private String userInfoTableName;

	public UserDB(){

		securityTableName = "SECURITY";
		userInfoTableName = "INFO";

	}

	/**
	 * given core name creates two tables: security_table, info_table
	 * @param tableName tables' core name
	 */
	public UserDB(String tableName) {

		securityTableName = "SECURITY_" + tableName.toUpperCase();
		userInfoTableName = "INFO_" + tableName.toUpperCase();

	}

	public void createTable() {

		Connection conn = DBUtil.getConnection();

		boolean tableExists = false;
		try {

			DatabaseMetaData dbmd = conn.getMetaData();
			ResultSet rs = dbmd.getTables(null, null, null, new String[] { "TABLE" });

			while (rs.next()) {

				if (rs.getString(3).equals(securityTableName)) {

					tableExists = true;
					break;

				}

			}//while

		} catch (SQLException e) {

			DBUtil.displaySQLExceptions(e);

		}

		if (!tableExists) {

			try {

				Statement statement = conn.createStatement();
				String createSecuritySQL = "CREATE TABLE " + securityTableName + "("
						+ "id INT NOT NULL AUTO_INCREMENT, "
						+ "username VARCHAR(50) NOT NULL UNIQUE, "
						+ "password VARCHAR(130) NOT NULL, "
						+ "salt VARCHAR(30) NOT NULL, "
						+ "PRIMARY KEY (id)" + ")";

				String createUserInfoSQL = "CREATE TABLE " + userInfoTableName + "("
						+ "id INT NOT NULL AUTO_INCREMENT, "
						+ "first_name VARCHAR(50), "
						+ "last_name VARCHAR(50), "
						+ "email VARCHAR(50), "
						+ "creationDate VARCHAR(50), "
						+ "lastLoginDate VARCHAR(50), "
						+ "PRIMARY KEY (id), "
						+ "KEY id(id), "
						+ "CONSTRAINT id_ibfk_1 "
						+ "FOREIGN KEY(id) REFERENCES " +  securityTableName + "(id)"
						+ "ON DELETE CASCADE " + ") ENGINE=InnoDB";

				statement.execute(createSecuritySQL);
				statement.execute(createUserInfoSQL);
				//conn.commit();

			} catch (SQLException e) {

				DBUtil.displaySQLExceptions(e);

			}

		} else {

			System.out.println("Table " + securityTableName + "and" + userInfoTableName + "already exist so we'll insert data here.");

		}
	}

	public void insertUser (User newUser) throws IOException {

		Connection conn = DBUtil.getConnection();

		try {

			Statement statement = conn.createStatement();
			
			String salt = SecurePassword.createSalt();
			
			String hashPassword = SecurePassword.getSHA512SecurePassword(newUser.getPassword(), salt);			
			
			String insertToSecuritySQL = "INSERT INTO " + securityTableName 
					+ " (username, password, salt) VALUE( '" 
					+ newUser.getUsername() + "', '"
					+ hashPassword + "', '"
					+ salt 	+ "' )";

			String insertToUserInfoSQL = "INSERT INTO " + userInfoTableName
					+ " (first_name, last_name, email, creationDate, lastLoginDate) VALUE ( '" 
					+ newUser.getFirst_name() 	 + "', '" 
					+ newUser.getLast_name() 	 + "', '" 
					+ newUser.getEmail()		 + "', '" 
					+ newUser.getCreationDate()  + "', '" 
					+ newUser.getLastLoginDate() + "')";

			statement.execute(insertToSecuritySQL);
			statement.execute(insertToUserInfoSQL);

		} catch (SQLException e) {

			DBUtil.displaySQLExceptions(e);

		}

	}

	public void deleteUser (String username) {

		Connection conn = DBUtil.getConnection();

		String insertUserSQL = "DELETE FROM " + securityTableName + " WHERE username = '" + username + "'";

		try {

			Statement statement = conn.createStatement();
			statement.execute(insertUserSQL);			

		} catch (SQLException e) {

			DBUtil.displaySQLExceptions(e);

		}

	}

	public void updateUser (String username) {

		Connection conn = DBUtil.getConnection();

		Calendar current = Calendar.getInstance();
		String currentTime  = User.df.format(current.getTime());

		String updateUser = "UPDATE " + userInfoTableName + " SET lastLoginDate = '" + currentTime + "' " +
				"WHERE id = (SELECT id FROM " + securityTableName + " WHERE username = '" + username + "')"; 

		try {

			Statement statement = conn.createStatement();
			statement.execute(updateUser);

		} catch (SQLException e) {

			DBUtil.displaySQLExceptions(e);

		}

	}

	public User loginUser(String username, String password) throws SQLException, IOException {

		Connection conn = DBUtil.getConnection();

		String getPasswordQuery = "SELECT id, password, salt FROM " + securityTableName + " WHERE username = '" + username + "'";

		User u = new User();

		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(getPasswordQuery);

		//while executes one time
		while (resultSet.next()) {

			String hashPassword = resultSet.getString("password");
			String salt = resultSet.getString("salt");
			String passwordHashed = SecurePassword.getSHA512SecurePassword(password, salt);

			//if username and password match get info
			if(passwordHashed.equals(hashPassword)) {

				String getInfo = "SELECT * FROM " + userInfoTableName + " WHERE id = " + resultSet.getInt("id");

				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(getInfo);

				u.setUsername(username);
				u.setPassword(password);
				u.setSalt(salt);

				//while executes one time
				while (rs.next()) {

					u.setFirst_name(rs.getString("first_name"));
					u.setLast_name(rs.getString("last_name"));
					u.setEmail(rs.getString("email"));
					u.setCreationDate(rs.getString("creationDate"));
					u.setLastLoginDate(rs.getString("lastLoginDate"));

				}

				System.out.println("Welcome, " + username + "!");
				return u;

			} else {

				System.out.println("The username and password you entered did not match our records. Please double-check and try again.");
			}
		}//while

		return u;
	}

	/**
	//check security table update function
	public void displaySecurityTable () {

		Connection conn = DBUtil.getConnection();

		String displaySQL = "SELECT * FROM " + securityTableName;

		try{

			Statement statement = conn.createStatement();

			ResultSet resultSet = statement.executeQuery(displaySQL);

			while (resultSet.next()) {

				System.out.println(resultSet.getString("username") + ", " + resultSet.getString("password"));

			}
		} catch (SQLException e) {

			DBUtil.displaySQLExceptions(e);

		}

	}

	//test function
	public static void main(String[] args) throws SQLException{

		System.out.println("Connect to database ...");
		DBUtil.connectToDatabase();

		UserDB database = new UserDB();

		System.out.println(database.loginUser("radu", "random02"));
		database.updateUser("radu");
	}
	 */

}
