package login;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class LoginMainFrame extends JFrame implements ActionListener {
	// 배치
	CardLayout card = new CardLayout();
	Login login = new Login();
	Join join = new Join();
	FindInfo find = new FindInfo();
	ResetPWD resetPW = new ResetPWD();

	public LoginMainFrame() {
		// GUI 화면 디자인, 변수 초기화, 데이터베이스 연결, 서버연결, 쓰레드 동작
		// Layout => 화면배치

		// setting main frame
		System.out.println("Login Frame **");
		setLayout(card);
		setSize(800, 650);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// add panels for frame
		add("LOGIN", login);
		add("JOIN", join);
		add("FIND", find);
		add("FIND_ID_LIST", find.fi);
		add("FIND_PW", find.fp);
		add("RESET", resetPW);

		// each panel button's add action
		login.signUp.addActionListener(this);
		join.signUp.addActionListener(this);
		join.back.addActionListener(this);
		login.findInfo.addActionListener(this);
		find.back.addActionListener(this);
		find.forID.addActionListener(this);
		find.forPW.addActionListener(this);
		find.fi.goLogin.addActionListener(this);
		find.fi.goFindPW.addActionListener(this);
		find.fp.goFindID.addActionListener(this);
		find.fp.goLogin.addActionListener(this);
		find.fp.resetPW.addActionListener(this);
		resetPW.back.addActionListener(this);
		resetPW.ok.addActionListener(this);

		// center screen
		Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

	}

	public Login getLoginInstance() {
		return this.login;
	}

	@Override
	public void actionPerformed(ActionEvent e)

	{
		// TODO Auto-generated method stub

		if (e.getSource() == login.signUp) {
			card.show(getContentPane(), "JOIN");
			initializationText(e);
			join.tfID.grabFocus();

		} else if (e.getSource() == join.signUp) {

			join.joinAction();

			if (join.succesJoin == true) {
				card.show(getContentPane(), "LOGIN");
				initializationText(e);
				join.succesJoin = false;
			}
			login.tfID.grabFocus();
		} else if (e.getSource() == join.back) {
			if (join.backAction() == true) {
				card.show(getContentPane(), "LOGIN");
				initializationText(e);
			}
			login.tfID.grabFocus();
		} else if (e.getSource() == login.findInfo) {
			card.show(getContentPane(), "FIND");
			initializationText(e);
			find.nameForID.grabFocus();
		} else if (e.getSource() == find.back) {
			card.show(getContentPane(), "LOGIN");
			initializationText(e);
			login.tfID.grabFocus();
		} else if (e.getSource() == find.forID) {

			find.findIDList();

			if (find.flag == true) {
				card.show(getContentPane(), "FIND_ID_LIST");
				initializationText(e);
			}
		} else if (e.getSource() == find.forPW) {
			find.findPW();

			if (find.flag == true) {
				card.show(getContentPane(), "FIND_PW");
				initializationText(e);
			}

			// in search id panel
		} else if (e.getSource() == find.fi.goLogin) {
			find.fi.goLoginFunc();
			login.tfID.setText(find.fi.user_id);
			card.show(getContentPane(), "LOGIN");
			initializationText(e);
			login.tfPW.grabFocus();

		} else if (e.getSource() == find.fi.goFindPW) {
			card.show(getContentPane(), "FIND");
			initializationText(e);
			find.idForPW.grabFocus();
			// in search pw panel

		} else if (e.getSource() == find.fp.goLogin) {
			login.tfID.setText(find.fp.searchID);
			card.show(getContentPane(), "LOGIN");
			initializationText(e);
			login.tfPW.grabFocus();
		} else if (e.getSource() == find.fp.goFindID) {
			card.show(getContentPane(), "FIND");
			initializationText(e);
			find.nameForID.grabFocus();
		} else if (e.getSource() == find.fp.resetPW) {
			card.show(getContentPane(), "RESET");
			resetPW.setContainerID(find.fp.searchID);
			resetPW.inputNew.setText("");
			resetPW.inputCheck.setText("");
		} else if (e.getSource() == resetPW.back) {
			card.show(getContentPane(), "FIND_PW");
		} else if (e.getSource() == resetPW.ok) {

			if (resetPW.updatePwd(find.fp.searchID) == 0) {
				login.tfID.setText(find.fp.searchID);
				login.tfPW.grabFocus();
				card.show(getContentPane(), "LOGIN");
			}
		}
	}

	void initializationText(ActionEvent e) {

		if (e.getSource() == join.signUp || e.getSource() == join.back) {
			join.tfID.setText("");
			join.tfPW.setText("");
			join.tfPW2.setText("");
			join.tfNAME.setText("");
			join.tfPH_NUM.setText("");
			join.tfEMAIL.setText("");
		} else if (e.getSource() == login.signUp) {
			login.tfID.setText("");
			login.tfPW.setText("");
		} else if (e.getSource() == login.findInfo || e.getSource() == find.fi.goFindPW
				|| e.getSource() == find.fp.goFindID) {
			find.nameForID.setText("");
			find.emailForID.setText("");
			find.idForPW.setText("");
			find.nameForPW.setText("");
			find.ph_numForPW.setText("");
			find.emailForPW.setText("");
		}
	}

}