package GUI;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import database.DBUtil;
import database.User;
import database.UserDB;

@SuppressWarnings("serial")
public class AccountInfoGUI extends JFrame {

	private final int width	 = 370;
	private final int height = 230;
	
	private String appName = "my app";
	
	public AccountInfoGUI(String username, String first_name, String last_name, String email, String creationDate, String lastLoginDate) {
		
		JLabel usernameLabel 		= new JLabel("username:");
		JLabel firstNameLabel 		= new JLabel("First Name:");
		JLabel lastNameLabel 		= new JLabel("Last Name:");
		JLabel emailLabel 		= new JLabel("e-mail:");
		JLabel dateOfCreationLabel	= new JLabel("Date of creation:");
		JLabel lastLoginDateLabel	= new JLabel("Last log in date:");

		JLabel userUsername		= new JLabel(username);
		JLabel userFirstName 		= new JLabel(first_name);
		JLabel userLastName 		= new JLabel(last_name);
		JLabel userEmail 		= new JLabel(email);
		JLabel userCreationDate		= new JLabel(creationDate);
		JLabel userLastLoginDate	= new JLabel(lastLoginDate);
		
		JLabel hiMessage = new JLabel(first_name + "'s profile: ");
		
		JButton signOutButton = new JButton("Sign out");
		JButton deleteButton = new JButton("Delete");

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(hiMessage)
				
				.addGroup(layout.createParallelGroup(LEADING)
						.addComponent(usernameLabel)
						.addComponent(firstNameLabel)
						.addComponent(lastNameLabel)
						.addComponent(emailLabel)
						.addComponent(dateOfCreationLabel)
						.addComponent(lastLoginDateLabel)
						.addComponent(signOutButton))

				.addGroup(layout.createParallelGroup(LEADING)
						.addComponent(userUsername)
						.addComponent(userFirstName)
						.addComponent(userLastName)
						.addComponent(userEmail)
						.addComponent(userCreationDate)
						.addComponent(userLastLoginDate)
						.addComponent(deleteButton))
				);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(usernameLabel)
						.addComponent(userUsername)
						.addComponent(hiMessage))
				
				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(firstNameLabel)
						.addComponent(userFirstName))

				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(lastNameLabel)
						.addComponent(userLastName))

				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(emailLabel)
						.addComponent(userEmail))

				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(dateOfCreationLabel)
						.addComponent(userCreationDate))

				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(lastLoginDateLabel)
						.addComponent(userLastLoginDate))

				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(signOutButton)
						.addComponent(deleteButton))
				);
		
		this.pack();
		this.setSize(width, height);
		this.setMinimumSize(new Dimension(width, height));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		/**
		 *  Action listeners: sign out, delete
		 *  - Go to log in GUI
		 *  - Delete user from database 
		 */
		//sign out
		signOutButton.addActionListener((ActionEvent event) -> {
			
			this.dispose();
			
			LoginGUI login = new LoginGUI();
			login.setName(appName);
			login.startLogin();
		});
		
		//delete
		deleteButton.addActionListener((ActionEvent event) -> {
			
			String errorMessage = "Your account has been deleted from our database.\n"
								+ "                     We are sorry to see you go.";
			JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Dialog",JOptionPane.ERROR_MESSAGE);
			this.dispose();
			
			/**
			 * Step 1: connect to database
			 * Step 2: delete the user from database
			 * Step 3: go to log in
			 */
			DBUtil.connectToDatabase();
			UserDB database = new UserDB();
			
			database.deleteUser(username);
			
			LoginGUI login = new LoginGUI();
			login.setName(appName);
			login.startLogin();
		});
	}

	public void setAppName(String appName) {
		
		this.appName = appName;

	}
	
	public void setUser(User user) {
		
		user = new User(user);
		
	}
	
	public void startAccountInfo() {
	
		setTitle("Welcome to your " + appName + " profile!");
		setVisible(true);
	
	}

}


