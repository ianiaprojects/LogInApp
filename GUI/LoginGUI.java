package GUI;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import database.DBUtil;
import database.User;
import database.UserDB;
import security.SecurePassword;

@SuppressWarnings("serial")
public class LoginGUI extends JFrame {

	private final int width = 300;
	private final int height = 160;

	private String appName = "my app";

	public LoginGUI() {

		JLabel usernameLabel = new JLabel("username:");
		JLabel passwordLabel = new JLabel("password:");

		JTextField usernameTextField = new JTextField();
		JTextField passwordTextField = new JPasswordField();

		JButton loginButton = new JButton("Log in");
		JButton signupButton = new JButton("Signu up");

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(LEADING)
						.addComponent(usernameLabel)
						.addComponent(passwordLabel))

				.addGroup(layout.createParallelGroup(LEADING)
						.addComponent(usernameTextField)
						.addComponent(passwordTextField)

						.addGroup(layout.createSequentialGroup()
								.addComponent(loginButton)
								.addComponent(signupButton)))
				);

		layout.linkSize(SwingConstants.VERTICAL, usernameTextField, passwordTextField, loginButton, signupButton);


		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(usernameLabel)
						.addComponent(usernameTextField))

				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(passwordLabel)
						.addComponent(passwordTextField))

				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(loginButton)
						.addComponent(signupButton))
				);

		this.pack();
		this.setSize(width, height);
		this.setMinimumSize(new Dimension(width, height));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		/**
		 *  Action listeners: log in, sign up
		 */
		//log in
		loginButton.addActionListener((ActionEvent event) -> {

			this.dispose();

			/**
			 * Step 1: get username and password from text fields
			 * Step 2: connect to database
			 * Step 3: check if username and password match database
			 * Step 4: if OK go to profile
			 * 		   else  go back to login with error message
			 */
			String username = usernameTextField.getText();
			String password = passwordTextField.getText();

			DBUtil.connectToDatabase();
			UserDB database = new UserDB();

			try {
				
				User user = new User(database.loginUser(username, password));
				
				if (user.getUsername() != null) {

					AccountInfoGUI userProfile = new AccountInfoGUI(user.getUsername(), user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getCreationDate(), user.getLastLoginDate());
					userProfile.setAppName(appName);
					userProfile.startAccountInfo();

					database.updateUser(user.getUsername());

				} else {

					String errorMessage = "The username and password you entered did not match our records.\n"
							+"                              Please double-check and try again.";
					JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Dialog",JOptionPane.ERROR_MESSAGE);

					LoginGUI login = new LoginGUI();
					login.setName(appName);
					login.startLogin();

				}

			} catch (SQLException e) {

				DBUtil.displaySQLExceptions(e);
				
			} catch (IOException e) {
				
				SecurePassword.displaysIOExceptions(e);
			}

		});

		//sign up
		signupButton.addActionListener((ActionEvent event) -> {

			this.dispose();

			/**
			 * Go to sign up
			 */
			SignUpGUI signup;
			try {

				signup = new SignUpGUI();
				signup.setAppName(appName);
				signup.startSignUp();

			} catch (IOException e) {

				SecurePassword.displaysIOExceptions(e);

			}

		});

	}

	public void setAppName(String appName) {

		this.appName = appName;

	}

	public void startLogin() {

		setTitle("Welcome to " + appName + "!");
		setVisible(true);

	}

	public String getAppName() {

		return appName;

	}

}

