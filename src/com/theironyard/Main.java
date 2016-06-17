package com.theironyard;

import jodd.json.JsonParser;
import org.h2.tools.Server;
import spark.Spark;

import java.sql.*;

public class Main {

    public static void createTables(Connection conn) throws SQLException {
        Statement stmt= conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS users (user_id IDENTITY, username VARCHAR, password VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS bars (bar_id IDENTITY, bar_name VARCHAR bar_location VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS reviews (review_id IDENTITY, review VARCHAR, rating INT, author VARCHAR)");
    }

    public static void insertUser(Connection conn, User user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?)");
        stmt.setString(1, user.username);
        stmt.execute();
    }

    static User selectUser(Connection conn, String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE name = ?");
        stmt.setString(1, username);
        ResultSet results = stmt.executeQuery();
        if (results.next()){
            int id = results.getInt("id");
            return new User(id, username);
        }
        return null;
    }


    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        createTables(conn);

        Spark.init();

        Spark.get("/get-bars",
                (request, response) -> {

                    return "";
                }
        );

        Spark.get("/get-reviews",
                (request, response) -> {

                    return "";
                }
        );

        Spark.post("/create-user",
                (request, response) -> {
                    String body = request.body();
                    JsonParser p = new JsonParser();
                    User user = p.parse(body, User.class);
                    insertUser(conn, user);
                    return"";
                    }
        );

        Spark.post("/create-bar",
                (request, response) -> {

                    return "";
                }
        );

        Spark.post("/create-review",
                (request, response) -> {

                    return "";
                }
        );

        Spark.put("/edit-bar",
                (request, response) -> {

                    return "";
                }
        );

        Spark.put("/edit-review",
                (request, response) -> {

                    return "";
                }
        );

        Spark.delete("/delete-bar",
                (request, response) -> {

                    return "";
                }
        );

        Spark.delete("/delete-review",
                (request, response) -> {

                    return "";
                }
        );

    }


}
