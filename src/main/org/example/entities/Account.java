package main.org.example.entities;

import main.org.example.characters.Character;
import java.util.ArrayList;
import java.util.SortedSet;

public class Account {

    // builder pattern for information class
    public static class Information {
        private final Credentials credentials;
        private final SortedSet<String> favoriteGames;
        private final String name;
        private final String country;

        private Information(Builder builder) {
            this.credentials = builder.credentials;
            this.favoriteGames = builder.favoriteGames;
            this.name = builder.name;
            this.country = builder.country;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public SortedSet<String> getFavoriteGames() {
            return favoriteGames;
        }

        // Builder class to construct Information object
        public static class Builder {
            private Credentials credentials;
            private SortedSet<String> favoriteGames;
            private String name;
            private String country;

            public Builder setCredentials(Credentials credentials) {
                this.credentials = credentials;
                return this;
            }

            public Builder setFavoriteGames(SortedSet<String> favoriteGames) {
                this.favoriteGames = favoriteGames;
                return this;
            }

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Builder setCountry(String country) {
                this.country = country;
                return this;
            }

            public Information build() {
                return new Information(this);
            }
        }
    }

    private final Information information;
    private final ArrayList<Character> characters;
    private int gamesNumber;

    public Account(ArrayList<Character> characters, int gamesNumber, Information information) {
        this.characters = characters;
        this.gamesNumber = gamesNumber;
        this.information = information;
    }

    public Information getInformation() {
        return information;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public int getGamesNumber() {
        return gamesNumber;
    }

    public void setGamesNumber(int gamesNumber) {
        this.gamesNumber = gamesNumber;
    }

    // Getter methods for username and password
    public String getUsername() {
        return information.getCredentials().getEmail();
    }

    public String getPassword() {
        return information.getCredentials().getPassword();
    }

    @Override
    public String toString() {
        return "Name: " + information.getName() + ", Country: " + information.getCountry() +
                ", Favourite Games: " + information.getFavoriteGames() + ", Maps Completed: " + gamesNumber;
    }
}
