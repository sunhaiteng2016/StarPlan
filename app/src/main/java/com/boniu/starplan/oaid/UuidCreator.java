package com.boniu.starplan.oaid;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.boniu.starplan.utils.AESUtil;
import com.bun.miitmdid.core.ErrorCode;
import com.bun.miitmdid.core.JLibrary;
import com.bun.miitmdid.core.MdidSdkHelper;
import com.bun.supplier.IIdentifierListener;
import com.bun.supplier.IdSupplier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

public class UuidCreator {
    private final String TAG = "UUID_TAG";
    private final String SP_NAME = "oaid_info";
    private final String SP_KEY_DEVICE_ID = "oaid_txt";

    private final String TEMP_DIR = "system_uuid";
    private final String TEMP_FILE_NAME = "uuid_file";

    private final String filename = "uuid_file";
    public String file10path = "";
    private String oaid;
    private String oaidKey;
    private Context context;
    private String key = "boniu--uuid--key";

    //初始化uuid返回的code  0标示成功
    public int code;

    private volatile static UuidCreator myuuid = null;
    private UuidCreator(Context context) {
        this.context = context;
        if (TextUtils.isEmpty(oaid)){
            code = MdidSdkHelper.InitSdk(context, true, new IIdentifierListener() {
                @Override
                public void OnSupport(boolean b, IdSupplier idSupplier) {
                    String myoaid=idSupplier.getOAID();
                    if (TextUtils.isEmpty(myoaid)){
                        oaid = "error-" + UUID.randomUUID().toString();
                    }else{
                        oaid = myoaid;
                    }
                    Log.d(TAG, "OnSupport: " + oaid );
                }
            });
            Log.d(TAG, "UuidCreator: " + code );
        }
    }
    public static UuidCreator getInstance(Context context) {
        if (myuuid == null) {
            synchronized (UuidCreator.class) {
                if (myuuid == null) {
                    myuuid = new UuidCreator(context);
                }
            }
        }
        return myuuid;

    }


