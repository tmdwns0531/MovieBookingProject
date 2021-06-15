package movieRestAPI;

import java.io.*;
import java.net.*;
import java.util.*;

import org.json.*;

public class BoxOfficeMovieInfoAPI {
	// 상수 설정
	// - 요청(Request) 요청 변수
	private final static String REQUEST_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";
	private final static String AUTH_KEY = "6719f4d60a8be3d5d01056e4e8977dbe";

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
	public static Map<String, String> requestAPI(String movieCd) {

		Map<String, String> paramMap = new HashMap<String, String>();
		Map<String, String> info_list = new LinkedHashMap<>();

		try {

			paramMap.put("key", AUTH_KEY); // 발급받은 인증키
			paramMap.put("movieCd", movieCd); // movieCode for get info

			// Request URL 연결 객체 생성
			URL requestURL = new URL(REQUEST_URL + "?" + makeQueryString(paramMap));

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
			JSONObject movieInfoResult = responseBody.getJSONObject("movieInfoResult");

			// movieInfoResult 하위 객체
			JSONObject movieInfo = movieInfoResult.getJSONObject("movieInfo");

			// 배열
			JSONArray nations = movieInfo.getJSONArray("nations");
			JSONArray genres = movieInfo.getJSONArray("genres");
			JSONArray directors = movieInfo.getJSONArray("directors");
			JSONArray actors = movieInfo.getJSONArray("actors");
			JSONArray companys = movieInfo.getJSONArray("companys");
			JSONArray audits = movieInfo.getJSONArray("audits");

			// 패킷 ( 개봉일 / 영화 정보 / 제작 국가 / 장르 )
			String movieNm = movieInfo.get("movieNm").toString();
			String openDt = movieInfo.get("openDt").toString();
			String typeNm = movieInfo.get("typeNm").toString();
			JSONObject nationsObj = (JSONObject) nations.get(0);
			String nationNm = nationsObj.get("nationNm").toString();
			JSONObject genresObj = (JSONObject) genres.get(0);
			String genreNm = genresObj.get("genreNm").toString();

			// 배열처리
			// 감독이름
			StringBuilder sb = new StringBuilder();
			String directorsNm, actorsNm;

			for (Object list : directors) {
				JSONObject directorsObj = (JSONObject) list;
				sb.append(directorsObj.get("peopleNm").toString() + ",");
			}
			if (sb.toString().length() == 0) {
				directorsNm = sb.toString().substring(0, sb.toString().length());
			} else {
				directorsNm = sb.toString().substring(0, sb.toString().length() - 1);

			}
			sb.delete(0, sb.length());

			// 배우이름
			for (Object list : actors) {
				JSONObject actorsObj = (JSONObject) list;
				sb.append(actorsObj.get("peopleNm").toString() + ",");
			}
			if (sb.toString().length() == 0) {
				actorsNm = sb.toString().substring(0, sb.toString().length());
			} else {
				actorsNm = sb.toString().substring(0, sb.toString().length() - 1);

			}
			sb.delete(0, sb.length());

			// 배급사
			String companyNm = "";

			for (Object list : companys) {

				JSONObject companyObj = (JSONObject) list;
				if (companyObj.get("companyPartNm").toString().equals("배급사")) {
					companyNm = companyObj.get("companyNm").toString();
				}
			}

			// 관람등급
			JSONObject auditsObj = (JSONObject) audits.get(0);
			String watchGradeNm = auditsObj.get("watchGradeNm").toString();

			// 출처
			String source = movieInfoResult.get("source").toString();

			String[] infoIndexes = { movieNm, openDt, typeNm, nationNm, genreNm, directorsNm, actorsNm, watchGradeNm,
					companyNm, source };

			for (int i = 0; i < infoIndexes.length; i++) {
				if (infoIndexes[i].length() == 0) {
					infoIndexes[i] = "< no information >";
				}
			}

			info_list.put("영화 이름 : ", infoIndexes[0]);
			info_list.put("개봉일 : ", infoIndexes[1]);
			info_list.put("영화 유형 : ", infoIndexes[2]);
			info_list.put("제작 국가 : ", infoIndexes[3]);
			info_list.put("장르 : ", infoIndexes[4]);
			info_list.put("감독 : ", infoIndexes[5]);
			info_list.put("배우 : ", infoIndexes[6]);
			info_list.put("관람등급 : ", infoIndexes[7]);
			info_list.put("배급사 : ", infoIndexes[8]);
			info_list.put("출처 : ", infoIndexes[9]);

			System.out.println("영화 이름 :  " + infoIndexes[0]);
			System.out.println("개봉일 :  " + infoIndexes[1]);
			System.out.println("영화유형 : " + infoIndexes[2]);
			System.out.println("제작국가 :" + infoIndexes[3]);
			System.out.println("장르 : " + infoIndexes[4]);
			System.out.println("감독 : " + infoIndexes[5]);
			System.out.println("배우  : " + infoIndexes[6]);
			System.out.println("관람등급 : " + infoIndexes[7]);
			System.out.println("배급사 :  " + infoIndexes[8]);
			System.out.println("출처 : " + infoIndexes[9]);

		} catch (

		IOException e) {
			e.printStackTrace();
		}
		return info_list;
	}

//	public static void main(String[] args) {
//		requestAPI("20207582");
//	}
}
