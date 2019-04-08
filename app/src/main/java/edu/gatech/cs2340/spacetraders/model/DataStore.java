package edu.gatech.cs2340.spacetraders.model;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Data store.
 */
public class DataStore {
//    private static final DataStore instance = new DataStore();

//    /**
//     * Gets instance.
//     *
//     * @return the instance
//     */
//    public static DataStore getInstance() {
//        return instance;
//    }

    private static FilenameFilter createFilter(String toMatch) {
        return ((dir, name) -> name.endsWith(toMatch));
    }

    /**
     * The Saved player map.
     */
    private static Map<String, SavedPlayer> savedPlayerMap;

    /**
     * Json to player player.
     *
     * @param context  the context
     * @param fileName the file name
     * @return the player
     * @throws FileNotFoundException the file not found exception
     */
    private static Player jsonToPlayer(Context context, String fileName)
            throws FileNotFoundException {
        Gson gson = new Gson();
        File file = context.getFilesDir();
        Scanner sc =
                new Scanner(new File(file.getAbsolutePath() + "/" + fileName));
        StringBuilder jsonFile = new StringBuilder();

        while (sc.hasNext()) {
            jsonFile.append(sc.next());
        }

        return gson.fromJson(jsonFile.toString(), Player.class);
    }

//    public static Universe jsonToUniverse(Context context, String fileName)
//            throws FileNotFoundException {
//        Gson gson = new Gson();
//        File file = context.getFilesDir();
//        Scanner sc =
//                new Scanner(new File(file.getAbsolutePath() + "/" + fileName));
//        String jsonFile = "";
//
//        while (sc.hasNext()) {
//            jsonFile += sc.next();
//        }
//
//        Universe universe = gson.fromJson(jsonFile, Universe.class);
//
//        return universe;
//    }

