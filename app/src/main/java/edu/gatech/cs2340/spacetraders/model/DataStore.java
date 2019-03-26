package edu.gatech.cs2340.spacetraders.model;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import processing.opengl.PGraphics2D;

public class DataStore {

    private static FilenameFilter createFilter(String toMatch) {
        return ((dir, name) -> name.endsWith(toMatch));
    }

    public static Map<String, SavedPlayer> savedPlayerMap;

    public static Player jsonToPlayer(Context context, String fileName)
            throws FileNotFoundException {
        Gson gson = new Gson();
        Scanner sc =
                new Scanner(new File(context.getFilesDir().getAbsolutePath() + "/" + fileName));
        String jsonFile = "";

        while (sc.hasNext()) {
            jsonFile += sc.next();
        }

        Player player = gson.fromJson(jsonFile, Player.class);

        return player;
    }

    public static Universe jsonToUniverse(Context context, String fileName)
            throws FileNotFoundException {
        Gson gson = new Gson();
        Scanner sc =
                new Scanner(new File(context.getFilesDir().getAbsolutePath() + "/" + fileName));
        String jsonFile = "";

        while (sc.hasNext()) {
            jsonFile += sc.next();
        }

        Universe universe = gson.fromJson(jsonFile, Universe.class);

        return universe;
    }

    public static void universeToJson(Context context, Universe universe)
            throws FileNotFoundException {
        String fileName = getCurrentPlayerTxt(context) + "_universe.json";
        String fileContents = new Gson().toJson(universe);
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playerToJson(Context context, Player player) throws FileNotFoundException {
        String fileName = getCurrentPlayerTxt(context) + "_player.json";
        String fileContents = new Gson().toJson(player);
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void newPlayerToJson(Context context, Player player) {
        String fileName = createHeader(player.getName()) + "_player.json";
        String fileContents = new Gson().toJson(player);
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public static String getCurrentPlayerTxt(Context context) throws FileNotFoundException {
        Scanner sc = new Scanner(
                new File(context.getFilesDir().getAbsolutePath() + "/current_player.txt"));

        return sc.next();
    }

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
            playerToAdd.setCurrentPlayerText(keyName);
            savedPlayerMap.put(keyName, playerToAdd);
        }

        File[] savedUniverses = directory.listFiles(createFilter("_universe.json"));
        for (File currentFile : savedUniverses) {
            String fileName = currentFile.getName();
            String keyName = fileName.substring(0, fileName.indexOf("_universe.json"));
            SavedPlayer playerToEdit = savedPlayerMap.get(keyName);
            playerToEdit.setUniverseJsonName(fileName);
        }
        return savedPlayerMap;
    }

    public static void deletePlayerAndUniverse(Context context) {
        File directory = context.getFilesDir();
        for (File currentFile : directory.listFiles(createFilter("_player.json"))) {
            currentFile.delete();
        }
        for (File currentFile : directory.listFiles(createFilter("_universe.json"))) {
            currentFile.delete();
        }
    }

    public static String[] getSavedPlayerNames(Context context) {
        return getSavedPlayers(context).values().stream().map(SavedPlayer::getName)
                .toArray(String[]::new);
    }

    private static String getKeyFromPlayerName(String name) {
        return savedPlayerMap.values().stream().filter(savedPlayer -> savedPlayer.getName().equals(name)).findFirst().get().getCurrentPlayerText();
    }

    public static void setCurrentPlayerText(Context context, String name) {
        File currentPlayerFile = new File(context.getFilesDir().getAbsolutePath() +
                                          "/current_player.txt");
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

    public static Player getCurrentPlayer(Context context) throws FileNotFoundException {
        return jsonToPlayer(context, getCurrentPlayerTxt(context) + "_player.json");
    }
}
