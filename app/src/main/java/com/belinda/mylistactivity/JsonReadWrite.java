package com.belinda.mylistactivity;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A class that can read and write a generic ArrayList to and from a file in external storage as JSON
 * @param <T> The type of the ArrayList
 */
public class JsonReadWrite<T>
{
    static  boolean mExternalStorageAvailable = false;
    static  boolean mExternalStorageWriteable = false;

    /**
     * Writes an ArrayList to a file as JSON
     * @param arrayList
     * @param fileName
     * @param context
     * @return true if successful, false otherwise
     */
    boolean writeJsonArrayList(ArrayList<T> arrayList, String fileName, Context context) {
        if (! checkExternalStorageState()) {
            Log.e("WriteJsonArrayList", "Cannot writeFile - External storage not available or not Writeable");
            return false;
        }

        // Convert ArrayList to JSON String
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);

        // writeFile JSON String to file
        File txtFile = new File(context.getExternalFilesDir(null), fileName);
        try(    FileOutputStream fos = new FileOutputStream(txtFile);
                OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                BufferedWriter writer = new BufferedWriter(osw);                                ) {
            writer.append(json);
            return true;
        }
        catch (Exception e) {
            Log.e("WriteJsonArrayList", "Error writing to file: " + fileName);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Reads an ArrayList from a file as JSON
     * @param fileName
     * @param context
     * @param type Class type to read. For example of type is Person.class
     * @return the ArrayList if successful, null otherwise
     */
    public ArrayList<T> readJsonArrayList(String fileName, Context context, Class<T> type) {
        checkExternalStorageState();
        if (! mExternalStorageAvailable) {
            Log.e("ReadJsonArrayList", "Cannot readFile - External storage not available");
            return null;
        }

        // Read JSON text String from file
        String json = "";
        File txtFile = new File(context.getExternalFilesDir(null), fileName);
        try (   FileInputStream in = new FileInputStream(txtFile)  ) {
            byte[] buffer=new byte[in.available()];
            in.read(buffer);
            json = new String(buffer, "UTF-8");//Support Hebrew
        }
        catch (Exception e) {
            Log.e("ReadJsonArrayList", "Error reading from file: " + fileName);
            e.printStackTrace();
            return null;
        }

        // Convert JSON String to ArrayList
        Gson gson = new Gson();
        Type arrayListType = TypeToken.getParameterized(ArrayList.class, type).getType();
        ArrayList<T> arrList = new ArrayList<T>();
        try {
            arrList = gson.fromJson(json, arrayListType);
        }
        catch (Exception e) {
            Log.e("ReadJsonArrayList", "Error decoding json: " + json);
            e.printStackTrace();
            return null;
        }
        return arrList;
    }

    /**
     * Utility to verify external storage state
     */
    private static boolean checkExternalStorageState() {
        // Verify that the external storage is available for writing
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
        { // We can readFile and writeFile the media
            return true;
        }
        return false;
    }
}
