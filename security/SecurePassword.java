package security;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecurePassword {

	public static String createSalt() {
		
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);

	}

	public static String getSHA512SecurePassword(String passwordToHash, String salt) throws IOException{

		String generatedPassword = null;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");

			md.update(salt.getBytes("UTF-8"));
			byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));

			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < bytes.length; i++){

				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));

			}

			generatedPassword = sb.toString();

		} catch (NoSuchAlgorithmException e){

			e.printStackTrace();

		}
		return generatedPassword;
	}

	public static void displaysIOExceptions(IOException exception) {

		System.out.println("Message: " + exception.getMessage());

		Throwable cause = exception.getCause();
		while (cause != null) {

			System.out.println("Cause:" + cause);
			cause = cause.getCause();

		}
		
	}

}


