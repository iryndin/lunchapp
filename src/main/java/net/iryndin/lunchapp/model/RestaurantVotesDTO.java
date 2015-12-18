package net.iryndin.lunchapp.model;

import java.util.List;

public class RestaurantVotesDTO {
    private RestaurantDTO restaurant;
    private List<String> usernames;

    public RestaurantDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDTO restaurant) {
        this.restaurant = restaurant;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }
}
