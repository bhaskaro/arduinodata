/**
 *
 */
package com.iot.arduino.arduinodata.utils;

import com.iot.arduino.arduinodata.excep.RTException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bhaskar
 */
public class HttpUtils {

    private static final Logger logger = LogManager.getLogger();

    private final HttpClient client = HttpClientBuilder.create().build();

    /**
     * @param args args
     */
    public static void main(String[] args) {

        HttpUtils httpUtils = new HttpUtils();

        String url = "https://api.weather.gov/gridpoints/STO/32,33/forecast/hourly";

        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");

        String response = httpUtils.get(url, headers);
        logger.info("response : {}", response);
    }

    public String get(String url, Map<String, String> headers) {

        HttpGet get = new HttpGet(url);

        if (headers != null)
            headers.forEach(get::setHeader);

        try {
            HttpResponse response = client.execute(get);

            if (response.getStatusLine().getStatusCode() >= 400)
                throw new RTException(response.getStatusLine().getStatusCode() + "-" + response.getStatusLine().getReasonPhrase());
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RTException(e);
        }
    }

    public String post(String url, CharSequence cs, Map<String, String> headers) {

        try {
            HttpPost post = new HttpPost(url);
            post.setEntity(new StringEntity(cs.toString()));

            if (headers != null)
                headers.forEach(post::setHeader);

            HttpResponse response = client.execute(post);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RTException(e);
        }

    }

    public String patch(String url, CharSequence cs, Map<String, String> headers) {

        try {
            HttpPatch patch = new HttpPatch(url);
            patch.setEntity(new StringEntity(cs.toString()));

            if (headers != null)
                headers.forEach(patch::setHeader);

            HttpResponse response = client.execute(patch);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RTException(e);
        }
    }

    public String put(String url, CharSequence cs, Map<String, String> headers) {

        try {
            HttpPut put = new HttpPut(url);
            put.setEntity(new StringEntity(cs.toString()));

            if (headers != null)
                headers.forEach(put::setHeader);

            HttpResponse response = client.execute(put);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RTException(e);
        }
    }

    public String delete(String url, Map<String, String> headers) {

        HttpDelete del = new HttpDelete(url);

        if (headers != null)
            headers.forEach(del::setHeader);

        String resp;

        try {
            HttpResponse response = client.execute(del);

            if (response.getEntity().getContentLength() > 0)
                resp = EntityUtils.toString(response.getEntity());
            else
                resp = response.getStatusLine().getStatusCode() + " : " + response.getStatusLine().getReasonPhrase();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RTException(e);
        }

        return resp;
    }

}
