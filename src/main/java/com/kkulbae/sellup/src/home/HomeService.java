package com.kkulbae.sellup.src.home;

import com.kkulbae.sellup.config.BaseException;
import com.kkulbae.sellup.src.home.model.*;
import com.kkulbae.sellup.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.kkulbae.sellup.config.BaseResponseStatus.DATABASE_ERROR;
import static com.kkulbae.sellup.config.BaseResponseStatus.INVALID_CELEB;

@Service
public class HomeService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final HomeDao homeDao;
    private final HomeProvider homeProvider;
    private final JwtService jwtService;



    @Autowired
    public HomeService(HomeDao homeDao, HomeProvider homeProvider, JwtService jwtService) {
        this.homeDao = homeDao;
        this.homeProvider = homeProvider;
        this.jwtService = jwtService;

    }

    public void createTheme(int clbIdx, PostThemeReq postThemeReq) throws BaseException {
        try{
            if(homeProvider.checkCelebIdx(clbIdx) == 0){
                throw new BaseException(INVALID_CELEB);
            }
            homeDao.createTheme(clbIdx, postThemeReq);

        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public void createPlace(int clbIdx, int userIdx, PostPlaceReq postPlaceReq) throws BaseException {
        try{
            if(homeProvider.checkCelebIdx(clbIdx) == 0){
                throw new BaseException(INVALID_CELEB);
            }
            homeDao.createPlace(clbIdx, userIdx, postPlaceReq);

        } catch(Exception exception){
            // System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public void createCeleb(PostCelebReq postCelebReq) throws BaseException {
        try{

            homeDao.createCeleb(postCelebReq);

        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

