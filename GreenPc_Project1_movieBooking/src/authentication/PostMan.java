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

	// ����Ű ����
	private String getKey() {
		return getAuthCode();
	}

	// �����ڵ� ���� �߻�
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

	public static int sendCodeToEmail(String to) { // ���� �޴� ���
		code = getAuthCode();

		String from = "tmdwns0535@gmail.com"; // ���� ������ ���

		String cc = "cc@daum.net"; // ����
		String subject = "[STAR CINEMA] �̸��� ������ȣ ��û";// ����
		String content = " �Ʒ��� ������ȣ�� �Է����ּ���. \n " + "[ " + code + " ]";// ����

		if (from.trim().equals("")) {
			System.out.println("������ ����� �Է����� �ʾҽ��ϴ�.");
		} else if (to.trim().equals("")) {
			System.out.println("�޴� ����� �Է����� �ʾҽ��ϴ�.");
		} else {
			try {
				MailTest mt = new MailTest();

				// ���Ϻ�����
				mt.sendEmail(from, to, cc, subject, content);
				System.out.println("���� ���ۿ� �����Ͽ����ϴ�.");
				return 0;
			} catch (MessagingException me) {
				System.out.println("���� ���ۿ� �����Ͽ����ϴ�.");
				System.out.println("���� ���� : " + me.getMessage());
				return -1;
			} catch (Exception e) {
				System.out.println("���� ���ۿ� �����Ͽ����ϴ�.");
				System.out.println("���� ���� : " + e.getMessage());
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
//		System.out.println("�̸��� �ڵ��Է� : ");
//		Scanner sc = new Scanner(System.in);
//		String iptCode = sc.next();
//
//		if (iptCode.equals(code)) {
//			System.out.println("�Է���  �ڵ� : " + code);
//		} else {
//			System.out.println("��������");
//		}
//
//	}
}