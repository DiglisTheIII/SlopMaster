package tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.FileWriter;

public class SlopTools {
    static int slops;
    static int slopDebt;
    int timer;
    File userFile;

    public SlopTools(File userFile) {
        this.userFile = userFile;
    }

    //Currently has no implementation. I will need to find a use for it, or refactor my slopLoan command
    public static List<String> getSlopLoan(List<String> userLines, File user, int loanAmount) {
        if(loanAmount > 5000) {
            return null;
        }

        userLines.set(1, String.valueOf(loanAmount));
        userLines.set(2, String.valueOf(loanAmount));

        return userLines;

    }

    public static List<String> paySlopLoan(List<String> userLines, File user) {
        try {
            userLines = Files.readAllLines(Paths.get(user.toURI()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Pretty basic logic. If you have more than you owe, then you pay that down to 0, if less, then not and pay your current balance.
        int slops = Integer.valueOf(userLines.get(1));
        int slopDebt = Integer.valueOf(userLines.get(2));
        if (slops >= slopDebt) {
            slops = slops - slopDebt;
            slopDebt = 0;
        } else if (slops <= slopDebt) {
            slopDebt = slopDebt - slops;
            slops = 0;
        }

        userLines.set(1, String.valueOf(slops));
        userLines.set(2, String.valueOf(slopDebt));
        return userLines;
    }

    public void updateSlops(int slops) {
        try {
            List<String> toUpdate = Files.readAllLines(Paths.get(userFile.toURI()));

            FileWriter fw = new FileWriter(userFile);
            toUpdate.set(1, String.valueOf(slops));
            userFile.delete();
            userFile.createNewFile();
            for(String s : toUpdate) {
                fw.write(s + "\n");
                fw.flush();
            }
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
