package movieRestAPI;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

import org.json.*;

public class BoxOfficeAPI {
	// ��� ����
	// - ��û(Request) ��û ����
	private final static String DAILY_REQUEST_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
	private final static String WEEKLY_REQUEST_URL = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json";
	private final static String AUTH_KEY = "6719f4d60a8be3d5d01056e4e8977dbe";
	static Calendar cal = Calendar.getInstance();
	private static String date;
	// - ���� ����
	private final static SimpleDateFormat DATE_URL_FMT = new SimpleDateFormat("yyyyMMdd");
	private final static SimpleDateFormat SHOW_LABEL_FMT = new SimpleDateFormat("yyyy-MM-dd");

	// movie code
	private static String[][] movieCd = new String[2][10];

	// Map -> QueryString
	static String makeQueryString(Map<String, String> paramMap) {
		final StringBuilder sb = new StringBuilder();

		paramMap.entrySet().forEach((entry) -> {
			if (sb.length() > 0) {
				sb.append('&');
			}
			sb.append(entry.getKey()).append('=').append(entry.getValue());
		}); // (rest api -> api url + key + ���� ) : request url �� ���� �߱޵� key + ��û�������̽� -> url
			// ���ڿ��� �����۾�

		//
		return sb.toString();
	}

	// API��û
	public static List<String> requestAPI(String type) {

		List<String> list = new ArrayList<String>();
		Map<String, String> paramMap = new HashMap<String, String>();

		try {
			// Request URL ���� ��ü ����
			URL requestURL = null;

			int DAILY_ROW;

			if (type.equals("DAILY")) {

				DAILY_ROW = 0;

				// ��������
				// - �Ϸ��� ��¥

				cal.setTime(new Date());
				cal.add(Calendar.DATE, -1);

				System.out.println(DATE_URL_FMT.format(cal.getTime()));

				// ���� ����
				// - ��û(Request) �������̽� Map
				// - ������ �پ缺 �ѱ���ȭ 10�� ��ȸ

				paramMap.put("key", AUTH_KEY); // �߱޹��� ����Ű
				paramMap.put("targetDt", DATE_URL_FMT.format(cal.getTime())); // ��ȸ�ϰ��� �ϴ� ��¥
				paramMap.put("itemPerPage", "10"); // ��� ROW �� ����( �ִ� 10�� )

				requestURL = new URL(DAILY_REQUEST_URL + "?" + makeQueryString(paramMap));

			} else {

				DAILY_ROW = 1;

				cal.setTime(new java.util.Date());
				cal.add(Calendar.DAY_OF_WEEK, -1);
				cal.setFirstDayOfWeek(Calendar.SUNDAY);
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

				System.out.println(DATE_URL_FMT.format(cal.getTime()));

				paramMap.put("key", AUTH_KEY); // �߱޹��� ����Ű
				paramMap.put("targetDt", DATE_URL_FMT.format(cal.getTime())); // ��ȸ�ϰ��� �ϴ� ��¥
				paramMap.put("itemPerPage", "10"); // ��� ROW �� ����( �ִ� 10�� )
				paramMap.put("weekGb", "0");

				requestURL = new URL(WEEKLY_REQUEST_URL + "?" + makeQueryString(paramMap));

			}

			HttpURLConnection conn = (HttpURLConnection) requestURL.openConnection();

			// GET ������� ��û
			conn.setRequestMethod("GET");
			conn.setDoInput(true);

			// ����(Response) ���� �ۼ�
			// - Stream -> JSONObject
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String readline = null;
			StringBuffer response = new StringBuffer();
			while ((readline = br.readLine()) != null) {
				response.append(readline);
			}
			// JSON ��ü�� ��ȯ
			JSONObject responseBody = new JSONObject(response.toString());
			// ������ ���� ( �ֻ��� ��ü�� get )
			JSONObject boxOfficeResult = responseBody.getJSONObject("boxOfficeResult");

			// �ڽ����ǽ� ���� ��� ( �ֻ��� ��ü -> key �� ��ȸ -> ���ڿ� (value))
			String boxofficeType = boxOfficeResult.getString("boxofficeType");
			System.out.println("�ڽ� ���ǽ� Ÿ�� : " + boxofficeType);

			// �ڽ����ǽ� ��� ���
			JSONArray dailyBoxOfficeList;

			if (type.equals("DAILY")) {
				dailyBoxOfficeList = boxOfficeResult.getJSONArray("dailyBoxOfficeList");
			} else {
				dailyBoxOfficeList = boxOfficeResult.getJSONArray("weeklyBoxOfficeList");

			}
			Iterator<Object> iter = dailyBoxOfficeList.iterator();

			int LISTS = 0;

			while (iter.hasNext()) {
				JSONObject boxOffice = (JSONObject) iter.next();
				System.out.printf("  %s - %s - ��ȭ�ڵ� : %s\n", boxOffice.get("rnum"), boxOffice.get("movieNm"),
						boxOffice.get("movieCd"));
				list.add(boxOffice.get("movieNm").toString());
				movieCd[DAILY_ROW][LISTS] = boxOffice.get("movieCd").toString();
				LISTS++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static String getDateTypeAlert(String type) {

		if (type.equals("DAILY")) {

			cal.setTime(new Date());
			cal.add(Calendar.DATE, -1);
			date = SHOW_LABEL_FMT.format(cal.getTime());
			return date;
		} else if (type.equals("WEEKLY")) {
			cal.setTime(new java.util.Date());
			cal.add(Calendar.DAY_OF_WEEK, -1);
			cal.setFirstDayOfWeek(Calendar.SUNDAY);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

			return SHOW_LABEL_FMT.format(cal.getTime());
		}
		return date;
	}

	public static String[][] getMovieCodeArray() {
		return movieCd;
	}
}
