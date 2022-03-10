package com.kkulbae.sellup.src.home;

import com.kkulbae.sellup.src.home.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class HomeDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetCelebRes> getCelebBySearch(String word){
        String getCelebInfoBySearchQuery = "select clbIdx, name, description, job, profileImage\n" +
                                            "from celebs\n"+
                                            "where name IN (?)\n"+
                                            "and isDeleted = 'N'";
        String getCelebInfoBySearchParams = word;
        return this.jdbcTemplate.query(getCelebInfoBySearchQuery,
                (rs, rowNum) -> new GetCelebRes(
                        rs.getInt("clbIdx"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("job"),
                        rs.getString("profileImage")),
                getCelebInfoBySearchParams);

    }

    public int checkCelebIdx(int clbIdx){
        String checkExistCelebQuery = "select exists(select clbIdx from celebs where clbIdx = ? and isDeleted='N')";
        int checkExistCelebParams = clbIdx;
        return this.jdbcTemplate.queryForObject(checkExistCelebQuery,
                int.class,
                checkExistCelebParams);
    }

    public void createTheme(int clbIdx, PostThemeReq postThemeReq){
        String createThemeQuery = "insert into themes(clbIdx, title, themeUrl) VALUES (?,?,?)";
        Object[] createThemeParams = new Object[]{clbIdx, postThemeReq.getTitle(), postThemeReq.getThemeUrl()};
        this.jdbcTemplate.update(createThemeQuery, createThemeParams );
    }

    public List<GetThemeRes> getThemeInfo(int clbIdx){
        String getThemeInfoQuery = "select thmIdx, name, title, themeUrl\n" +
                                    "from themes t\n" +
                                    "inner join celebs cs on t.clbIdx = cs.clbIdx\n" +
                                    "where t.clbIdx = ? and t.isDeleted = 'N'\n" +
                                    "order by t.createdAt DESC;";
        int getThemeInfoParams = clbIdx;
        return this.jdbcTemplate.query(getThemeInfoQuery,
                (rs, rowNum) -> new GetThemeRes(
                        rs.getInt("thmIdx"),
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getString("themeUrl")),
                getThemeInfoParams);

    }


    public void createPlace(int clbIdx, int userIdx, PostPlaceReq postPlaceReq){
        String createPlaceQuery = "insert into places(clbIdx, userIdx, address, name, rating, latitude, longitude) VALUES (?,?,?,?,?,?,?)";
        Object[] createPlaceParams = new Object[]{clbIdx, userIdx, postPlaceReq.getAddress(), postPlaceReq.getName(), postPlaceReq.getRating(),postPlaceReq.getLat(), postPlaceReq.getLng()};
        this.jdbcTemplate.update(createPlaceQuery, createPlaceParams);
    }

    public List<GetPlaceListRes> getPlaceInfoList(int clbIdx){
        String getPlaceInfoQuery = "select plcIdx, address, name, rating, latitude, longitude\n" +
                "from places t\n" +
                "where clbIdx = ? and isDeleted = 'N'\n" +
                "order by createdAt DESC;";
        int getPlaceInfoParams = clbIdx;
        return this.jdbcTemplate.query(getPlaceInfoQuery,
                (rs, rowNum) -> new GetPlaceListRes(
                        rs.getInt("plcIdx"),
                        rs.getString("address"),
                        rs.getString("name"),
                        rs.getFloat("rating"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude")),
                getPlaceInfoParams);

    }

    public void createCeleb(PostCelebReq postCelebReq){
        String createCelebQuery = "insert into celebs(name, description, job, profileImage) VALUES (?,?,?,?)";
        Object[] createCelebParams = new Object[]{postCelebReq.getName(), postCelebReq.getDescription(), postCelebReq.getJob(),postCelebReq.getProfileImage() };
        this.jdbcTemplate.update(createCelebQuery, createCelebParams);
    }
}
