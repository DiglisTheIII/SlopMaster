package tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import okhttp3.internal.ws.RealWebSocket.Message;
import util.SendMessage;

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

    public static List<String> paySlopLoan(List<String> userLines, File user) {
        try {
            userLines = Files.readAllLines(Paths.get(user.toURI()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int slops = Integer.valueOf(userLines.get(1));
        int slopDebt = Integer.valueOf(userLines.get(2));
        if (slops > slopDebt) {
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

    
}
