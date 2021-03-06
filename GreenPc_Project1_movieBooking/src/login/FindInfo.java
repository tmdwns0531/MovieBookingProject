package login;

import java.util.*;

import javax.swing.*;

import dao.*;
import vo.*;

class FindInfo extends JPanel {
	JPanel p1, p2;
	JLabel findID, findPW, name1, ph_num1, email1, id2, name2, ph_num2, email2;
	JTextField nameForID, emailForID, idForPW, nameForPW, ph_numForPW, emailForPW;
	JButton forID, forPW, back;
	boolean flag;

	Find_IDlist fi = new Find_IDlist();
	Find_PWlist fp = new Find_PWlist();

	FindInfo() {

		p1 = new JPanel(null);
		p2 = new JPanel(null);

		findID = new JLabel(" ▶  아이디 찾기 ");
		findPW = new JLabel(" ▶  비밀번호 찾기 ");

		name1 = new JLabel(" 이름 ");
		ph_num1 = new JLabel(" 핸드폰 번호 ");
		email1 = new JLabel(" 이메일 ");

		id2 = new JLabel("아이디");
		name2 = new JLabel("이름");
		ph_num2 = new JLabel(" 핸드폰 번호 ");
		email2 = new JLabel(" 이메일 ");

		nameForID = new JTextField();
		emailForID = new JTextField();
		idForPW = new JTextField();
		nameForPW = new JTextField();
		ph_numForPW = new JTextField();
		emailForPW = new JTextField();

		forID = new JButton(" 아이디 찾기");
		forPW = new JButton(" 비밀번호 찾기");

		back = new JButton("◀");

		setLayout(null);

		// 화면 구성

		// add panel1 , panel2
		add(p1).setBounds(80, 0, 550, 300);
		add(p2).setBounds(80, 250, 550, 330);

		// add Label p1
		p1.add(name1).setBounds(200, 90, 50, 50);
		p1.add(email1).setBounds(190, 145, 50, 50);

		// add textfield p1
		p1.add(findID).setBounds(100, 30, 100, 100);
		p1.add(nameForID).setBounds(250, 100, 250, 40);
		p1.add(emailForID).setBounds(250, 150, 250, 40);

		// add button p1
		p1.add(forID).setBounds(250, 200, 100, 30);

		// label add p2
		p2.add(id2).setBounds(200, 100, 50, 50);
		p2.add(name2).setBounds(210, 150, 50, 50);
		p2.add(ph_num2).setBounds(180, 200, 100, 50);
		p2.add(email2).setBounds(200, 245, 50, 50);

		// textfield add p2
		p2.add(findPW).setBounds(100, 30, 100, 100);
		p2.add(idForPW).setBounds(250, 100, 250, 40);
		p2.add(nameForPW).setBounds(250, 150, 250, 40);
		p2.add(ph_numForPW).setBounds(250, 200, 250, 40);
		p2.add(emailForPW).setBounds(250, 250, 250, 40);

		// button add p1 p2
		p2.add(forPW).setBounds(250, 300, 130, 30);

		// back button
		add(back).setBounds(10, 550, 50, 50);

	}

