package tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class FileCreator {

    public FileCreator(File file, MessageReceivedEvent event, String slops, String slopDebt, String timer,
            boolean deleteFile) throws IOException {
        File userFile = new File(file.getAbsolutePath() + "\\" + event.getMember().getEffectiveName() + ".txt");
        File timerFile = new File(file.getAbsolutePath() + "\\timer.txt");

        if (!file.exists()) {
            file.mkdirs();
            userFile.createNewFile();
            timerFile.createNewFile();
        }

        if (slops.isEmpty() && slopDebt.isEmpty() && timer.isEmpty()) {
            slops = "0";
            slopDebt = "0";
            timer = "0";
        }

        FileWriter fw = new FileWriter(userFile);

        if (file != null && userFile != null) {
            fw.write(event.getMember().getEffectiveName() + "\n" + slops + "\n" + slopDebt); // Slops, slopdebt, timer
                                                                                             // (1 day)
            fw.flush();
            fw.close();
            new LogEvent("fw wrote to slop stats to file successfully");
        }

        FileWriter tw = new FileWriter(timerFile);
        tw.write(timer);
        tw.flush();
        tw.close();
    }
}
