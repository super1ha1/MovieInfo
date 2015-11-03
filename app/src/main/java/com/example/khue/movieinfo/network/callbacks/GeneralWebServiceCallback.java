package com.example.khue.movieinfo.network.callbacks;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Interface for callback from server
 */
public interface GeneralWebServiceCallback {
    void failure();
    void failure(JSONObject data);
    void success();
    void success(String data);
    void success(JSONObject data);
    void success(JSONArray data);
    void success(Object data);

}
