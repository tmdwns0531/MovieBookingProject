package dbconnect;

import java.io.*;

import org.apache.ibatis.io.*;
import org.apache.ibatis.session.*;

public class MybatisSessionFactory {

	static SqlSessionFactory sessionFactory = null;

	static {
		String resource = "session/mybatis_setting.xml"; // �����ͺ��̽��� ���������� �Է����� xml ����
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			System.out.println("�ʱ�ȭ ����");
		} catch (IOException e) {
			e.printStackTrace();
		}
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	public static SqlSession getSqlSession() {
		SqlSession session = sessionFactory.openSession();

		return session;
	}

}