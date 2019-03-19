package com.test.simpleweatherapi.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.test.simpleweatherapi.model.City;
import com.test.simpleweatherapi.model.WeatherInfo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class OpenWeatherMapService {

    private static final String API_URL = "http://api.openweathermap.org";
    private static final String TEST_APP_ID = "7a8a10698921afa4ad918285326033ac";
    private static final String SERACH_CITY_TYPE = "like";
    private static final int SERACH_CITY_COUNT = 10;

    public interface SearchCity {
        @GET("/data/2.5/find")
        Call<List<City>> getInfo(
                @Query("q") String cityName,
                @Query("type") String type,
                @Query("appid") String appID);

        @GET("/data/2.5/find")
        Call<List<City>> getInfo(
                @Query("lat") double lat,
                @Query("lon") double lon,
                @Query("cnt") int count,
                @Query("appid") String appID);
    }

    public interface SearchCurrentWeather {
        @GET("/data/2.5/weather")
        Call<WeatherInfo> getInfo(
                @Query("id") long cityID,
                @Query("appid") String appID);
    }

    public interface SearchForecastWeather {
        @GET("/data/2.5/forecast")
        Call<List<WeatherInfo>> getInfo(
                @Query("id") long cityID,
                @Query("appid") String appID);
    }

    private class CityDeserializer implements JsonDeserializer<List<City>> {
        @Override
        public List<City> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject root = json.getAsJsonObject();
            int count = root.getAsJsonPrimitive("count").getAsInt();
            ArrayList<City> result = new ArrayList<>();
            JsonArray jsonArray = root.getAsJsonArray("list");
            for(int i = 0; i < count; i++) {
                JsonObject cityJsonObject = jsonArray.get(i).getAsJsonObject();
                long id = cityJsonObject.getAsJsonPrimitive("id").getAsLong();
                String name = cityJsonObject.getAsJsonPrimitive("name").getAsString();
                double lat = cityJsonObject.getAsJsonObject("coord").getAsJsonPrimitive("lat").getAsDouble();
                double lon = cityJsonObject.getAsJsonObject("coord").getAsJsonPrimitive("lon").getAsDouble();
                String country = cityJsonObject.getAsJsonObject("sys").getAsJsonPrimitive("country").getAsString();
                City city = new City(id, name, lat, lon, country);
                result.add(city);
            }
            return result;
        }
    }

    private class CurrentWeatherDeserializer implements JsonDeserializer<WeatherInfo> {
        @Override
        public WeatherInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return parseFromJsonObject(json.getAsJsonObject());
        }
    }

    private class ForecastWeatherDeserializer implements JsonDeserializer<List<WeatherInfo>> {
        @Override
        public List<WeatherInfo> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject root = json.getAsJsonObject();
            int count = root.getAsJsonPrimitive("cnt").getAsInt();
            ArrayList<WeatherInfo> result = new ArrayList<>();
            JsonArray jsonArray = root.getAsJsonArray("list");
            for(int i = 0; i < count; i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                result.add(parseFromJsonObject(jsonObject));
            }
            return result;
        }
    }

    private WeatherInfo parseFromJsonObject(JsonObject jsonObject) {
        WeatherInfo weatherInfo = new WeatherInfo();
        JsonObject mainJsonObject = jsonObject.getAsJsonObject("main");
        weatherInfo.setTemp(mainJsonObject.getAsJsonPrimitive("temp").getAsDouble());
        weatherInfo.setMaxTemp(mainJsonObject.getAsJsonPrimitive("temp_max").getAsDouble());
        weatherInfo.setMinTemp(mainJsonObject.getAsJsonPrimitive("temp_min").getAsDouble());
        weatherInfo.setPressure(mainJsonObject.getAsJsonPrimitive("pressure").getAsInt());
        weatherInfo.setHumidity(mainJsonObject.getAsJsonPrimitive("humidity").getAsInt());
        weatherInfo.setWeather(jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().getAsJsonPrimitive("main").getAsString());
        weatherInfo.setWeatherDescription(jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().getAsJsonPrimitive("description").getAsString());
        weatherInfo.setIcon(jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().getAsJsonPrimitive("icon").getAsString());
        weatherInfo.setDate(new Date(jsonObject.getAsJsonPrimitive("dt").getAsLong()));
        return weatherInfo;
    }

    public List<City> fetchCity(String cityName) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(List.class, new CityDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SearchCity search = retrofit.create(SearchCity.class);
        Call<List<City>> call = search.getInfo(cityName, SERACH_CITY_TYPE, TEST_APP_ID);
        List<City> cities = call.execute().body();

        return cities;
    }

    public List<City> fetchCity(double lat, double lon) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(List.class, new CityDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SearchCity search = retrofit.create(SearchCity.class);
        Call<List<City>> call = search.getInfo(lat, lon, SERACH_CITY_COUNT, TEST_APP_ID);
        List<City> cities = call.execute().body();

        return cities;
    }

    public WeatherInfo fetchCurrentWeather(long cityID) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(WeatherInfo.class, new CurrentWeatherDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SearchCurrentWeather search = retrofit.create(SearchCurrentWeather.class);

        return search.getInfo(cityID, TEST_APP_ID).execute().body();
    }

    public List<WeatherInfo> fetchForecastWeather(long cityID) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(List.class, new ForecastWeatherDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SearchForecastWeather search = retrofit.create(SearchForecastWeather.class);

        return search.getInfo(cityID, TEST_APP_ID).execute().body();
    }
}
