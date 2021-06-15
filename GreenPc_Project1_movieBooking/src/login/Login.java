package login;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import dao.*;
import home.*;
import vo.*;

public class Login extends JPanel {

	Label id, pw, msg;
	Dialog al;
	Button signIn, signUp, findInfo, goHome;
	JTextField tfID;
	JPasswordField tfPW;
	Panel p;
	String sessionID;
	boolean logged;

	public Login() {

		id = new Label("���̵� : ", Label.RIGHT);
		pw = new Label("��й�ȣ : ", Label.RIGHT);
		signIn = new Button("Sign In");
		signUp = new Button("Sign Up");
		findInfo = new Button("Forgot your id / password ?");
		tfID = new JTextField(20);
		tfPW = new JPasswordField(20);
		msg = new Label();
		goHome = new Button("go Home");

		setLayout(null);

		setBackground(Color.lightGray);
		setSize(100, 100);
		setLocation(0, 0);

		add(id).setBounds(160, 240, 80, 50);
		add(tfID).setBounds(260, 240, 300, 40);

		add(pw).setBounds(160, 300, 80, 50);
		add(tfPW).setBounds(260, 300, 300, 40);
		tfPW.setEchoChar('*');

		add(signIn).setBounds(360, 370, 100, 30);
		add(signUp).setBounds(360, 420, 100, 30);
		add(findInfo).setBounds(325, 470, 170, 30);
		add(goHome).setBounds(360, 520, 100, 30);
	}

	public boolean login() {
		// TODO Auto-generated method stub
		String id = tfID.getText();
		char[] pw_t = tfPW.getPassword();
		String pw = String.valueOf(pw_t);

		List<MemberVo> li = MemberDAO.getInstance().list();

		boolean check = true;

		for (MemberVo vo : li) {
			if (id.equals(vo.getUser_id()) && pw.equals(vo.getUser_pwd())) {
				System.out.println("�α��οϷ� !! ");
				check = true;
				JOptionPane.showMessageDialog(null, "�α����� �Ϸ�Ǿ����ϴ�.", "login success", JOptionPane.INFORMATION_MESSAGE);
				Session.getInstance().logIn(id, pw); // Session.logged = true , Session.getID == id
				sessionID = Session.getInstance().getIdx();
				logged = Session.getInstance().logFlag();
				return true;
			} else if (!(id.equals(vo.getUser_id()))) {
				check = false;
			} else {
				System.out.println(" ��й�ȣ ����ġ !! ");
				check = true;
				JOptionPane.showMessageDialog(null, "��й�ȣ�� ��ġ���� �ʽ��ϴ�", "login fail", JOptionPane.ERROR_MESSAGE);
			}
		}

		if (!check) {
			System.out.println("��ϵ� �Ƶ� ���� !! ");
			JOptionPane.showMessageDialog(null, "��ϵ� ���̵� �����ϴ�", "login fail", JOptionPane.ERROR_MESSAGE);
		}
		return false;

	};

	public Button getHomeButton() {
		return this.goHome;
	}

	public String sessionID() {
		return this.sessionID;
	}

	public boolean logged() {
		return this.logged;
	}

	public void setLogOutSessionID() {
		this.sessionID = null;
		this.logged = false;
	}

	public Button getSignInButton() {
		return this.signIn;
	}
}
