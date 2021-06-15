package service;

import java.util.*;

import org.apache.ibatis.session.*;

import dao.*;
import dbconnect.*;
import vo.*;

public class OracleMyBatisService {

	BookingDAO dao;

	public OracleMyBatisService() {
		dao = new BookingDAO();
	}

	public List<ScreeningVo> selectYearInfo(String movieNm) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // 접속 완료
		List<ScreeningVo> list = null;

		try {
			list = dao.selectYears(session, movieNm); // dao에 SqlSession 전송
		} finally {
			session.close(); // connection.close()와 비슷, 모든 함수마다 닫기
		}
		return list;
	}

	public List<ScreeningVo> selectMonthInfo(String movieNm) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // 접속 완료
		List<ScreeningVo> list = null;

		try {
			list = dao.selectMonths(session, movieNm); // dao에 SqlSession 전송
		} finally {
			session.close(); // connection.close()와 비슷, 모든 함수마다 닫기
		}
		return list;
	}

	public List<ScreeningVo> selectDateInfo(String movieNm) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // 접속 완료
		List<ScreeningVo> list = null;

		try {
			list = dao.selectDates(session, movieNm); // dao에 SqlSession 전송
		} finally {
			session.close(); // connection.close()와 비슷, 모든 함수마다 닫기
		}
		return list;
	}

	public List<ScreeningVo> selectTheater(Map<String, String> map) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // 접속 완료
		List<ScreeningVo> list = null;

		try {
			list = dao.selectAllTheater(session, map); // dao에 SqlSession 전송
		} finally {
			session.close(); // connection.close()와 비슷, 모든 함수마다 닫기
		}
		return list;
	}

	public List<String> selectSeats(String theaterCode) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // 접속 완료
		List<String> list = null;

		try {
			list = dao.selectAllSeats(session, theaterCode); // dao에 SqlSession 전송
		} finally {
			session.close(); // connection.close()와 비슷, 모든 함수마다 닫기
		}
		return list;
	}

	public List<Integer> selectPrice() {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // 접속 완료
		List<Integer> list = null;

		try {
			list = dao.selectPrice(session); // dao에 SqlSession 전송
		} finally {
			session.close(); // connection.close()와 비슷, 모든 함수마다 닫기
		}
		return list;
	}

	public List<String> selectBookingStateSeats(Map<String, Object> screeningInfo) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // 접속 완료
		List<String> list = null;

		try {
			list = dao.selectBookingStateSeat(session, screeningInfo); // dao에 SqlSession 전송
		} finally {
			session.close(); // connection.close()와 비슷, 모든 함수마다 닫기
		}
		return list;
	}

	public int selectUserIDX(String myID) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // 접속 완료
		int result = 0;

		try {
			result = dao.selectUserIDX(session, myID); // dao에 SqlSession 전송
		} finally {
			session.close(); // connection.close()와 비슷, 모든 함수마다 닫기
		}
		return result;
	}

	// insert into booking table
	public int insertBooking(Map<String, Object> bookingInfo) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // 접속 완료
		int result = 0;

		try {
			result = dao.insertBooking(session, bookingInfo); // dao에 SqlSession 전송
			if (result > 0) {
				session.commit();
				System.out.println("insert into booking info success");
			} else {
				session.rollback();
				System.out.println("insert into booking info fail");
			}
		} finally {
			session.close(); // connection.close()와 비슷, 모든 함수마다 닫기
		}
		return result;
	}

	public List<BookingVo> selectMyBookingHistoryDate(int IDX) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // 접속 완료
		List<BookingVo> list = null;

		try {
			list = dao.selectMyBookingHistoryDate(session, IDX); // dao에 SqlSession 전송
		} finally {
			session.close(); // connection.close()와 비슷, 모든 함수마다 닫기
		}
		return list;
	}

	public List<BookingVo> selectSeatPriceList(Map<String, Object> timePriceMap) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // 접속 완료
		List<BookingVo> list = null;

		try {
			list = dao.selectSeatPriceList(session, timePriceMap); // dao에 SqlSession 전송
		} finally {
			session.close(); // connection.close()와 비슷, 모든 함수마다 닫기
		}
		return list;
	}

	// insert into booking table
	public int cancleBooking(Map<String, Object> cancleMap) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // 접속 완료
		int result = 0;

		try {
			result = dao.cancleBooking(session, cancleMap); // dao에 SqlSession 전송
			if (result > 0) {
				session.commit();
				System.out.println("delete booking info success");
			} else {
				session.rollback();
				System.out.println("delete booking info fail");
			}
		} finally {
			session.close(); // connection.close()와 비슷, 모든 함수마다 닫기
		}
		return result;
	}

}
//반환값을 준비 try { list = dao.selectAll(session); //dao에 SqlSession 전송 }finally { session.close(); //connection.close()와 비슷, 모든 함수마다 닫기 }return list; }// end class
