package authentication;

import javax.mail.*;

/**
 * @author Ray
 *
 */
public class SMTPAuthenticator extends Authenticator {

	protected PasswordAuthentication getPasswordAuthentication() {
		String username = "tmdwns0535"; // gmail �����;
		String password = "cjswo4865@@"; // �н�����;
		return new PasswordAuthentication(username, password);
	}

}