package home;

import java.awt.*;
import java.util.concurrent.*;

import javax.swing.*;

public class LodingPage extends JFrame {

	static JLabel alert = new JLabel();
	JPanel p1 = new JPanel();
	Font font2 = new Font("monospaced", Font.BOLD | Font.ITALIC, 40);
	int cnt;
	final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	static ScheduledFuture<?> t;

	LodingPage() {
		// TODO Auto-generated constructor stub
		setLayout(null);
		setSize(900, 700);
		setVisible(true);
		setBackground(Color.BLACK);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setAlwaysOnTop(true);

		add(p1);
		p1.add(alert);
		p1.setBackground(Color.black);
		p1.setBounds(0, 0, 900, 700);
		p1.setLayout(null);
		alert.setBounds(300, 230, 400, 200);
		alert.setFont(font2);
		alert.setForeground(Color.white);

		// center screen
		Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

		t = executorService.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				StringBuilder sb = new StringBuilder("·Î µù Áß");
				for (int i = 0; i < 3; i++) {
					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					alert.setText(sb.append('.').toString());
				}
				cnt++;
				if (cnt == 5) {
					t.cancel(true);
				}
			}
		}, 0, 1, TimeUnit.SECONDS);
	}
}
