package com.horizon.music.artist.vo;

/**
 * Created by taos on 2017/7/3.
 */
public class Music {

    private Integer id;

    private String musicName;

    private String MusicLength;

    private Integer artistId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicLength() {
        return MusicLength;
    }

    public void setMusicLength(String musicLength) {
        MusicLength = musicLength;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    private String artistName;

    private String imgPath;

    private String musicPath;
}
