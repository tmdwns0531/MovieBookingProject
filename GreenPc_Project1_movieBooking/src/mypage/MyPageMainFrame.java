package mypage;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import home.*;

public class MyPageMainFrame extends JFrame implements ActionListener {

	CardLayout card = new CardLayout();
	static MyHistory mh = new MyHistory();

	public MyPageMainFrame() {
		setLayout(card);
		setSize(600, 650);
		setVisible(true);
		setBackground(Color.LIGHT_GRAY);

		// add panel
		add("BOOKING", mh);
		add("BOOKING_HISTORY", mh.bh);
		add("MY_BORARD_HISTORY", mh.mbh);

		// add action
		mh.checkingHistory.addActionListener(this);
		mh.checkingMyBoard.addActionListener(this);
		mh.bh.back.addActionListener(this);

		// center screen
		Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == mh.checkingHistory) {
			mh.bh.listP.removeAll();
			mh.bh.listP.revalidate();
			mh.bh.listP.repaint();
			String myID = HomeRootFrame.getSessionID();
			myID = "tmdwns0531";
			if (myID == null || myID.length() == 0) {
				JOptionPane.showMessageDialog(null, "로그인 후 이용가능합니다", "check fail", JOptionPane.ERROR_MESSAGE);
				return;
			}
			mh.bh.showBookingTable(myID);
			card.show(getContentPane(), "BOOKING_HISTORY");
		} else if (e.getSource() == mh.checkingMyBoard) {
			card.show(getContentPane(), "MY_BORARD_HISTORY");
		} else if (e.getSource() == mh.bh.back) {
			card.show(getContentPane(), "BOOKING");
		}
	}

	public static MyHistory getMyHistoryInstance() {
		return mh;
	}

}
