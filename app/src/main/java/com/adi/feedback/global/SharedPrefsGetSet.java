package com.adi.feedback.global;

import android.content.Context;

/**
 * Created by root on 8/8/16.
 */
public class SharedPrefsGetSet {

    public static final String KEY_USERID = "userId";
    public static final String KEY_ANSWER = "userResponse";

    public static void setUserId(Context mContext, int userId){
        GlobalFunctions.setSharedPrefs(mContext, KEY_USERID, userId);
    }

    public static int getUserId(Context mContext) {
        return GlobalFunctions.getSharedPrefs(mContext, KEY_USERID, 0);
    }

    public static void removeUserId(Context mContext) {
        GlobalFunctions.removeSharedPrefs(mContext, KEY_USERID);
    }

    public static void setAnswer(Context mContext, String option){
        GlobalFunctions.setSharedPrefs(mContext, KEY_ANSWER, option);
    }

    public static void setUserDetails(Context mContext, String name,String email,String phone,String address){
        GlobalFunctions.setSharedPrefs(mContext, "name", name);
        GlobalFunctions.setSharedPrefs(mContext, "email", email);
        GlobalFunctions.setSharedPrefs(mContext, "phone", phone);
        GlobalFunctions.setSharedPrefs(mContext, "address", address);
    }

    public static String getName(Context mContext) {
        return GlobalFunctions.getSharedPrefs(mContext, "name", "");
    }
    public static String getEmail(Context mContext) {
        return GlobalFunctions.getSharedPrefs(mContext, "email", "");
    }
    public static String getPhone(Context mContext) {
        return GlobalFunctions.getSharedPrefs(mContext, "phone", "");
    }
    public static String getAddress(Context mContext) {
        return GlobalFunctions.getSharedPrefs(mContext, "address", "");
    }
    public static String getAnswer(Context mContext) {
        return GlobalFunctions.getSharedPrefs(mContext, KEY_ANSWER, "");
    }




    public static void removeAnswer(Context mContext) {
        GlobalFunctions.removeSharedPrefs(mContext, KEY_ANSWER);
    }

}
