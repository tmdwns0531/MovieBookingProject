package boxoffice;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;

import movieRestAPI.*;

public class BoxOffice extends JPanel {

	JPanel p1 = new JPanel(), p2 = new JPanel(), pParent = new JPanel();
	JLabel l1 = new JLabel("일간 관객수 기준");
	JButton back = new JButton(" ◀ ");
	EtchedBorder eb = new EtchedBorder(EtchedBorder.LOWERED, Color.gray, Color.GRAY);
	Font font1, font2;
	int countList = 0;
	JLabel al1, al2;
	ButtonGroup bg = new ButtonGroup();
	JCheckBox cb1, cb2;

	String showDate;
	CardLayout card = new CardLayout();

	// list in p1 & p2
	JButton[][] showInfo = new JButton[2][10];
	static JLabel[][] movieLists = new JLabel[2][10];
	JPanel[][] indexBackGround = new JPanel[2][10];
	JLabel[][] index = new JLabel[2][10];

	// movie code
	String[][] movieCd = new String[2][10];

	public BoxOffice() {
		System.out.println("BoxOffice Frame **");
		al1 = new JLabel(" <<  Box Office  >> ");
		al2 = new JLabel(BoxOfficeAPI.getDateTypeAlert("DAILY") + " < 일간 관객수 기준 > ");

		font1 = new Font("monospaced", Font.BOLD | Font.ITALIC, 14);
		font2 = new Font("monospaced", Font.BOLD | Font.ITALIC, 23);

		cb1 = new JCheckBox(" 일간 ");
		cb2 = new JCheckBox(" 주간 ");

		setLayout(null);

		// Parent panel 생성
		add(pParent);
		pParent.setBounds(180, 80, 500, 500);
		pParent.setLayout(card);

		// p1 세팅
		p1.setLayout(new GridBagLayout());
		p1.setBorder(eb);
		p1.setBackground(Color.white);

		// p2 세팅
		p2.setLayout(new GridBagLayout());
		p2.setBorder(eb);
		p2.setBackground(Color.white);

		// p1, p2 panel 생성
		p1 = showBoxOffice("DAILY", p1);
		p2 = showBoxOffice("WEEKLY", p2);

		// add p1, p2 to Parent Panel
		pParent.add("DAILY", p1);
		pParent.add("WEEKLY", p2);

		// 뒤로가기
		add(back);
		back.setBounds(30, 550, 50, 50);

		// title
		add(al1);
		al1.setBounds(300, 15, 250, 50);
		al1.setFont(font2);

		// show date
		add(al2);
		al2.setBounds(350, 570, 200, 50);

		// check box
		add(cb1);
		add(cb2);
		bg.add(cb1);
		bg.add(cb2);
		cb1.setBounds(200, 570, 60, 50);
		cb2.setBounds(270, 570, 60, 50);
		cb1.setSelected(true);

		cb1.addActionListener((event) -> {
			al2.setText(showDate = BoxOfficeAPI.getDateTypeAlert("DAILY") + " < 일간 관객수 기준 > ");
			card.show(pParent, "DAILY");

		});
		cb2.addActionListener((event) -> {
			al2.setText(showDate = BoxOfficeAPI.getDateTypeAlert("WEEKLY") + " < 주간 관객수 기준 > ");
			card.show(pParent, "WEEKLY");
		});

		// BoxOfficeAPI "movieCode" 2차원배열 복사
		for (int i = 0; i < movieCd.length; i++) {
			movieCd[i] = BoxOfficeAPI.getMovieCodeArray()[i].clone();
		}

	}

	public JPanel showBoxOffice(String type, JPanel panel) {

		final int ROW;
		List<String> list = new ArrayList<String>();

		if (type.equals("DAILY")) {
			ROW = 0;
			list = BoxOfficeAPI.requestAPI("DAILY");
		} else {
			ROW = 1;
			list = BoxOfficeAPI.requestAPI("WEEKLY");
		}

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1.0;
		gbc.weighty = 2.0;
		gbc.fill = GridBagConstraints.BOTH;

		Iterator<String> ir = list.iterator();

		while (ir.hasNext()) {
			if (countList == list.size()) {
				break;
			}
			index[ROW][countList] = new JLabel("   " + String.valueOf(countList + 1) + "...  ");
			indexBackGround[ROW][countList] = new JPanel(null);
			movieLists[ROW][countList] = new JLabel(ir.next() + " ");
			showInfo[ROW][countList] = new JButton("영화 정보");
			index[ROW][countList].setBounds(0, 0, 100, 50);
			index[ROW][countList].setFont(font1);
			indexBackGround[ROW][countList].add(index[ROW][countList]);
			indexBackGround[ROW][countList].setBackground(Color.WHITE);
			movieLists[ROW][countList].setFont(font1);
			showInfo[ROW][countList].setSize(100, 50);

			gbc.gridx = 0;
			gbc.gridy = countList;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;

			panel.add(indexBackGround[ROW][countList], gbc);
			gbc.gridx = 1;
			gbc.gridwidth = 5;
			panel.add(movieLists[ROW][countList], gbc);
			gbc.gridx = 7;
			gbc.gridwidth = 1;
			panel.add(showInfo[ROW][countList], gbc);

			countList++;
		}
		countList = 0;

		return panel;
	}

	public static JLabel[][] getMovieLists() {
		return movieLists;
	}

	public JButton getBackButton() {
		return back;
	}
}
