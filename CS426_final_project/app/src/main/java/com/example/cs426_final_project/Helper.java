package com.example.cs426_final_project;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.security.SecureRandom;
import java.util.Random;

public class Helper {

    private Helper() {}

    public static int getSizeOfJSONObject(
            JSONObject object
    ) {
        if (object == null)
            return 0;
        Iterator<String> iterator = object.keys();
        int result = 0;

        while (iterator.hasNext()) {
            iterator.next();
            ++result;
        }

        return result;
    }

    public static JSONObject loadJSONObjectFromJSONFile(
            Activity activity,
            final String fileName
    ) {
        FileInputStream fileInputStream = null;
        JSONObject result = new JSONObject();

        System.out.println("Read file " + fileName);

        try {
            fileInputStream = activity.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String text;

            while ((text = bufferedReader.readLine()) != null)
                stringBuilder.append(text);

            try {
                result = new JSONObject(stringBuilder.toString());
            } catch (JSONException e) {
                result = new JSONObject();
            }

        } catch (FileNotFoundException e) {
            result = new JSONObject();
            e.printStackTrace();
        } catch (IOException e) {
            result = new JSONObject();
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public static void writeJSONObjectToJSONFile(
            Activity activity,
            final String fileName,
            final JSONObject object
    ) {
        FileOutputStream fileOutputStream = null;
        final String text = object.toString();

        try {
            fileOutputStream = activity.openFileOutput(fileName, activity.MODE_PRIVATE);
            fileOutputStream.write(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("File " + "'" + fileName + "'"  + "is successfully written!!! ");
    }

    public static int getRandomIntegerInRange(
            final int l,
            final int r
    ) {
        SecureRandom randomness = new SecureRandom();
        return l + randomness.nextInt(r - l);
    }

    public static String getRandomStringOfAlphabets(
            final int length
    ) {
        final int lowerBound = 97, upperBound = 122;
        Random randomness = new Random();
        /*
        String result = "";
        for (int i = 0; i < length; ++i)
            result += Helper.getRandomIntegerInRange(lowerBound, upperBound);
        return result;

         */
        return randomness.ints(lowerBound, upperBound + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
