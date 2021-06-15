package mypage;

import java.awt.*;

import javax.swing.*;

public class MyHistory extends JPanel {

	JButton checkingHistory = new JButton("예약 내역 확인하기");
	JButton checkingMyBoard = new JButton("나의 게시글 확인하기");
	BookingHistory bh = new BookingHistory();
	MyBoardHistroy mbh = new MyBoardHistroy();

	Rectangle bf = new Rectangle(170, 200, 250, 50);

	JButton back = new JButton("◀");

	MyHistory() {
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);

		add(checkingHistory);
		add(checkingMyBoard);
		add(back);
		checkingHistory.setBounds(bf);
		checkingMyBoard.setBounds((int) bf.getX(), (int) bf.getY() + 100, (int) bf.getWidth(), (int) bf.getHeight());
		back.setBounds(30, 550, 50, 50);
	}

	public JButton getBackBtn() {
		return back;
	}
}
