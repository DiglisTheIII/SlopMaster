package tools;

import java.io.File;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SlopTools {
    static int slops;
    static int slopDebt;
    int timer;
    File userFile;

    public SlopTools(File userFile) {
        this.userFile = userFile;
    }

    public static List<String> getSlopLoan(List<String> userLines, File user) {
        int random = ThreadLocalRandom.current().nextInt(1, 255);
        slops = random;
        slopDebt = random;

        userLines.set(1, String.valueOf(slops));
        userLines.set(2, String.valueOf(slopDebt));

        return userLines;

    }

    public void paySlopLoan() {
        
    }
}
