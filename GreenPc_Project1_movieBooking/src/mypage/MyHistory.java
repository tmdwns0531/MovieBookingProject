package mypage;

import java.awt.*;

import javax.swing.*;

public class MyHistory extends JPanel {

	JButton checkingHistory = new JButton("���� ���� Ȯ���ϱ�");
	JButton checkingMyBoard = new JButton("���� �Խñ� Ȯ���ϱ�");
	BookingHistory bh = new BookingHistory();
	MyBoardHistroy mbh = new MyBoardHistroy();

	Rectangle bf = new Rectangle(170, 200, 250, 50);

	JButton back = new JButton("��");

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
