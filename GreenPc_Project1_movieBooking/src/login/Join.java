package login;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import authentication.*;
import dao.*;
import vo.*;

class Join extends JPanel {

	JPanel p, p1, p2, p3, titleP1, titleP2;
	JButton signUp, back, checkID, checkEmale;
	JLabel title, title2, id, pw, pw2, name, gender, phon_num, email, create_date, t1, authCode;
	JTextField tfID, tfNAME, tfPH_NUM, tfEMAIL, tfAuthCode;
	JPasswordField tfPW, tfPW2;
	CheckboxGroup cg;
	Checkbox cbM, cbW;
	boolean checkForDuplication, checkForAuthEmail, succesJoin;
	Font fon1, font2;

	String onchangeID = "";

	Join() {
		p = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		p2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		p2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		p3 = new JPanel(new FlowLayout(FlowLayout.LEADING));

		titleP1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		titleP2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		title = new JLabel(" [ 회원 ");
		title2 = new JLabel("가입 ] ");
		id = new JLabel("     * 회원 ID          ");
		pw = new JLabel("     * 비밀번호 ");
		pw2 = new JLabel("     * 비밀번호 확인 ");
		t1 = new JLabel("(특수문자 / 문자 / 숫자 포함 형태의 8~15자리)");
		name = new JLabel("     * 이 름 ");
		gender = new JLabel("     * 성 별 ");
		phon_num = new JLabel("     * 휴대전화 (ex.010-0000-0000) ");
		email = new JLabel("     * 이메일             ");
		authCode = new JLabel("     *이메일 인증번호");

		signUp = new JButton("등록");
		back = new JButton("취소");
		checkID = new JButton("아이디 중복검사");
		checkEmale = new JButton("이메일 인증");

		tfID = new JTextField();
		tfPW = new JPasswordField();
		tfPW2 = new JPasswordField();
		tfNAME = new JTextField();
		tfPH_NUM = new JTextField();
		tfEMAIL = new JTextField();
		tfAuthCode = new JTextField();
		cg = new CheckboxGroup();
		cbM = new Checkbox("남 성", true, cg);
		cbW = new Checkbox("여 성", false, cg);

		setLayout(new GridLayout(10, 2, 5, 10));

		setBackground(Color.LIGHT_GRAY);
		setBounds(0, 0, 350, 400);
		tfID.grabFocus();

		add(titleP1);
		add(titleP2);
		titleP1.add(title);
		titleP2.add(title2);
		titleP1.setBackground(Color.LIGHT_GRAY);
		titleP2.setBackground(Color.LIGHT_GRAY);

		add(p1);

		// 회원id + 아이디 중복검사
		p1.add(id);
		p1.add(checkID);
		p1.setBackground(Color.LIGHT_GRAY);

		// grid 값 정렬하기
		add(tfID);
		add(pw);
		add(tfPW);
		add(p2);
		p2.add(pw2);
		p2.add(t1);
		p2.setBackground(Color.LIGHT_GRAY);
		add(tfPW2);
		add(name);
		add(tfNAME);
		add(gender);

		// check box
		add(p);
		p.setBackground(Color.LIGHT_GRAY);
		p.add(cbM);
		p.add(cbW);

		add(phon_num);
		add(tfPH_NUM);
		add(p3);
		p3.setBackground(Color.LIGHT_GRAY);
		p3.add(email);
		p3.add(checkEmale);
		add(tfEMAIL);
		add(authCode);
		add(tfAuthCode);
		add(signUp);
		add(back);

		// 폰트 세팅
		fon1 = new Font("SanSerif", Font.HANGING_BASELINE, 30);
		font2 = new Font("monospaced", Font.BOLD | Font.ITALIC, 18);
		title.setFont(fon1);
		title2.setFont(fon1);
		id.setFont(font2);
		pw.setFont(font2);
		pw2.setFont(font2);
		name.setFont(font2);
		gender.setFont(font2);
		phon_num.setFont(font2);
		email.setFont(font2);
		authCode.setFont(font2);

		// 아이디 중복 검사
		checkID.addActionListener(event -> {

			List<MemberVo> li = MemberDAO.getInstance().checkID(tfID.getText());

			if (li.size() != 0) {
				JOptionPane.showMessageDialog(null, "중복된 ID 입니다", "check fail", JOptionPane.ERROR_MESSAGE);
				checkForDuplication = false;

			} else if (tfID.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "아이디를 입력하세요", "check fail", JOptionPane.ERROR_MESSAGE);
			} else if (tfID.getText().contains(" ")) {
				JOptionPane.showMessageDialog(null, "아이디에 공백을 포함 할 수 없습니다", "check fail", JOptionPane.ERROR_MESSAGE);

			} else if (!(tfID.getText().matches("^[a-zA-Z]{1}[a-zA-Z0-9_]{7,20}$"))) {
				JOptionPane.showMessageDialog(null, "아이디는 영문으로 시작하며  8~20자의 영문 소문자, 숫자와 특수기호(_),(-) 만 사용 가능합니다 ",
						"check fail", JOptionPane.ERROR_MESSAGE);
				tfID.grabFocus();
			} else {
				JOptionPane.showMessageDialog(null, "사용가능한 ID 입니다", "check success", JOptionPane.INFORMATION_MESSAGE);
				checkForDuplication = true;
				onchangeID = tfID.getText();
			}
		});