    /**
     * 获取设备唯一码
     * 如果返回为空，获取下 code看看是什么情况
     * oaid 未加密的uuid
     * oaidkey  加密的uuid
     * deviceId 未加密的uuid
     * @return
     */
    public String getDeviceId() {
        Log.d(TAG, "no1:  " + code + ":::" + oaid );
        if (code == 0 || code == ErrorCode.INIT_ERROR_RESULT_DELAY){
            if (TextUtils.isEmpty(oaid)){
                oaid = "error-" + UUID.randomUUID().toString();
            }
        }else{
            if (TextUtils.isEmpty(oaid)){
                oaid = "code-" + UUID.randomUUID().toString();
            }
        }
        oaidKey = AESUtil.encrypt(oaid,key);
        Log.d(TAG, "getDeviceId: " + oaid + ":::" + oaidKey );

        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        String deviceId = sharedPreferences.getString(SP_KEY_DEVICE_ID, "");

        if (TextUtils.isEmpty(deviceId)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                String fileuuid = getUUid(context,filename);
                if (TextUtils.isEmpty(fileuuid)){
                    creatUUIDFile(context,filename,oaidKey);
                    deviceId = oaid;
                }else{
                    deviceId = AESUtil.decrypt(fileuuid,key);
                    if (TextUtils.isEmpty(deviceId)){
                        creatUUIDFile(context,filename,oaidKey);
                        deviceId = oaid;
                    }
                }
                Log.d(TAG, "deviceId: " + deviceId );
            }else{
                String fileuuid = get9UUid();
                if (TextUtils.isEmpty(fileuuid)){
                    createFile(oaidKey);
                    deviceId = oaid;
                }else{
                    deviceId = AESUtil.decrypt(fileuuid,key);
                    if (TextUtils.isEmpty(deviceId)){
                        createFile(oaidKey);
                        deviceId = oaid;
                    }
                }
            }
        }else{
//            if (deviceId.contains("error")){
//                int i = deviceId.indexOf("-");
//                if (i > 0){
//                    String substring = deviceId.substring(0,i);
//                    if ("error".equals(substring)){
//                        deviceId = oaid;
//                    }
//                }
//            }

            oaidKey = AESUtil.encrypt(deviceId,key);
            Log.d(TAG, "getDeviceId1: " + oaidKey );

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                creatUUIDFile(context,filename,oaidKey);
            }else{
                createFile(oaidKey);
            }
        }


        sharedPreferences.edit()
                .putString(SP_KEY_DEVICE_ID, deviceId)
                .apply();
        return deviceId.toUpperCase();
    }


    /**
     *
     * android 10写入文件
     * 在download中 生成fileName文件
     * 向Mediastore添加内容
     *
     * @param saveFileName 保存文件的名称
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void creatUUIDFile(Context context, String saveFileName, String uuid) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, saveFileName);
//         TODO: 2019-10-11 IS_PENDING = 1表示对应的item还没准备好
//        values.put(MediaStore.Images.Media.IS_PENDING, 1);

        ContentResolver resolver = context.getContentResolver();
        Uri collection = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
        resolver.delete(collection,MediaStore.Images.Media.DISPLAY_NAME +  "=" + "'" + saveFileName + "'" ,null);
        Uri uri = resolver.insert(collection, values);
        try {
            //访问 对于单个媒体文件，请使用 openFileDescriptor()。
            ParcelFileDescriptor fielDescriptor = resolver.openFileDescriptor(uri, "w", null);
            FileOutputStream outputStream = new FileOutputStream(fielDescriptor.getFileDescriptor());
            try {
                //讲UUID写入到文件中
                outputStream.write(uuid.getBytes());
                outputStream.close();
                Log.d(TAG, "写入 uuidStr:" + uuid);
                file10path = collection.getPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            values.clear();
            values.put(MediaStore.Images.Media.IS_PENDING, 0);          //设置为0
            resolver.update(uri, values, null, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "creatUUIDFile: " + e.getMessage() );
        }
    }


    /**
     * android 10以下获取本地uuid
     * @return
     */
    private String get9UUid(){
        String my9uuid = "";
        File externalDownloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File applicationFileDir = new File(externalDownloadsDir, TEMP_DIR);
        if (!applicationFileDir.exists()) {
            if (!applicationFileDir.mkdirs()) {
                Log.d(TAG, "文件夹创建失败: " + applicationFileDir.getPath());
            }
        }
        File file = new File(applicationFileDir, TEMP_FILE_NAME);
        if (file.exists()){
            FileReader fileReader = null;
            BufferedReader bufferedReader = null;
            try {
                fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader);
                my9uuid = bufferedReader.readLine();
                bufferedReader.close();
                fileReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return my9uuid;
        }else{
            return "";
        }

    }
    /**
     * android 10以下生成文件
     */
    private String createFile(String myoaidkey){
        File externalDownloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File applicationFileDir = new File(externalDownloadsDir, TEMP_DIR);
        if (!applicationFileDir.exists()) {
            applicationFileDir.mkdirs();
        }
        File file = new File(applicationFileDir, TEMP_FILE_NAME);
        Log.e(TAG, "creatUUIDFile1: " + file.getPath());
        if (file.exists()){
            file.delete();
        }
        FileWriter fileWriter = null;
        try {
            if (file.createNewFile()) {
                fileWriter = new FileWriter(file, false);
                fileWriter.write(myoaidkey);
            } else {
                Log.d(TAG, "文件创建失败：" + file.getPath());
            }
        } catch (Exception e) {
            Log.d(TAG, "文件创建失败：" + file.getPath() + "::" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return file.getPath()+"";
    }




    /**
     * android 10 获取本地存储的uuid
     *
     * @param context
     * @param saveFileName
     * @return
     */

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private String getUUid(Context context, String saveFileName){
        Uri mImageUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
//        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA
        };
        //查询
        ContentResolver contentResolver = context.getContentResolver();

        // 添加筛选条件
        String selection = MediaStore.Images.Media.DISPLAY_NAME + "=" + "'" + saveFileName + "'";
        Cursor mCursor = contentResolver.query(mImageUri, projection, selection, null, null);

        String getSaveContent = "";
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                int columnIndex = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                File file = new File(mCursor.getString(columnIndex));
                Log.d(TAG, "getUUid: " + file.getPath() );
                if (file.exists()){
                    try {
                        InputStream instream = new FileInputStream(file);
                        if (instream != null)
                        {
                            InputStreamReader inputreader = new InputStreamReader(instream);
                            BufferedReader buffreader = new BufferedReader(inputreader);
                            String line;
                            //分行读取
                            while (( line = buffreader.readLine()) != null) {
                                getSaveContent += line + "\n";
                            }
                            instream.close();
                        }
                    }
                    catch (Exception e)
                    {
                        Log.d("TestFile", e.getMessage());
                    }
                    Log.d(TAG, "checkUUIDFileByUricontent: " + getSaveContent );
                }
//                String thumbPath = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon()
//                        .appendPath(saveFileName).build().toString();
            }

            mCursor.close();
        }
        return getSaveContent;

    }



}