    /**
     * Universe to json.
     *
     * @param context  the context
     * @param universe the universe
     * @throws FileNotFoundException the file not found exception
     */
    public static void universeToJson(Context context, Universe universe)
            throws FileNotFoundException {
        String fileName = getCurrentPlayerTxt(context) + "_universe.json";
        Gson gson = new Gson();
        String fileContents = gson.toJson(universe);
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Player to json.
     *
     * @param context the context
     * @param player  the player
     * @throws FileNotFoundException the file not found exception
     */
    public static void playerToJson(Context context, Player player) throws FileNotFoundException {
        String fileName = getCurrentPlayerTxt(context) + "_player.json";
        Gson gson = new Gson();
        String fileContents = gson.toJson(player);
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * New player to json.
     *
     * @param context the context
     * @param player  the player
     */
    public static void newPlayerToJson(Context context, Player player) {
        String fileName = createHeader(player.getName()) + "_player.json";
        Gson gson = new Gson();
        String fileContents = gson.toJson(player);        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create current player txt.
     *
     * @param context the context
     * @param player  the player
     */
    public static void createCurrentPlayerTxt(Context context, Player player) {
        String name = createHeader(player.getName());
        String fileName = "current_player.txt";
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(name.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String createHeader(String name) {
        Date currDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        DateFormat timeFormat = new SimpleDateFormat("HHmm");

        return name + "_" + dateFormat.format(currDate) + "_" + timeFormat.format(currDate);
    }

    /**
     * Gets current player txt.
     *
     * @param context the context
     * @return the current player txt
     * @throws FileNotFoundException the file not found exception
     */
    private static String getCurrentPlayerTxt(Context context) throws FileNotFoundException {
        File file = context.getFilesDir();
        Scanner sc =
                new Scanner(new File(file.getAbsolutePath() + "/" + "/current_player.txt"));
        return sc.next();
    }

    /**
     * Gets saved players.
     *
     * @param context the context
     * @return the saved players
     */
    public static Map<String, SavedPlayer> getSavedPlayers(Context context) {
        savedPlayerMap = new HashMap<>();
        File directory = context.getFilesDir();
        File[] savedPlayers = directory.listFiles(createFilter("_player.json"));
        for (File currentFile : savedPlayers) {
            String fileName = currentFile.getName();
            String playerName = fileName.substring(0, fileName.indexOf("_"));
            SavedPlayer playerToAdd = new SavedPlayer(playerName);
            playerToAdd.setPlayerJsonName(fileName);
            String keyName = fileName.substring(0, fileName.indexOf("_player.json"));
            String playerJsonContent = readJson(context, currentFile.getName());
            playerToAdd.setPlayerJsonContent(playerJsonContent);
            playerToAdd.setCurrentPlayerText(keyName);
            savedPlayerMap.put(keyName, playerToAdd);
        }

        File[] savedUniverses = directory.listFiles(createFilter("_universe.json"));
        for (File currentFile : savedUniverses) {
            String fileName = currentFile.getName();
            String keyName = fileName.substring(0, fileName.indexOf("_universe.json"));
            SavedPlayer playerToEdit = savedPlayerMap.get(keyName);
            String universeJsonContent = readJson(context, currentFile.getName());
            assert playerToEdit != null;
            playerToEdit.setUniverseJsonContent(universeJsonContent);
            playerToEdit.setUniverseJsonName(fileName);
        }
        return Collections.unmodifiableMap(savedPlayerMap);
    }

    private static String readJson(Context context, String filename) {
        File file = context.getFilesDir();
        File[] savedPlayers = file.listFiles(createFilter(filename));
        String toReturn = "";
        for (File currentFile : savedPlayers) {
            try (InputStream inputStream = new FileInputStream(currentFile);
                 BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(inputStream))) {
                Stream<String> stringStream = bufferedReader.lines();
                toReturn = stringStream.collect(Collectors.joining());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return toReturn;
    }


    /**
     * Delete player and universe.
     *
     * @param context the context
     */
    public static void deletePlayerAndUniverse(Context context) {
        File directory = context.getFilesDir();
        for (File currentFile : directory.listFiles(createFilter("_player.json"))) {
            currentFile.delete();
        }
        for (File currentFile : directory.listFiles(createFilter("_universe.json"))) {
            currentFile.delete();
        }
    }

    /**
     * Get saved player names string [ ].
     *
     * @param context the context
     * @return the string [ ]
     */
    public static String[] getSavedPlayerNames(Context context) {
        Map<String, SavedPlayer> savedPlayerMap = getSavedPlayers(context);
        Collection<SavedPlayer> savedPlayers = savedPlayerMap.values();
        Stream<SavedPlayer> savedPlayerStream = savedPlayers.stream();
        Stream<String> savedPlayerString = savedPlayerStream.map(SavedPlayer::getName);
        return savedPlayerString.toArray(String[]::new);
    }

    private static String getKeyFromPlayerName(String name) {
        Collection<SavedPlayer> savedPlayers = savedPlayerMap.values();
        Stream<SavedPlayer> savedPlayerStream = savedPlayers.stream();
        Stream<SavedPlayer> savedPlayerString = savedPlayerStream.filter(savedPlayer -> {
            String savedPlayerName = savedPlayer.getName();
            return savedPlayerName.equals(name);
        });

        Optional<SavedPlayer> firstSaved = savedPlayerString.findFirst();
        if (firstSaved.isPresent()) {
            SavedPlayer player = firstSaved.get();
            return player.getCurrentPlayerText();
        }
        return "Not found";
    }

    /**
     * Sets current player text.
     *
     * @param context the context
     * @param name    the name
     */
    public static void setCurrentPlayerText(Context context, String name) {
        File file = context.getFilesDir();
        File currentPlayerFile =
                new File(file.getAbsolutePath() + "/current_player.txt");
        String newContent = getKeyFromPlayerName(name);
        try {
            FileOutputStream overWriteCurrentPlayerText = new FileOutputStream(currentPlayerFile);
            overWriteCurrentPlayerText.write(newContent.getBytes());
            overWriteCurrentPlayerText.flush();
            overWriteCurrentPlayerText.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets current player.
     *
     * @param context the context
     * @return the current player
     * @throws FileNotFoundException the file not found exception
     */
    public static Player getCurrentPlayer(Context context) throws FileNotFoundException {
        return jsonToPlayer(context, getCurrentPlayerTxt(context) + "_player.json");
    }
}
