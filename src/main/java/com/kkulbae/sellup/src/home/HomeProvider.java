package com.kkulbae.sellup.src.home;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.kkulbae.sellup.config.BaseException;
import com.kkulbae.sellup.src.home.model.GetSellupRes;
import com.kkulbae.sellup.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

import static com.kkulbae.sellup.config.BaseResponseStatus.DATABASE_ERROR;

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


    public List<GetSellupRes> getSellupInfo(String word) throws BaseException {

        try{
            List<GetSellupRes> GetSellupRes = homeDao.getSellupBySearch(word);
            return GetSellupRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    private static YouTube youtube;
    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();

    private String youtubeApikey = "";

    public Mono<SearchListResponse> GetNewSellupInfo(String word) throws BaseException {

        return Mono.create(sink -> {

            try {

                youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                    }
                }).setApplicationName("kkulbae").build();

                YouTube.Search.List search = youtube.search().list("id,snippet");
                String apiKey = youtubeApikey;
                search.setKey(apiKey);
                search.setQ(word);
                search.setType("video");
                search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/medium/url,snippet/description)");
                search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

                SearchListResponse searchResponse = search.execute();
                sink.success(searchResponse);

                // sink.success();
            } catch (Exception exception) {
                exception.printStackTrace();
               // throw new BaseException(DATABASE_ERROR);
            }
        });
    }

}
