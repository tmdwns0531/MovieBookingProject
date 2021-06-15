package mypage;

import java.awt.*;
import java.text.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import service.*;
import vo.*;

public class BookingHistory extends JPanel {

	private OracleMyBatisService service = new OracleMyBatisService();

	DefaultTableModel dtm;
	String header[] = { "영화명", "상영관", "상영일", "상영시간", "좌석번호", "결제금액", "상태" };
	String contents[][];
	JTable table;

	JPanel listP = new JPanel();
	JButton back = new JButton("◀");
	int IDX;

	public BookingHistory() {
		// TODO Auto-generated constructor stub
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		setSize(500, 650);

		add(listP);
		listP.setBounds(20, 50, 550, 500);
		listP.setBackground(Color.red);
		listP.setLayout(new BorderLayout());

		add(back);
		back.setBounds(30, 550, 50, 50);
	}

	public void showBookingTable(String myID) {
		myID = "tmdwns0531";
		IDX = service.selectUserIDX(myID);

		List<BookingVo> bookingList = service.selectMyBookingHistoryDate(IDX); // 해당아이디가 예약한 < 영화 , 상영관, 연도, 월 ,일> 정보

		contents = new String[bookingList.size()][header.length]; // 테이블 내용 row = bookinglist.size / col = header.length

		for (int j = 0; j < contents.length; j++) {
			String seats = "";
			int price = 0;

			// for price, seatcode
			String movie = bookingList.get(j).getBooking_movie();
			String theater = bookingList.get(j).getBooking_theater();
			int year = bookingList.get(j).getBooking_year();
			int month = bookingList.get(j).getBooking_month();
			int date = bookingList.get(j).getBooking_date();
			String time = bookingList.get(j).getBooking_time();

			Map<String, Object> timePriceMap = new HashMap<String, Object>();
			timePriceMap.put("movie", movie);
			timePriceMap.put("theater", theater);
			timePriceMap.put("year", year);
			timePriceMap.put("month", month);
			timePriceMap.put("date", date);
			timePriceMap.put("time", time);

			List<BookingVo> timePriceList = service.selectSeatPriceList(timePriceMap); // for price & seat_code

			for (BookingVo vo : timePriceList) {
				seats += vo.getBooking_seat() + " , ";
				price += vo.getBooking_price();
			}
			seats = seats.substring(0, seats.length() - 3); // seats code 쉼표 제거

			StringTokenizer st = new StringTokenizer(time, "[ :]");
			int hour = Integer.parseInt(st.nextToken());
			int min = Integer.parseInt(st.nextToken());

			Calendar c = Calendar.getInstance();
			c.set(year, month - 1, date, hour, min);
			SimpleDateFormat sd = new SimpleDateFormat("yyyy.MM.dd ");
			SimpleDateFormat sd2 = new SimpleDateFormat("HH 시 mm 분");
			String bookingDate = sd.format(c.getTime());
			String bookingTime = sd2.format(c.getTime());

			contents[j] = new String[] { movie, theater, bookingDate, bookingTime, seats, String.valueOf(price), "" };

		}

		dtm = new DefaultTableModel(contents, header);
		table = new JTable(dtm);
		table.setModel(dtm);
		table.setRowHeight(40);
		table.getColumn("영화명").setPreferredWidth(150);
		table.getColumn("상영관").setPreferredWidth(50);
		table.getColumn("좌석번호").setPreferredWidth(50);
		table.getColumn("결제금액").setPreferredWidth(50);

		JScrollPane scroll = new JScrollPane(table);
		JTableHeader header = table.getTableHeader();

		table.setBounds(50, 200, 550, 100);
		table.setFillsViewportHeight(true);
		listP.add(table);
		listP.add(scroll);

		listP.add(header, BorderLayout.NORTH);
		listP.add(table, BorderLayout.CENTER);

		// add btn
		table.getColumnModel().getColumn(6).setCellRenderer(new TableCell());
		table.getColumnModel().getColumn(6).setCellEditor(new TableCell());

	}

	class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

		JButton jb;

		public TableCell() {
			// TODO Auto-generated constructor stub

			jb = new JButton("예매취소");

			jb.addActionListener(e -> {

				DefaultTableModel model = (DefaultTableModel) table.getModel();

				int result = JOptionPane.showConfirmDialog(null, "예매를 취소하시겠습니까?", "check", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.NO_OPTION) {
					return;
				} else if (result == JOptionPane.CLOSED_OPTION) {
					return;
				} else if (result == JOptionPane.YES_OPTION) {

					int row = table.convertRowIndexToModel(table.getSelectedRow());

					StringTokenizer st = new StringTokenizer(model.getValueAt(row, 2).toString(), "[. ]");
					StringBuilder sb = new StringBuilder(model.getValueAt(row, 3).toString()); // xx시xx분
					StringTokenizer st2 = new StringTokenizer(sb.substring(0, sb.toString().length() - 1), "시"); // xx 시
					String movie = model.getValueAt(row, 0).toString();
					String theater = model.getValueAt(row, 1).toString();
					int year = Integer.parseInt(st.nextToken());
					int month = Integer.parseInt(st.nextToken());
					int date = Integer.parseInt(st.nextToken());
					String time = st2.nextToken().trim() + ":" + st2.nextToken().trim(); // xx:xx

					Map<String, Object> map = new HashMap<>();
					map.put("idx", IDX);
					map.put("movie", movie);
					map.put("theater", theater);
					map.put("year", year);
					map.put("month", month);
					map.put("date", date);
					map.put("time", time);

					int rs = service.cancleBooking(map);

					if (rs > 0) {

						if (row == -1) {
							return;
						}
						model.removeRow(row);
						jb.setVisible(false);

						JOptionPane.showMessageDialog(null, "예매취소가 완료되었습니다", "check success",
								JOptionPane.INFORMATION_MESSAGE);
						listP.removeAll();
						listP.revalidate();
						listP.repaint();
						showBookingTable("tmdwns0531");
						return;
					} else {
						JOptionPane.showMessageDialog(null, "예매취소가 불가합니다", "check success", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}

		@Override
		public Object getCellEditorValue() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			// TODO Auto-generated method stub
			return jb;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			// TODO Auto-generated method stub
			return jb;
		}

	}

}
