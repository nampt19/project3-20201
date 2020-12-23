package com.example.project3_2.helper;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveFileToStorage {
    public String saveToInternalStorage(Bitmap bitmapImage,Context context,String url_image) {
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imgUsers", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, url_image);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
    public Bitmap loadImageFromStorage(String url_image) {
        try {
            File f = new File("/data/user/0/com.example.project3_2/app_imgUsers", url_image);
            Log.v("link_image_afterGet",f.getAbsolutePath());
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
           // imageView2.setImageBitmap(b);
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void deleteFileFromStorage(String url_image){
        try {
            File f = new File("/data/user/0/com.example.project3_2/app_imgUsers", url_image);
            f.delete();
            // imageView2.setImageBitmap(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
