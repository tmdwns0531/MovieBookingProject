package booking;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MovieBookingMainFrame extends JFrame implements ActionListener {

	CardLayout card = new CardLayout();
	MovieBookingHome mb = new MovieBookingHome();
	ShowList sl = new ShowList();

	JTabbedPane tab;

	public MovieBookingMainFrame() {

		// dispose
		sl.bs.dispose();
		sl.bk.dispose();

		// setting
		setLayout(card);
		setSize(800, 650);
		setVisible(true);
		setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// add tab
		tab = new JTabbedPane();
		this.getContentPane().add(tab);

		tab.add("메인홈", mb);
		tab.add("영화보기", sl);

		// add action
		mb.goMovie.addActionListener(this);
		sl.bk.bookingBtn.addActionListener(this);
		sl.bs.back.addActionListener(this);
		sl.bs.bookingRS.addActionListener(this);

		// center screen
		Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}

	public MovieBookingHome getMovieBookingInstance() {
		return mb;
	}

	public ShowList getBookingInstance() {
		return sl;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == mb.goMovie) {
			tab.setSelectedIndex(1);
		} else if (e.getSource() == sl.bk.bookingBtn) {
			if (sl.bk.sendDateInfoToSeatTable().size() == 0) {
				return;
			}
			System.out.println("MovieBookingMainFrame -> show seat table ");
			sl.bs.showBookingState(sl.bk.sendDateInfoToSeatTable()); // Booking 클래스 booking btn -> send date info
																		// bookingstate
			this.dispose();
			sl.bk.dispose();
			sl.bs.setVisible(true);
		} else if (e.getSource() == sl.bs.back) {
			System.out.println("안녕~");
			sl.bs.headCount.setModel(new DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4" }));
			sl.bs.seatCode.removeAll(sl.bs.seatCode);
			sl.bs.priceTot = 0;
			sl.bs.priceV.setText(String.valueOf(sl.bs.priceTot) + " 원 ");
			for (Boolean flag : sl.bs.flagInputBtn) {
				flag = false;
			}
			sl.bs.setVisible(false);
			this.setVisible(true);
		} else if (e.getSource() == sl.bs.bookingRS) {
			System.out.println("안녕");
			int rs = sl.bs.actionBookingRS();
			if (rs == 0) {
				sl.bs.setVisible(false);
				this.setVisible(true);
				sl.bs.headCount.setModel(new DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4" }));
				sl.bs.seatCode.removeAll(sl.bs.seatCode);
				sl.bs.priceTot = 0;
				sl.bs.priceV.setText(String.valueOf(sl.bs.priceTot) + " 원 ");
			}

		}

	}
}
