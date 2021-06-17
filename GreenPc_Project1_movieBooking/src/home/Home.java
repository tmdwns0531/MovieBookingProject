package home;

import java.awt.*;

import javax.swing.*;

// 로그인 /  예매 /  상영시간표 (예매 , 영화정보)  /  마이페이지 - 내 정보( 게시글, 댓글, 위시리스트)  /게시판 / 예매순랭킹

public class Home extends JPanel {

	JPanel p1 = new JPanel();

	JButton goLogin = new JButton("로그인");
	JButton LogOut = new JButton("로그아웃");
	JButton booking = new JButton("예매하기");
	JButton movie_info = new JButton("상영 시간표");
	JButton myPage = new JButton("마이 페이지");
	JButton board = new JButton("게시판");
	JButton boxOffice = new JButton("박스 오피스");

	Rectangle bf = new Rectangle(50, 50, 100, 80);

	Home() {
		// TODO Auto-generated constructor stub
		setLayout(null);
		setBounds(0, 0, 500, 650);
		add(p1);
		p1.setLayout(null);
		p1.setBounds(0, 30, 500, 600);

		p1.add(goLogin).setBounds((int) bf.getX() + 80, (int) bf.getY() + 50, (int) bf.getWidth(),
				(int) bf.getHeight());
		p1.add(LogOut).setBounds((int) bf.getX() + 80, (int) bf.getY() + 50, (int) bf.getWidth(), (int) bf.getHeight());
		p1.add(booking).setBounds((int) bf.getX() + 200, (int) bf.getY() + 50, (int) bf.getWidth(),
				(int) bf.getHeight());
		p1.add(boxOffice).setBounds((int) bf.getX() + 80, (int) bf.getY() + 170, (int) bf.getWidth(),
				(int) bf.getHeight());
		p1.add(myPage).setBounds((int) bf.getX() + 200, (int) bf.getY() + 170, (int) bf.getWidth(),
				(int) bf.getHeight());

		p1.add(movie_info).setBounds((int) bf.getX() + 80, (int) bf.getY() + 290, (int) bf.getWidth(),
				(int) bf.getHeight());
		p1.add(board).setBounds((int) bf.getX() + 200, (int) bf.getY() + 290, (int) bf.getWidth(),
				(int) bf.getHeight());

	}
}
