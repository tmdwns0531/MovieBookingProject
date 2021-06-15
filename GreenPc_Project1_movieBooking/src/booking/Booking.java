package booking;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import service.*;
import vo.*;

public class Booking extends JFrame implements ActionListener, ItemListener {

	private OracleMyBatisService service = new OracleMyBatisService();

	JLabel movieNm = new JLabel();

	JLabel year = new JLabel(" 상 영 년 도 ");
	JLabel date = new JLabel(" 상 영 월 ");
	JLabel time = new JLabel(" 상 영 일 ");
	JLabel theaterNmL = new JLabel();
	JLabel screening_timeL = new JLabel();
	JLabel movieNL = new JLabel();

	JButton ok = new JButton(" 확 인 ");
	JButton bookingBtn = new JButton(" 예 약 하 기");

	ButtonGroup bg = new ButtonGroup();
	JRadioButton[] rb;

	JPanel showTheaterCode = new JPanel(null);
	Font f1 = new Font("돋움", Font.BOLD, 15);
	Font f2 = new Font("돋움", Font.BOLD, 15);

	JComboBox<Integer> comboYears, comboMonths, comboDates;

	// plate info for external -> booking seat
	private String movieName, screening_time, theater;
	private Vector<String> theaterPL = new Vector<>();

	boolean timeCheck;

	/**
	 * 
	 */
	public Booking() {

		// this frame setting
		setLayout(null);
		add(movieNm);
		movieNm.setBounds(60, 50, 300, 50);

		// frame setting
		setSize(400, 700);
		setVisible(true);
		setBackground(Color.LIGHT_GRAY);

		// add panel
		add(showTheaterCode);
		showTheaterCode.setBounds(0, 320, 400, 300);

		// add movieName label
		add(movieNL);
		movieNL.setBounds(130, 50, 400, 50);
		movieNL.setFont(f1);

		// comp setting
		add(year);
		year.setBounds(50, 100, 100, 50);
		add(date);
		date.setBounds(70, 150, 100, 50);

		add(time);
		time.setBounds(70, 200, 100, 50);

		// add ok
		add(ok);
		ok.setBounds(150, 250, 100, 30);
		ok.addActionListener(this);

		// add bookingBtn
		add(bookingBtn);
		bookingBtn.setBounds(150, 620, 100, 30);
		bookingBtn.setVisible(false);

		// center screen
		Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

	}

	void showBookingTable(String movieNm) {

		this.movieNm.setText("영화 이름");
		this.movieName = movieNm;
		movieNL.setText(movieNm);

		Vector<Integer> years = new Vector<>();
		Vector<Integer> months = new Vector<>();
		Vector<Integer> dates = new Vector<>();

		// 해당 영화의 '상영년도', '상영월', '상영일'을 combobox 에 기술
		List<ScreeningVo> yearsInfo = service.selectYearInfo(movieNm);
		List<ScreeningVo> monthsInfo = service.selectMonthInfo(movieNm);
		List<ScreeningVo> datesInfo = service.selectDateInfo(movieNm);

		for (ScreeningVo items : yearsInfo) {
			years.add(items.getScreening_year());
		}

		for (ScreeningVo items : monthsInfo) {
			months.add(items.getScreening_month());
		}

		for (ScreeningVo items : datesInfo) {
			dates.add(items.getScreening_date());
		}

		comboYears = new JComboBox<>(years);
		comboMonths = new JComboBox<>(months);
		comboDates = new JComboBox<>(dates);

		// setting combobox
		add(comboYears);
		add(comboMonths);
		add(comboDates);
		comboYears.setBounds(130, 110, 150, 30);
		comboMonths.setBounds(130, 160, 150, 30);
		comboDates.setBounds(130, 210, 150, 30);

		System.out.println("시작 : " + comboDates.getSelectedItem().toString());
		comboDates.addItemListener(this);
	}

