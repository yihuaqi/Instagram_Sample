package com.example.yihuaqi.instagram_sample;

/**
 * Created by yihuaqi on 2014/12/10.
 */
public class ImageURL {
    private String thumbnailurl;
    private String low_imageurl;
    private String imageurl;

    public ImageURL(){

    }
    public ImageURL(String thumbnailurl,String low_imageurl,String imageurl){
        this.thumbnailurl = thumbnailurl;
        this.low_imageurl = low_imageurl;
        this.imageurl = imageurl;
    }

    public String getThumbnailUrl(){
        return thumbnailurl;
    }
    public String getImageUrl(){
        return imageurl;
    }
    public String getLow_imageurl(){
        return low_imageurl;
    }

}
