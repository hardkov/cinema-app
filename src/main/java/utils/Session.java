package utils;

import model.Movie;
import model.User;

import javax.swing.plaf.InsetsUIResource;

public class Session {
    public static Session session = new Session();
    private User currentUser;
    private Movie currentlyViewedMovie;

    private Session(){
    }

    public static Session getSession(){
        if(session == null){
            session = new Session();
        }

        return session;
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public void setCurrentUser(User user){
        currentUser = user;
    }

    public Movie getCurrentlyViewedMovie() {
        return currentlyViewedMovie;
    }

    public void setCurrentlyViewedMovie(Movie currentlyViewedMovie) {
        this.currentlyViewedMovie = currentlyViewedMovie;
    }
}
