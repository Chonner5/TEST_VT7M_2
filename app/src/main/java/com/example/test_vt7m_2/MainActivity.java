package com.example.test_vt7m_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.storage.StorageManager;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;


import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.lang.reflect.Method;




public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    public  SwitchCompat sw1;
    public  SwitchCompat sw2;
    public  SwitchCompat sw3;
    public  SwitchCompat sw4;
    public  SwitchCompat sw5;
    public  SwitchCompat sw6;
    public  SwitchCompat sw7;
    public  SwitchCompat sw8;
    public  SwitchCompat sw9;
    public Button bt1,bt2,bt3,bt4,bt5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sw1 = findViewById(R.id.switch1);
        sw2 = findViewById(R.id.switch2);
        sw3 = findViewById(R.id.switch3);
        sw4 = findViewById(R.id.switch4);
        sw5 = findViewById(R.id.switch5);
        sw6 = findViewById(R.id.switch6);
        sw7 = findViewById(R.id.switch7);
        sw8 = findViewById(R.id.switch8);
        sw9 = findViewById(R.id.switch9);
        bt1 = findViewById(R.id.button1);
        bt2 = findViewById(R.id.button2);
        bt3 = findViewById(R.id.button3);
        bt4 = findViewById(R.id.button4);
        bt5 = findViewById(R.id.button5);

//        SwitchCompat sw3 = findViewById(R.id.switch3);
//        SwitchCompat sw4 = findViewById(R.id.switch4);

        sw1.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        sw2.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        sw3.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        sw4.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        sw5.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        sw6.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        sw7.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        sw8.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        sw9.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);


        String SD_path = getStoragePath(MainActivity.this,true);    //获取SD卡的路径



        //点击向指定文件写入
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this,SD_path,Toast.LENGTH_LONG).show();
                createFile("sdcard/Apns/etc/");           //创建文件夹
                copyFile(SD_path+"/apns-conf.xml","sdcard/Apns/etc/apns-conf.xml");//从外部SD卡copy -> 内部
                Toast.makeText(MainActivity.this,"重新启动后生效",Toast.LENGTH_LONG).show();
            }
        });



        //点击安装指定软件
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wirte_bao = "com.demo.test3/com.demo.test3.MainActivity";
                String file_path = "apd/";                   //后续根据需求要改目录

                createFile(file_path+"default_launcher.txt");           //创建文件
                writeTxtToFile(wirte_bao,file_path,"default_launcher.txt");

                //app-release.apk
                //copyFile(SD_path+"/app-release.apk",file_path+"app-release.apk");//从外部SD卡copy -> 内部

                Toast.makeText(MainActivity.this,wirte_bao,Toast.LENGTH_LONG).show();
            }
        });


        //点击后跳转到COLOR界面
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] eCommand = {"am start -n com.android.settings/.display.ColorTemperatureActivity" + "\n",};
                ShellUtils.CommandResult su = ShellUtils.execCommand(eCommand, false, true);
                Toast.makeText(MainActivity.this,su.successMsg,Toast.LENGTH_LONG).show();
            }
        });

        //关机键
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] eCommand = {"reboot -p" + "\n",};
                ShellUtils.execCommand(eCommand, false, true);
                //Toast.makeText(MainActivity.this,power_msg.successMsg,Toast.LENGTH_LONG).show();
            }
        });

        //删除键
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String file_path = "/apd/";
                String[] eCommand = {"rm -f /apd/default_launcher.txt" + "\n",};
                ShellUtils.execCommand(eCommand, false, true);
                Toast.makeText(MainActivity.this,"已删除",Toast.LENGTH_LONG).show();
            }
        });




        //电源键初始化
        if (Settings.System.getInt(getContentResolver(), "power_Short_Long", 1)==0){
            sw1.setChecked(true);
        }
        else {
            sw1.setChecked(false);
        }


        //显示重启键
        if (Settings.System.getInt(getContentResolver(), "power_restart_Show", 1)==0){
            sw2.setChecked(true);
        }
        else {
            sw2.setChecked(false);
        }


        //虚拟按键初始化
        if (Settings.System.getInt(getContentResolver(), "show_enable", 1)==1){
            sw3.setChecked(true);
        }
        else {
            sw3.setChecked(false);
        }

        //显示背光按键
        if (Settings.System.getInt(getContentResolver(), "key_light_enable ", 1)==1){
            sw4.setChecked(true);
        }
        else {
            sw4.setChecked(false);
        }

        //进入安全模式
        if (Settings.System.getInt(getContentResolver(), "safemode_enable ", 1)==1){
            sw5.setChecked(true);
        }
        else {
            sw5.setChecked(false);
        }

        //ACC
        if (Settings.System.getInt(getContentResolver(), "acc_enable ", 1)==1){
            sw6.setChecked(true);
        }
        else {
            sw6.setChecked(false);
        }

