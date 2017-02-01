package com.limethecoder;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DBInitializer {
    private final static String JDBC_USER = "user";
    private final static String JDBC_PASSWORD = "password";
    private final static String JDBC_DRIVER = "driver";
    private final static String JDBC_URL = "url";

    private final static String SQL_SCRIPT = "scheme.sql";
    private final static String PROPERTIES_FILE = "db";


    public void initTestJdbcDB() throws Exception {
        ResourceBundle jdbcProperties = ResourceBundle.getBundle(PROPERTIES_FILE);

        String multiQuery = readFile(SQL_SCRIPT, StandardCharsets.UTF_8);

        Class.forName(jdbcProperties.getString(JDBC_DRIVER));
        try (Connection con = DriverManager.getConnection(
                jdbcProperties.getString(JDBC_URL),
                jdbcProperties.getString(JDBC_USER),
                jdbcProperties.getString(JDBC_PASSWORD));
             Statement st = con.createStatement()) {

            st.execute(multiQuery);
        }
    }

    private String readFile(String fileName, Charset encoding)
            throws IOException {
        File script = new File(
                this.getClass()
                        .getClassLoader()
                        .getResource(fileName)
                        .getFile());

        byte[] encoded = Files.readAllBytes(script.toPath());
        return new String(encoded, encoding);
    }
}
