package login;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import dao.*;
import vo.*;

class Join extends JPanel {

	JPanel p, p1, p2, titleP1, titleP2;
	JButton signUp, back, checkID;
	JLabel title, title2, id, pw, pw2, name, gender, phon_num, email, create_date, t1;
	JTextField tfID, tfNAME, tfPH_NUM, tfEMAIL;
	JPasswordField tfPW, tfPW2;
	CheckboxGroup cg;
	Checkbox cbM, cbW;
	String onchangeID = "";
	boolean checkForDuplication;
	boolean succesJoin;
	Font fon1, font2;

	Join() {
		p = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		p2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		titleP1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		titleP2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		title = new JLabel(" [ ȸ�� ");
		title2 = new JLabel("���� ] ");
		id = new JLabel("     * ȸ�� ID             ");
		pw = new JLabel("     * ��й�ȣ ");
		pw2 = new JLabel("     * ��й�ȣ Ȯ�� ");
		t1 = new JLabel("(Ư������ / ���� / ���� ���� ������ 8~15�ڸ�)");
		name = new JLabel("     * �� �� ");
		gender = new JLabel("     * �� �� ");
		phon_num = new JLabel("     * �޴���ȭ (ex.010-0000-0000) ");
		email = new JLabel("     * �̸��� ");

		signUp = new JButton("���");
		back = new JButton("���");
		checkID = new JButton("���̵� �ߺ��˻�");

		tfID = new JTextField();
		tfPW = new JPasswordField();
		tfPW2 = new JPasswordField();
		tfNAME = new JTextField();
		tfPH_NUM = new JTextField();
		tfEMAIL = new JTextField();
		cg = new CheckboxGroup();
		cbM = new Checkbox("�� ��", true, cg);
		cbW = new Checkbox("�� ��", false, cg);

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

		// ȸ��id + ���̵� �ߺ��˻�
		p1.add(id);
		p1.add(checkID);
		p1.setBackground(Color.LIGHT_GRAY);

		// grid �� �����ϱ�
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
		add(email);
		add(tfEMAIL);
		add(signUp);
		add(back);

		// ��Ʈ ����
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

		// ���̵� �ߺ� �˻�
		checkID.addActionListener(event -> {

			List<MemberVo> li = MemberDAO.getInstance().checkID(tfID.getText());

			if (li.size() != 0) {
				JOptionPane.showMessageDialog(null, "�ߺ��� ID �Դϴ�", "check fail", JOptionPane.ERROR_MESSAGE);
				checkForDuplication = false;

			} else if (tfID.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "���̵� �Է��ϼ���", "check fail", JOptionPane.ERROR_MESSAGE);
			} else if (tfID.getText().contains(" ")) {
				JOptionPane.showMessageDialog(null, "���̵� ������ ���� �� �� �����ϴ�", "check fail", JOptionPane.ERROR_MESSAGE);

			} else if (!(tfID.getText().matches("^[a-zA-Z]{1}[a-zA-Z0-9_]{7,20}$"))) {
				JOptionPane.showMessageDialog(null, "���̵�� �������� �����ϸ�  8~20���� ���� �ҹ���, ���ڿ� Ư����ȣ(_),(-) �� ��� �����մϴ� ",
						"check fail", JOptionPane.ERROR_MESSAGE);
				tfID.grabFocus();
			} else {
				JOptionPane.showMessageDialog(null, "��밡���� ID �Դϴ�", "check success", JOptionPane.INFORMATION_MESSAGE);
				checkForDuplication = true;
				onchangeID = tfID.getText();

			}

		});

	}

	// ȸ������ ��Ϲ�ư
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
				JOptionPane.showMessageDialog(null, "* ���� * (�����ڿ��� �����ϼ���. 010-3365-9545) ", "check fail",
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

		if (!(onchangeID.equals(tfID.getText()))) {
			checkForDuplication = false;
		}

		if (!checkForDuplication) {
			JOptionPane.showMessageDialog(null, "* ���̵� �ߺ��˻� �ʼ� *", "check fail", JOptionPane.ERROR_MESSAGE);
			tfID.grabFocus();
			return -1;
		} else if (id.length() == 0) {
			JOptionPane.showMessageDialog(null, "���̵� �Է��ϼ���.", "check fail", JOptionPane.ERROR_MESSAGE);
			tfID.grabFocus();
			return -1;
		} else if (id.contains(" ")) {
			JOptionPane.showMessageDialog(null, "���̵� ������ ���ԵǾ����ϴ�", "check fail", JOptionPane.ERROR_MESSAGE);
			tfID.grabFocus();
			return -1;
		} else if (pw.length() == 0) {
			JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է��ϼ���.", "check fail", JOptionPane.ERROR_MESSAGE);
			tfPW.grabFocus();
			return -1;
		} else if (pw.contains(" ")) {
			JOptionPane.showMessageDialog(null, "��й�ȣ�� ������ ���ԵǾ����ϴ�", "check fail", JOptionPane.ERROR_MESSAGE);
			tfPW.grabFocus();
			return -1;
		} else if (pw2.length() == 0) {
			JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է��ϼ���.", "check fail", JOptionPane.ERROR_MESSAGE);
			tfPW.grabFocus();
			return -1;
		} else if (pw2.contains(" ")) {
			JOptionPane.showMessageDialog(null, "��й�ȣ�� ������ ���ԵǾ����ϴ�", "check fail", JOptionPane.ERROR_MESSAGE);
			tfPW.grabFocus();
			return -1;
		} else if (name.length() == 0) {
			JOptionPane.showMessageDialog(null, "�̸��� �Է��ϼ���.", "check fail", JOptionPane.ERROR_MESSAGE);
			tfNAME.grabFocus();
			return -1;
		} else if (name.contains(" ")) {
			JOptionPane.showMessageDialog(null, "�̸��� ������ ���ԵǾ����ϴ�", "check fail", JOptionPane.ERROR_MESSAGE);
			tfNAME.grabFocus();
			return -1;
		} else if (ph_num.length() == 0) {
			JOptionPane.showMessageDialog(null, "�ڵ��� ��ȣ�� �Է��ϼ���.", "check fail", JOptionPane.ERROR_MESSAGE);
			tfPH_NUM.grabFocus();
			return -1;
		} else if (ph_num.contains(" ")) {
			JOptionPane.showMessageDialog(null, "�ڵ��� ��ȣ�� ������ ���ԵǾ����ϴ�", "check fail", JOptionPane.ERROR_MESSAGE);
			tfPH_NUM.grabFocus();
			return -1;
		} else if (u_email.length() == 0) {
			JOptionPane.showMessageDialog(null, "�̸����� �Է��ϼ���.", "check fail", JOptionPane.ERROR_MESSAGE);
			tfEMAIL.grabFocus();
			return -1;
		} else if (u_email.contains(" ")) {
			JOptionPane.showMessageDialog(null, "�̸��Ͽ� ������ ���ԵǾ����ϴ�", "check fail", JOptionPane.ERROR_MESSAGE);
			tfEMAIL.grabFocus();
			return -1;
		}

		if (!(pw.equals(pw2))) {
			JOptionPane.showMessageDialog(null, "��й�ȣ�� ���� ��ġ���� �ʽ��ϴ�", "check fail", JOptionPane.ERROR_MESSAGE);
			tfPW2.grabFocus();
			return -1;
		}

		if (checkPattern(list) == -1) {
			return -1;
		}

		JOptionPane.showMessageDialog(null, "ȸ�������� �Ϸ�Ǿ����ϴ�", "join success", JOptionPane.INFORMATION_MESSAGE);

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
			JOptionPane.showMessageDialog(null, "���̵�� �������� �����ϸ�  7~20���� ���� �ҹ���, ���ڿ� Ư����ȣ(_),(-) �� ��� �����մϴ� ",
					"check fail", JOptionPane.ERROR_MESSAGE);
			tfID.grabFocus();
			return -1;
		} else if (!(pw.matches(
				"^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&.^*%()-_=+~`])[A-Za-z\\d$@$!%*#?&.^*%()-_=+~`]{8,15}$"))) {
			JOptionPane.showMessageDialog(null, "��й�ȣ��  �ּ� �ϳ��� ����, �ϳ��� ���� �� �ϳ��� Ư�� ���ڸ� ������  8~15 �ڸ� �̳��� �����մϴ�",
					"check fail", JOptionPane.ERROR_MESSAGE);
			tfPW.grabFocus();
			return -1;
		} else if (!(name.matches("^[��-�R]{2,10}|[a-zA-Z]{2,10}"))) {
			JOptionPane.showMessageDialog(null, "�̸��� �ѱ� �Ǵ� ������ �����ϸ� 2~10�ڸ� �̳��� �����մϴ�", "check fail",
					JOptionPane.ERROR_MESSAGE);
			tfNAME.grabFocus();

		} else if (!(ph_num.matches("^\\d{3}-\\d{3,4}-\\d{4}$"))) {
			JOptionPane.showMessageDialog(null, "�ڵ��� ��ȣ�� ��Ȯ�� �Է����ּ��� ", "check fail", JOptionPane.ERROR_MESSAGE);
			tfPH_NUM.grabFocus();
			System.out.println(ph_num);
			return -1;
		} else if (!(u_email.matches("^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*$"))) {
			JOptionPane.showMessageDialog(null, "�̸��� ������ ���� �ʽ��ϴ�", "check fail", JOptionPane.ERROR_MESSAGE);
			tfEMAIL.grabFocus();
			System.out.println(u_email);
			return -1;
		}

		return 0;
	}

	boolean backAction() {

		int result = JOptionPane.showConfirmDialog(null, " ȸ�������� ����Ͻðڽ��ϱ�? ", "Confirm", JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.NO_OPTION) {
			return false;
		} else if (result == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}
}
