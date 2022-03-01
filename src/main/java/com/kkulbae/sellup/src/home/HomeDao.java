package com.kkulbae.sellup.src.home;

import com.kkulbae.sellup.src.home.model.GetSellupRes;
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

    public List<GetSellupRes> getSellupBySearch(String word){
        String getSellupInfoBySearchQuery = "select slpIdx, name, description, job, profileImage\n" +
                                            "from sellups\n"+
                                            "where name IN (?)\n"+
                                            "and isDeleted = 'N'";
        String getSellupInfoBySearchParams = word;
        return this.jdbcTemplate.query(getSellupInfoBySearchQuery,
                (rs, rowNum) -> new GetSellupRes(
                        rs.getInt("slpIdx"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("job"),
                        rs.getString("profileImage")),
                getSellupInfoBySearchParams);

    }
}
