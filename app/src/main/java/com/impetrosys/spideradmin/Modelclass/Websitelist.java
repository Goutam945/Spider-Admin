package com.impetrosys.spideradmin.Modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Websitelist {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("minrefil")
    @Expose
    private String minrefil;
    @SerializedName("minmaintainbal")
    @Expose
    private String minmaintainbal;
    @SerializedName("minwithdraw")
    @Expose
    private String minwithdraw;
    @SerializedName("maxwithdraw")
    @Expose
    private String maxwithdraw;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("games")
    @Expose
    private List<Game> games = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMinrefil() {
        return minrefil;
    }

    public void setMinrefil(String minrefil) {
        this.minrefil = minrefil;
    }

    public String getMinmaintainbal() {
        return minmaintainbal;
    }

    public void setMinmaintainbal(String minmaintainbal) {
        this.minmaintainbal = minmaintainbal;
    }

    public String getMinwithdraw() {
        return minwithdraw;
    }

    public void setMinwithdraw(String minwithdraw) {
        this.minwithdraw = minwithdraw;
    }

    public String getMaxwithdraw() {
        return maxwithdraw;
    }

    public void setMaxwithdraw(String maxwithdraw) {
        this.maxwithdraw = maxwithdraw;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public static class Game {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("status")
        @Expose
        private Integer status;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

    }

}

