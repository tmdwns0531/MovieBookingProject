package authentication;

import javax.mail.*;

/**
 * @author Ray
 *
 */
public class SMTPAuthenticator extends Authenticator {

	protected PasswordAuthentication getPasswordAuthentication() {
		String username = "tmdwns0535"; // gmail 사용자;
		String password = "cjswo4865@@"; // 패스워드;
		return new PasswordAuthentication(username, password);
	}

}