package booking;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import boxoffice.*;
import home.*;
import service.*;
import vo.*;

public class ShowList extends JPanel {

	JLabel title;
	JPanel pParent;

	JButton goHome;

	JLabel[][] movieLists = new JLabel[2][10]; // �ְ� �ڽ����ǽ� ��ȭ��'
	JButton[] selectBtn = new JButton[10];

	Booking bk = new Booking();
	BookingSeat bs = new BookingSeat();

	String[] movies = new String[10];

	public ShowList() {

		// TODO Auto-generated constructor stub
		pParent = new JPanel();
		goHome = new JButton(" H O M E ");
		setLayout(null);

		add(pParent);
		pParent.setBounds(12, 20, 760, 500);
		pParent.setLayout(new GridLayout(2, 5, 20, 20));

		// add goHome btn
		add(goHome);
		goHome.setBounds(350, 525, 100, 50);

		showList();

		// add action for selectBtn
		for (int i = 0; i < selectBtn.length; i++) {
			selectBtn[i].addActionListener((event) -> {

				for (int j = 0; j < movies.length; j++) {
					if (event.getSource() == selectBtn[j]) {

						if (HomeRootFrame.getSessionID() == null || HomeRootFrame.getSessionID().length() == 0) {
							JOptionPane.showMessageDialog(null, "�α����� �ʿ��մϴ�", "check fail", JOptionPane.ERROR_MESSAGE);
							return;
						}

						OracleMyBatisService service = new OracleMyBatisService();
						List<ScreeningVo> metainfo = service.selectDateInfo(movies[j]);

						if (metainfo.size() == 0) {
							JOptionPane.showMessageDialog(null, "�ش� ��ȭ�� ������ �ʽ��ϴ�", "check fail",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						bk.showTheaterCode.removeAll();
						bk.showBookingTable(movies[j]);
						bk.setVisible(true);
					}
				}
			});
		}
	}

	void showList() {

		movieLists = BoxOffice.getMovieLists(); // �ְ� �ڽ����ǽ� ����Ʈ

		final int ROW = 1;

		for (int i = 0; i < movieLists[ROW].length; i++) {
			String tem = movieLists[ROW][i].getText();
			movies[i] = tem.trim(); // ��������
			System.out.println(movies[i]);

			JPanel panel = new JPanel();
			MovieInfo.showImage(movies[i], panel, "Booking");
			pParent.add(panel);

			// add select btn

			panel.add(selectBtn[i] = new JButton("�����ϱ�"));
			selectBtn[i].setBounds(50, 220, 100, 50);
		}
	}

	public JButton getHomeButton() {
		return goHome;
	}

	public BookingSeat getBookingSeatInstance() {
		return bs;
	}
}
