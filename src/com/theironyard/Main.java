package com.theironyard;

import jodd.json.JsonParser;
import jodd.json.JsonSerializer;
import org.h2.tools.Server;
import spark.Session;
import spark.Spark;

import java.sql.*;
import java.util.ArrayList;


public class Main {

    public static void createTables (Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLES IF NOT EXISTS users (userId IDENTITY, username VARCHAR)");
        stmt.execute("CREATE TABLES IF NOT EXISTS bars (barId IDENTITY, barName VARCHAR, barLocation VARCHAR, author VARCHAR, user_id INT)");
        stmt.execute("CREATE TABLES IF NOT EXISTS reviews(reviewId IDENTITY, review VARCHAR, rating INT, author VARCHAR, bar_id INT)");
    }

    public static void insertUser (Connection conn, String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?)");
        stmt.setString(1, username);
        stmt.execute();
    }

    public static User selectUser (Connection conn, String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE name = ?");
        stmt.setString(1, username);
        ResultSet results = stmt.executeQuery();
        if (results.next()) {
            Integer userId = results.getInt("userId");
            return new User(userId, username);
        }
        return null;
    }

    public static void insertBar (Connection conn, Bar bar) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO bars VALUES (NULL, ?, ?)");
        stmt.setString(1, bar.barName);
        stmt.setString(2, bar.barLocation);
        stmt.execute();
    }

    public static Bar selectBar (Connection conn, Integer barId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bars WHERE barId = ?");
        stmt.setInt(1, barId);
        ResultSet results = stmt.executeQuery();
        if (results.next()) {
            String barName = results.getString("barName");
            String barLocation = results.getString("barLocation");
            String imageUrl = results.getString("imageUrl");
            String author = results.getString("author");
            return new Bar(barId, barName, barLocation, imageUrl, author);
        }
        return null;
    }

    public static ArrayList<Bar> selectBars(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bars INNER JOIN users ON bars.user_id = users.userId WHERE users.userId = ?");
        ResultSet results = stmt.executeQuery();
        ArrayList<Bar> bars = new ArrayList<>();
        while (results.next()) {
            Integer barId = results.getInt("barId");
            String barName = results.getString("barName");
            String barLocation = results.getString("barLocation");
            String imageUrl = results.getString("imageUrl");
            String author = results.getString("author");
            Bar bar = new Bar(barId, barName, barLocation, imageUrl, author);
            bars.add(bar);
        }
        return bars;
    }

    public static void updateBar (Connection conn, Integer barId, String barName, String barLocation) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE bars SET barName = ?, barLocation = ? WHERE barId = ?");
        stmt.setInt(1, barId);
        stmt.setString(2, barName);
        stmt.setString(3, barLocation);
        stmt.execute();
    }

    public static void deleteBar(Connection conn, Integer barId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM bars WHERE barId = ?");
        stmt.setInt(1, barId);
        stmt.execute();
    }

    public static void insertReview (Connection conn, String review, int rating, String author) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO reviews VALUES (NULL, ?, ?, ?)");
        stmt.setString(1, review);
        stmt.setInt(2, rating);
        stmt.setString(3, author);
        stmt.execute();
    }

    public static Review selectReview (Connection conn, Integer reviewId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM reviews WHERE reviewId = ?");
        stmt.setInt(1, reviewId);
        ResultSet results = stmt.executeQuery();
        if (results.next()) {
            String review = results.getString("review");
            int rating = results.getInt("rating");
            String author = results.getString("author");
            return new Review(reviewId, review, rating, author);
        }
        return null;
    }

    public static ArrayList<Review> selectReviews(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM reviews INNER JOIN bars ON reviews.bar_id = bars.barId WHERE bars.barId = ?");
        ResultSet results = stmt.executeQuery();
        ArrayList<Review> reviewList = new ArrayList<>();
        while (results.next()) {
            Integer reviewId = results.getInt("reviewId");
            String review = results.getString("review");
            int rating = results.getInt("rating");
            String author = results.getString("author");
            Review reviews = new Review(reviewId, review, rating, author);
            reviewList.add(reviews);
        }
        return reviewList;
    }

    public static void updateReview (Connection conn, Integer reviewId, String review, int rating, String author) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE reviews SET review = ?, rating = ?, author = ? WHERE reviewId = ?");
        stmt.setInt(1, reviewId);
        stmt.setString(2, review);
        stmt.setInt(3, rating);
        stmt.setString(4, author);
        stmt.execute();
    }

    public static void deleteReview(Connection conn, Integer reviewId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM reviews WHERE reviewId = ?");
        stmt.setInt(1, reviewId);
        stmt.execute();
    }

    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        createTables(conn);

        JsonParser parser = new JsonParser();
        JsonSerializer serializer = new JsonSerializer();


        Spark.init();

        Spark.post(
                "/login",
                (request, response) -> {

                }
        );
        Spark.post(
                "/logout",
                (request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                }
        );
        Spark.get(
                "/get-bars",
                (request, response) -> {
                    ArrayList<Bar> bars = selectBars(conn);
                    return serializer.serialize(bars);
                }
        );
        Spark.post(
                "/add-bars",
                (request, response) -> {
                    String body = request.body();
                    Bar bar = parser.parse(body, Bar.class);
                    insertBar(conn, bar);
                    return "";
                }
        );

        Spark.delete(
                "/delete-bar/:id",
                (request, response) -> {
                    int barId = Integer.valueOf(request.params(":id"));

                    Session session = request.session();
                    String username = session.attribute("username");
                    Bar b = selectBar(conn, barId);

                    if (b.author.equals(username)) {
                        throw new Exception("Unable to delete");
                    }

                    deleteBar(conn, barId);

                    response.redirect("/");
                    return "";
                }
        );
    }


}
