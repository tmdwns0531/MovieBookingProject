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

		findID = new JLabel(" ¢º  ¾ÆÀÌµð Ã£±â ");
		findPW = new JLabel(" ¢º  ºñ¹Ð¹øÈ£ Ã£±â ");

		name1 = new JLabel(" ÀÌ¸§ ");
		ph_num1 = new JLabel(" ÇÚµåÆù ¹øÈ£ ");
		email1 = new JLabel(" ÀÌ¸ÞÀÏ ");

		id2 = new JLabel("¾ÆÀÌµð");
		name2 = new JLabel("ÀÌ¸§");
		ph_num2 = new JLabel(" ÇÚµåÆù ¹øÈ£ ");
		email2 = new JLabel(" ÀÌ¸ÞÀÏ ");

		nameForID = new JTextField();
		emailForID = new JTextField();
		idForPW = new JTextField();
		nameForPW = new JTextField();
		ph_numForPW = new JTextField();
		emailForPW = new JTextField();

		forID = new JButton(" ¾ÆÀÌµð Ã£±â");
		forPW = new JButton(" ºñ¹Ð¹øÈ£ Ã£±â");

		back = new JButton("¢¸");

		setLayout(null);

		// È­¸é ±¸¼º

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
			JOptionPane.showMessageDialog(null, "ÀÏÄ¡ÇÏ´Â Á¤º¸°¡ ¾ø½À´Ï´Ù", "check fail", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, "ÀÏÄ¡ÇÏ´Â Á¤º¸°¡ ¾ø½À´Ï´Ù", "check fail", JOptionPane.ERROR_MESSAGE);
			flag = false;
		}
	}

	// check pattern to submit for finding id
	int checkingPatternForID() {
		if (nameForID.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "ÀÌ¸§À» ÀÔ·ÂÇÏ¼¼¿ä.", "check fail", JOptionPane.ERROR_MESSAGE);
			nameForID.grabFocus();
			return -1;
		} else if (nameForID.getText().contains(" ")) {
			JOptionPane.showMessageDialog(null, "ÀÌ¸§¿¡ °ø¹éÀÌ Æ÷ÇÔµÇ¾ú½À´Ï´Ù", "check fail", JOptionPane.ERROR_MESSAGE);
			nameForID.grabFocus();
			return -1;
		} else if (emailForID.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "ÀÌ¸ÞÀÏÀ» ÀÔ·ÂÇÏ¼¼¿ä.", "check fail", JOptionPane.ERROR_MESSAGE);
			emailForID.grabFocus();
			return -1;
		} else if (emailForID.getText().contains(" ")) {
			JOptionPane.showMessageDialog(null, "ÀÌ¸ÞÀÏ¿¡ °ø¹éÀÌ Æ÷ÇÔµÇ¾ú½À´Ï´Ù", "check fail", JOptionPane.ERROR_MESSAGE);
			emailForID.grabFocus();
			return -1;
		} else if (!(nameForID.getText().matches("^[°¡-ÆR]{2,10}|[a-zA-Z]{2,10}"))) {
			JOptionPane.showMessageDialog(null, "ÀÌ¸§Àº ÇÑ±Û ¶Ç´Â ¿µ¹®¸¸ °¡´ÉÇÏ¸ç 2~10ÀÚ¸® ÀÌ³»·Î °¡´ÉÇÕ´Ï´Ù", "check fail",
					JOptionPane.ERROR_MESSAGE);
			nameForID.grabFocus();
			return -1;
		} else if (!(emailForID.getText().matches("^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*$"))) {
			JOptionPane.showMessageDialog(null, "ÀÌ¸ÞÀÏ Çü½ÄÀÌ ¸ÂÁö ¾Ê½À´Ï´Ù", "check fail", JOptionPane.ERROR_MESSAGE);
			emailForID.grabFocus();
			return -1;
		}

		return 0;

	}

	// check pattern to submit for finding pw
	int checkingPatternForPW() {

		if (idForPW.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "¾ÆÀÌµð¸¦ ÀÔ·ÂÇÏ¼¼¿ä.", "check fail", JOptionPane.ERROR_MESSAGE);
			idForPW.grabFocus();
			return -1;
		} else if (idForPW.getText().contains(" ")) {
			JOptionPane.showMessageDialog(null, "¾ÆÀÌµð¿¡ °ø¹éÀÌ Æ÷ÇÔµÇ¾ú½À´Ï´Ù", "check fail", JOptionPane.ERROR_MESSAGE);
			idForPW.grabFocus();
			return -1;
		} else if (nameForPW.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "ÀÌ¸§À» ÀÔ·ÂÇÏ¼¼¿ä.", "check fail", JOptionPane.ERROR_MESSAGE);
			nameForPW.grabFocus();
			return -1;
		} else if (nameForPW.getText().contains(" ")) {
			JOptionPane.showMessageDialog(null, "ÀÌ¸§¿¡ °ø¹éÀÌ Æ÷ÇÔµÇ¾ú½À´Ï´Ù", "check fail", JOptionPane.ERROR_MESSAGE);
			nameForPW.grabFocus();
			return -1;
		} else if (ph_numForPW.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "ÇÚµåÆù ¹øÈ£¸¦ ÀÔ·ÂÇÏ¼¼¿ä.", "check fail", JOptionPane.ERROR_MESSAGE);
			ph_numForPW.grabFocus();
			return -1;
		} else if (ph_numForPW.getText().contains(" ")) {
			JOptionPane.showMessageDialog(null, "ÇÚµåÆù ¹øÈ£¿¡ °ø¹éÀÌ Æ÷ÇÔµÇ¾ú½À´Ï´Ù", "check fail", JOptionPane.ERROR_MESSAGE);
			ph_numForPW.grabFocus();
			return -1;
		} else if (emailForPW.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "ÀÌ¸ÞÀÏÀ» ÀÔ·ÂÇÏ¼¼¿ä.", "check fail", JOptionPane.ERROR_MESSAGE);
			emailForPW.grabFocus();
			return -1;
		} else if (emailForPW.getText().contains(" ")) {
			JOptionPane.showMessageDialog(null, "ÀÌ¸ÞÀÏ¿¡ °ø¹éÀÌ Æ÷ÇÔµÇ¾ú½À´Ï´Ù", "check fail", JOptionPane.ERROR_MESSAGE);
			emailForPW.grabFocus();
			return -1;
		} else if (!(idForPW.getText().matches("^[a-zA-Z]{1}[a-zA-Z0-9_]{7,20}$"))) {
			JOptionPane.showMessageDialog(null, "¾ÆÀÌµð´Â ¿µ¹®À¸·Î ½ÃÀÛÇÏ¸ç  7~20ÀÚÀÇ ¿µ¹® ¼Ò¹®ÀÚ, ¼ýÀÚ¿Í Æ¯¼ö±âÈ£(_),(-) ¸¸ »ç¿ë °¡´ÉÇÕ´Ï´Ù ",
					"check fail", JOptionPane.ERROR_MESSAGE);
			idForPW.grabFocus();
			return -1;
		} else if (!(nameForPW.getText().matches("^[°¡-ÆR]{2,10}|[a-zA-Z]{2,10}"))) {
			JOptionPane.showMessageDialog(null, "ÀÌ¸§Àº ÇÑ±Û ¶Ç´Â ¿µ¹®¸¸ °¡´ÉÇÏ¸ç 2~10ÀÚ¸® ÀÌ³»·Î °¡´ÉÇÕ´Ï´Ù", "check fail",
					JOptionPane.ERROR_MESSAGE);
			nameForPW.grabFocus();
			return -1;

		} else if (!(ph_numForPW.getText().matches("^\\d{3}-\\d{3,4}-\\d{4}$"))) {
			JOptionPane.showMessageDialog(null, "ÇÚµåÆù ¹øÈ£¸¦ Á¤È®È÷ ÀÔ·ÂÇØÁÖ¼¼¿ä ", "check fail", JOptionPane.ERROR_MESSAGE);
			ph_numForPW.grabFocus();
			return -1;
		} else if (!(emailForPW.getText().matches("^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*$"))) {
			JOptionPane.showMessageDialog(null, "ÀÌ¸ÞÀÏ Çü½ÄÀÌ ¸ÂÁö ¾Ê½À´Ï´Ù", "check fail", JOptionPane.ERROR_MESSAGE);
			emailForPW.grabFocus();
			return -1;
		}
		return 0;
	}

}
