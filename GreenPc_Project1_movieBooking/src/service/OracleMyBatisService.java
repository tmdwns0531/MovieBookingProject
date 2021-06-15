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
		SqlSession session = MybatisSessionFactory.getSqlSession(); // ���� �Ϸ�
		List<ScreeningVo> list = null;

		try {
			list = dao.selectYears(session, movieNm); // dao�� SqlSession ����
		} finally {
			session.close(); // connection.close()�� ���, ��� �Լ����� �ݱ�
		}
		return list;
	}

	public List<ScreeningVo> selectMonthInfo(String movieNm) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // ���� �Ϸ�
		List<ScreeningVo> list = null;

		try {
			list = dao.selectMonths(session, movieNm); // dao�� SqlSession ����
		} finally {
			session.close(); // connection.close()�� ���, ��� �Լ����� �ݱ�
		}
		return list;
	}

	public List<ScreeningVo> selectDateInfo(String movieNm) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // ���� �Ϸ�
		List<ScreeningVo> list = null;

		try {
			list = dao.selectDates(session, movieNm); // dao�� SqlSession ����
		} finally {
			session.close(); // connection.close()�� ���, ��� �Լ����� �ݱ�
		}
		return list;
	}

	public List<ScreeningVo> selectTheater(Map<String, String> map) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // ���� �Ϸ�
		List<ScreeningVo> list = null;

		try {
			list = dao.selectAllTheater(session, map); // dao�� SqlSession ����
		} finally {
			session.close(); // connection.close()�� ���, ��� �Լ����� �ݱ�
		}
		return list;
	}

	public List<String> selectSeats(String theaterCode) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // ���� �Ϸ�
		List<String> list = null;

		try {
			list = dao.selectAllSeats(session, theaterCode); // dao�� SqlSession ����
		} finally {
			session.close(); // connection.close()�� ���, ��� �Լ����� �ݱ�
		}
		return list;
	}

	public List<Integer> selectPrice() {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // ���� �Ϸ�
		List<Integer> list = null;

		try {
			list = dao.selectPrice(session); // dao�� SqlSession ����
		} finally {
			session.close(); // connection.close()�� ���, ��� �Լ����� �ݱ�
		}
		return list;
	}

	public List<String> selectBookingStateSeats(Map<String, Object> screeningInfo) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // ���� �Ϸ�
		List<String> list = null;

		try {
			list = dao.selectBookingStateSeat(session, screeningInfo); // dao�� SqlSession ����
		} finally {
			session.close(); // connection.close()�� ���, ��� �Լ����� �ݱ�
		}
		return list;
	}

	public int selectUserIDX(String myID) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // ���� �Ϸ�
		int result = 0;

		try {
			result = dao.selectUserIDX(session, myID); // dao�� SqlSession ����
		} finally {
			session.close(); // connection.close()�� ���, ��� �Լ����� �ݱ�
		}
		return result;
	}

	// insert into booking table
	public int insertBooking(Map<String, Object> bookingInfo) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // ���� �Ϸ�
		int result = 0;

		try {
			result = dao.insertBooking(session, bookingInfo); // dao�� SqlSession ����
			if (result > 0) {
				session.commit();
				System.out.println("insert into booking info success");
			} else {
				session.rollback();
				System.out.println("insert into booking info fail");
			}
		} finally {
			session.close(); // connection.close()�� ���, ��� �Լ����� �ݱ�
		}
		return result;
	}

	public List<BookingVo> selectMyBookingHistoryDate(int IDX) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // ���� �Ϸ�
		List<BookingVo> list = null;

		try {
			list = dao.selectMyBookingHistoryDate(session, IDX); // dao�� SqlSession ����
		} finally {
			session.close(); // connection.close()�� ���, ��� �Լ����� �ݱ�
		}
		return list;
	}

	public List<BookingVo> selectSeatPriceList(Map<String, Object> timePriceMap) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // ���� �Ϸ�
		List<BookingVo> list = null;

		try {
			list = dao.selectSeatPriceList(session, timePriceMap); // dao�� SqlSession ����
		} finally {
			session.close(); // connection.close()�� ���, ��� �Լ����� �ݱ�
		}
		return list;
	}

	// insert into booking table
	public int cancleBooking(Map<String, Object> cancleMap) {
		SqlSession session = MybatisSessionFactory.getSqlSession(); // ���� �Ϸ�
		int result = 0;

		try {
			result = dao.cancleBooking(session, cancleMap); // dao�� SqlSession ����
			if (result > 0) {
				session.commit();
				System.out.println("delete booking info success");
			} else {
				session.rollback();
				System.out.println("delete booking info fail");
			}
		} finally {
			session.close(); // connection.close()�� ���, ��� �Լ����� �ݱ�
		}
		return result;
	}

}
//��ȯ���� �غ� try { list = dao.selectAll(session); //dao�� SqlSession ���� }finally { session.close(); //connection.close()�� ���, ��� �Լ����� �ݱ� }return list; }// end class
