package tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class FileCreator {

    public FileCreator(File file, MessageReceivedEvent event, String slops, String slopDebt, String timer, boolean deleteFile) throws IOException {
        if(!file.exists()) {
            file.createNewFile();
            new LogEvent(file.getAbsolutePath() + " created at bin\\member\\");
        } else if(deleteFile && file.exists()) {
            file.delete();
            file.createNewFile();
        } else {
            System.out.println("You exist");
            new LogEvent(event.getMember().getEffectiveName() + " exists. Exiting function.");
            return;
        }

        FileWriter fw = new FileWriter(file);
        new LogEvent("FileWriter fw initialized to file: " + file.getAbsolutePath());
        if(slops.isEmpty() && slopDebt.isEmpty() && timer.isEmpty()) {
            slops = "0"; slopDebt = "0"; timer = "0";
        }

        fw.write(event.getMember().getEffectiveName() + "\n" + slops + "\n" + slopDebt + "\n" + timer); //Slops, slopdebt, timer (1 day)
        fw.flush();
        fw.close();
        new LogEvent("fw wrote to slop stats to file successfully");
    }    
}
