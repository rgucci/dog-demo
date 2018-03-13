package com.russellgutierrez.demo.zuhlke.dog.data.api.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.russellgutierrez.demo.zuhlke.dog.data.api.model.ListAllBreedsResponse;
import com.russellgutierrez.demo.zuhlke.dog.data.api.model.ListImagesForBreedResponse;
import com.russellgutierrez.demo.zuhlke.dog.util.MyGsonTypeAdapterFactory;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DogsEndpoint {

    String ENDPOINT = "https://dog.ceo/";

    @GET("api/breeds/list/all")
    Observable<ListAllBreedsResponse> listAllBreeds();

    @GET("api/breed/{breedName}/images")
    Observable<ListImagesForBreedResponse> listImages(@Path("breedName") String breedName);

    @GET("api/breed/{breedName}/{subBreedName}/images")
    Observable<ListImagesForBreedResponse> listImages(@Path("breedName") String breedName,
                                                      @Path("subBreedName") String subBreedName);

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        public static DogsEndpoint newDogsEndpoint() {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
                    .build();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .setDateFormat(DATETIME_FORMAT)
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return retrofit.create(DogsEndpoint.class);
        }
    }

}
