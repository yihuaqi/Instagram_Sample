package com.example.yihuaqi.instagram_sample;

/**
 * Created by yihuaqi on 2014/12/10.
 */
public class ApplicationData {
    public final static String AUTHURL = "https://api.instagram.com/oauth/authorize/";
    public final static String TOKENURL = "https://api.instagram.com/oauth/access_token";
    public final static String APIURL = "https://api.instagram.com/v1";
    public final static String CALLBACKURL = "https://bitbucket.org/yihuaqi";
    public final static String CLIENT_ID = "41c0a804b79a44afb31088861644f66f";
    public final static String CLIENT_SECRET = "dfe855a382474580abbd2ebc1a1699ad";

    public static String getAuthURLString(){
        return AUTHURL+"?client_id="+CLIENT_ID+"&redirect_url="+CALLBACKURL+"&response_type=code&display=touch&scope=likes+comments+relationships";
    }

    public static String getTokenURLString(){
        return TOKENURL+"?client_id="+CLIENT_ID+"&client_secret="+CLIENT_SECRET+"&redirect_uri="+CALLBACKURL+"&grant_type=authorization_code";
    }
}
