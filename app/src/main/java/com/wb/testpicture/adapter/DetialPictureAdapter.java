package com.wb.testpicture.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wb.testpicture.R;
import com.wb.testpicture.entity.DetialPictures;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by YZBbanban on 16/5/31.
 */
public class DetialPictureAdapter extends BaseAdapter<DetialPictures> {
    private DetialPictures picture;
    private URL url;
    private ImageView ivImage;
    private Handler handler;
    private Thread thread;
    private ViewHolder holder;
    private static final String TAG="supergirl";
    private static final int MAX_SIZE=250;
    public DetialPictureAdapter(Context context, List<DetialPictures> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        picture=getData().get(position);


        if (convertView == null) {
            convertView=getLayoutInflater().inflate(R.layout.detial_picture_item,null);
            holder=new ViewHolder();
            holder.ivImage= (ImageView) convertView.findViewById(R.id.iv_detial_local_picture);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
            Log.i(TAG, "handleMessage: "+1111111);
            
            thread=new ImageThread();
            thread.start();


            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 1:
                        	
                            Bitmap bm2 = (Bitmap) msg.obj;
                            Log.i(TAG, "handleMessage: "+bm2);

                            holder.ivImage.setImageBitmap(bm2);

                            break;

                    }


                }
            };

        return convertView;
    }
    class ViewHolder{
        ImageView ivImage;
    }


    class ImageThread extends Thread {


        @Override
        public void run() {
            try {
                url = new URL(picture.getPath());
               // Log.i(TAG, "adapter222   "+picture.getPath());
                URLConnection connection = url.openConnection();

                connection.connect();

                InputStream is = connection.getInputStream();
                
                int rate = 1;
    			if (picture.getWidth() > MAX_SIZE && picture.getHeight() > MAX_SIZE) {
    			if (picture.getWidth() < picture.getHeight()) {
    					rate = picture.getHeight() / MAX_SIZE;
    				} else {
    					rate = picture.getWidth() / MAX_SIZE;
    				}
    			}

    			// ����ͼƬ���õ�Bitmap
    			Options opts = new Options();
    			opts.inSampleSize = rate;
                
                Bitmap bm = BitmapFactory.decodeStream(is,null, opts);

                Message msg = Message.obtain();
                msg.obj = bm;
                msg.what = 1;
                handler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

}
