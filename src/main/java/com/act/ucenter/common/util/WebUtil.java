package com.act.ucenter.common.util;

import org.apache.commons.lang3.Validate;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.Map.Entry;


/**
 * Http与Servlet工具类.
 * 
 * @author calvin
 */
public class WebUtil {

	// -- 常用数值定义 --//
	public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;
	private static final String ACCEPT = "Accept";

	private static final String ACCEPT_CHARSET = "Accept-Charset";

	private static final String ALLOW = "Allow";

	private static final String CACHE_CONTROL = "Cache-Control";

	private static final String CONTENT_DISPOSITION = "Content-Disposition";

	private static final String CONTENT_LENGTH = "Content-Length";

	private static final String CONTENT_TYPE = "Content-Type";

	private static final String DATE = "Date";

	private static final String ETAG = "ETag";

	private static final String EXPIRES = "Expires";

	private static final String IF_MODIFIED_SINCE = "If-Modified-Since";

	private static final String IF_NONE_MATCH = "If-None-Match";

	private static final String LAST_MODIFIED = "Last-Modified";

	private static final String LOCATION = "Location";

	private static final String PRAGMA = "Pragma";

	/**
	 * 设置客户端缓存过期时间 的Header.
	 */
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		// Http 1.0 header, set a fix expires date.
		response.setDateHeader(EXPIRES, System.currentTimeMillis() + (expiresSeconds * 1000));
		// Http 1.1 header, set a time after now.
		response.setHeader(CACHE_CONTROL, "private, max-age=" + expiresSeconds);
	}

	/**
	 * 设置禁止客户端缓存的Header.
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader(EXPIRES, 1L);
		response.addHeader(PRAGMA, "no-cache");
		// Http 1.1 header
		response.setHeader(CACHE_CONTROL, "no-cache, no-store, max-age=0");
	}

	/**
	 * 设置LastModified Header.
	 */
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
		response.setDateHeader(LAST_MODIFIED, lastModifiedDate);
	}

	/**
	 * 设置Etag Header.
	 */
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader(ETAG, etag);
	}

	/**
	 * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
	 * 
	 * 如果无修改, checkIfModify返回false ,设置304 not modify status.
	 * 
	 * @param lastModified 内容的最后修改时间.
	 */
	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response,
			long lastModified) {
		long ifModifiedSince = request.getDateHeader(IF_MODIFIED_SINCE);
		if ((ifModifiedSince != -1) && (lastModified < (ifModifiedSince + 1000))) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	/**
	 * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
	 * 
	 * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
	 * 
	 * @param etag 内容的ETag.
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
		String headerValue = request.getHeader(IF_NONE_MATCH);
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader(ETAG, etag);
				return false;
			}
		}
		return true;
	}

	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 
	 * @param fileName 下载后的文件名.
	 */
	public static void setFileDownloadHeader(HttpServletResponse response, String fileName) {
		try {
			// 中文文件名支持
			String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
			response.setHeader(CONTENT_DISPOSITION, "attachment; filename=\"" + encodedfileName + "\"");
		} catch (UnsupportedEncodingException e) {
		}
	}

	/**
	 * 取得带相同前缀的Request Parameters, copy from spring WebUtils.
	 * 
	 * 返回的结果的Parameter名已去除前缀.
	 */
	public static Map<String, String> getParametersStartingWith(ServletRequest request, String prefix) {
		Validate.notNull(request, "Request must not be null");
		Enumeration<String> paramNames = request.getParameterNames();
		Map<String, String> params = new TreeMap<String, String>();
		if (prefix == null) {
			prefix = "";
		}
		while ((paramNames != null) && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if ((values == null) || (values.length == 0)) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					StringBuffer sb = new StringBuffer();
					for (String value: values){
						if (sb.length()>0)
							sb.append(",");
						sb.append(value);
					}
					params.put(unprefixed, sb.toString());
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	/**
	 * 组合Parameters生成Query String的Parameter部分, 并在paramter name上加上prefix.
	 * 
	 * @see #getParametersStartingWith
	 */
	public static String encodeParameterStringWithPrefix(Map<String, Object> params, String prefix) {
		if ((params == null) || (params.size() == 0)) {
			return "";
		}

		if (prefix == null) {
			prefix = "";
		}

		StringBuilder queryStringBuilder = new StringBuilder();
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			queryStringBuilder.append(prefix).append(entry.getKey()).append('=').append(entry.getValue());
			if (it.hasNext()) {
				queryStringBuilder.append('&');
			}
		}
		return queryStringBuilder.toString();
	}

	/**
	 * 客户端对Http Basic验证的 Header进行编码.
	 */
	public static String encodeHttpBasic(String userName, String password) {
		String encode = userName + ":" + password;
		return "Basic " + EncodeUtil.encodeBase64(encode.getBytes());
	}
	
	public static boolean isAjaxRequest(HttpServletRequest requestH) {
    	String json = requestH.getParameter("_ajax"); //URLRewriter set
    	if (json!=null&&json.length()>0){
    		return "Y".equalsIgnoreCase(json);
    	}
    	else {
    		String xRequestedWith = requestH.getHeader("x-requested-with");
			return "XMLHttpRequest".equalsIgnoreCase(xRequestedWith);
	    }
		
	}
	
	public static boolean isJSRequest(HttpServletRequest request){
		String uri = request.getRequestURI();
		if (uri==null||uri.length()==0)
			return false;
		if (uri.indexOf("?")>=0){
			uri = uri.substring(0,uri.lastIndexOf("?"));
		}
		if (uri.toLowerCase().endsWith(".js"))
			return true;
		return false;
	}
	
	public static void sendJson(HttpServletResponse response,String s) throws IOException{
		response.setContentType("text/javascript;charset=UTF-8");
		response.getWriter().write(s);
		//IOUtils.write(s, response.getOutputStream());
	}
	
}
