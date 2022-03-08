package com.kkulbae.sellup.src.home;


import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import com.kkulbae.sellup.config.secret.Secret;
import com.kkulbae.sellup.config.BaseException;
import com.kkulbae.sellup.src.home.model.GetCelebRes;
import com.kkulbae.sellup.src.home.model.GetThemeRes;
import com.kkulbae.sellup.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.kkulbae.sellup.config.BaseResponseStatus.*;

@Service
public class HomeProvider {
    private final HomeDao homeDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    public HomeProvider(HomeDao homeDao, JwtService jwtService) {
        this.homeDao = homeDao;
        this.jwtService = jwtService;
    }


    public List<GetCelebRes> getCelebInfo(String word) throws BaseException {

        try {
            List<GetCelebRes> GetCelebRes = homeDao.getCelebBySearch(word);
            return GetCelebRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    private static YouTube youtube;
    private static final long NUMBER_OF_VIDEOS_RETURNED = 10;
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();

    private String youtubeApikey = Secret.YOUTUBE_API_KEY;

    public List<SearchResult> GetNewCelebInfo(String word) throws BaseException {

        try {

            youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("kkulbae").build();

            YouTube.Search.List search = youtube.search().list("id,snippet");
            String apiKey = youtubeApikey;
            search.setKey(apiKey);
            search.setQ(word);
            search.setType("channel");  // video
            search.setFields("items(id/kind,id/channelId,snippet/title,snippet/thumbnails/medium/url,snippet/description)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            SearchListResponse searchResponse = search.execute();

            List<SearchResult> searchResultList = searchResponse.getItems();
            return searchResultList;

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(THIRD_PARTY_ERROR);
        }
    }


    public int checkCelebIdx(int clbIdx) throws BaseException{
        try{
            return homeDao.checkCelebIdx(clbIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public List<GetThemeRes> getThemeInfo(int clbIdx) throws BaseException{
        try{
            if(checkCelebIdx(clbIdx) == 0){
                throw new BaseException(INVALID_CELEB);
            }
            return homeDao.getThemeInfo(clbIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


}