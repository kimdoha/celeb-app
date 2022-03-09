package com.kkulbae.sellup.src.home;


import com.google.api.services.youtube.model.SearchResult;
import com.kkulbae.sellup.config.BaseException;
import com.kkulbae.sellup.config.BaseResponse;
import com.kkulbae.sellup.src.home.model.GetCelebRes;
import com.kkulbae.sellup.src.home.model.GetPlaceInfoRes;
import com.kkulbae.sellup.src.home.model.GetThemeRes;
import com.kkulbae.sellup.src.home.model.PostThemeReq;
import com.kkulbae.sellup.src.user.UserProvider;
import com.kkulbae.sellup.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kkulbae.sellup.config.BaseResponseStatus.*;


@RestController
@RequestMapping("/celeb")
public class HomeController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final HomeProvider homeProvider;
    @Autowired
    private final HomeService homeService;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final UserProvider userProvider;


    public HomeController(HomeProvider homeProvider, HomeService homeService, UserProvider userProvider, JwtService jwtService){
        this.homeProvider = homeProvider;
        this.userProvider = userProvider;
        this.homeService = homeService;
        this.jwtService = jwtService;
    }



    /** 셀럽 조회 API */
    @ResponseBody
    @GetMapping("/search")
    public BaseResponse<List<GetCelebRes>> getCelebInfo(@RequestParam String word) {
        if(word == null){
            return new BaseResponse<>(REQUEST_ERROR);
        }
        try{
            int userIdx = jwtService.getUserIdx();
            if(userProvider.checkUser(userIdx) == 0){
                return new BaseResponse<>(DELETED_USER);
            }

            List<GetCelebRes> GetCelebRes = homeProvider.getCelebInfo(word);
            if(GetCelebRes.isEmpty()){
                return new BaseResponse<>(RESPONSE_ERROR);
            } else {
                return new BaseResponse<>(GetCelebRes);
            }

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /** 셀럽 조회 (유튜브/인스타) API */
    @ResponseBody
    @GetMapping("/social-search")
    public BaseResponse<List<SearchResult>> getCelebInfoBySocial(@RequestParam String word) {
        if(word == null){
            return new BaseResponse<>(REQUEST_ERROR);
        }
        try{
            int userIdx = jwtService.getUserIdx();
            if(userProvider.checkUser(userIdx) == 0){
                return new BaseResponse<>(DELETED_USER);
            }

            List<SearchResult> SearchListResponse = homeProvider.GetNewCelebInfo(word);

            return new BaseResponse<>(SearchListResponse);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /** 셀럽별 테마 등록 API */
    @ResponseBody
    @PostMapping("/{clbIdx}/theme")
    public BaseResponse<String> createTheme(@PathVariable("clbIdx") int clbIdx, @RequestBody PostThemeReq theme){
        if(theme.getTitle() == null){
            return new BaseResponse<>(REQUEST_ERROR);
        }

        try{
            int userIdx = jwtService.getUserIdx();
            if(userProvider.checkUser(userIdx) == 0){
                return new BaseResponse<>(DELETED_USER);
            }

            PostThemeReq postThemeReq = new PostThemeReq(theme.getTitle(), theme.getThemeUrl());
            homeService.createTheme(clbIdx, postThemeReq);

            return new BaseResponse<>("");
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /** 셀럽 테마 조회 API */
    @ResponseBody
    @GetMapping("/{clbIdx}/theme")
    public BaseResponse<List<GetThemeRes>> getThemeInfo(@PathVariable("clbIdx") int clbIdx) {
        try{
            int userIdx = jwtService.getUserIdx();
            if(userProvider.checkUser(userIdx) == 0){
                return new BaseResponse<>(DELETED_USER);
            }

            List<GetThemeRes> GetThemeRes = homeProvider.getThemeInfo(clbIdx);
            return new BaseResponse<>(GetThemeRes);


        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /** 주소 검색 with Places Search API  */
    @ResponseBody
    @GetMapping("/place-search")
    public BaseResponse<GetPlaceInfoRes> getPlaceInfoBySearch(@RequestParam String input){
        try {
            GetPlaceInfoRes getPlaceInfoRes = homeProvider.getPlaceInfoBySearch(input);

            return new BaseResponse<>(getPlaceInfoRes);
        } catch(BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /** 핫플 등록 with Places API(Place search / details) + Geocoding API  */
}
