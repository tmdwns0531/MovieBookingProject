package booking;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;

import home.*;
import service.*;

public class BookingSeat extends JFrame implements ItemListener {

	private OracleMyBatisService service = new OracleMyBatisService();
	JLabel movieNmL = new JLabel(" 영 화 이 름 : ");
	JLabel dateL = new JLabel(" 상 영 일 : ");
	JLabel theaterL = new JLabel(" 상 영 관 : ");
	JLabel headCountL = new JLabel(" 인 원 수 : ");
	JLabel screenL = new JLabel("screen");
	JPanel screenP = new JPanel();

	JPanel seatLfPanel, seatRgPanel;
	JLabel title, dateInfoL, theaterCode, priceL, priceV;
	JButton back = new JButton("◀");
	JButton bookingRS = new JButton("예약하기");
	JButton[] seatsB;
	JComboBox<String> headCount = new JComboBox<>(new String[] { "0", "1", "2", "3", "4" });
	EtchedBorder eb = new EtchedBorder(EtchedBorder.LOWERED, Color.GRAY, Color.black);

	int seatsValue, countSeats, checkHead, priceTot;
	boolean flagCtrlBtns = true;
	Vector<String> seatCode;
	Vector<Integer> seatPrice;
	String preOverlapSeatBtn;
	boolean[] flagInputBtn;
	int[] priceOfSeat;
	String movieNm, theater, TIME;
	int YEAR, MONTH, DATE; // DATE

	public BookingSeat() {

		setLayout(null);
		setSize(800, 700);
		setVisible(true);
		setBackground(Color.LIGHT_GRAY);

		title = new JLabel();
		dateInfoL = new JLabel();
		seatLfPanel = new JPanel();
		seatRgPanel = new JPanel();
		theaterCode = new JLabel();
		priceL = new JLabel("총 금액 : ");
		priceV = new JLabel();

		// add screen panel
		add(screenP);
		screenP.setBounds(150, 135, 510, 20);
		screenP.add(screenL);
		screenP.setBackground(Color.DARK_GRAY);
		screenL.setForeground(Color.WHITE);

		// add label
		add(movieNmL);
		add(dateL);
		add(title);
		add(dateInfoL);
		add(theaterL);
		add(theaterCode);
		add(headCountL);
		title.setBounds(200, 30, 200, 50);
		dateInfoL.setBounds(200, 50, 200, 50);
		theaterCode.setBounds(200, 70, 200, 50);

		movieNmL.setBounds(100, 30, 100, 50);
		dateL.setBounds(110, 50, 100, 50);
		theaterL.setBounds(110, 70, 100, 50);
		headCountL.setBounds(110, 90, 100, 50);
		add(headCount);
		headCount.setBounds(200, 105, 50, 20);
		add(priceL);
		priceL.setBounds(550, 570, 100, 50);
		add(priceV);
		priceV.setBounds(610, 570, 100, 50);

		// add panel
		add(seatLfPanel);
		add(seatRgPanel);
		seatLfPanel.setLayout(new GridLayout(6, 4, 5, 10));
		seatRgPanel.setLayout(new GridLayout(6, 5, 5, 10));

		seatLfPanel.setBounds(150, 170, 210, 400);
		seatRgPanel.setBounds(400, 170, 260, 400);

		// add back btn
		add(back);
		add(bookingRS);
		back.setBounds(20, 570, 50, 50);
		bookingRS.setBounds(340, 580, 100, 35);

		// center screen
		Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}

	void showBookingState(List<String> m_info) {

		movieNm = m_info.get(0); // TITLE
		theater = m_info.get(1);
		YEAR = Integer.parseInt(m_info.get(3)); // YEAR
		MONTH = Integer.parseInt(m_info.get(4)); // MONTH
		DATE = Integer.parseInt(m_info.get(5)); // DATE
		TIME = m_info.get(2);

		StringTokenizer st = new StringTokenizer(TIME, "[ :]");
		int HOUR, MIN;
		HOUR = Integer.parseInt(st.nextToken()); // HOUR
		MIN = Integer.parseInt(st.nextToken()); // MIN

		Calendar c = Calendar.getInstance();
		c.set(YEAR, MONTH - 1, DATE, HOUR, MIN);

		SimpleDateFormat sd = new SimpleDateFormat("yyyy.MM.dd-HH.mm");
		System.out.println(c.getTime());

		title.setText(movieNm); // add title label
		dateInfoL.setText(sd.format(c.getTime())); // add date label
		theaterCode.setText(theater);

		layOutSeats(m_info, theater);

		if (flagCtrlBtns == true) {
			controlSeatBtn();
		}

	}

