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

	// 인증키 생성
	private String getKey() {
		return getAuthCode();
	}

	// 인증코드 난수 발생
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

	// 인증메일 보내기
	public String sendAuthMail(String email) {

		// 6자리 난수 인증번호 생성
		String authKey = getKey();

		// 인증메일 보내기
		MimeMessage mail = mailSender.createMimeMessage();
		String mailContent = "<h1>[이메일 인증]</h1><br><p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>"
				+ "<a href='http://localhost:9080/member/signUpConfirm?email=" + email + "&authKey=" + authKey
				+ "' target='_blenk'>이메일 인증 확인</a>";

		try {
			mail.setSubject("회원가입 이메일 인증 ", "utf-8");
			mail.setText(mailContent, "utf-8", "html");
			mail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			mailSender.send(mail);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return authKey;
	}

}
