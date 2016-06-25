package com.wb.testpicture.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;

import com.wb.testpicture.Const;
import com.wb.testpicture.R;
import com.wb.testpicture.SkipActivity;
import com.wb.testpicture.adapter.PictureAdapter;
import com.wb.testpicture.app.PictureApplication;
import com.wb.testpicture.dal.PictureDao;
import com.wb.testpicture.entity.Pictures;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener, Const {
    private ImageView ivImage;
    private TextView tvPath1;
    private TextView tvPath2;
    private GridView gvLocalImage;
    private PictureApplication app;
    private List<Pictures> pics;
    private PictureAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setAdapter();
        setListener();

    }

    private void setListener() {
        gvLocalImage.setOnItemClickListener(this);
    }

    private void setAdapter() {

        PictureDao dao = new PictureDao();
        //Log.i(TAG, "run: helle~~~~~11111");
        app = (PictureApplication) getApplication();
        //Log.i(TAG, "run: helle~~~~~22222");
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(4000);
                    // Log.i(TAG, "run: helle~~~~~33333");
                    pics = app.getPictures();
                    // Log.i(TAG, "run: helle~~~~~44444");
                    //Log.i(TAG, "run: "+pics.get(0).getPath());


                    adapter = new PictureAdapter(MainActivity.this, pics);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gvLocalImage.setAdapter(adapter);

                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    private void initView() {

        tvPath1 = (TextView) findViewById(R.id.tv_path1);
        gvLocalImage = (GridView) findViewById(R.id.gv_image);


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, SkipActivity.class);
        String path = pics.get(position).getSkipPagePath();
        intent.putExtra(EXTEA_SKIP_PAGE_PATH, path);

        startActivity(intent);


    }
}