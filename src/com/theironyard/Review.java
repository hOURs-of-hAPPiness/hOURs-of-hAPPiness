package com.theironyard;

/**
 * Created by Erik on 6/16/16.
 */
public class Review {
    Integer reviewId;
    String review;
    Integer rating;

    public Review(Integer reviewId, String review, Integer rating) {
        this.reviewId = reviewId;
        this.review = review;
        this.rating = rating;
    }

    public Review() {
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
