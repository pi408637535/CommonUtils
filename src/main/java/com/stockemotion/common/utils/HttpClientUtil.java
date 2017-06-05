package com.stockemotion.common.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;


public class HttpClientUtil {

    public static String doGet(String url, Map<String, String> param, Map<String, String> heads) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            if (heads != null) {
                for (Map.Entry<String, String> entry : heads.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doGet(String url, Map<String, String> haeads) {
        return doGet(url, null, haeads);
    }

    /**
     * 对应表单
     *
     * @param url
     * @param param
     * @return
     */
    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "UTF-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    public static String doPost(String url) {
        return doPost(url, null);
    }

    public static String doPostJson(String url, String json) {
        return doPostJson(url, json, null);
    }

    public static String doPostJson(String url, String json, Map<String, String> heads) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            if (heads != null) {
                for (Map.Entry<String, String> entry : heads.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }

            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    /*
  * 专门用于获取用户
  * */
    public static final JSONObject getHttpResponse(String url, Header header) {
        JSONObject res = null;
        HttpClient hc = new HttpClient();
        GetMethod httpget = new GetMethod(url);

        httpget.setRequestHeader(header);

        try {
            hc.executeMethod(httpget);
            res = JsonUtils.TO_JSONObject(httpget.getResponseBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static final JSONObject getHttpResponse(String url, String token) {
        JSONObject res = null;
        HttpClient hc = new HttpClient();
        GetMethod httpget = new GetMethod(url);

        httpget.setRequestHeader(new Header("Authorization", token));
        try {
            hc.executeMethod(httpget);
            res = JsonUtils.TO_JSONObject(httpget.getResponseBodyAsString());
        } catch (IOException io) {
            System.out.println("Get http response from wode duration failed");
        } catch (JSONException jsone) {
            System.out.println("Failed to response to json");
        }
        return res;
    }

    public static final JSONObject httpPost(String url, JSONObject obj) throws IOException {
        JSONObject res = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost post = new HttpPost(url);
            StringEntity s = new StringEntity(obj.toString(),"UTF-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);
            CloseableHttpResponse response = httpclient.execute(post);
            try {
                HttpEntity entity = response.getEntity();
                res = JsonUtils.TO_JSONObject(EntityUtils.toString(entity, Charset.forName("UTF-8")));
                EntityUtils.consume(entity);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return res;
    }

    /*public static final JSONObject httpsPost(String url, JSONObject obj) throws KeyStoreException, NoSuchAlgorithmException,
            CertificateException, IOException, KeyManagementException, JSONException
    {
        JSONObject res = null;
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream in = Reader.class.getResourceAsStream("/server.keystore");
        keyStore.load(in, "123456".toCharArray());

        SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(keyStore, new TrustSelfSignedStrategy()).build();
        SSLConnectionSocketFactory sslf = new SSLConnectionSocketFactory(sslcontext);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslf).build();
        try
        {
            HttpPost post = new HttpPost(url);
            StringEntity s = new StringEntity(obj.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);
            CloseableHttpResponse response = httpclient.execute(post);
            try
            {
                HttpEntity entity = response.getEntity();
                res = JsonUtils.TO_JSONObject(EntityUtils.toString(entity, Charset.forName("UTF-8")));
                EntityUtils.consume(entity);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                response.close();
            }
        }
        finally
        {
            httpclient.close();
        }
        return res;
    }*/

    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "http://118.244.212.178:8001/wd/forum/comment/to_me/inner";
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", 156+"");
        map.put("pageNumber", 1+"");
        map.put("pageSize", 1+"");

        Map<String, String> head = new HashMap<String, String>();
        head.put("version", 14+"");
        head.put("deviceType", 1+"");


        String string = HttpClientUtil.doGet(url, map, head);
       // System.out.println(string);

        String messageurl = "http://api3.stockemotion.com/cms/authority/add";


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", 140);
        jsonObject.put("code", 9);
        String dateString = DateUtils.getCurrentTimestamp().toString();
        jsonObject.put("date", dateString.substring(0, dateString.length() - 5));
        jsonObject.put("title", "@我的");
        jsonObject.put("content", string);

        try {
            HttpClientUtil.httpPost(messageurl, jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

/*
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", 77);
        jsonObject.put("authorityNumber", 1);
        Date date = DateUtils.getCurrentTimestamp();
        jsonObject.put("expireDate", date.getTime());
        Date dateNow = DateUtils.getCurrentTimestamp();
        jsonObject.put("effectiveDate", date.getTime());


        String url = "http://api3.stockemotion.com/cms/authority/add";*/
}