	void layOutSeats(List<String> m_info, String theaterCode) {

		List<String> seats = service.selectSeats(theaterCode);
		seatsValue += seats.size();

		if (seatsValue == seats.size()) {
			seatsB = new JButton[seats.size()];
			// add seatsbtn to left, right panel
			for (int i = 0; i < seats.size(); i++) {
				seatsB[i] = new JButton(seats.get(i));
				if (Integer.parseInt(seats.get(i).substring(1)) <= 4) {
					seatLfPanel.add(seatsB[i]);
				} else {
					seatRgPanel.add(seatsB[i]);
				}
			}
		}

		// add color & add price each of seat
		priceOfSeat = new int[seatsB.length];
		List<Integer> priceList = service.selectPrice();

		for (int i = 0; i < seatsB.length; i++) {
			if (i <= 17) {
				EtchedBorder eb = new EtchedBorder(EtchedBorder.LOWERED, Color.GREEN, Color.black);
				seatsB[i].setBorder(eb);
				priceOfSeat[i] = priceList.get(0);
			} else if (i > 17 && i <= 35) {
				EtchedBorder eb = new EtchedBorder(EtchedBorder.LOWERED, Color.MAGENTA, Color.black);
				seatsB[i].setBorder(eb);
				priceOfSeat[i] = priceList.get(2);
			} else {
				EtchedBorder eb = new EtchedBorder(EtchedBorder.LOWERED, Color.CYAN, Color.black);
				seatsB[i].setBorder(eb);
				priceOfSeat[i] = priceList.get(4);
			}
			seatsB[i].setEnabled(false);
		}

		headCount.addItemListener(this);

	}

	void controlSeatBtn() {

		seatCode = new Vector<>();
		seatPrice = new Vector<>();
		flagInputBtn = new boolean[seatsB.length];

		// add action seatsBtn
		for (int i = 0; i < seatsB.length; i++) {
			flagInputBtn[i] = false;
			seatsB[i].addActionListener((event) -> {
				for (int j = 0; j < seatsB.length; j++) {

					// action
					if (event.getSource() == seatsB[j]) {

						flagInputBtn[j] = !flagInputBtn[j];

						if (flagInputBtn[j] == true) {
							if (checkHead == countSeats) {
								flagInputBtn[j] = false;
								int result = JOptionPane.showConfirmDialog(null, "이미 좌석을 모두 선택하셨습니다. 선택을 취소하시겠습니까?",
										"check", JOptionPane.YES_NO_OPTION);
								if (result == JOptionPane.NO_OPTION) {
									return;
								} else if (result == JOptionPane.CLOSED_OPTION) {
									return;
								} else if (result == JOptionPane.YES_OPTION) {
									seatCode.removeAll(seatCode);
									resetSelectedBtn();
									countSeats = 0;
									return;
								}
							}

							seatsB[j].setBackground(Color.LIGHT_GRAY);
							seatCode.add(seatsB[j].getActionCommand());
							countSeats++;
							seatPrice.add(priceOfSeat[j]);
							priceTot += priceOfSeat[j];
							priceV.setText(String.valueOf(priceTot) + " 원 ");

						} else if (flagInputBtn[j] == false) {
							seatsB[j].setBackground(Color.GRAY);
							seatCode.remove(seatsB[j].getActionCommand());
							countSeats--;
							seatPrice.removeElement(priceOfSeat[j]);
							priceTot -= priceOfSeat[j];
							priceV.setText(String.valueOf(priceTot) + " 원 ");
						}

						for (int k = 0; k < seatCode.size(); k++) {
							System.out.println("좌석 : " + seatCode.get(k));
						}

					}
				}
			});
		}
		flagCtrlBtns = !flagCtrlBtns;

	}

