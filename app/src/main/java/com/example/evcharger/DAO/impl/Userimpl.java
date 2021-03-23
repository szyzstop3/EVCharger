package com.example.evcharger.DAO.impl;

import android.util.Log;

import com.example.evcharger.DAO.UserDAO;
import com.example.evcharger.vo.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Userimpl implements UserDAO {
    public static final int SHOW_DATA = 0X123;
    private String detail = "";

//    private Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            if(msg.what == SHOW_DATA)
//            {
//                wView.loadDataWithBaseURL("",detail, "text/html","UTF-8","");
//            }
//        };
//    };

    @Override
    public boolean InsertUser(User user) {
//        new Thread()
//        {
//            public void run()
//            {
                try{
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://8.140.120.98:8080/EVBackEnd-1.0-SNAPSHOT/myservlet");
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("name", user.getName()));
                    params.add(new BasicNameValuePair("pwd", user.getPassword()));
                    params.add(new BasicNameValuePair("ph", user.getPhone()));
                    params.add(new BasicNameValuePair("sex", "" + user.getSex()));
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
                    httpPost.setEntity(entity);
                    HttpResponse httpResponse =  httpClient.execute(httpPost);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        return true;
//                        HttpEntity entity2 = httpResponse.getEntity();
//                        detail = EntityUtils.toString(entity2, "utf-8");
//                        handler.sendEmptyMessage(SHOW_DATA);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
//            };
//        }.start();
        return false;
    }

    @Override
    public boolean LoginUser(User user) {
        boolean r = false;
        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://8.140.120.98:8080/EVBackEnd-1.0-SNAPSHOT/usercf");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", user.getName()));
            params.add(new BasicNameValuePair("pwd", user.getPassword()));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse =  httpClient.execute(httpPost);
            Log.d("mytest",String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            //if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity2 = httpResponse.getEntity();
                InputStream in = entity2.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(in));
                String line = reader.readLine();
                Log.d("mytest","line");
                Log.d("mytest",line);
                if(line.equals("true")){
                    Log.d("mytest","1");
                    r = true;
                }else {
                    Log.d("mytest","2");
                    r = false;
                }
//
//                        detail = EntityUtils.toString(entity2, "utf-8");
//                        handler.sendEmptyMessage(SHOW_DATA);
          //  }
        }catch(Exception e){
            e.printStackTrace();
        }
        Log.d("mytest","r="+r);
        return r;
    }


}
