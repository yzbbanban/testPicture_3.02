package com.wb.testpicture.dal;


import android.os.Handler;
import android.os.Message;
import android.util.Log;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.wb.testpicture.Const;
import com.wb.testpicture.entity.Pictures;

import java.io.IOException;
import java.util.*;

/**
 * Created by YZBbanban on 16/5/31.
 */

public class PictureDao implements IDao,Const{
    private String path2;
    private int width;
    private int height;
    private Thread thread;
    private String skipPagePath;
    private List<Pictures> pictures= new ArrayList<Pictures>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pictures = (List<Pictures>) msg.obj;
           // Log.i(TAG, "getData: pathlallalalalla~~~~ " + pictures.get(0).getPath());
        }
    };

    public PictureDao() {
        thread = new InnerThread();
        thread.start();
    }

    @Override
    public List<Pictures> getData() {


        return pictures;
    }

    class InnerThread extends Thread {

        @Override
        public void run() {
            try {
                String webPath = "http://m.xxxiao.com";

                Document doc = Jsoup.connect(webPath).get();
                Elements e = doc.getElementsByClass("post-thumb");
                for (int i = 0; i < e.size(); i++) {
                    //<img width="760" height="500"
                    Elements a = e.get(i).getElementsByTag("a");
                    skipPagePath=a.first().attr("href");

                    String width2 = a.first().getElementsByTag("img").attr("width");
                    String height2 = a.first().getElementsByTag("img").attr("height");
                    path2 = a.first().getElementsByTag("img").attr("src");
                    width = Integer.parseInt(width2);
                    height = Integer.parseInt(height2);
                    Pictures pic = new Pictures();
                    pic.setSkipPagePath(skipPagePath);
                    pic.setPath(path2);
                    pic.setHeight(height);
                    pic.setWidth(width);
                    pictures.add(pic);
                   // Log.i(TAG, "getData: path:"+ i+";"+pic.getPath());
                   // Log.i(TAG, "getData: skippath:"+ i+";"+pic.getSkipPagePath());

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