	void resetSelectedBtn() {
		for (int i = 0; i < seatsB.length; i++) {
			seatsB[i].setBackground(Color.GRAY);
			flagInputBtn[i] = false;
		}
	}

	List<String> loadingBookingSeatsState() {
		Map<String, Object> screeningInfo = new HashMap<String, Object>();
		screeningInfo.put("movie", movieNm);
		screeningInfo.put("theater", theater);
		screeningInfo.put("year", YEAR);
		screeningInfo.put("month", MONTH);
		screeningInfo.put("date", DATE);
		screeningInfo.put("time", TIME);

		List<String> loadingBS = service.selectBookingStateSeats(screeningInfo);

		return loadingBS;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		List<String> screeningInfo = loadingBookingSeatsState();

		for (int i = 0; i < screeningInfo.size(); i++) {
			for (int j = 0; j < seatsB.length; j++) {
				if (seatsB[j].getActionCommand().equals(screeningInfo.get(i))) {
					seatsB[j].setEnabled(false);
				}
			}
		}

		countSeats = 0;
		checkHead = Integer.parseInt(headCount.getSelectedItem().toString());
		seatCode.removeAll(seatCode);
		priceTot = 0;
		priceV.setText(String.valueOf(priceTot) + " 원 ");
		for (int i = 0; i < flagInputBtn.length; i++) {
			flagInputBtn[i] = false;
		}
		System.out.println("checkHead : " + checkHead);
		System.out.println("countseats : " + countSeats);

		if (e.getStateChange() == ItemEvent.SELECTED) {
			if (headCount.getSelectedItem() == "0") {
				for (int i = 0; i < seatsB.length; i++) {
					seatsB[i].setEnabled(false);
				}
			}
		} else if (headCount.getSelectedItem().toString() != "0") {
			for (int i = 0; i < seatsB.length; i++) {
				seatsB[i].setEnabled(true);
				seatsB[i].setBackground(Color.GRAY);
			}
		}

	}

	int actionBookingRS() {
		// TODO Auto-generated method stub

		if (countSeats < checkHead) {
			JOptionPane.showMessageDialog(null, "선택하신 인원수에 맞게 예약하세요", "check fail", JOptionPane.ERROR_MESSAGE);
			return -1;
		}

		if (checkHead == 0) {
			JOptionPane.showMessageDialog(null, "인원수를 선택하세요", "check fail", JOptionPane.ERROR_MESSAGE);
			return -1;
		}

		System.out.println();
		System.out.println("아이디 : " + HomeRootFrame.getSessionID());
		System.out.println("영화 이름 : " + movieNm);
		System.out.println("상영관 : " + theater);
		System.out.println("상영 연도 : " + YEAR);
		System.out.println("상영 월 : " + MONTH);
		System.out.println("상영 일 : " + DATE);
		System.out.println("상영 시간 : " + TIME);

		for (int i = 0; i < seatCode.size(); i++) {
			System.out.println("좌석  : " + seatCode.get(i));
		}

		System.out.println("총 금액 : " + priceTot);

		// get user idx
		String myID = HomeRootFrame.getSessionID();

		int userIDX = service.selectUserIDX(myID);

		// insert booking info to booking db

		int[] rs = new int[seatCode.size()];
		for (int i = 0; i < seatCode.size(); i++) {
			Map<String, Object> bookingInfo = new HashMap<String, Object>();
			bookingInfo.put("userIDX", userIDX);
			bookingInfo.put("movie", movieNm);
			bookingInfo.put("theater", theater);
			bookingInfo.put("year", YEAR);
			bookingInfo.put("month", MONTH);
			bookingInfo.put("date", DATE);
			bookingInfo.put("time", TIME);
			bookingInfo.put("seat", seatCode.get(i));
			bookingInfo.put("price", seatPrice.get(i));
			System.out.println(seatPrice.get(i));

			rs[i] = service.insertBooking(bookingInfo);
		}
		int count = 0;
		for (int i = 0; i < rs.length; i++) {
			if (rs[i] > 0) {
				count++;
			}
		}

		if (count == rs.length) {
			JOptionPane.showMessageDialog(null, "예매가 완료되었습니다", "check success", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "예매 실패 (개발자 문의)", "check fail", JOptionPane.ERROR_MESSAGE);
		}
		return 0;
	}

}
