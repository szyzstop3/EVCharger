package com.example.evcharger.DAO.impl;

import android.util.Log;

import com.example.evcharger.DAO.ToolDAO;
import com.example.evcharger.vo.Charger;
import com.example.evcharger.vo.Comment;

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

public class Toolimpl implements ToolDAO {
    @Override
    public boolean validCharger(Charger charger) {
        boolean r = false;
        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://8.140.120.98:8080/EVBackEnd-1.0-SNAPSHOT/validcharger");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("chargerid", ""+charger.getChargerid()));
            params.add(new BasicNameValuePair("csa", charger.getCSA()));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse =  httpClient.execute(httpPost);
            Log.d("validCharger",String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            //if (httpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity2 = httpResponse.getEntity();
            InputStream in = entity2.getContent();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            String line = reader.readLine();
            Log.d("validCharger","line");
            Log.d("validCharger",line);
            if(line.equals("true")){
                Log.d("validCharger","1");
                r = true;
            }else {
                Log.d("validCharger","2");
                r = false;
            }
//
//                        detail = EntityUtils.toString(entity2, "utf-8");
//                        handler.sendEmptyMessage(SHOW_DATA);
            //  }
        }catch(Exception e){
            e.printStackTrace();
        }
        Log.d("validCharger","r="+r);
        return r;
    }

    @Override
    public String getCharger(Charger charger) {
        String r = null;
        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://8.140.120.98:8080/EVBackEnd-1.0-SNAPSHOT/getcharger");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("chargerid", ""+charger.getChargerid()));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse =  httpClient.execute(httpPost);
            Log.d("getCharger",String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            //if (httpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity2 = httpResponse.getEntity();
            InputStream in = entity2.getContent();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            r = reader.readLine();
        }catch(Exception e){
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public void addComment(Comment comment) {
        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://8.140.120.98:8080/EVBackEnd-1.0-SNAPSHOT/addcoment");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("comment", comment.getComment()));
            params.add(new BasicNameValuePair("chargerid", ""+comment.getChargerid()));
            params.add(new BasicNameValuePair("userid", ""+comment.getUserid()));
            params.add(new BasicNameValuePair("pay", ""+comment.getPay()));
            params.add(new BasicNameValuePair("stars", ""+comment.getStars()));
            params.add(new BasicNameValuePair("reduction", ""+comment.getReduction()));
            params.add(new BasicNameValuePair("payoption", comment.getPayoption()));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse =  httpClient.execute(httpPost);
            Log.d("validCharger",String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            //if (httpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity2 = httpResponse.getEntity();
            InputStream in = entity2.getContent();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void registcharger(Charger charger) {
        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://8.140.120.98:8080/EVBackEnd-1.0-SNAPSHOT/regcharger");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("chargername", charger.getChargerName()));
            params.add(new BasicNameValuePair("brand", ""+charger.getBrand()));
            params.add(new BasicNameValuePair("latitude", ""+charger.getLatitude()));
            params.add(new BasicNameValuePair("longitude", ""+charger.getLongitude()));
            params.add(new BasicNameValuePair("location", ""+charger.getLocation()));
            params.add(new BasicNameValuePair("state", ""+charger.getState()));
            params.add(new BasicNameValuePair("userid", ""+charger.getUserid()));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse =  httpClient.execute(httpPost);
            Log.d("registcharger",String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            //if (httpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity2 = httpResponse.getEntity();
            InputStream in = entity2.getContent();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
