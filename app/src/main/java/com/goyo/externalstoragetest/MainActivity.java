package com.goyo.externalstoragetest;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private String sdCardRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnWriteExternalStorage(View view) {
        sdCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = createDirOnSDCard("myDir");
        File file = createFileOnSDCard("myFile.txt", "myDir");
        try {
            InputStream in = new FileInputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"openMe.txt"));
            writeData2SDCard("myDir2","myFile2.txt",in);
            writeData2SDCard(System.getenv("SECONDARY_STORAGE"),"myFile2.txt",in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public File createDirOnSDCard(String dir)
    {
        File dirFile = new File(sdCardRoot + File.separator + dir +File.separator);
        Log.v("createDirOnSDCard", sdCardRoot + File.separator + dir + File.separator);
        dirFile.mkdirs();
        return dirFile;
    }
    /**
     * 在SD卡上创建文件
     */
    public File createFileOnSDCard(String fileName, String dir)
    {
        File file = new File(sdCardRoot + File.separator + dir + File.separator + fileName);
        Log.v("createFileOnSDCard", sdCardRoot + File.separator + dir + File.separator + fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    /**
     * 写入数据到SD卡中
     */
    public File writeData2SDCard(String path, String fileName, InputStream data)
    {
        File file = null;
        OutputStream output = null;

        try {
            createDirOnSDCard(path);  //创建目录
            file = createFileOnSDCard(fileName, path);  //创建文件
            output = new FileOutputStream(file);
            byte buffer[] = new byte[2*1024];          //每次写2K数据
            int temp;
            while((temp = data.read(buffer)) != -1 )
            {
                output.write(buffer,0,temp);
            }
            output.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try {
                output.close();    //关闭数据流操作
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return file;
    }


}
