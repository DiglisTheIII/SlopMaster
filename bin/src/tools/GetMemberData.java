package tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GetMemberData {

    public static List<String> getData(String name) throws IOException {
        name = name + ".txt";
        File data = new File("bin\\member\\" + name.replaceAll(".txt", "") + "\\" + name);
        return Files.readAllLines(Paths.get(data.toURI()));
    }

}
