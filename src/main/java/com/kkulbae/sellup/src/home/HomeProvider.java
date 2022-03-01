package com.kkulbae.sellup.src.home;

import com.kkulbae.sellup.config.BaseException;
import com.kkulbae.sellup.src.home.model.GetSellupRes;

import com.kkulbae.sellup.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<GetSellupRes> getSellupInfo(String word) throws BaseException {

        try{
            List<GetSellupRes> GetSellupRes = homeDao.getSellupBySearch(word);
            return GetSellupRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
