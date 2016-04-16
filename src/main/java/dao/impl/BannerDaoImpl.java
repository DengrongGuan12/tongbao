package dao.impl;

import dao.BannerDao;
import model.Banner;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cg on 2016/4/16.
 */
@Repository
public class BannerDaoImpl extends BaseDaoImpl implements BannerDao {
    @Override
    public List<Banner> getAllBanners() {
        return super.getAllList(Banner.class);
    }
}
