package com.wb.testpicture.dal;

import com.wb.testpicture.entity.DetialPictures;
import com.wb.testpicture.entity.Pictures;


/**
 * Created by YZBbanban on 16/5/31.
 */
public class PictureDaoFactury {
    private PictureDaoFactury() {
        super();
    }

    public static IDao<Pictures> newPicture() {
        return new PictureDao();
    }

    public static IDao<DetialPictures> newDetailPicture() {
        return new DetialImageDal();
    }


}
