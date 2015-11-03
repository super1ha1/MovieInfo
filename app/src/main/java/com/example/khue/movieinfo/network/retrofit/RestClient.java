package com.example.khue.movieinfo.network.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


/**
 * RestClient for Retrofit
 */
public class RestClient {
    private static final String BASE_URL = "http://api.themoviedb.org/3";

    private APIService apiService;

    public RestClient()
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .serializeNulls()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();
        apiService = restAdapter.create(APIService.class);
    }

    public APIService getApiService()
    {
        return apiService;
    }

    public class RestDeserializer<T> implements JsonDeserializer<T> {

        private Class<T> mClass;
        private String mKey;

        public RestDeserializer(Class<T> targetClass, String key) {
            mClass = targetClass;
            mKey = key;
        }

        @Override
        public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
                throws JsonParseException {
            JsonElement content = je.getAsJsonObject().get(mKey);
            return new Gson().fromJson(content, mClass);

        }
    }
}
