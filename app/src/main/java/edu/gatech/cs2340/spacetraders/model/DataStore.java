package edu.gatech.cs2340.spacetraders.model;
import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.google.gson.Gson;

import java.util.Scanner;

public class DataStore {
    public static Player jsonToPlayer(String fileName) throws FileNotFoundException {
        Gson gson = new Gson();
        Scanner sc = new Scanner(new File(fileName));
        String jsonFile = "";

        while (sc.hasNext()) {
            jsonFile += sc.next();
        }

        Player player = gson.fromJson(jsonFile, Player.class);

        return player;
    }

    public static Universe jsonToUniverse(String fileName) throws FileNotFoundException {
        Gson gson = new Gson();
        Scanner sc = new Scanner(new File(fileName));
        String jsonFile = "";

        while (sc.hasNext()) {
            jsonFile += sc.next();
        }

        Universe universe = gson.fromJson(jsonFile, Universe.class);

        return universe;
    }

    public static void universeToJson(Context context, Universe universe)
            throws FileNotFoundException {
        String fileName = getCurrentPlayerTxt() + "_universe.json";
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

    public static void playerToJson(Context context, Player player) {
        String fileName = createHeader(player.getName().toLowerCase()) + "_player.json";
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
        String name = createHeader(player.getName().toLowerCase());
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

    public static String getCurrentPlayerTxt() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("current_player.txt"));

        return sc.next();
    }

    public static Player getCurrentPlayer() throws FileNotFoundException {
        return jsonToPlayer(getCurrentPlayerTxt() + "_player.json");
    }
}
