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

    public static Inventory jsonToInventory(String fileName) throws FileNotFoundException {
        Gson gson = new Gson();
        Scanner sc = new Scanner(new File(fileName));
        String jsonFile = "";

        while (sc.hasNext()) {
            jsonFile += sc.next();
        }

        Inventory inventory = gson.fromJson(jsonFile, Inventory.class);

        return inventory;
    }

    public static void universeToJson(Context context, ModelFacade facade) {
        Date currDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        DateFormat timeFormat = new SimpleDateFormat("HHmm");
        String name = facade.getPlayer().getName().toLowerCase()
                + "_" + dateFormat.format(currDate)
                + "_" + timeFormat.format(currDate);
        String fileName = name + "_universe.json";
        String fileContents = facade.getUniverse().toJSONString();
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playerToJson(Context context, ModelFacade facade) {
        Date currDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        DateFormat timeFormat = new SimpleDateFormat("HHmm");
        String name = facade.getPlayer().getName().toLowerCase();
        String fileName = name + "_" + dateFormat.format(currDate)
                + "_" + timeFormat.format(currDate) + "_player.json";
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(fileName.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void inventoryToJson(Context context, ModelFacade facade) {
//        Date currDate = new Date();
//        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
//        DateFormat timeFormat = new SimpleDateFormat("HHmm");
//        String name = facade.getPlayer().getName().toLowerCase()
//                + "_" + dateFormat.format(currDate)
//                + "_" + timeFormat.format(currDate);
//        String filename = name + "_inventory.json";
//        //String fileContents = facade.getInventory().toJSONString();
//        FileOutputStream outputStream;
//
//        try {
//            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
//            outputStream.write(fileContents.getBytes());
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
