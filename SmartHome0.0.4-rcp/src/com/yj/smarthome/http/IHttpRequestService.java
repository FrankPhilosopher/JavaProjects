package com.yj.smarthome.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * http request service
 * 
 * @author wuxuehong
 * 
 */
public interface IHttpRequestService {

	/**
	 * 
	 * @param urlString
	 *            : url address
	 * @return : response
	 * @throws IOException
	 *             :exception
	 */
	public HttpURLConnection sendGet(String urlString) throws IOException;

	/**
	 * 
	 * @param urlString
	 *            : url address
	 * @param params
	 *            : paramaters
	 * @return response
	 * @throws IOException
	 */
	public HttpURLConnection sendGet(String urlString, Map<String, String> params) throws IOException;

	/**
	 * 
	 * @param urlString
	 *            :url address
	 * @param params
	 *            : paramaters
	 * @param propertys
	 *            : http head properties
	 * @return: response
	 * @throws IOException
	 */
	public HttpURLConnection sendGet(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException;

	/**
	 * 
	 * @param urlString
	 * @return
	 * @throws IOException
	 */
	public HttpURLConnection sendPost(String urlString) throws IOException;

	/**
	 * 
	 * @param urlString
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public HttpURLConnection sendPost(String urlString, Map<String, String> params) throws IOException;

	/**
	 * 
	 * @param urlString
	 * @param params
	 * @param propertys
	 * @return
	 * @throws IOException
	 */
	public HttpURLConnection sendPost(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException;

	/**
	 * set the encode
	 * 
	 * @param defaultContentEncoding
	 */
	public void setDefaltContentEncoding(String defaultContentEncoding);
}
