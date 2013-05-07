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

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author saint
 * @date 2013-4-14
 */
public final class HttpService {
	private static final Logger LOGGER = Logger.getLogger(HttpService.class);
	
	private HttpService() {
		
	}
	
	/**
	 * 
	 * @param uri uri
	 * @return
	 * @author saint
	 * @date 2013-4-17
	 */
	public static  byte[] get(String uri) {
		HttpClient httpClient = HttpClientFactory.getInstance();
		HttpGet httpGet = new HttpGet(uri);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			byte[] bytes = EntityUtils.toByteArray(httpEntity);
			return bytes;
		} catch (ClientProtocolException e) {
			LOGGER.error("get" + uri + "error", e);
		} catch (IOException e) {
			LOGGER.error("get" + uri + "error", e);
		} finally {
			httpGet.releaseConnection();
		}
		return null;
	}

    /**
     *
     * @param uri uri
     * @return
     * @author saint
     * @date 2013-4-17
     */
    public static  Boolean post(String uri, UrlEncodedFormEntity entity) {
        HttpClient httpClient = HttpClientFactory.getInstance();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(entity);
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            String jsonStr = EntityUtils.toString(httpEntity);
            JSONObject json = JSON.parseObject(jsonStr);
            if (!json.getString("code").equals("99999")) {
                LOGGER.error("post uri:" + uri + ":" + entity + "error!" + "error msg: " + jsonStr);
            } else {
                return true;
            }
        } catch (ClientProtocolException e) {
            LOGGER.error("get" + uri + "error", e);
        } catch (IOException e) {
            LOGGER.error("get" + uri + "error", e);
        } finally {
            httpPost.releaseConnection();
        }

        return false;
    }
}
