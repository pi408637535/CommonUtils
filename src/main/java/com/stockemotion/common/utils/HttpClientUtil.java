package com.stockemotion.common.utils;

import com.alibaba.fastjson.JSONObject;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientUtil
{

    public static String doGet(String url, Map<String, String> param, Map<String, String > heads)
    {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try
        {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null)
            {
                for (String key : param.keySet())
                {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            if(heads != null){
                for(Map.Entry<String, String> entry: heads.entrySet()){
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200)
            {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (response != null)
                {
                    response.close();
                }
                httpclient.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doGet(String url, Map<String,String> haeads)
    {
        return doGet(url, null, haeads);
    }

    public static String doPost(String url, Map<String, String> param)
    {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try
        {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null)
            {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (String key : param.keySet())
                {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "UTF-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                response.close();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    public static String doPost(String url)
    {
        return doPost(url, null);
    }

    public static String doPostJson(String url, String json){
        return doPostJson(url, json, null);
    }

    public static String doPostJson(String url, String json, Map<String, String > heads)
    {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try
        {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            if(heads != null){
                for(Map.Entry<String, String> entry: heads.entrySet()){
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }

            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                response.close();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "支付陈宫");
        map.put("actorId", 102 + "");
        map.put("actorName", "nickanme");
        map.put("containerId", "pay");
        map.put("containerName", "pay");
        map.put("content", "支付成功--测试完截止时间2017-3-4");
        map.put("containerName", "pay");
        map.put("noticeContent", "setNoceContent");



        String url = "http://api3.stockemotion.com/cms/appNotify";
        String result = HttpClientUtil.doPost(url, map);

        JSONObject jsonObject = JsonUtils.TO_JSONObject(result);
        String string = (String) jsonObject.get("type");
        if(string.equals("success"))
        {
            System.out.println(result);
        }
    }
}
