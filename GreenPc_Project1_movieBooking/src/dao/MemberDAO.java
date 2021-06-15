package dao;

import java.sql.*;
import java.util.*;

import dbconnect.*;
import vo.*;

public class MemberDAO {

	boolean flag = false;

	private Connection conn;
	private ResultSet rs;
	private PreparedStatement stmt;

	static private MemberDAO md = null;

	static public MemberDAO getInstance() {
		if (md == null) {
			md = new MemberDAO();
		}
		return md;
	}

	// login
	public List<MemberVo> list() {

		List<MemberVo> list = new ArrayList<>();

		try {
			conn = DBconnection.getInstance().getConnect();
			String query = "select * from member_info";
			System.out.println("SQL : " + query);

			stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery();

			rs.last();
			System.out.println("rs.getRow():" + rs.getRow());

			if (rs.getRow() == 0) {
				System.out.println("0 row selected....");
			} else {
				System.out.println(rs.getRow() + "row selected....");
				rs.beforeFirst();

				while (rs.next()) {
					MemberVo mb = new MemberVo();
					String user_id = rs.getString("user_id");
					String user_pwd = rs.getString("user_pwd");

					mb.setUser_id(user_id);
					mb.setUser_pwd(user_pwd);
					list.add(mb);
				}
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// checking for duplication of id
	public List<MemberVo> checkID(String ID) {

		List<MemberVo> list = new ArrayList<>();

		try {
			conn = DBconnection.getInstance().getConnect();
			String query = "select * from member_info where user_id = ?";

			stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.setString(1, ID);
			rs = stmt.executeQuery();

			rs.last();
			System.out.println("rs.getRow():" + rs.getRow());

			if (rs.getRow() == 0) {
				System.out.println("0 row selected....");
			} else {
				System.out.println(rs.getRow() + "row selected....");
				rs.previous();

				while (rs.next()) {
					MemberVo mb = new MemberVo();
					String user_id = rs.getString("user_id");

					mb.setUser_id(user_id);
					list.add(mb);
				}
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// signup
	public void signUp(MemberVo member) {

		try {
			conn = DBconnection.getInstance().getConnect();

			String inputPh_num = "";
			String query = "insert into member_info values(seq_member_info_idx.NEXTVAL , ? , ?, ?, ?, ?, 'user', to_date(to_char(sysdate,'yyyy-MM-dd')),?)";

			stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			// 핸드폰 번호 int 형 변환
			StringTokenizer st = new StringTokenizer(member.getPhone_num(), "-");

			while (st.hasMoreElements()) {
				inputPh_num += st.nextToken();
			}
			member.setPhone_num(inputPh_num);

			// gender 영문변환
			if (member.getGender().equals("남 성")) {
				member.setGender("man");
			} else {
				member.setGender("woman");
			}

			System.out.println("success insert info of member");
			stmt.setString(1, member.getUser_id());
			stmt.setString(2, member.getUser_pwd());
			stmt.setString(3, member.getUser_name());
			stmt.setInt(4, Integer.parseInt(member.getPhone_num()));
			stmt.setString(5, member.getEmail());
			stmt.setString(6, member.getGender());

			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// find user's id
	public ArrayList<MemberVo> findID(String name, String email) {

		ArrayList<MemberVo> list = new ArrayList<>();

		try {
			conn = DBconnection.getInstance().getConnect();
			String query = "select * from member_info where user_name = ? and user_email = ?";

			stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.setString(1, name);
			stmt.setString(2, email);

			rs = stmt.executeQuery();

			rs.last();
			System.out.println("rs.getRow():" + rs.getRow());

			if (rs.getRow() == 0) {
				System.out.println("0 row selected....");
			} else {
				System.out.println(rs.getRow() + "row selected....");
				rs.beforeFirst();

				while (rs.next()) {
					MemberVo mb = new MemberVo();
					String user_id = rs.getString("user_id");
					String date = rs.getString("create_date").substring(0, 10);
					mb.setUser_id(user_id);
					mb.setCreate_date(date);
					list.add(mb);
				}
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// find user's password
	public ArrayList<MemberVo> findPW(MemberVo member) {

		ArrayList<MemberVo> list = new ArrayList<>();

		try {

			String inputPh_num = "";

			conn = DBconnection.getInstance().getConnect();
			String query = "select * from member_info where user_id = ? and user_name = ? and phone_num = ?  and  user_email = ?";

			stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			// 핸드폰 번호 int 형 변환
			StringTokenizer st = new StringTokenizer(member.getPhone_num(), "-");

			while (st.hasMoreElements()) {
				inputPh_num += st.nextToken();
			}
			member.setPhone_num(inputPh_num);
			stmt.setString(1, member.getUser_id());
			stmt.setString(2, member.getUser_name());
			stmt.setString(3, member.getPhone_num());
			stmt.setString(4, member.getEmail());

			rs = stmt.executeQuery();

			rs.last();
			System.out.println("rs.getRow():" + rs.getRow());

			if (rs.getRow() == 0) {
				System.out.println("0 row selected....");
			} else {
				System.out.println(rs.getRow() + "row selected....");
				rs.beforeFirst();

				while (rs.next()) {
					MemberVo mb = new MemberVo();
					String user_pwd = rs.getString("user_pwd");
					String user_id = rs.getString("user_id");
					mb.setUser_pwd(user_pwd);
					mb.setUser_id(user_id);
					list.add(mb);
				}
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// reset user's password
	public int resetPassword(String user_id, String user_pwd) {

		try {
			conn = DBconnection.getInstance().getConnect();

			String query = "update member_info set user_pwd = ? where user_id = ?";

			stmt = conn.prepareStatement(query);

			stmt.setString(1, user_pwd);
			stmt.setString(2, user_id);

			if (stmt.executeUpdate() == 2) {
				System.out.println(" * not commit to sql * ");
				return -1;
			} else {
				System.out.println("success insert info of member");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// read ID to insert session
	public List<MemberVo> read(String ID, String PW) {

		List<MemberVo> list = new ArrayList<>();

		try {
			conn = DBconnection.getInstance().getConnect();
			String query = "select * from member_info where user_id = ? and  user_pwd= ?";

			stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.setString(1, ID);
			stmt.setString(2, PW);
			rs = stmt.executeQuery();

			rs.last();
			System.out.println("rs.getRow():" + rs.getRow());

			if (rs.getRow() == 0) {
				System.out.println("0 row selected....");
			} else {
				System.out.println(rs.getRow() + "row selected....");
				rs.previous();

				while (rs.next()) {
					MemberVo mb = new MemberVo();
					String user_id = rs.getString("user_id");

					mb.setUser_id(user_id);
					list.add(mb);
				}
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
