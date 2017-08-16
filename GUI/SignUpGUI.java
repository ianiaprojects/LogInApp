package GUI;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import database.DBUtil;
import database.User;
import database.UserDB;
import security.SecurePassword;
//import security.SecurePassword;

@SuppressWarnings("serial")
public class SignUpGUI extends JFrame {

	private final int width	 = 350;
	private final int height = 250;

	private String appName = "my app";

	public SignUpGUI() throws IOException {

		//components from security_table
		JLabel usernameLabel = new JLabel("username:");
		JLabel passwordLabel = new JLabel("password:");

		JTextField usernameTextField = new JTextField();
		JTextField passwordTextField = new JPasswordField();

		//components from info_table
		JLabel firstNameLabel = new JLabel("First Name:");
		JLabel lastNameLabel  = new JLabel("Last Name:");
		JLabel emailLabel	  = new JLabel("e-mail:");

		JTextField firstNameTextField = new JTextField();
		JTextField lastNameTextField  = new JTextField();
		JTextField emailTextField	  = new JTextField();

		JButton signUpButton = new JButton("Sign up");
		JButton loginButton = new JButton("Log in");

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(LEADING)
						.addComponent(firstNameLabel)
						.addComponent(lastNameLabel)
						.addComponent(emailLabel)
						.addComponent(usernameLabel)
						.addComponent(passwordLabel))

				.addGroup(layout.createParallelGroup(LEADING)
						.addComponent(firstNameTextField)
						.addComponent(lastNameTextField)
						.addComponent(emailTextField)
						.addComponent(usernameTextField)
						.addComponent(passwordTextField)

						.addGroup(layout.createSequentialGroup()
								.addComponent(signUpButton)
								.addComponent(loginButton)))
				);

		layout.linkSize(SwingConstants.VERTICAL, firstNameTextField, lastNameTextField, emailTextField, usernameTextField, passwordTextField, signUpButton);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(firstNameLabel)
						.addComponent(firstNameTextField))

				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(lastNameLabel)
						.addComponent(lastNameTextField))

				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(emailLabel)
						.addComponent(emailTextField))

				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(usernameLabel)
						.addComponent(usernameTextField))

				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(passwordLabel)
						.addComponent(passwordTextField))

				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(signUpButton)
						.addComponent(loginButton))
				);

		this.pack();
		this.setSize(width, height);
		this.setMinimumSize(new Dimension(width, height));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		/**
		 *  Action listeners: sign up, log in
		 *  Store received info into a User then insertUser(user) into database 
		 */
		//sign up
		signUpButton.addActionListener((ActionEvent event) -> {

			this.dispose();

			User user = new User(usernameTextField.getText(), passwordTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText(), emailTextField.getText());
			DBUtil.connectToDatabase();
			UserDB database = new UserDB();
			database.createTable();

			try {
				
				database.insertUser(user);
				
			} catch (IOException e) {
				
				SecurePassword.displaysIOExceptions(e);
			}

			AccountInfoGUI userProfile = new AccountInfoGUI(user.getUsername(), user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getCreationDate(), user.getLastLoginDate());
			userProfile.setAppName(appName);
			userProfile.startAccountInfo();

		});
		
		loginButton.addActionListener((ActionEvent event) -> {
			
			this.dispose();
			
			LoginGUI login = new LoginGUI();
			login.setName(appName);
			login.startLogin();
		});

	}

	public void setAppName(String appName) {

		this.appName = appName;

	}

	public void startSignUp() {

		setTitle("Join " + appName + " today!");
		setVisible(true);

	}

	public String getAppName() {

		return appName;
	}

}
