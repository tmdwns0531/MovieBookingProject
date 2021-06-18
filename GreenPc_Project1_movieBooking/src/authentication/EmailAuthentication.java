package authentication;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

@Service("mss")
public class EmailAuthentication {
	@Autowired
	private JavaMailSenderImpl mailSender;

	final int SIZE = 6;

	// ����Ű ����
	private String getKey() {
		return getAuthCode();
	}

	// �����ڵ� ���� �߻�
	private String getAuthCode() {
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		int num = 0;

		while (buffer.length() < SIZE) {
			num = random.nextInt(10);
			buffer.append(num);
		}

		return buffer.toString();
	}

	// �������� ������
	public String sendAuthMail(String email) {

		// 6�ڸ� ���� ������ȣ ����
		String authKey = getKey();

		// �������� ������
		MimeMessage mail = mailSender.createMimeMessage();
		String mailContent = "<h1>[�̸��� ����]</h1><br><p>�Ʒ� ��ũ�� Ŭ���Ͻø� �̸��� ������ �Ϸ�˴ϴ�.</p>"
				+ "<a href='http://localhost:9080/member/signUpConfirm?email=" + email + "&authKey=" + authKey
				+ "' target='_blenk'>�̸��� ���� Ȯ��</a>";

		try {
			mail.setSubject("ȸ������ �̸��� ���� ", "utf-8");
			mail.setText(mailContent, "utf-8", "html");
			mail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			mailSender.send(mail);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return authKey;
	}

}