//        acc_sleep_switch
        if (Settings.System.getInt(getContentResolver(), "acc_sleep_switch ", 1)==1){
            sw7.setChecked(true);
        }
        else {
            sw7.setChecked(false);
        }

        //ACC_Sleep_delay
        if (Settings.System.getInt(getContentResolver(), "acc_sleep_delay ", 1)==1){
            sw8.setChecked(true);
        }
        else {
            sw8.setChecked(false);
        }

        //acc_shutdown_switch
        if (Settings.System.getInt(getContentResolver(), "acc_shutdown_switch ", 1)==1){
            sw9.setChecked(true);
        }
        else {
            sw9.setChecked(false);
        }



    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.switch1:              //禁用电源键
                if(sw1.isChecked()){
                    Settings.System.putInt(getContentResolver(), "power_Short_Long", 0);
                }
                else{
                    //Toast.makeText(this,compoundButton.getText()+"unChecked",Toast.LENGTH_SHORT).show();
                    Settings.System.putInt(getContentResolver(), "power_Short_Long", 1);
                }
                break;
            case R.id.switch2:              //显示重启键
                if(sw2.isChecked()){
                    //Toast.makeText(this,"当前状态:"+ compoundButton.getText(),Toast.LENGTH_SHORT).show();
                    Settings.System.putInt(getContentResolver(), "power_restart_Show", 0);
                }
                else{
                    //Toast.makeText(this,compoundButton.getText()+"unChecked",Toast.LENGTH_SHORT).show();
                    Settings.System.putInt(getContentResolver(), "power_restart_Show", 1);
                }
                break;
            case R.id.switch3:          //显示虚拟按键
                if (sw3.isChecked()){
                    Settings.System.putInt(getContentResolver(), "show_enable", 1);
                }
                else {
                    Settings.System.putInt(getContentResolver(), "show_enable", 0);
                }
                break;
            case R.id.switch4:          //显示背光按键
                if (sw4.isChecked()){
                    if (sw4.isChecked()){
                        Settings.System.putInt(getContentResolver(), "key_light_enable", 0);
                    }
                    else {
                        Settings.System.putInt(getContentResolver(), "key_light_enable", 1);
                    }
                }
                break;
            case R.id.switch5:          //进入安全模式
                if (sw5.isChecked()){
                    if (sw5.isChecked()){
                        Settings.System.putInt(getContentResolver(), "safemode_enable", 1);
                    }
                    else {
                        Settings.System.putInt(getContentResolver(), "safemode_enable", 0);
                    }
                }
                break;
            case R.id.switch6:          //开启ACC
                if (sw6.isChecked()){
                    if (sw6.isChecked()){
                        Settings.System.putInt(getContentResolver(), "acc_enable ", 1);
                    }
                    else {
                        Settings.System.putInt(getContentResolver(), "acc_enable ", 0);
                    }
                }
                break;

            case R.id.switch7:          //acc_sleep_switch
                if (sw7.isChecked()){
                    if (sw7.isChecked()){
                        Settings.System.putInt(getContentResolver(), "acc_sleep_switch ", 1);
                    }
                    else {
                        Settings.System.putInt(getContentResolver(), "acc_sleep_switch ", 0);
                    }
                }
                break;
            case R.id.switch8:          //ACC_Sleep_delay
                if (sw8.isChecked()){
                    if (sw8.isChecked()){
                        Settings.System.putInt(getContentResolver(), "acc_sleep_delay ", 1);
                    }
                    else {
                        Settings.System.putInt(getContentResolver(), "acc_sleep_delay ", 0);
                    }
                }
                break;

            case R.id.switch9:          //acc_shutdown_switch
                if (sw9.isChecked()){
                    if (sw9.isChecked()){
                        Settings.System.putInt(getContentResolver(), "acc_shutdown_switch  ", 1);
                    }
                    else {
                        Settings.System.putInt(getContentResolver(), "acc_shutdown_switch  ", 0);
                    }
                }
                break;

            default:
                break;
        }
    }
    //------------------------------------------------------------------------------------------------

    /**
     * 通过反射调用获取内置存储和外置sd卡根路径(通用)
     *
     * @param mContext    上下文
     * @param is_removale 是否可移除，false返回内部存储路径，true返回外置SD卡路径
     * @return
     */
    public static String getStoragePath(Context mContext, boolean is_removale) {
        String path = "";
        //使用getSystemService(String)检索一个StorageManager用于访问系统存储功能。
        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);

            for (int i = 0; i < Array.getLength(result); i++) {
                Object storageVolumeElement = Array.get(result, i);
                path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean)isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    return path;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    //---------------------------------------------------------------------------------------------
    /**
     * 创建文件或文件夹   加点就是创建文件，不加就是创建目录
     *
     * @param fileName
     *            文件名或问文件夹名
     */
    public static void createFile(String fileName) {
        File file = new File(fileName);
        //Toast.makeText(MainActivity.this,inside_storage_path+fileName,Toast.LENGTH_LONG).show();
        if (fileName.indexOf(".") != -1) {
            // 说明包含，即创建文件, 返回值为-1就说明不包含.,即是文件
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//            System.out.println("创建了文件");
            //Toast.makeText(MainActivity.this,"创建了文件",Toast.LENGTH_LONG).show();
        } else {
            // 创建文件夹
            file.mkdir();
//            System.out.println("创建了文件夹");
            //Toast.makeText(MainActivity.this,"创建文件夹",Toast.LENGTH_LONG).show();
        }

    }
    //---------------------------------------------------------------------------------------------------
    /**
     * 复制单个文件
     *
     * @param oldPath$Name String 原文件路径+文件名 如：data/user/0/com.test/files/abc.txt
     * @param newPath$Name String 复制后路径+文件名 如：data/user/0/com.test/cache/abc.txt
     * @return <code>true</code> if and only if the file was copied;
     * <code>false</code> otherwise
     */
    public boolean copyFile(String oldPath$Name, String newPath$Name) {
        try {
            File oldFile = new File(oldPath$Name);
            if (!oldFile.exists()) {
                Toast.makeText(MainActivity.this,"文件已经存在",Toast.LENGTH_LONG).show();
                //Log.e("--Method--", "copyFile:  oldFile not exist.");
                return false;
            } else if (!oldFile.isFile()) {
                Toast.makeText(MainActivity.this,"不是文件",Toast.LENGTH_LONG).show();
                //Log.e("--Method--", "copyFile:  oldFile not file.");
                return false;
            } else if (!oldFile.canRead()) {
                Toast.makeText(MainActivity.this,"文件不能读取",Toast.LENGTH_LONG).show();
                //Log.e("--Method--", "copyFile:  oldFile cannot read.");
                return false;
            }

        /* 如果不需要打log，可以使用下面的语句
        if (!oldFile.exists() || !oldFile.isFile() || !oldFile.canRead()) {
            return false;
        }
        */
            FileInputStream fileInputStream = new FileInputStream(oldPath$Name);    //读入原文件
            FileOutputStream fileOutputStream = new FileOutputStream(newPath$Name);
            byte[] buffer = new byte[1024];
            int byteRead;
            while ((byteRead = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * 写内容到指定文件    文件不会自动创建目录，加入createFile() 后就可以
     *
     * @param strcontent String 写入文件的内容
     * @param filePath String 文件目录
     * @param fileName String 文件名
     *
     *
     */
    public static void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //createFile(filePath);
        String strFilePath = filePath + fileName;
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                //Log.d(TAG, "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            //Log.e(TAG, "Error on write File:" + e);
        }
    }



}