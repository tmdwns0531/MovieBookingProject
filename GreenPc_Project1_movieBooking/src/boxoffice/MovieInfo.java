package boxoffice;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.*;

import movieRestAPI.*;

public class MovieInfo extends JPanel {

	JPanel p1 = new JPanel(), p2 = new JPanel(), pParent = new JPanel();
	JLabel l1 = new JLabel("일간 관객수 기준");
	JButton back = new JButton(" ◀ ");
	EtchedBorder eb = new EtchedBorder(EtchedBorder.LOWERED, Color.gray, Color.GRAY);
	Font font1, font2;
	int countList = 0;
	JLabel al1, al2, userRating;

	String showDate, movieName;
	CardLayout card = new CardLayout();

	public MovieInfo() {
		// TODO Auto-generated constructor stub
		font1 = new Font("monospaced", Font.BOLD | Font.ITALIC, 23);
		font2 = new Font("monospaced", Font.BOLD | Font.ITALIC, 14);
		al1 = new JLabel("<< Movie's Info >> ");
		userRating = new JLabel();

		setLayout(null);

		add(al1);
		al1.setBounds(300, 15, 230, 50);
		al1.setFont(font1);

		add(pParent);
		pParent.setLayout(null);
		pParent.setBounds(130, 100, 600, 500);
		pParent.setBackground(Color.red);

		// add child panel for img
		pParent.add(p1);
		p1.setBounds(0, 0, 300, 500);
		p1.setBackground(Color.DARK_GRAY);

		// add child panel for info
		pParent.add(p2);
		p2.setLayout(null);
		p2.setBounds(300, 0, 300, 500);
		p2.setBackground(Color.WHITE);

		// add user rating
		add(userRating);
		userRating.setBounds(500, 30, 300, 100);
		userRating.setFont(font2);

		// add back btn
		add(back);
		back.setBounds(30, 550, 50, 50);
	}

	void showInfo(String movieCode) {

		Map<String, String> map = BoxOfficeMovieInfoAPI.requestAPI(movieCode);

		Iterator<Map.Entry<String, String>> iteratorE = map.entrySet().iterator();

		int i = 0;
		while (iteratorE.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iteratorE.next();

			JLabel index = new JLabel();
			String key = entry.getKey();
			String value = entry.getValue() + " ";

			if (i == 0) {
				movieName = value;
				System.out.println(movieName);
			}

			// 글자수 표기 제한
			if (value.length() < 20) {
				index.setText(key + value);
				index.setBounds(0, 50 * i, 300, 40);
			} else if (value.length() >= 20 && value.length() < 50) {
				index.setText("<html>" + key + value.substring(0, 20) + "<br>" + value.substring(20, value.length())
						+ "</html>");
				index.setBounds(0, (50 * i) - 10, 300, 70);
			} else {
				index.setText(
						"<html>" + key + value.substring(0, 20) + "<br>" + value.substring(20, 50) + "..." + "</html>");
				index.setBounds(0, (50 * i) - 10, 300, 70);
			}

			index.setFont(font2);

			p2.add(index);
			i++;
		}

		// for posterImg
		showImage(movieName, p1);
		// for userRating

		userRating.setText("네이버 평점 (관람객) : " + MoviePosterAPI.getUserRating() + " ★ ");

	}

	public static Image showImage(String movieNm, JPanel panel) {
		Image posterIMG = null;
		URL imgURL = null;
		try {

			imgURL = MoviePosterAPI.rerquestAPI(movieNm);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println("url 불러오기 실패");
			e.printStackTrace();
		}
		// Image background = new
		// ImageIcon(MovieInfo.class.getResource("../image/background1.png")).getImage();
		// -> 로컬
		try {

			posterIMG = ImageIO.read(imgURL);

			// for posterImg
			ImageIcon img = new ImageIcon(posterIMG.getScaledInstance(150, 200, Image.SCALE_SMOOTH));
			JLabel posterIcon = new JLabel(img);
			panel.add(posterIcon);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return posterIMG;

	}

}
