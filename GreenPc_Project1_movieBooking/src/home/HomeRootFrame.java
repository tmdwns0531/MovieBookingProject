package home;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import booking.*;
import boxoffice.*;
import login.*;
import mypage.*;

public class HomeRootFrame extends JFrame implements ActionListener {

	private static String userID;
	LodingPage lp = new LodingPage();
	LoginMainFrame logM = new LoginMainFrame();
	BoxOfficeMainFrame boxOfficeM = new BoxOfficeMainFrame();
	MovieBookingMainFrame movieBM = new MovieBookingMainFrame();
	MyPageMainFrame mypageM = new MyPageMainFrame();

	Home home = new Home();
	Login lg = logM.getLoginInstance();
	BoxOffice bo = boxOfficeM.getBoxOfficeInstance();
	MovieBookingHome mb = movieBM.getMovieBookingInstance();
	ShowList bk = movieBM.getBookingInstance();
	MyHistory mh = MyPageMainFrame.getMyHistoryInstance();

	JLabel userIDL = new JLabel("접속 아이디 :  " + userID);

	public HomeRootFrame() {

		// setVisible
		logM.dispose();
		boxOfficeM.dispose();
		movieBM.dispose();
		bk.getBookingSeatInstance().dispose();
		mypageM.dispose();

		// setting main frame
		setLayout(null);
		setSize(500, 650);
		setVisible(true);
		setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// add Panel
		add("HOME", home);

		// add action
		home.goLogin.addActionListener(this);
		home.LogOut.addActionListener(this);
		home.boxOffice.addActionListener(this);
		home.booking.addActionListener(this);
		home.myPage.addActionListener(this);
		lg.getHomeButton().addActionListener(this);
		lg.getSignInButton().addActionListener(this);
		bo.getBackButton().addActionListener(this);
		mb.getBackButton().addActionListener(this);
		bk.getHomeButton().addActionListener(this);
		mh.getBackBtn().addActionListener(this);

		// add id label
		home.add(userIDL);
		userIDL.setBounds(330, 0, 200, 50);

		// center screen
		Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

		// off loding page
		lp.dispose();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

		} catch (Exception e) {
			e.getStackTrace();
			/*
			 * ClassNotFoundException InstantiationException IllegalAccessException
			 * UnsupportedLookAndFeelException
			 */
		}

		new HomeRootFrame();

	}

	public static String getSessionID() {
		return userID;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == home.goLogin) {
			logM.setVisible(true);
			this.dispose();
		} else if (e.getSource() == home.LogOut) {
			int rs = JOptionPane.showConfirmDialog(null, " 로그아웃 하시겠습니까? ", "Confirm", JOptionPane.YES_NO_OPTION);
			if (rs == JOptionPane.OK_OPTION) {
				logM.getLoginInstance().setLogOutSessionID();
				userID = lg.sessionID();
				userIDL.setText("접속 아이디 :  " + userID);
				home.goLogin.setVisible(true);
				home.LogOut.setVisible(false);
			}
		} else if (e.getSource() == home.boxOffice) {
			boxOfficeM.setVisible(true);
			this.dispose();
		} else if (e.getSource() == home.booking) {
			movieBM.setVisible(true);
			this.dispose();

		} else if (e.getSource() == home.myPage) {
			mypageM.setVisible(true);
			this.dispose();
		} else if (e.getSource() == lg.getHomeButton()) {

			String tem = lg.sessionID();
			System.out.println("접속 아이디 : " + tem);

			if (tem == null || tem.length() == 0) {
				home.goLogin.setVisible(true);
				home.LogOut.setVisible(false);

			} else {
				home.goLogin.setVisible(false);
				home.LogOut.setVisible(true);
				userID = tem;
				userIDL.setText("접속 아이디 :  " + userID);
			}
			logM.dispose();
			this.setVisible(true);

		} else if (e.getSource() == lg.getSignInButton()) {

			if (lg.login()) {
				logM.dispose();
				String tem = lg.sessionID();
				System.out.println("접속 아이디 : " + tem);

				if (tem == null || tem.length() == 0) {
					home.goLogin.setVisible(true);
					home.LogOut.setVisible(false);

				} else {
					home.goLogin.setVisible(false);
					home.LogOut.setVisible(true);
					userID = tem;
					userIDL.setText("접속 아이디 :  " + userID);
				}
				this.setVisible(true);
			}
		} else if (e.getSource() == bo.getBackButton()) {
			this.setVisible(true);
			boxOfficeM.setVisible(false);
		} else if (e.getSource() == mb.getBackButton()) {
			this.setVisible(true);
			movieBM.setVisible(false);

		} else if (e.getSource() == bk.getHomeButton()) {
			this.setVisible(true);
			movieBM.setVisible(false);
		} else if (e.getSource() == mh.getBackBtn()) {
			this.setVisible(true);
			mypageM.setVisible(false);
		}
	}
}
