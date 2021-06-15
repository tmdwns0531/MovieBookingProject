package login;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import vo.*;

class Find_IDlist extends JPanel {

	JLabel title;
	JPanel p1;

	JLabel[] create_date;

	ButtonGroup bg;
	JRadioButton[] rb;

	EtchedBorder eb = new EtchedBorder(EtchedBorder.LOWERED, Color.gray, Color.GRAY);

	JButton goLogin, goFindPW;

	String user_id;
	int size;

	Find_IDlist() {

		setLayout(null);
		title = new JLabel("● 아이디 찾기 ● ");
		p1 = new JPanel();
		create_date = new JLabel[20];
		bg = new ButtonGroup();
		rb = new JRadioButton[20];
		goLogin = new JButton("로그인하기");
		goFindPW = new JButton("비밀번호 찾기");

		p1.setLayout(null);
		add(p1).setBounds(250, 100, 300, 400);
		add(title).setBounds(350, 50, 100, 50);

		// set button
		add(goLogin).setBounds(240, 550, 150, 50);
		add(goFindPW).setBounds(goLogin.getX() + 170, goLogin.getY(), 150, goLogin.getHeight());
	}

	void showIDs(ArrayList<MemberVo> li) {

		JPanel list = new JPanel();
		list.setLayout(new GridLayout(li.size(), 2, 5, 10));

		for (int i = 0; i < li.size(); i++) {
			rb[i] = new JRadioButton(li.get(i).getUser_id(), true);

			if (bg.getButtonCount() >= li.size()) {
				break;
			}
			bg.add(rb[i]);

			list.add(rb[i]);
			JLabel jl = new JLabel("  가입일 : " + li.get(i).getCreate_date());
			list.add(jl);
			list.setSize(p1.getWidth(), 50 * (i + 1));
			System.out.println(rb[i].isSelected());
			System.out.println(bg.getButtonCount());

		}

		list.setLocation(0, 0);
		list.setBorder(eb);
		p1.add(list);

		size = li.size();

	}

	void goLoginFunc() {

		System.out.println();
		for (int i = 0; i < size; i++) {
			System.out.println(rb[i].isSelected());
			if (rb[i].isSelected()) {
				user_id = rb[i].getText();
			}
		}
		System.out.println(user_id);
		bg.clearSelection();
	}

}
