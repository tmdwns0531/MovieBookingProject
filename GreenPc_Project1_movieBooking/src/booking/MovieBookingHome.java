package booking;

import java.awt.*;
import java.net.*;

import javax.imageio.*;
import javax.swing.*;

public class MovieBookingHome extends JPanel {

	JLabel title;
	JButton goMovie, back;
	JPanel pParent;

	Font font1 = new Font("monospaced", Font.BOLD | Font.ITALIC, 25);
	Font font2 = new Font("monospaced", Font.BOLD | Font.ITALIC, 15);

	public MovieBookingHome() {
		// TODO Auto-generated constructor stub
		title = new JLabel(" * WELCOM TO STAR CINEMA * ");
		goMovie = new JButton("영화 보기");
		back = new JButton(" ◀ ");
		pParent = new JPanel();

		setLayout(null);
		setBounds(0, 0, 800, 650);
		setBackground(Color.LIGHT_GRAY);

		add(title);
		title.setBounds(200, 50, 400, 50);
		title.setFont(font1);

		add(pParent);
		pParent.setBounds(100, 100, 600, 400);
		pParent.setBackground(Color.LIGHT_GRAY);

		// add goMovie btn
		add(goMovie);
		goMovie.setBounds(200, 520, 400, 50);
		goMovie.setFont(font2);

		// add back btn
		add(back);
		back.setBounds(30, 520, 50, 50);

		URL url = null;
		Image background = null;

		try {

			url = new URL("https://cdn.pixabay.com/photo/2015/09/02/12/45/movie-918655_960_720.jpg");
			background = ImageIO.read(url);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		ImageIcon img = new ImageIcon(background.getScaledInstance(600, 400, Image.SCALE_SMOOTH));

		JLabel posterIcon = new JLabel(img);
		pParent.add(posterIcon);

	}

	public JButton getBackButton() {
		return back;
	}
}
