package database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class User {

	public static DateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm aaa");
	
	private String username;
	private String password;
	private String salt;
	
	private String first_name;
	private String last_name;
	private String email;
	private String creationDate;
	private String lastLoginDate;

	public User() {}
	
	public User(String username, String password, String first_name, String last_name, String email) {
		super();
		this.username	= username;
		this.password	= password;
		this.first_name = first_name;
		this.last_name	= last_name;
		this.email		= email;
		
		Calendar current = Calendar.getInstance();
		creationDate  = df.format(current.getTime());
		lastLoginDate = df.format(current.getTime());
		
	}
	
	/**
	 * Copy constructor
	 */
	public User(User user) {
		
		username 	  = user.username;
		password 	  = user.password;
		salt		  = user.salt;
		first_name 	  = user.first_name;
		last_name 	  = user.last_name;
		email 		  = user.email;
		creationDate  = user.creationDate;
		lastLoginDate = user.lastLoginDate;
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", salt=" + salt + ", first_name=" + first_name
				+ ", last_name=" + last_name + ", email=" + email + ", creationDate=" + creationDate
				+ ", lastLoginDate=" + lastLoginDate + "]";
	}
	
}