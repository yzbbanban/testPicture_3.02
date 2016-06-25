package com.wb.testpicture.app;


import android.app.Application;


/**
 * Created by YZBbanban on 16/5/31.
 */
import java.util.*;

import com.wb.testpicture.dal.IDao;
import com.wb.testpicture.dal.PictureDaoFactury;
import com.wb.testpicture.entity.DetialPictures;
import com.wb.testpicture.entity.Pictures;

public class PictureApplication extends Application {
    List<Pictures> pictures = new ArrayList<Pictures>();
    List<DetialPictures> detialPictures = new ArrayList<DetialPictures>();

    @Override
    public void onCreate() {
        IDao<Pictures> iDao = PictureDaoFactury.newPicture();
        IDao<DetialPictures> iDao2 = PictureDaoFactury.newDetailPicture();
        pictures = iDao.getData();
        detialPictures = iDao2.getData();


    }

    public List<Pictures> getPictures() {
        return pictures;
    }

    public List<DetialPictures> getDetialPictures() {
        return detialPictures;
    }

}
