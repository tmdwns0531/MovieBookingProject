package dao;

import java.util.*;

import org.apache.ibatis.session.*;

import vo.*;

public class BookingDAO {

	public List<ScreeningVo> selectAll(SqlSession session, String movieNm) {
		List<ScreeningVo> list = session.selectList("selectAll", movieNm);
		return list;
	}

	public List<ScreeningVo> selectYears(SqlSession session, String movieNm) {
		List<ScreeningVo> list = session.selectList("selectYears", movieNm);
		return list;
	}

	public List<ScreeningVo> selectMonths(SqlSession session, String movieNm) {
		List<ScreeningVo> list = session.selectList("selectMonths", movieNm);
		return list;
	}

	public List<ScreeningVo> selectDates(SqlSession session, String movieNm) {
		List<ScreeningVo> list = session.selectList("selectDates", movieNm);
		return list;
	}

	public List<ScreeningVo> selectAllTheater(SqlSession session, Map<String, String> map) {
		List<ScreeningVo> list = session.selectList("selectAllTheater", map);
		return list;
	}

	public List<String> selectAllSeats(SqlSession session, String theaterCode) {
		List<String> list = session.selectList("selectAllSeats", theaterCode);
		return list;
	}

	public List<Integer> selectPrice(SqlSession session) {
		List<Integer> list = session.selectList("selectPrice");
		return list;
	}

	public List<String> selectBookingStateSeat(SqlSession session, Map<String, Object> screening_info) {
		List<String> list = session.selectList("selectBookingStateSeat", screening_info);
		return list;
	}

	public int selectUserIDX(SqlSession session, String myID) {
		int result = session.selectOne("selectUserIDX", myID);
		return result;
	}

	public int insertBooking(SqlSession session, Map<String, Object> bookingInfo) {
		int result = session.insert("insertBooking", bookingInfo);
		return result;
	}

	public List<BookingVo> selectMyBookingHistoryDate(SqlSession session, int IDX) {
		List<BookingVo> list = session.selectList("selectMyBookingHistoryDate", IDX);
		return list;
	}

	public List<BookingVo> selectSeatPriceList(SqlSession session, Map<String, Object> map) {
		List<BookingVo> list = session.selectList("selectSeatPriceList", map);
		return list;
	}

	public int cancleBooking(SqlSession session, Map<String, Object> cancleMap) {
		int result = session.delete("cancleBooking", cancleMap);
		return result;
	}
}