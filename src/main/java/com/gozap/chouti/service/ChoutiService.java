package com.gozap.chouti.service;

import com.gozap.chouti.RssSource.RssSource;
import com.gozap.chouti.conf.Configuration;
import com.gozap.chouti.model.RssItem;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: saint
 * Date: 13-5-3
 * Time: 下午4:08
 * To change this template use File | Settings | File Templates.
 */
public class ChoutiService {
    private static final Logger LOGGER = Logger.getLogger(ChoutiService.class);
    private static final String BASE_URI = Configuration.getInstance().getBaseUri();
    private static final String source = Configuration.getInstance().getSource();
    private  static final  String secretKey = Configuration.getInstance().getSecretKey();

    public static boolean save(RssItem rssItem, RssSource rssSource) throws UnsupportedEncodingException {
        String uri = BASE_URI + "api/r/" + rssSource.getType() + "/create.json";
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        if (rssItem.isValidate()) {
            formParams.add(new BasicNameValuePair("source", source));
            formParams.add(new BasicNameValuePair("secret_key", secretKey));
            formParams.add(new BasicNameValuePair("jid", rssSource.getUserName()));
            formParams.add(new BasicNameValuePair("content", StringUtils.substring(removeHtml(rssItem.getDescription()) ,0, 120)));
            formParams.add(new BasicNameValuePair("title", StringUtils.substring(removeHtml(rssItem.getTitle()), 0, 150)));
            formParams.add(new BasicNameValuePair("url", rssItem.getLink()));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
            Boolean ret = HttpService.post(uri, entity);
            if (!ret) {
                LOGGER.error("post " + uri + "?" + entity.toString()  + "error");
                return false;
            } else {
                LOGGER.info("post:" + rssItem + "success" );
                return true;
            }
        }  else {
            LOGGER.info(rssItem + " Illegal" );
            return false;
        }

    }

    private static String removeHtml(String input) {
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "")
                .replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "")
                .replaceAll("\r\n", "").trim();
        return str;
    }


}
