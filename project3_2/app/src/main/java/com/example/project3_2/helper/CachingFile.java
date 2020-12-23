package com.example.project3_2.helper;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class CachingFile {
    /**
     * Lấy toàn bộ file cache
     */
    public void loadAllCache(Context context)
    {
        File pathCacheDir = context.getCacheDir();
        File []listCache= pathCacheDir.listFiles();
        for(File f :listCache)
        {
            //process f here
            f.delete();
        }
    }
    /**
     * đọc cache file
     * getCacheDir() trả về đúng thư mục cache
     */
    public String readCache(Context context) {
        try {
            File pathCacheDir = context.getCacheDir();
            String strCacheFileName = "myCacheFile.cache";
            File newCacheFile = new
                    File(pathCacheDir, strCacheFileName);
            Scanner sc=new Scanner(newCacheFile);
            String data="";
            while(sc.hasNext())
            {
                data+=sc.nextLine()+"\n";
            }
            sc.close();
            return  data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * Lưu cache file
     */
    public void createCache(Context context,String strFileContents)
    {
        try {
            File pathCacheDir = context.getCacheDir();
            String strCacheFileName = "myCacheFile.cache";
            File newCacheFile = new
                    File(pathCacheDir, strCacheFileName);
            newCacheFile.createNewFile();
            FileOutputStream foCache =
                    new FileOutputStream(
                            newCacheFile.getAbsolutePath());
            foCache.write(strFileContents.getBytes());
            foCache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
