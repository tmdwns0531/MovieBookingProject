package movieRestAPI;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

import org.json.*;

public class BoxOfficeAPI {
	// 상수 설정
	// - 요청(Request) 요청 변수
	private final static String DAILY_REQUEST_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
	private final static String WEEKLY_REQUEST_URL = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json";
	private final static String AUTH_KEY = "6719f4d60a8be3d5d01056e4e8977dbe";
	static Calendar cal = Calendar.getInstance();
	private static String date;
	// - 일자 포맷
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
		}); // (rest api -> api url + key + 조건 ) : request url 에 붙일 발급된 key + 요청인터페이스 -> url
			// 문자열값 생성작업

		//
		return sb.toString();
	}

	// API요청
	public static List<String> requestAPI(String type) {

		List<String> list = new ArrayList<String>();
		Map<String, String> paramMap = new HashMap<String, String>();

		try {
			// Request URL 연결 객체 생성
			URL requestURL = null;

			int DAILY_ROW;

			if (type.equals("DAILY")) {

				DAILY_ROW = 0;

				// 변수설정
				// - 하루전 날짜

				cal.setTime(new Date());
				cal.add(Calendar.DATE, -1);

				System.out.println(DATE_URL_FMT.format(cal.getTime()));

				// 변수 설정
				// - 요청(Request) 인터페이스 Map
				// - 어제자 다양성 한국영화 10개 조회

				paramMap.put("key", AUTH_KEY); // 발급받은 인증키
				paramMap.put("targetDt", DATE_URL_FMT.format(cal.getTime())); // 조회하고자 하는 날짜
				paramMap.put("itemPerPage", "10"); // 결과 ROW 의 개수( 최대 10개 )

				requestURL = new URL(DAILY_REQUEST_URL + "?" + makeQueryString(paramMap));

			} else {

				DAILY_ROW = 1;

				cal.setTime(new java.util.Date());
				cal.add(Calendar.DAY_OF_WEEK, -1);
				cal.setFirstDayOfWeek(Calendar.SUNDAY);
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

				System.out.println(DATE_URL_FMT.format(cal.getTime()));

				paramMap.put("key", AUTH_KEY); // 발급받은 인증키
				paramMap.put("targetDt", DATE_URL_FMT.format(cal.getTime())); // 조회하고자 하는 날짜
				paramMap.put("itemPerPage", "10"); // 결과 ROW 의 개수( 최대 10개 )
				paramMap.put("weekGb", "0");

				requestURL = new URL(WEEKLY_REQUEST_URL + "?" + makeQueryString(paramMap));

			}

			HttpURLConnection conn = (HttpURLConnection) requestURL.openConnection();

			// GET 방식으로 요청
			conn.setRequestMethod("GET");
			conn.setDoInput(true);

			// 응답(Response) 구조 작성
			// - Stream -> JSONObject
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String readline = null;
			StringBuffer response = new StringBuffer();
			while ((readline = br.readLine()) != null) {
				response.append(readline);
			}
			// JSON 객체로 변환
			JSONObject responseBody = new JSONObject(response.toString());
			// 데이터 추출 ( 최상위 객체를 get )
			JSONObject boxOfficeResult = responseBody.getJSONObject("boxOfficeResult");

			// 박스오피스 주제 출력 ( 최상위 객체 -> key 값 조회 -> 문자열 (value))
			String boxofficeType = boxOfficeResult.getString("boxofficeType");
			System.out.println("박스 오피스 타입 : " + boxofficeType);

			// 박스오피스 목록 출력
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
				System.out.printf("  %s - %s - 영화코드 : %s\n", boxOffice.get("rnum"), boxOffice.get("movieNm"),
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
