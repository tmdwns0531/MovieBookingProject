package authentication;

import java.util.*;

import javax.mail.*;

/**
 * @author Ray
 *
 */
public class PostMan {

	/**
	 * @param args
	 */

	private static String code;
	final static int SIZE = 6;

	// 인증키 생성
	private String getKey() {
		return getAuthCode();
	}

	// 인증코드 난수 발생
	private static String getAuthCode() {
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		int num = 0;

		while (buffer.length() < SIZE) {
			num = random.nextInt(10);
			buffer.append(num);
		}

		return buffer.toString();
	}

	public static int sendCodeToEmail(String to) { // 메일 받는 사람
		code = getAuthCode();

		String from = "tmdwns0535@gmail.com"; // 메일 보내는 사람

		String cc = "cc@daum.net"; // 참조
		String subject = "[STAR CINEMA] 이메일 인증번호 요청";// 제목
		String content = " 아래의 인증번호를 입력해주세요. \n " + "[ " + code + " ]";// 내용

		if (from.trim().equals("")) {
			System.out.println("보내는 사람을 입력하지 않았습니다.");
		} else if (to.trim().equals("")) {
			System.out.println("받는 사람을 입력하지 않았습니다.");
		} else {
			try {
				MailTest mt = new MailTest();

				// 메일보내기
				mt.sendEmail(from, to, cc, subject, content);
				System.out.println("메일 전송에 성공하였습니다.");
				return 0;
			} catch (MessagingException me) {
				System.out.println("메일 전송에 실패하였습니다.");
				System.out.println("실패 이유 : " + me.getMessage());
				return -1;
			} catch (Exception e) {
				System.out.println("메일 전송에 실패하였습니다.");
				System.out.println("실패 이유 : " + e.getMessage());
				return -1;
			}
		}
		return 0;
	}

	public static String getCode() {
		return code;
	}

//	public static void main(String[] args) {
//		sendCodeToEmail();
//
//		System.out.println("이메일 코드입력 : ");
//		Scanner sc = new Scanner(System.in);
//		String iptCode = sc.next();
//
//		if (iptCode.equals(code)) {
//			System.out.println("입력한  코드 : " + code);
//		} else {
//			System.out.println("인증실패");
//		}
//
//	}
}