	// submit info for finding id
	void findIDList() {

		ArrayList<MemberVo> li = MemberDAO.getInstance().findID(nameForID.getText(), emailForID.getText());

		if (checkingPatternForID() == -1) {
			return;
		}

		if (li.size() != 0) {
			flag = true;
			fi.showIDs(li);
		} else {
			JOptionPane.showMessageDialog(null, "일치하는 정보가 없습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			flag = false;
		}
	}

	// submit info for finding password
	void findPW() {
		MemberVo member = new MemberVo();
		member.setUser_id(idForPW.getText());
		member.setUser_name(nameForPW.getText());
		member.setPhone_num(ph_numForPW.getText());
		member.setEmail(emailForPW.getText());

		ArrayList<MemberVo> li = MemberDAO.getInstance().findPW(member);

		if (checkingPatternForPW() == -1) {
			flag = false;
			return;
		}

		if (li.size() != 0) {
			flag = true;
			fp.showPWs(li);
		} else {
			JOptionPane.showMessageDialog(null, "일치하는 정보가 없습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			flag = false;
		}
	}

	// check pattern to submit for finding id
	int checkingPatternForID() {
		if (nameForID.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "이름을 입력하세요.", "check fail", JOptionPane.ERROR_MESSAGE);
			nameForID.grabFocus();
			return -1;
		} else if (nameForID.getText().contains(" ")) {
			JOptionPane.showMessageDialog(null, "이름에 공백이 포함되었습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			nameForID.grabFocus();
			return -1;
		} else if (emailForID.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "이메일을 입력하세요.", "check fail", JOptionPane.ERROR_MESSAGE);
			emailForID.grabFocus();
			return -1;
		} else if (emailForID.getText().contains(" ")) {
			JOptionPane.showMessageDialog(null, "이메일에 공백이 포함되었습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			emailForID.grabFocus();
			return -1;
		} else if (!(nameForID.getText().matches("^[가-힣]{2,10}|[a-zA-Z]{2,10}"))) {
			JOptionPane.showMessageDialog(null, "이름은 한글 또는 영문만 가능하며 2~10자리 이내로 가능합니다", "check fail",
					JOptionPane.ERROR_MESSAGE);
			nameForID.grabFocus();
			return -1;
		} else if (!(emailForID.getText().matches("^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*$"))) {
			JOptionPane.showMessageDialog(null, "이메일 형식이 맞지 않습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			emailForID.grabFocus();
			return -1;
		}

		return 0;

	}

	// check pattern to submit for finding pw
	int checkingPatternForPW() {

		if (idForPW.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "아이디를 입력하세요.", "check fail", JOptionPane.ERROR_MESSAGE);
			idForPW.grabFocus();
			return -1;
		} else if (idForPW.getText().contains(" ")) {
			JOptionPane.showMessageDialog(null, "아이디에 공백이 포함되었습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			idForPW.grabFocus();
			return -1;
		} else if (nameForPW.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "이름을 입력하세요.", "check fail", JOptionPane.ERROR_MESSAGE);
			nameForPW.grabFocus();
			return -1;
		} else if (nameForPW.getText().contains(" ")) {
			JOptionPane.showMessageDialog(null, "이름에 공백이 포함되었습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			nameForPW.grabFocus();
			return -1;
		} else if (ph_numForPW.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "핸드폰 번호를 입력하세요.", "check fail", JOptionPane.ERROR_MESSAGE);
			ph_numForPW.grabFocus();
			return -1;
		} else if (ph_numForPW.getText().contains(" ")) {
			JOptionPane.showMessageDialog(null, "핸드폰 번호에 공백이 포함되었습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			ph_numForPW.grabFocus();
			return -1;
		} else if (emailForPW.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "이메일을 입력하세요.", "check fail", JOptionPane.ERROR_MESSAGE);
			emailForPW.grabFocus();
			return -1;
		} else if (emailForPW.getText().contains(" ")) {
			JOptionPane.showMessageDialog(null, "이메일에 공백이 포함되었습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			emailForPW.grabFocus();
			return -1;
		} else if (!(idForPW.getText().matches("^[a-zA-Z]{1}[a-zA-Z0-9_]{7,20}$"))) {
			JOptionPane.showMessageDialog(null, "아이디는 영문으로 시작하며  7~20자의 영문 소문자, 숫자와 특수기호(_),(-) 만 사용 가능합니다 ",
					"check fail", JOptionPane.ERROR_MESSAGE);
			idForPW.grabFocus();
			return -1;
		} else if (!(nameForPW.getText().matches("^[가-힣]{2,10}|[a-zA-Z]{2,10}"))) {
			JOptionPane.showMessageDialog(null, "이름은 한글 또는 영문만 가능하며 2~10자리 이내로 가능합니다", "check fail",
					JOptionPane.ERROR_MESSAGE);
			nameForPW.grabFocus();
			return -1;

		} else if (!(ph_numForPW.getText().matches("^\\d{3}-\\d{3,4}-\\d{4}$"))) {
			JOptionPane.showMessageDialog(null, "핸드폰 번호를 정확히 입력해주세요 ", "check fail", JOptionPane.ERROR_MESSAGE);
			ph_numForPW.grabFocus();
			return -1;
		} else if (!(emailForPW.getText().matches("^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*$"))) {
			JOptionPane.showMessageDialog(null, "이메일 형식이 맞지 않습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			emailForPW.grabFocus();
			return -1;
		}
		return 0;
	}

}
