package com.vinaymaneti.codertmdb.Util;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vinay on 14/10/16.
 */

public class RetrofitUtil {
    public static Retrofit get(String apiKey) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client(apiKey))
                .addConverterFactory(GsonConverterFactory.create())// this will help parse json into json object
                .build();
    }

    // we are gonna create a HTTPClient it is just an object to communicate to network
    //@get. @post http method

    //Interceptor is like a middleware between layers like move to a layer extract the params
    // and it will extract the session/ cookies of the request and it will turn the request into object
    //here we are going to move into a layer that we had a api key into the request

    //and here we can add more addInterceptor() methods like logger to see the request
    // and for some api require header for authentication/ authorisation, we need to have a the api token
    // we try to add the interceptor before the api moves
    private static OkHttpClient client(String apiKey) {
        return new OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor(apiKey))
                .build();
    }


    //here chain object is our request into layer
    //we are gonna add api key into the request
    //first we need to gather the request
    //remember the request is passed in the interceptor by the chain object
    //first we are gonna get request and the we get a url and they have a object to detail with the url
    //AND THIS URL object has host() --> hostname and schema() --> http, https,fpt --> the way the protocol
    // and there are more methods this here another one is queryParameterName()
    // what is queryParameter in the url after the '?' what every the key, value pairs available they are called queryParameter
    //here for this okHttp/ retrofit we can't add/ set parameter it is immutable

    //here it is going to copy the parameter like host name url and other parameter  into newBuilder()
    //and we addQueryParameter() we are gonna create new url and build which doesn't effect the last one
    private static Interceptor apiKeyInterceptor(final String apiKey) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //this is to build the base url with api key
                HttpUrl url = request.url()
                        .newBuilder()
                        .addQueryParameter("api_key", apiKey)
                        .build();

                //we have the url and we have to change the url of the request
                //request contain a url and other stuff
                //it is the builder patter of java
                request = request.newBuilder()
                        .url(url)
                        .build();

                //here we are passed it ino the next interceptor
                return chain.proceed(request);
            }
        };
    }
}
