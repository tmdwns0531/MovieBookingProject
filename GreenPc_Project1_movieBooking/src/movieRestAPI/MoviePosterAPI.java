package movieRestAPI;

//네이버 검색 API 예제 - blog 검색
import java.io.*;
import java.net.*;
import java.util.*;

import org.json.*;

public class MoviePosterAPI {

	static String imgURL = "";
	static String userRating = "";

	public static URL rerquestAPI(String movieNm) throws MalformedURLException {

		String clientId = "DKJVBu6de5hg6gEABXHd"; // 애플리케이션 클라이언트 아이디
		String clientSecret = "D0GppEXbwB"; // 애플리케이션 클라이언트 시크릿

		String text = null;
		try {
			text = URLEncoder.encode(movieNm, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("검색어 인코딩 실패", e);
		}

		String apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + text; // json 결과
		// String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text;
		// // xml 결과

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);

		get(apiURL, requestHeaders);

		return new URL(imgURL);
	}

	private static void get(String apiUrl, Map<String, String> requestHeaders) {
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod("GET");

			// 키 값 노출막기위함 get 방식 x -> 쿼리문은 get param (영화명)
			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
				readBody(con.getInputStream());
			} else { // 에러 발생
				readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
	}

	private static HttpURLConnection connect(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
		}
	}

	private static void readBody(InputStream body) {
		InputStreamReader streamReader = new InputStreamReader(body);

		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder response = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				response.append(line);
			}

			JSONObject responseBody = new JSONObject(response.toString());

			// 배열 item
			JSONArray items = responseBody.getJSONArray("items");

			// 객체 item[0]
			JSONObject itemObj = (JSONObject) items.get(0);
			imgURL = itemObj.get("image").toString();

			// 객체 userRating
			userRating = itemObj.get("userRating").toString();

		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
		}
	}

	public static String getUserRating() {
		return userRating;
	}
}