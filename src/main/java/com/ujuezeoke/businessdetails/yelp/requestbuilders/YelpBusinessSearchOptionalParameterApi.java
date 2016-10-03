package com.ujuezeoke.businessdetails.yelp.requestbuilders;

import com.ujuezeoke.businessdetails.yelp.tinytypes.Result;
import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.BusinessSearchResult;
import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.YelpToken;

import java.util.Map;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

/**
 * Created by Obianuju Ezeoke on 25/09/2016.
 */
public class YelpBusinessSearchOptionalParameterApi {
    private final YelpToken token;
    private final YelpRequestSender sender;
    private final String requestUrl;
    private final Map<String, String> queryParams;

    YelpBusinessSearchOptionalParameterApi(YelpToken token, YelpRequestSender sender, String requestUrl, Map<String, String> queryParams) {
        this.token = token;
        this.sender = sender;
        this.requestUrl = requestUrl;
        this.queryParams = queryParams;
    }

    public YelpBusinessSearchOptionalParameterApi withTerm(String term) {
        queryParams.put("term", term);
        return this;
    }

    public YelpBusinessSearchOptionalParameterApi withRadius(int radius) {
        queryParams.put("radius", String.valueOf(radius));
        return this;
    }

    public YelpBusinessSearchOptionalParameterApi withCategories(String... categories) {
        queryParams.put("categories", String.join(",", asList(categories)));
        return this;
    }

    public YelpBusinessSearchOptionalParameterApi withLocale(String locale) {
        queryParams.put("locale", locale);
        return this;
    }

    public YelpBusinessSearchOptionalParameterApi withLimit(int limit) {
        queryParams.put("limit", String.valueOf(limit));
        return this;
    }

    public YelpBusinessSearchOptionalParameterApi withOffset(int offset) {
        queryParams.put("offset", String.valueOf(offset));
        return this;
    }

    public Result<BusinessSearchResult> execute() {
        final String requestUrlWithParameters = format("%s?%s", requestUrl, collectQueryParameters());
        return sender.getRequest(requestUrlWithParameters, BusinessSearchResult.class, token);
    }

    private String collectQueryParameters() {
        return queryParams.entrySet().stream()
                .map(it -> it.getKey() + "=" + it.getValue()).collect(joining("&"));
    }
}
