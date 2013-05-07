/**
 *  Copyright (c)  2011-2020 Gozap, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of Gozap,
 *  Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with Gozap.
 */
package com.gozap.chouti.service;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParamBean;

/**
 * httpclient facotry
 * 
 * @author saint
 * @date 2013-1-7
 */
public final class HttpClientFactory {
	

	private static final String USERAGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31";

	private static final String CHARSET = "UTF-8";

	private static HttpClient httpClient;
	
	static {
		HttpParams params = new BasicHttpParams();
		HttpProtocolParamBean paramsBean = new HttpProtocolParamBean(params);
		paramsBean.setVersion(HttpVersion.HTTP_1_1);
		paramsBean.setContentCharset(CHARSET);
		paramsBean.setUserAgent(USERAGENT);
		paramsBean.setUseExpectContinue(true);
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 300000);
		params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 300000);
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
		cm.setMaxTotal(200);
		cm.setDefaultMaxPerRoute(200);
		httpClient = new DefaultHttpClient(cm, params);
	}

	/**
	 * get HttpClient
	 * 
	 * @return
	 * @author saint
	 * @date 2013-1-9
	 */
	public static HttpClient getInstance() {		
		return httpClient;
	}
	

	private HttpClientFactory() {
		
	}
	
}