		// 이메일 인증
		checkEmale.addActionListener((event) -> {

			if (tfEMAIL.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "이메일을 입력하세요.", "check fail", JOptionPane.ERROR_MESSAGE);
				tfEMAIL.grabFocus();
				return;
			} else if (tfEMAIL.getText().contains(" ")) {
				JOptionPane.showMessageDialog(null, "이메일에 공백이 포함되었습니다", "check fail", JOptionPane.ERROR_MESSAGE);
				tfEMAIL.grabFocus();
				return;
			} else if (!(tfEMAIL.getText().matches("^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*$"))) {
				JOptionPane.showMessageDialog(null, "이메일 형식이 맞지 않습니다", "check fail", JOptionPane.ERROR_MESSAGE);
				tfEMAIL.grabFocus();
				return;
			}

			if (PostMan.sendCodeToEmail(tfEMAIL.getText()) == 0) {
				JOptionPane.showMessageDialog(null, "인증번호를 발송했습니다", "check success", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "인증번호발송 실패 *개발자 문의*", "check fail", JOptionPane.ERROR_MESSAGE);
			}
			System.out.println("이메일 코드입력 : " + PostMan.getCode());
		});
	}

	// 회원가입 등록버튼
	void joinAction() {

		// TODO Auto-generated method stub
		MemberVo member = new MemberVo();

		String[] list = { "id", "pw", "pw2", "name", "ph_num", "u_email", "gender" };

		list[0] = tfID.getText();
		list[1] = String.valueOf(tfPW.getPassword());
		list[2] = String.valueOf(tfPW2.getPassword());
		list[3] = tfNAME.getText();
		list[4] = tfPH_NUM.getText();
		list[5] = tfEMAIL.getText();

		if (cbM.getState()) {
			list[6] = cbM.getLabel();
		} else {
			list[6] = cbW.getLabel();
		}

		if (checkingInfo(list) == -1) {
			return;
		} else {

			member.setUser_id(list[0]);
			member.setUser_pwd(list[1]);
			member.setUser_name(list[3]);
			member.setPhone_num(list[4]);
			member.setEmail(list[5]);
			member.setGender(list[6]);

			try {
				MemberDAO.getInstance().signUp(member);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "* 오류 * (개발자에게 문의하세요. 010-3365-9545) ", "check fail",
						JOptionPane.ERROR_MESSAGE);
			}

			succesJoin = true;

		}

	};

	int checkingInfo(String[] list) {

		String id, pw, pw2, name, ph_num, u_email, gender;

		id = list[0];
		pw = list[1];
		pw2 = list[2];
		name = list[3];
		ph_num = list[4];
		u_email = list[5];
		gender = list[6];

		if (!onchangeID.equals(tfID.getText())) {
			checkForDuplication = false;
		}

		if (!checkForDuplication) {
			JOptionPane.showMessageDialog(null, "* 아이디 중복검사 필수 *", "check fail", JOptionPane.ERROR_MESSAGE);
			tfID.grabFocus();
			return -1;
		} else if (id.length() == 0) {
			JOptionPane.showMessageDialog(null, "아이디를 입력하세요.", "check fail", JOptionPane.ERROR_MESSAGE);
			tfID.grabFocus();
			return -1;
		} else if (id.contains(" ")) {
			JOptionPane.showMessageDialog(null, "아이디에 공백이 포함되었습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			tfID.grabFocus();
			return -1;
		} else if (pw.length() == 0) {
			JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요.", "check fail", JOptionPane.ERROR_MESSAGE);
			tfPW.grabFocus();
			return -1;
		} else if (pw.contains(" ")) {
			JOptionPane.showMessageDialog(null, "비밀번호에 공백이 포함되었습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			tfPW.grabFocus();
			return -1;
		} else if (pw2.length() == 0) {
			JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요.", "check fail", JOptionPane.ERROR_MESSAGE);
			tfPW.grabFocus();
			return -1;
		} else if (pw2.contains(" ")) {
			JOptionPane.showMessageDialog(null, "비밀번호에 공백이 포함되었습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			tfPW.grabFocus();
			return -1;
		} else if (name.length() == 0) {
			JOptionPane.showMessageDialog(null, "이름을 입력하세요.", "check fail", JOptionPane.ERROR_MESSAGE);
			tfNAME.grabFocus();
			return -1;
		} else if (name.contains(" ")) {
			JOptionPane.showMessageDialog(null, "이름에 공백이 포함되었습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			tfNAME.grabFocus();
			return -1;
		} else if (ph_num.length() == 0) {
			JOptionPane.showMessageDialog(null, "핸드폰 번호를 입력하세요.", "check fail", JOptionPane.ERROR_MESSAGE);
			tfPH_NUM.grabFocus();
			return -1;
		} else if (ph_num.contains(" ")) {
			JOptionPane.showMessageDialog(null, "핸드폰 번호에 공백이 포함되었습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			tfPH_NUM.grabFocus();
			return -1;
		} else if (u_email.length() == 0) {
			JOptionPane.showMessageDialog(null, "이메일을 입력하세요.", "check fail", JOptionPane.ERROR_MESSAGE);
			tfEMAIL.grabFocus();
			return -1;
		} else if (u_email.contains(" ")) {
			JOptionPane.showMessageDialog(null, "이메일에 공백이 포함되었습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			tfEMAIL.grabFocus();
			return -1;
		}

		if (!(pw.equals(pw2))) {
			JOptionPane.showMessageDialog(null, "비밀번호가 서로 일치하지 않습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			tfPW2.grabFocus();
			return -1;
		}

		// 정규식 패턴 체크
		if (checkPattern(list) == -1) {
			return -1;
		}

		if (tfAuthCode.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "인증번호를 입력하세요.", "check fail", JOptionPane.ERROR_MESSAGE);
			tfEMAIL.grabFocus();
			return -1;
		} else if (tfAuthCode.getText().contains(" ")) {
			JOptionPane.showMessageDialog(null, "인증번호에 공백이 포함되었습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			tfEMAIL.grabFocus();
			return -1;
		}

		// 이메일 코드 인증
		if (authEmailCode() == -1) {
			JOptionPane.showMessageDialog(null, "이메일 인증번호가 일치하지 않습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			tfAuthCode.grabFocus();
			return -1;
		}

		JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다", "join success", JOptionPane.INFORMATION_MESSAGE);
		return 0;
	}

	int checkPattern(String[] list) {

		String id, pw, name, ph_num, u_email;

		id = list[0];
		pw = list[1];
		name = list[3];
		ph_num = list[4];
		u_email = list[5];

		if (!(id.matches("^[a-zA-Z]{1}[a-zA-Z0-9_]{7,20}$"))) {
			JOptionPane.showMessageDialog(null, "아이디는 영문으로 시작하며  7~20자의 영문 소문자, 숫자와 특수기호(_),(-) 만 사용 가능합니다 ",
					"check fail", JOptionPane.ERROR_MESSAGE);
			tfID.grabFocus();
			return -1;
		} else if (!(pw.matches(
				"^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&.^*%()-_=+~`])[A-Za-z\\d$@$!%*#?&.^*%()-_=+~`]{8,15}$"))) {
			JOptionPane.showMessageDialog(null, "비밀번호는  최소 하나의 문자, 하나의 숫자 및 하나의 특수 문자를 포함한  8~15 자리 이내만 가능합니다",
					"check fail", JOptionPane.ERROR_MESSAGE);
			tfPW.grabFocus();
			return -1;
		} else if (!(name.matches("^[가-힣]{2,10}|[a-zA-Z]{2,10}"))) {
			JOptionPane.showMessageDialog(null, "이름은 한글 또는 영문만 가능하며 2~10자리 이내로 가능합니다", "check fail",
					JOptionPane.ERROR_MESSAGE);
			tfNAME.grabFocus();

		} else if (!(ph_num.matches("^\\d{3}-\\d{3,4}-\\d{4}$"))) {
			JOptionPane.showMessageDialog(null, "핸드폰 번호를 정확히 입력해주세요 ", "check fail", JOptionPane.ERROR_MESSAGE);
			tfPH_NUM.grabFocus();
			System.out.println(ph_num);
			return -1;
		} else if (!(u_email.matches("^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*$"))) {
			JOptionPane.showMessageDialog(null, "이메일 형식이 맞지 않습니다", "check fail", JOptionPane.ERROR_MESSAGE);
			tfEMAIL.grabFocus();
			System.out.println(u_email);
			return -1;
		}

		return 0;
	}

	private int authEmailCode() {
		if (tfAuthCode.getText().equals(PostMan.getCode())) {
			return 0;
		} else {
			return -1;
		}
	}

	boolean backAction() {

		int result = JOptionPane.showConfirmDialog(null, " 회원가입을 취소하시겠습니까? ", "Confirm", JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.NO_OPTION) {
			return false;
		} else if (result == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}

}
