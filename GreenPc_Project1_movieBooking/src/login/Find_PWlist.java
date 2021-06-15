package login;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import vo.*;

class Find_PWlist extends JPanel {

	JLabel title;
	JPanel p1;
	JLabel pw;

	EtchedBorder eb = new EtchedBorder(EtchedBorder.LOWERED, Color.gray, Color.GRAY);
	JButton goLogin, goFindID, resetPW;
	Font font;

	String searchID;

	Find_PWlist() {
		title = new JLabel("● 비밀번호 찾기 ● ");
		p1 = new JPanel();
		pw = new JLabel();
		goLogin = new JButton("로그인하기");
		goFindID = new JButton("아이디 찾기");
		resetPW = new JButton("비밀번호 재설정");

		font = new Font("monospaced", Font.BOLD | Font.ITALIC, 18);

		setLayout(null);
		p1.setLayout(null);
		add(title).setBounds(350, 50, 150, 50);
		add(p1).setBounds(250, 100, 300, 400);

		// set button
		add(goLogin).setBounds(180, 550, 150, 50);
		add(goFindID).setBounds(goLogin.getX() + 170, goLogin.getY(), goLogin.getWidth(), goLogin.getHeight());
		add(resetPW).setBounds(goFindID.getX() + 170, goLogin.getY(), goLogin.getX(), goLogin.getHeight());

	}

	void showPWs(ArrayList<MemberVo> li) {
		JPanel list = new JPanel();
		list.setLayout(new GridLayout(li.size(), 1, 5, 20));

		String privatePW = li.get(0).getUser_pwd().substring(li.get(0).getUser_pwd().length() - 4);
		String screenPW = li.get(0).getUser_pwd().replaceAll(privatePW, "****");

		System.out.println(privatePW);
		System.out.println(screenPW);

		JLabel jl = new JLabel("  비밀번호 : " + screenPW);

		jl.setFont(font);

		list.add(jl);
		list.setSize(p1.getWidth(), 50);
		list.setLocation(0, 0);
		list.setBorder(eb);
		p1.add(list);

		searchID = li.get(0).getUser_id();
	}

}
