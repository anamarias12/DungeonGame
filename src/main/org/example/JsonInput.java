package main.org.example;

import main.org.example.characters.*;
import main.org.example.characters.Character;
import main.org.example.entities.Account;
import main.org.example.entities.Credentials;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class JsonInput {
    public static ArrayList<Account> deserializeAccounts() {
        String accountPath = "C:\\Users\\Asus\\IdeaProjects\\tema1\\src\\main\\resources\\accounts.json";
        try {
            String content = new String(Files.readAllBytes(Paths.get(accountPath)));

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(content);

            JSONArray accountsArray = (JSONArray) obj.get("accounts");

            ArrayList<Account> accounts = new ArrayList<>();
            for (Object accountObj : accountsArray) {
                JSONObject accountJson = (JSONObject) accountObj;

                String name = (String) accountJson.get("name");
                String country = (String) accountJson.get("country");
                int gamesNumber = Integer.parseInt(accountJson.get("maps_completed").toString());

                Credentials credentials = null;
                JSONObject credentialsJson = (JSONObject) accountJson.get("credentials");
                if (credentialsJson != null) {
                    String email = (String) credentialsJson.get("email");
                    String password = (String) credentialsJson.get("password");
                    credentials = new Credentials(email, password);
                }

                SortedSet<String> favoriteGames = new TreeSet<>();
                JSONArray games = (JSONArray) accountJson.get("favorite_games");
                if (games != null) {
                    for (Object game : games) {
                        favoriteGames.add(game.toString());
                    }
                }

                ArrayList<Character> characters = new ArrayList<>();
                JSONArray charactersListJson = (JSONArray) accountJson.get("characters");
                if (charactersListJson != null) {
                    for (Object charObj : charactersListJson) {
                        JSONObject charJson = (JSONObject) charObj;
                        String cname = (String) charJson.get("name");
                        String profession = (String) charJson.get("profession");
                        int level = Integer.parseInt(charJson.get("level").toString());
                        int experience = Integer.parseInt(charJson.get("experience").toString());

                        Character newCharacter = CharacterFactory.createCharacter(profession, cname, experience, level);
                        if (newCharacter != null) {
                            characters.add(newCharacter);
                        }
                    }
                }

                Account.Information information = new Account.Information.Builder()
                        .setCredentials(credentials)
                        .setFavoriteGames(favoriteGames)
                        .setName(name)
                        .setCountry(country)
                        .build();

                Account account = new Account(characters, gamesNumber, information);
                accounts.add(account);
            }
            return accounts;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
