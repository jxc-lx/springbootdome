package com.example.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class TestPost {
    private String URL;
    private ResourceBundle bundle;
    private CookieStore allcookieStore;
    //@BeforeTest
    public void getDemo(){
        bundle= ResourceBundle.getBundle("application", Locale.CHINA);
        URL=bundle.getString("test.url");

    }
    @Test
    public void getTest() throws IOException {
        String result;
        HttpGet get = new HttpGet(URL);
        DefaultHttpClient client = new DefaultHttpClient();
        //CloseableHttpClient client = HttpClientBuilder.create().build();

        HttpResponse response = client.execute(get);

        result=EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        CookieStore store = client.getCookieStore();
        List <Cookie> cookieList = store.getCookies();
        //Map cookieMap =
        for (Cookie cookie:cookieList) {
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("name===="+name+"value======"+value);
        }
    }
    @Test
    public void getCookie() throws IOException {
        String result;
        HttpGet get = new HttpGet(URL);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(get);

        result=EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
    }
    @Test
    public void ioTset() throws IOException {
        InputStream in = new FileInputStream(new File("D:\\ideaWorkspaces\\AutoTest\\httpClientDome\\src\\main\\resources\\application.properties"));

        byte [] arr = new byte[1024];
        int len ;
        
        StringBuffer buffer = new StringBuffer();
        while ((len = in.read(arr)) != -1){

            System.out.println(new String(arr));


        }
        in.close();
    }
    @Test
    public void testCookie() throws IOException {
        CookieStore cookieStore = new BasicCookieStore();
        HttpClientContext clientContext = new HttpClientContext().create();
        //List<Header> headerList = Lists.newArrayList();
        //  List <Header> list =new ArrayList<Header>();
        //HttpGet get = new HttpGet(URL);

        HttpUriRequest request = RequestBuilder.get().setUri(URL).build();
        HttpClient httpClient = HttpClients.custom().build();

        httpClient.execute(request,clientContext);
        allcookieStore=clientContext.getCookieStore();
        cookieStore= clientContext.getCookieStore();
        // HttpClient newhttpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        List <Cookie> cookieList= cookieStore.getCookies();
        for (Cookie c:cookieList
                ) {
            System.out.println(c.getName());
            System.out.println(c.getValue());
        }



    }
    @Test(dependsOnMethods = {"testCookie"})
    public void testPostCookie() throws IOException {
        HttpClientContext clientContext = new HttpClientContext().create();
        String uri="http://localhost:8888/post/with/cookies";
        //添加参数
        JSONObject para = new JSONObject();
        para.put("name","huhansan");
        para.put("age","18");
        StringEntity entity = new StringEntity(para.toString(),"utf-8");
        //创建request ,包括请求方式（post),URL，传入参数（body）
        HttpUriRequest request = RequestBuilder.post().setUri(uri).setEntity(entity).build();
        //创建client 并添加cookies
        HttpClient client = HttpClients.custom().setDefaultCookieStore(allcookieStore).build();
        //发送请求
        HttpResponse response= client.execute(request,clientContext);
        //获取返回信息
        String result=EntityUtils.toString(response.getEntity());
        //System.out.println(result);
        JSONObject jsonObject=new JSONObject(result);
        System.out.println(jsonObject.get("huhansan"));
    }
    public  void testBody(){
        HttpClientContext clientContext = new HttpClientContext().create();
        HttpUriRequest request = RequestBuilder.post(URL).build();
        StringEntity entity = new StringEntity("","UTF-8");
    }
    @Test
    public void testInsertUser() throws IOException {
        HttpClientContext clientContext = new HttpClientContext().create();
        String uri="http://localhost:8080/user/insertUserTest";
        //添加参数
        JSONObject para = new JSONObject();
        para.put("Name","huhansan");
        para.put("Email","11111@qq.com");
        para.put("Id","2");
        para.put("Username","zhangsan");
        para.put("Password","12121212");
        StringEntity entity = new StringEntity(para.toString(),"utf-8");
        //创建request ,包括请求方式（post),URL，传入参数（body）
        HttpUriRequest request = RequestBuilder.post().setUri(uri).setEntity(entity).build();
        //创建client 并添加cookies
        HttpClient client = HttpClients.custom().setDefaultCookieStore(allcookieStore).build();
        //发送请求
        HttpResponse response= client.execute(request,clientContext);
        System.out.println("---------------");
    }
}
