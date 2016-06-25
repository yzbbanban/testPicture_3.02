package com.wb.testpicture;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import com.wb.testpicture.adapter.DetialPictureAdapter;
import com.wb.testpicture.app.PictureApplication;
import com.wb.testpicture.dal.DetialImageDal;
import com.wb.testpicture.dal.PictureDao;
import com.wb.testpicture.entity.DetialPictures;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class SkipActivity extends Activity implements Const {
    private TextView tvTitle;
    private Intent intent;
    private String localPath;
    private String title;
    private GridView gvDetialLocalImage;
    private PictureApplication app;
    private List<DetialPictures> pics;
    private DetialPictureAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_TITLE_GET_SUCCESS:
                    tvTitle.setText(title);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skip);
        initView();

        intent = getIntent();
        localPath = intent.getStringExtra(EXTEA_SKIP_PAGE_PATH);


        Log.i(TAG, "onCreate: " + localPath);


        setText();

        setAdapter();


    }

    private void setAdapter() {

        DetialImageDal dimage = new DetialImageDal(localPath);
        //Log.i(TAG, "run: helle~~~~~11111");
        app = (PictureApplication) getApplication();
        //Log.i(TAG, "run: helle~~~~~22222");
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    // Log.i(TAG, "run: helle~~~~~33333");
                    pics = app.getDetialPictures();
                    // Log.i(TAG, "run: helle~~~~~44444");
                    Log.i(TAG, "run: "+pics.get(0).getPath());
                    adapter = new DetialPictureAdapter(SkipActivity.this, pics);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gvDetialLocalImage.setAdapter(adapter);

                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    private void setText() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(localPath).get();
                    Elements el = doc.getElementsByTag("title");
                    title = el.first().text();
                    handler.sendEmptyMessage(HANDLER_TITLE_GET_SUCCESS);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        gvDetialLocalImage = (GridView) findViewById(R.id.gv_skip_image);

    }
}
