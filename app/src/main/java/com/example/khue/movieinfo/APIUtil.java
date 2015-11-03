//package com.example.khue.movieinfo;
//
//import android.preference.PreferenceManager;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
///**
// * API Utility functions
// */
//
//public class APIUtil {
//    /**
//     * update User info in SharedPreference
//     * @param data
//     * @param pm
//     */
//    public static void updateUserLoginSuccess (JSONObject data, PreferenceManager pm){
//        try {
//            String token = data.getString("auth");
//            JSONObject user = data.getJSONObject("user_details");
//            int id = user.getInt("user_id");
//            String username = user.getString("username");
//
//            pm.setToken(token);
//            pm.setUsername(username);
//            pm.setId(id);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//}
