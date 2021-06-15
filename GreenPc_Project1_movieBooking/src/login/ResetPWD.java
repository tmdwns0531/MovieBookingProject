package login;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import dao.*;

class ResetPWD extends JPanel {

	JLabel title, containerID, alert;
	JPanel p1;
	JPasswordField inputNew, inputCheck;
	JButton back, ok;
	Font font;
	JCheckBox cbxShow;
	EtchedBorder eb = new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.LIGHT_GRAY);

	String spaceTextNew;
	String spaceTextCheck;

	ResetPWD() {

		title = new JLabel("��  ���̵� : ");
		containerID = new JLabel(" ");
		alert = new JLabel("�� ��й�ȣ��  �ּ� �ϳ��� ����, �ϳ��� ���� �� �ϳ��� Ư�� ���ڸ� ������  8~15 �ڸ� �̳��� �������ּ��� ��");
		p1 = new JPanel();
		inputNew = new JPasswordField("");
		inputCheck = new JPasswordField("");
		cbxShow = new JCheckBox(" Show Password ");

		back = new JButton("��");
		ok = new JButton("Ȯ��");

		font = new Font("monospaced", Font.BOLD | Font.ITALIC, 18);

		// add panel
		setLayout(null);
		p1.setLayout(null);
		add(p1).setBounds(100, 100, 600, 400);
		p1.setBorder(eb);

		// add comp
		p1.add(title).setBounds(0, 0, 200, 45);
		p1.add(containerID).setBounds(title.getX() + 130, title.getY(), title.getWidth() + 250, title.getHeight());
		p1.add(inputNew).setBounds(title.getX() + 100, title.getY() + 100, p1.getWidth() - 200, title.getHeight());
		p1.add(inputCheck).setBounds(title.getX() + 100, inputNew.getY() + 100, p1.getWidth() - 200,
				inputNew.getHeight());
		p1.add(alert).setBounds(title.getX() + 20, inputCheck.getY() + 80, p1.getWidth(), title.getHeight());

		// setting button
		add(back).setBounds(320, 500, 50, 50);
		add(ok).setBounds(back.getX() + 60, back.getY(), 100, 50);

		// text font
		title.setFont(font);
		containerID.setFont(font);
		containerID.setForeground(Color.MAGENTA);

		// setting private effect for password
		inputNew.setEchoChar('*');
		inputCheck.setEchoChar('*');

		// setting checkbox
		p1.add(cbxShow).setBounds(inputCheck.getX(), inputCheck.getY() + 50, 200, 50);

		inputNew.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				spaceTextNew = String.valueOf(inputNew.getPassword());

				if (spaceTextNew.length() == 0) {
					inputNew.setText("�� ��й�ȣ");
					inputNew.setEchoChar((char) 0);
				}

			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

				if (String.valueOf(inputNew.getPassword()).equals("�� ��й�ȣ")) {
					inputNew.setText("");
					inputNew.setEchoChar('*');
				}
			}
		});

		inputCheck.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				spaceTextCheck = String.valueOf(inputCheck.getPassword());

				if (spaceTextCheck.length() == 0) {
					inputCheck.setText("�� ��й�ȣ Ȯ��");
					inputCheck.setEchoChar((char) 0);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (String.valueOf(inputCheck.getPassword()).equals("�� ��й�ȣ Ȯ��")) {
					inputCheck.setText("");
					inputCheck.setEchoChar('*');
				}
			}
		});

		cbxShow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (!String.valueOf(inputNew.getPassword()).equals("�� ��й�ȣ")) {

					if (cbxShow.isSelected()) {
						inputNew.setEchoChar((char) 0);
					} else {
						inputNew.setEchoChar('*');
					}
				}

				if (!String.valueOf(inputCheck.getPassword()).equals("�� ��й�ȣ Ȯ��")) {
					if (cbxShow.isSelected()) {
						inputCheck.setEchoChar((char) 0);
					} else {
						inputCheck.setEchoChar('*');
					}
				}
			}
		});
	}

	// update for password
	int updatePwd(String searchID) {

		System.out.println(searchID);

		if (checkingPattern() == -1) {
			return -1;
		}

		int check = MemberDAO.getInstance().resetPassword(searchID, inputNew.getText());

		if (check == -1) {
			System.out.println(" * fail update password *");
			JOptionPane.showMessageDialog(null, "������Ʈ ���� ! ������ ���� ���", "check fail", JOptionPane.ERROR_MESSAGE);
			return -1;
		}
		JOptionPane.showMessageDialog(null, "��й�ȣ�� ���� �Ǿ����ϴ�", "join success", JOptionPane.INFORMATION_MESSAGE);
		return 0;
	}

	// show container user's id
	void setContainerID(String searchID) {
		containerID.setText(searchID + " ");
	}

	int checkingPattern() {
		String newPassword = String.valueOf(inputNew.getPassword());
		String checkPassword = String.valueOf(inputCheck.getPassword());
		System.out.println(newPassword);
		System.out.println(checkPassword);

		if (newPassword.length() == 0) {
			JOptionPane.showMessageDialog(null, "�� ��й�ȣ�� �Է��ϼ���.", "check fail", JOptionPane.ERROR_MESSAGE);
			inputNew.grabFocus();
			return -1;
		} else if (newPassword.contains(" ")) {
			JOptionPane.showMessageDialog(null, "�� ��й�ȣ�� ������ ���ԵǾ����ϴ�", "check fail", JOptionPane.ERROR_MESSAGE);
			inputNew.grabFocus();
			return -1;
		} else if (checkPassword.length() == 0) {
			JOptionPane.showMessageDialog(null, "�� ��й�ȣ Ȯ�� ���� �Է��ϼ���.", "check fail", JOptionPane.ERROR_MESSAGE);
			inputCheck.grabFocus();
			return -1;
		} else if (checkPassword.contains(" ")) {
			JOptionPane.showMessageDialog(null, "�� ��й�ȣ Ȯ�� ���� ������ ���ԵǾ����ϴ�", "check fail", JOptionPane.ERROR_MESSAGE);
			inputCheck.grabFocus();
			return -1;
		} else if (!(newPassword.equals(checkPassword))) {
			JOptionPane.showMessageDialog(null, "��й�ȣ�� ���� ��ġ���� �ʽ��ϴ�", "check fail", JOptionPane.ERROR_MESSAGE);
			inputCheck.grabFocus();
			return -1;
		} else if (!(newPassword.matches(
				"^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&.^*%()-_=+~`])[A-Za-z\\d$@$!%*#?&.^*%()-_=+~`]{8,15}$"))) {
			JOptionPane.showMessageDialog(null, "��й�ȣ��  �ּ� �ϳ��� ����, �ϳ��� ���� �� �ϳ��� Ư�� ���ڸ� ������  8~15 �ڸ� �̳��� �����մϴ�",
					"check fail", JOptionPane.ERROR_MESSAGE);
			inputNew.grabFocus();
			return -1;
		}
		return 0;
	}

}
