package com.varshajayadev.piks;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by varshajayadev on 2/24/17.
 */

public class ImageResponse {

    @SerializedName("data")
    private ArrayList<Image> image_List;

    public ImageResponse(){
        image_List = new ArrayList<>();
    }

    public ArrayList<Image> getImage_List() {
        return image_List;
    }

    public void setImage_List(ArrayList<Image> image_List) {
        this.image_List = image_List;
    }


    public class Image{
        @SerializedName("id")
        String image_ID;
        @SerializedName("title")
        String image_Title;
        @SerializedName("points")
        String points;
        @SerializedName("is_album")
        boolean is_Album;
        @SerializedName("ups")
        int ups;
        @SerializedName("downs")
        int downs;
        @SerializedName("cover")
        String cover;
        @SerializedName("mp4")
        String mp4_Link;
        @SerializedName("cover_height")
        int cover_height;

        public Image(){
            image_ID = "";
            image_Title = "";
            points = "";
            is_Album = false;
            ups = -1;
            downs = -1;
            cover = "";
            mp4_Link = "";
            cover_height = 0;
        }

        public String getImage_ID() {
            return image_ID;
        }

        public void setImage_ID(String image_ID) {
            this.image_ID = image_ID;
        }

        public String getImage_Title() {
            return image_Title;
        }

        public void setImage_Title(String image_Title) {
            this.image_Title = image_Title;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public boolean is_Album() {
            return is_Album;
        }

        public void setIs_Album(boolean is_Album) {
            this.is_Album = is_Album;
        }

        public int getUps() {
            return ups;
        }

        public void setUps(int ups) {
            this.ups = ups;
        }

        public int getDowns() {
            return downs;
        }

        public void setDowns(int downs) {
            this.downs = downs;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getMp4_Link() {
            return mp4_Link;
        }

        public void setMp4_Link(String mp4_Link) {
            this.mp4_Link = mp4_Link;
        }

        public int getCover_height() {
            return cover_height;
        }

        public void setCover_height(int cover_height) {
            this.cover_height = cover_height;
        }

    }
}
