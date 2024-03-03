package member;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.primitives.Bytes;

import bin.util.FileIO;
import tools.gambling.Items;
import java.util.EnumSet;
import java.util.ArrayList;

public class MemberInfo {

    /*
     * Make the constructor parameter assign all values.
     * Pass-By-Reference? Sorry I didn't pay attention to the vocab
     * in APCS
     */

    private String name;
    private int muntBucks;
    private int muntDebt;
    private FileIO io;
    private File user;
    private HashMap<Integer, Items> inventory;

    public MemberInfo(String path) {
        io = new FileIO(path);
        if (io.doesFileExist()) {
            System.out.println("You must use m$reg first retard");
            return;
        }

        setName();
        setCurrency();
        setDebt();

    }

    public void updateInventory(int itemValue) {
        if(itemValue > 4) {
            return;
        }
        io.writeToFile(String.valueOf(itemValue).concat("\n"), true);
    }

    public void getItemFromInventory(int line) {
        System.out.println(Items.getItemFromValue(Integer.valueOf(io.readFileLine(line))));
    } 

    public ArrayList<Items> getInventory() {
        ArrayList<Items> items = new ArrayList<Items>();
        try {
            List<String> lines = Files.readAllLines(io.getPath());
            for(int i = 8; i < lines.size(); i++) {
                if(!lines.get(i).isEmpty()) {
                    items.add(Items.getItemFromValue(Integer.parseInt(lines.get(i))));
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return items;
    }

    public void setName() {
        name = io.readFileLine(0);
    }

    public void setCurrency() {
        muntBucks = Integer.valueOf(io.readFileLine(1));
    }

    public void setDebt() {
        muntDebt = Integer.valueOf(io.readFileLine(1));
    }
}
