package home;

import java.awt.*;

import javax.swing.*;

// �α��� /  ���� /  �󿵽ð�ǥ (���� , ��ȭ����)  /  ���������� - �� ����( �Խñ�, ���, ���ø���Ʈ)  /�Խ��� / ���ż���ŷ

public class Home extends JPanel {

	JPanel p1 = new JPanel();

	JButton goLogin = new JButton("�α���");
	JButton LogOut = new JButton("�α׾ƿ�");
	JButton booking = new JButton("�����ϱ�");
	JButton movie_info = new JButton("�� �ð�ǥ");
	JButton myPage = new JButton("���� ������");
	JButton board = new JButton("�Խ���");
	JButton boxOffice = new JButton("�ڽ� ���ǽ�");

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
