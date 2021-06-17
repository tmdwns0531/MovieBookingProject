package home;

import java.awt.*;

import javax.swing.*;

public class LodingPage extends JFrame {

	JLabel alert = new JLabel(" ·Î µù Áß .. ");
	Font font2 = new Font("monospaced", Font.BOLD | Font.ITALIC, 40);

	LodingPage() {
		// TODO Auto-generated constructor stub
		setLayout(null);
		setSize(800, 650);
		setVisible(true);
		setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setAlwaysOnTop(true);
		setVisible(true);

		add(alert);
		alert.setBounds(280, 200, 400, 200);
		alert.setFont(font2);

		// center screen
		Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
}
