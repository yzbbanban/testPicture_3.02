package com.wb.testpicture.dal;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.wb.testpicture.Const;
import com.wb.testpicture.entity.DetialPictures;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YZBbanban on 16/6/3.
 */
public class DetialImageDal implements IDao<DetialPictures>,Const{
    private String path2;
    private int width;
    private int height;
    private Thread thread;
    private String skipPagePath;
    private List<DetialPictures> pictures= new ArrayList<DetialPictures>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pictures = (List<DetialPictures>) msg.obj;
            // Log.i(TAG, "getData: pathlallalalalla~~~~ " + pictures.get(0).getPath());
        }
    };

    public DetialImageDal(String skipPagePath) {
        this.skipPagePath=skipPagePath;
        thread = new InnerThread();
        thread.start();
    }

    public DetialImageDal() {

    }


    @Override
    public List<DetialPictures> getData() {
        return pictures;
    }

    class InnerThread extends Thread {
        @Override
        public void run() {
            try {
                String webPath = skipPagePath;

                Document doc = Jsoup.connect(webPath).get();

                Elements e = doc.getElementsByClass("rgg-imagegrid ");
                for (int i = 0; i < e.size(); i++) {
                    //<img width="760" height="500"
                    Elements a = e.get(i).getElementsByTag("a");

                    String width2 = a.first().getElementsByTag("img").attr("width");
                    String height2 = a.first().getElementsByTag("img").attr("height");
                    path2 = a.first().getElementsByTag("img").attr("src");
                    width = Integer.parseInt(width2);
                    height = Integer.parseInt(height2);
                    DetialPictures pic = new DetialPictures();
                    pic.setPath(path2);
                    pic.setHeight(height);
                    pic.setWidth(width);
                    pictures.add(pic);
                    Log.i(TAG, "getData: path:" + i + ";" + pic.getPath());
                    // Log.i(TAG, "getData: skippath:"+ i+";"+pic.getSkipPagePath());
                    //Log.i(TAG, "getData: title:" + i + ";" + pic.getSkipPagePath());

                }
                Message m = new Message();
                m.obj = pictures;
                handler.sendMessage(m);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
