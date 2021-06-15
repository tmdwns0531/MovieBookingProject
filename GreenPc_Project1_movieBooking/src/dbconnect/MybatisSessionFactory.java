package dbconnect;

import java.io.*;

import org.apache.ibatis.io.*;
import org.apache.ibatis.session.*;

public class MybatisSessionFactory {

	static SqlSessionFactory sessionFactory = null;

	static {
		String resource = "session/mybatis_setting.xml"; // 데이터베이스의 설정정보를 입력해준 xml 파일
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			System.out.println("초기화 성공");
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