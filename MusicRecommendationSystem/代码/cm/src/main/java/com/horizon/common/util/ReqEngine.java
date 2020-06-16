package com.horizon.common.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class ReqEngine {

	private static Logger log = Logger.getLogger(ReqEngine.class);

	public static String requestRoadJson(String reqUrl,
			Map<String, Object> params) throws IOException {
		BufferedReader in = null;
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept-Charset", "utf-8");
			con.setRequestProperty("contentType",
					"application/x-www-form-urlencoded; charset=UTF-8");
			// 设置参数
			if (params != null) {
				for (Entry<String, Object> entry : params.entrySet()) {
					String key = entry.getKey();
					con.setRequestProperty(key, (String) entry.getValue());
				}
			}

			con.setDoOutput(true);// 是否输入参数
			con.setDoInput(true);
			int responseCode = con.getResponseCode();
			log.info("Response code: " + responseCode);
			in = new BufferedReader(new InputStreamReader(con.getInputStream(),
					"utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			log.debug(buffer.toString());
			return buffer.toString();

		} catch (Throwable e) {
			log.error(e.getMessage());
			return "0";
		} finally {
			try {
				in.close();
			} catch (Exception e2) {
				log.error(e2.getMessage());
			}
		}
	}

	public static String requestDeleteJson(String reqUrl,
			Map<String, Object> params) throws IOException {
		BufferedReader in = null;
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("DELETE");
			con.setRequestProperty("Accept-Charset", "utf-8");
			con.setRequestProperty("contentType",
					"application/x-www-form-urlencoded; charset=UTF-8");
			// 设置参数
			if (params != null) {
				for (Entry<String, Object> entry : params.entrySet()) {
					String key = entry.getKey();
					con.setRequestProperty(key, (String) entry.getValue());
				}
			}

			con.setDoOutput(true);// 是否输入参数
			con.setDoInput(true);
			int responseCode = con.getResponseCode();
			log.info("Response code: " + responseCode);
			in = new BufferedReader(new InputStreamReader(con.getInputStream(),
					"utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			log.debug(buffer.toString());
			return buffer.toString();

		} catch (Throwable e) {
			log.error(e.getMessage());
			return "0";
		} finally {
			try {
				in.close();
			} catch (Exception e2) {
				log.error(e2.getMessage());
			}
		}
	}

	public static String requestGetJson(String reqUrl,
			Map<String, Object> params) throws IOException {
		BufferedReader in = null;
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept-Charset", "utf-8");
			con.setRequestProperty("contentType",
					"application/x-www-form-urlencoded; charset=UTF-8");
			// 设置参数
			if (params != null) {
				for (Entry<String, Object> entry : params.entrySet()) {
					String key = entry.getKey();
					con.setRequestProperty(key, (String) entry.getValue());
				}
			}
			con.setDoOutput(true);
			con.setDoInput(true);
			int responseCode = con.getResponseCode();
			log.info("Response code: " + responseCode);
			// if (responseCode == 200) {
			in = new BufferedReader(new InputStreamReader(con.getInputStream(),
					"utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			log.debug(buffer.toString());
			return buffer.toString();
			// }
		} catch (Throwable e) {
			log.error(e.getMessage());
			return "0";
		} finally {
			in.close();
		}
	}

	public static String requestContent(String reqUrl, String content)
			throws IOException {
		BufferedReader in = null;
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept-Charset", "utf-8");
			con.setRequestProperty("contentType",
					"application/x-www-form-urlencoded; charset=UTF-8");
			// 设置参数
			con.setDoOutput(true);
			con.setDoInput(true);
			OutputStream out = con.getOutputStream();
			out.write(content.getBytes());
			out.close();
			int responseCode = con.getResponseCode();
			log.info("Response code: " + responseCode);
			in = new BufferedReader(new InputStreamReader(con.getInputStream(),
					"utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			log.debug(buffer.toString());
			return buffer.toString();

		} catch (Throwable e) {
			log.error(e.getMessage());
			return "0";
		} finally {
			try {
				in.close();
			} catch (Exception e2) {
				log.error(e2.getMessage());
			}
		}
	}

	public static String sendPostReq(String reqURL, String reqBody) {
		String result = null;
		BufferedReader in = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(reqURL)
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
			OutputStream out = conn.getOutputStream();
			if (StringUtils.isNotBlank(reqBody)) {
				out.write(reqBody.getBytes());
			}
			int code = conn.getResponseCode();
			log.info(code);
			if (code >= 200 && code < 300) {
				result = IoUtility.readStringFromInputStream(
						conn.getInputStream(), "utf-8");
			} else {
				result = IoUtility.readStringFromInputStream(
						conn.getErrorStream(), "utf-8");
			}
			out.close();
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
				}
		}
		// 返回
		return result;
	}

	/**
	 * @author put 方式请求rest 接口
	 * @param reqBody
	 * @return
	 */
	public static String sendDelReq(String reqURL, String reqBody) {
		String result = null;
		BufferedReader in = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(reqURL)
					.openConnection();
			conn.setRequestMethod("DELETE");
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("contentType", "utf-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			OutputStream out = conn.getOutputStream();
			if (StringUtils.isNotBlank(reqBody)) {
				out.write(reqBody.getBytes());
			}
			int code = conn.getResponseCode();
			log.info(code);
			if (code >= 200 && code < 300) {
				result = IoUtility.readStringFromInputStream(
						conn.getInputStream(), "utf-8");
			} else {
				result = IoUtility.readStringFromInputStream(
						conn.getErrorStream(), "utf-8");
			}
			out.close();
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
				}
		}
		// 返回
		return result;
	}

}
