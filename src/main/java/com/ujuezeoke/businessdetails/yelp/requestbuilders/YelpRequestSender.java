package com.ujuezeoke.businessdetails.yelp.requestbuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ujuezeoke.businessdetails.yelp.tinytypes.Result;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.stream.Collectors.joining;

/**
 * Created by Obianuju Ezeoke on 25/09/2016.
 */
public class YelpRequestSender {
    private static final Logger LOGGER = Logger.getLogger(YelpRequestSender.class.getName());

    <T> Result<T> postRequest(String requestUri, Class<T> tClass) {
        try {
            final Response response = Request.Post(requestUri).execute();
            final HttpResponse httpResponse = response.returnResponse();
            final int statusCode = httpResponse.getStatusLine().getStatusCode();
            final String responseBody = stringFrom(httpResponse.getEntity().getContent());
            final T object = new Gson().fromJson(responseBody, tClass);

            return new Result<>(statusCode, object);
        } catch (IOException e) {
            throw new UnsupportedOperationException("Not Yet Implemented", e);
        }
    }

     <T> Result<T> getRequest(String requestUri, Class<T> tClass, RequestHeader... headers) {
        try {
            final Response response = getRequestWithHeaders(requestUri, headers).execute();
            final HttpResponse httpResponse = response.returnResponse();
            final int statusCode = httpResponse.getStatusLine().getStatusCode();
            final String responseBody = stringFrom(httpResponse.getEntity().getContent());

            LOGGER.log(Level.INFO, "Response from yelp \n{0}", responseBody);
            final T object;
            if (statusCode == 200) {
                object = new Gson().fromJson(responseBody, tClass);
                return new Result<>(statusCode, object);
            }

            return new Result<T>(statusCode, null);

        } catch (IOException e) {
            throw new UnsupportedOperationException("Not Yet Implemented", e);
        }
    }

    private Request getRequestWithHeaders(String requestUri, RequestHeader[] headers) {
        Request request = Request.Get(requestUri);
        for (RequestHeader header : headers) {
            request = request.addHeader(header.getKey(), header.getValue());
        }
        return request;
    }

    private String stringFrom(InputStream content) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content))) {
            return bufferedReader.lines()
                    .collect(joining("\n"));

        } catch (IOException e) {
            throw new UnsupportedOperationException("Not Yet Implemented", e);
        }
    }
}
