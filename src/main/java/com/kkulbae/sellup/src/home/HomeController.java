package com.kkulbae.sellup.src.home;

import com.kkulbae.sellup.config.BaseException;
import com.kkulbae.sellup.config.BaseResponse;

import com.kkulbae.sellup.src.home.model.*;
import com.kkulbae.sellup.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    public HomeController(HomeProvider homeProvider, HomeService homeService, JwtService jwtService){
        this.homeProvider = homeProvider;
        this.homeService = homeService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("/search")
    public BaseResponse<List<GetSellupRes>> getSellupInfo(@RequestParam String word) {
        if(word == null){
            return new BaseResponse<>(REQUEST_ERROR);
        }
        try{
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
}
