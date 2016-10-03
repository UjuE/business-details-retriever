package com.ujuezeoke.businessdetails.yelp.tinytypes.queryParameters;

import com.ujuezeoke.businessdetails.yelp.tinytypes.TinyType;

/**
 * Created by Obianuju Ezeoke on 25/09/2016.
 */
public abstract class QueryParameters<V> extends TinyType<String> {
    protected QueryParameters(String key, V value) {
        super(key + "=" + value.toString());
    }
}
