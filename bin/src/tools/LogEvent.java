package tools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogEvent {
    
    public LogEvent(String log) {
        try (BufferedWriter fw = new BufferedWriter(new FileWriter("EventLog.txt"))) {
            fw.write(log);
            fw.newLine();
            fw.flush();
            fw.close();
            System.out.println(log);
        } catch (IOException e) {
            System.out.println("Failed to log event");
        }
    }

}
