package com.ujuezeoke.businessdetails.dao;

import java.sql.Connection;

/**
 * Created by Obianuju Ezeoke on 01/10/2016.
 */
@FunctionalInterface
public interface DatabaseTask<RESULT_TYPE> {
    RESULT_TYPE run(Connection connection);
}
