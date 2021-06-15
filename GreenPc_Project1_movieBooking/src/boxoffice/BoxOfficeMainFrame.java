package boxoffice;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class BoxOfficeMainFrame extends JFrame implements ActionListener {

	CardLayout card = new CardLayout();
	private BoxOffice bo = new BoxOffice();
	private MovieInfo mi = new MovieInfo();

	public BoxOfficeMainFrame() {

		setLayout(card);
		setVisible(true);
		setSize(800, 650);
		setVisible(true);
		setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// add boxoffice panel
		add("BOX", bo);
		add("MOVIE_INFO", mi);

		System.out.println(BoxOffice.movieLists.length);

		// add action
		for (int i = 0; i < BoxOffice.movieLists.length; i++) {
			for (int j = 0; j < BoxOffice.movieLists[i].length; j++) {

				bo.showInfo[i][j].addActionListener(this);
			}
		}
		mi.back.addActionListener(this);

		// center screen
		Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for (int i = 0; i < bo.movieLists.length; i++) {
			for (int j = 0; j < bo.movieLists[i].length; j++) {
				if (e.getSource() == bo.showInfo[i][j]) {
					mi.p2.removeAll();
					mi.p1.removeAll();
					mi.showInfo(bo.movieCd[i][j]);

					card.show(getContentPane(), "MOVIE_INFO");

				}

			}
		}

		if (e.getSource() == mi.back) {
			card.show(getContentPane(), "BOX");
		}
	}

	public BoxOffice getBoxOfficeInstance() {
		return bo;
	}

}