	void bookingAction() {
		// add action radio btn
		for (int i = 0; i < rb.length; i++) {
			rb[i].addActionListener((event) -> {
				for (int j = 0; j < rb.length; j++) {
					if (event.getSource() == rb[j]) {
						screening_time = rb[j].getText();
					}
				}
			});
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// ok btn action
		if (e.getSource() == ok) {

			System.out.println("안녕!!");
			showTheaterCode.removeAll();
			showTheaterCode.revalidate();
			showTheaterCode.repaint();

			// add label
			theaterNmL.setText(" 상 영 관");
			screening_timeL.setText(" 상 영 시 간");
			showTheaterCode.add(theaterNmL);
			showTheaterCode.add(screening_timeL);

			theaterNmL.setBounds(30, 0, 100, 50);
			screening_timeL.setBounds(100, 0, 100, 50);
			theaterNmL.setFont(f2);
			screening_timeL.setFont(f2);

			// add time info
			Map<String, String> map = new HashMap<>();
			map.put("movieNm", movieName);
			map.put("screening_year", comboYears.getSelectedItem().toString());
			map.put("screening_month", comboMonths.getSelectedItem().toString());
			map.put("screening_date", comboDates.getSelectedItem().toString());

			System.out.println("map : " + map.get("movieNm"));
			System.out.println("map : " + map.get("screening_year"));
			System.out.println("map : " + map.get("screening_month"));
			System.out.println("map : " + map.get("screening_date"));

			System.out.println("combobox : " + comboYears.getSelectedItem().toString());
			System.out.println("combobox : " + comboMonths.getSelectedItem().toString());
			System.out.println("combobox : " + comboDates.getSelectedItem().toString());

			List<ScreeningVo> metainfo = service.selectTheater(map); // get theater code & screening_time

			System.out.println("metainfo size : " + metainfo.size());

			Vector<String> theaterV = new Vector<>();
			Vector<String> timeV = new Vector<>();

			// 같은 상영관 중복 x layout
			for (int i = 0; i < metainfo.size() - 1; i++) {

				timeV.add(metainfo.get(i).getScreening_time());
				if (metainfo.get(i).getTheater_code().equals(metainfo.get(i + 1).getTheater_code())) {
					continue;
				}
				theaterV.add(metainfo.get(i).getTheater_code());
			}

			theaterV.add(metainfo.get(metainfo.size() - 1).getTheater_code());
			timeV.add(metainfo.get(metainfo.size() - 1).getScreening_time());

			theaterPL = theaterV;

			System.out.println("theaterVsize : " + theaterV.size());
			System.out.println("timdVsize : " + timeV.size());

			// 상영관 code 라벨 setting
			for (int i = 0; i < theaterV.size(); i++) {
				JLabel theaterL = new JLabel(theaterV.get(i));
				showTheaterCode.add(theaterL);
				theaterL.setBounds(50, 30 + 30 * i, 100, 50);
			}

			rb = new JRadioButton[metainfo.size()];

			// add radiobtns
			for (int i = 0; i < metainfo.size(); i++) {
				rb[i] = new JRadioButton(timeV.get(i));
				bg.add(rb[i]);
				this.add(rb[i]);
			}

			int countTimeRow = -1;

			// 상영관별 상영시간 checkbox setting
			outer: for (int i = 0; i < theaterV.size(); i++) {

				for (int j = 0; j < timeV.size(); j++) {

					countTimeRow++;
					rb[countTimeRow].setBounds(100 + 60 * j, 30 + 30 * i, 65, 50);
					showTheaterCode.add(rb[countTimeRow]);
					System.out.println(countTimeRow);
					if (countTimeRow == timeV.size() - 1) { // 2
						break outer;
					}

					if (!(metainfo.get(countTimeRow).getTheater_code()
							.equals(metainfo.get(countTimeRow + 1).getTheater_code()))) {

						break;
					}

				}

			}

			bookingBtn.setVisible(true);

			// add action for radiobtn
			bookingAction();

			// booking btn action
		}
	}

	public List<String> sendDateInfoToSeatTable() {

		List<String> list = new ArrayList<String>();
		System.out.println("timeCheck : " + timeCheck);

		// add time info
		Map<String, String> map = new HashMap<>();
		map.put("movieNm", movieName);
		map.put("screening_year", comboYears.getSelectedItem().toString());
		map.put("screening_month", comboMonths.getSelectedItem().toString());
		map.put("screening_date", comboDates.getSelectedItem().toString());

		List<ScreeningVo> metainfo = service.selectTheater(map); // get theater code & screening_time

		int rowCount = 0;

		for (int j = 0; j < rb.length - 1; j++) {
			if (rb[j].isSelected()) {
				theater = theaterPL.get(rowCount);
				timeCheck = true;
			}
			if (!(metainfo.get(j).getTheater_code().equals(metainfo.get(j + 1).getTheater_code())))
				rowCount++;
		}
		if (rb[rb.length - 1].isSelected()) {
			theater = theaterPL.get(rowCount);
			timeCheck = true;
		}
		System.out.println("timeCheck : " + timeCheck);

		if (!timeCheck)

		{
			JOptionPane.showMessageDialog(null, "상영 시간을 선택하세요", "check fail", JOptionPane.ERROR_MESSAGE);
			return list;
		}
		System.out.println("이름 :" + movieName);
		System.out.println("상영관 : " + theater);
		System.out.println("상영시간 : " + screening_time);
		System.out.println("상영년도 : " + comboYears.getSelectedItem().toString());
		System.out.println("상영월 : " + comboMonths.getSelectedItem().toString());
		System.out.println("상영일 : " + comboDates.getSelectedItem().toString());
		System.out.println("Booking class -> ShowList class");

		list.add(movieName);
		list.add(theater);
		list.add(screening_time);
		list.add(comboYears.getSelectedItem().toString());
		list.add(comboMonths.getSelectedItem().toString());
		list.add(comboDates.getSelectedItem().toString());

		timeCheck = false;
		return list;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if (e.getStateChange() == ItemEvent.SELECTED) {
			showTheaterCode.removeAll();
			showTheaterCode.revalidate();
			showTheaterCode.repaint();
			timeCheck = false;

			bookingBtn.setVisible(false);
		}
	}

}
