package com.ujuezeoke.businessdetails.yelp.tinytypes;

/**
 * Created by Obianuju Ezeoke on 25/09/2016.
 */
public class Result<T> {
    private final int statusCode;
    private final T responseBody;

    public Result(int statusCode, T responseBody) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public boolean isSuccess(){
        return statusCode == 200;
    }
}
