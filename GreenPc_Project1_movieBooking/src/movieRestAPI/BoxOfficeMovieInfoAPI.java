package movieRestAPI;

import java.io.*;
import java.net.*;
import java.util.*;

import org.json.*;

public class BoxOfficeMovieInfoAPI {
	// ��� ����
	// - ��û(Request) ��û ����
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
		}); // (rest api -> api url + key + ���� ) : request url �� ���� �߱޵� key + ��û�������̽� -> url
			// ���ڿ��� �����۾�

		//
		return sb.toString();
	}

	// API��û
	public static Map<String, String> requestAPI(String movieCd) {

		Map<String, String> paramMap = new HashMap<String, String>();
		Map<String, String> info_list = new LinkedHashMap<>();

		try {

			paramMap.put("key", AUTH_KEY); // �߱޹��� ����Ű
			paramMap.put("movieCd", movieCd); // movieCode for get info

			// Request URL ���� ��ü ����
			URL requestURL = new URL(REQUEST_URL + "?" + makeQueryString(paramMap));

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
			JSONObject movieInfoResult = responseBody.getJSONObject("movieInfoResult");

			// movieInfoResult ���� ��ü
			JSONObject movieInfo = movieInfoResult.getJSONObject("movieInfo");

			// �迭
			JSONArray nations = movieInfo.getJSONArray("nations");
			JSONArray genres = movieInfo.getJSONArray("genres");
			JSONArray directors = movieInfo.getJSONArray("directors");
			JSONArray actors = movieInfo.getJSONArray("actors");
			JSONArray companys = movieInfo.getJSONArray("companys");
			JSONArray audits = movieInfo.getJSONArray("audits");

			// ��Ŷ ( ������ / ��ȭ ���� / ���� ���� / �帣 )
			String movieNm = movieInfo.get("movieNm").toString();
			String openDt = movieInfo.get("openDt").toString();
			String typeNm = movieInfo.get("typeNm").toString();
			JSONObject nationsObj = (JSONObject) nations.get(0);
			String nationNm = nationsObj.get("nationNm").toString();
			JSONObject genresObj = (JSONObject) genres.get(0);
			String genreNm = genresObj.get("genreNm").toString();

			// �迭ó��
			// �����̸�
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

			// ����̸�
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

			// ��޻�
			String companyNm = "";

			for (Object list : companys) {

				JSONObject companyObj = (JSONObject) list;
				if (companyObj.get("companyPartNm").toString().equals("��޻�")) {
					companyNm = companyObj.get("companyNm").toString();
				}
			}

			// �������
			JSONObject auditsObj = (JSONObject) audits.get(0);
			String watchGradeNm = auditsObj.get("watchGradeNm").toString();

			// ��ó
			String source = movieInfoResult.get("source").toString();

			String[] infoIndexes = { movieNm, openDt, typeNm, nationNm, genreNm, directorsNm, actorsNm, watchGradeNm,
					companyNm, source };

			for (int i = 0; i < infoIndexes.length; i++) {
				if (infoIndexes[i].length() == 0) {
					infoIndexes[i] = "< no information >";
				}
			}

			info_list.put("��ȭ �̸� : ", infoIndexes[0]);
			info_list.put("������ : ", infoIndexes[1]);
			info_list.put("��ȭ ���� : ", infoIndexes[2]);
			info_list.put("���� ���� : ", infoIndexes[3]);
			info_list.put("�帣 : ", infoIndexes[4]);
			info_list.put("���� : ", infoIndexes[5]);
			info_list.put("��� : ", infoIndexes[6]);
			info_list.put("������� : ", infoIndexes[7]);
			info_list.put("��޻� : ", infoIndexes[8]);
			info_list.put("��ó : ", infoIndexes[9]);

			System.out.println("��ȭ �̸� :  " + infoIndexes[0]);
			System.out.println("������ :  " + infoIndexes[1]);
			System.out.println("��ȭ���� : " + infoIndexes[2]);
			System.out.println("���۱��� :" + infoIndexes[3]);
			System.out.println("�帣 : " + infoIndexes[4]);
			System.out.println("���� : " + infoIndexes[5]);
			System.out.println("���  : " + infoIndexes[6]);
			System.out.println("������� : " + infoIndexes[7]);
			System.out.println("��޻� :  " + infoIndexes[8]);
			System.out.println("��ó : " + infoIndexes[9]);

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
