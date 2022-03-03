package com.kkulbae.sellup.src.home;


import com.kkulbae.sellup.config.BaseException;
import com.kkulbae.sellup.config.BaseResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.kkulbae.sellup.src.home.model.GetSellupRes;
import com.kkulbae.sellup.src.user.UserProvider;
import com.kkulbae.sellup.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.kkulbae.sellup.config.BaseResponseStatus.*;


@RestController
@RequestMapping("/sellup")
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
    public BaseResponse<List<GetSellupRes>> getSellupInfo(@RequestParam String word) {
        if(word == null){
            return new BaseResponse<>(REQUEST_ERROR);
        }
        try{
            int userIdx = jwtService.getUserIdx();
            if(userProvider.checkUser(userIdx) == 0){
                return new BaseResponse<>(DELETED_USER);
            }

            List<GetSellupRes> GetSellupRes = homeProvider.getSellupInfo(word);
            if(GetSellupRes.isEmpty()){
                return new BaseResponse<>(RESPONSE_ERROR);
            } else {
                return new BaseResponse<>(GetSellupRes);
            }

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /** 셀럽 조회 (유튜브/인스타) API */
    @ResponseBody
    @GetMapping("/search/social")
    public BaseResponse<Mono<SearchListResponse>> getSellupInfoBySocial(@RequestParam String word) {
        if(word == null){
            return new BaseResponse<>(REQUEST_ERROR);
        }
        try{
            int userIdx = jwtService.getUserIdx();
            if(userProvider.checkUser(userIdx) == 0){
                return new BaseResponse<>(DELETED_USER);
            }

            Mono<SearchListResponse> SearchListResponse = homeProvider.GetNewSellupInfo(word);

            return new BaseResponse<>(SearchListResponse);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
