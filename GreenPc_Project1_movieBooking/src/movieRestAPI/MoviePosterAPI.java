package movieRestAPI;

//���̹� �˻� API ���� - blog �˻�
import java.io.*;
import java.net.*;
import java.util.*;

import org.json.*;

public class MoviePosterAPI {

	static String imgURL = "";
	static String userRating = "";

	public static URL rerquestAPI(String movieNm) throws MalformedURLException {

		String clientId = "DKJVBu6de5hg6gEABXHd"; // ���ø����̼� Ŭ���̾�Ʈ ���̵�
		String clientSecret = "D0GppEXbwB"; // ���ø����̼� Ŭ���̾�Ʈ ��ũ��

		String text = null;
		try {
			text = URLEncoder.encode(movieNm, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("�˻��� ���ڵ� ����", e);
		}

		String apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + text; // json ���
		// String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text;
		// // xml ���

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

			// Ű �� ���⸷������ get ��� x -> �������� get param (��ȭ��)
			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // ���� ȣ��
				readBody(con.getInputStream());
			} else { // ���� �߻�
				readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API ��û�� ���� ����", e);
		} finally {
			con.disconnect();
		}
	}

	private static HttpURLConnection connect(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL�� �߸��Ǿ����ϴ�. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("������ �����߽��ϴ�. : " + apiUrl, e);
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

			// �迭 item
			JSONArray items = responseBody.getJSONArray("items");

			// ��ü item[0]
			JSONObject itemObj = (JSONObject) items.get(0);
			imgURL = itemObj.get("image").toString();

			// ��ü userRating
			userRating = itemObj.get("userRating").toString();

		} catch (IOException e) {
			throw new RuntimeException("API ������ �дµ� �����߽��ϴ�.", e);
		}
	}

	public static String getUserRating() {
		return userRating;
	}
}