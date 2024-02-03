package tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class BackupUserData {
    
    public static void backupFiles() throws IOException {

        File memberDir = new File("bin\\member\\");
        File tempDir = new File("bin\\tempMem\\");

        List<File> memberList = Arrays.asList(memberDir.listFiles());
        List<String> data;
        tempDir.mkdirs();

        for(File f : memberList) {
            data = Files.readAllLines(Paths.get(f.toURI()));
            File temp = new File(tempDir.getAbsolutePath() + "\\" + f.getName());
            temp.createNewFile();
            BufferedWriter wr = new BufferedWriter(new FileWriter(temp));
            wr.write(data.get(0) + "\n" + data.get(1) + "\n" + data.get(2) + "\n" + data.get(3));
            wr.flush();
            wr.close();
            
        }
    }

}
