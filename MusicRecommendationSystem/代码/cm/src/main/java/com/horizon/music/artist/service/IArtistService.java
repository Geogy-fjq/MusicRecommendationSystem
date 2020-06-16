package com.horizon.music.artist.service;

import com.horizon.music.artist.vo.Artist;
import com.horizon.music.artist.vo.Music;
import com.horizon.music.artist.vo.UserArtist;

import java.util.List;

public interface IArtistService {

    List<Music> getAllMusicList();

    void addUserArtist(UserArtist userArtist);

    List<Artist> getTheArtistList(String userID);
}
