package authentication;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

/**
 * @author Ray
 *
 */
public class MailTest {

	public void sendEmail(String from, String to, String cc, String subject, String content) throws Exception {

		// Properties ����
		// ������Ƽ �� �ν��Ͻ� ������ �⺻����(SMTP ���� ȣ��Ʈ ����)
		Properties props = new Properties();

		// G-Mail SMTP ����
		props.put("mail.transport.protocol", "smtp");// �������� ����
		props.put("mail.smtp.host", "smtp.gmail.com");// gmail SMTP ���� �ּ�(ȣ��Ʈ)
		props.put("mail.smtp.port", "465");// gmail SMTP ���� ��Ʈ ����
		// �α��� �Ҷ� Transport Layer Security(TLS)�� ����� ������ ����
		// gmail ���� tls�� �ʼ��� �ƴϹǷ� �ص� �׸� ���ص� �׸�
		props.put("mail.smtp.starttls.enable", "true");
		// gmail ������ Secure Socket Layer(SSL) ����
		// gmail ���� ������ ������ֹǷ� ��� �����ָ� �ȵ�
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		// props.put("mail.smtp.user", from);
		props.put("mail.smtp.auth", "true");// SMTP ������ ����
		/**
		 * SMTP ������ �ʿ��� ��� �ݵ�� Properties �� SMTP ������ ����Ѵٰ� �����Ͽ��� �Ѵ�. �׷��� ������ ������ �õ����� ����
		 * �ʴ´�. �׸��� Authenticator Ŭ������ ��ӹ��� SMTPAuthenticator Ŭ������ �����Ѵ�.
		 * getPasswordAuthentication() �޼ҵ常 override �ϸ� �ȴ�. �� ��� �ٸ� �޼ҵ�� final �޼ҵ忩��
		 * override �� �� ���� ����.
		 */

		Authenticator auth = new SMTPAuthenticator();
		Session mailSession = Session.getDefaultInstance(props, auth); // gmail �Ӽ� + ���� -> ����

		// create a message
		Message msg = new MimeMessage(mailSession);

		// set the from and to address
		msg.setFrom(new InternetAddress(from));// ������ ��� ����
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));// �޴� �������

		if (!cc.trim().equals("")) {
			msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc, false));
		}

		// Setting the Subject and Content Type
		msg.setHeader("content-type", "text/plain;charset=utf-8");
		msg.setSubject(subject); // ���� ����
		msg.setText(content); // ���� ����
		msg.setSentDate(new Date());// ������ ��¥ ����

		Transport.send(msg); // ���� ������
	}

}