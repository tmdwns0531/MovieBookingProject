package home;

import java.util.*;

import dao.*;
import vo.*;

public class Session {

	private String idx;
	static private Session sess;
	boolean logged;

	private Session() {
	}

	public static Session getInstance() {
		if (sess == null) {
			sess = new Session();
		}
		return sess;
	}

	public void logIn(String id, String pw) {
		List<MemberVo> li = MemberDAO.getInstance().read(id, pw);

		if (id.equals(li.get(0).getUser_id())) {
			try {
				setIdx(id);
				start();
				System.out.println(this.idx + " session OK");
				System.out.println("logged : " + logged);

			} catch (Exception e) {
				e.printStackTrace();
				e.getMessage();
			}
		}
	}

	public void logout() {
		idx = null;
		destroy();
	}

	public String getIdx() {
		return idx;
	}

	void setIdx(String idx) throws Exception {

		if (idx.length() == 0 || idx == null || idx.contains(" ")) {
			throw new Exception("아이디 오류");
		}
		this.idx = idx;

	}

	void start() {
		logged = true;
	}

	void destroy() {
		logged = false;
	}

	public boolean logFlag() {
		return this.logged;
	}

}
