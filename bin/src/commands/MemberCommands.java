package commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import tools.FileCreator;
import tools.GetMemberData;
import tools.SlopTools;
import util.SendMessage;

public class MemberCommands {
    FileCreator fileCreator;
    MessageReceivedEvent event;
    File userFile;

    List<String> lines;

    public MemberCommands(MessageReceivedEvent event) {
        this.event = event;
        userFile = new File("bin\\member\\" + event.getMember().getEffectiveName() + ".txt");

        try {
            lines = Files.readAllLines(Paths.get(userFile.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createNewFile() {
        try {
            fileCreator = new FileCreator(userFile, event, "", "", "", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getSlopLoan(boolean isPaying) {
        if (!userFile.exists()) {
            SendMessage.sendMessage(event, "You must register with s$reg first").queue();
            return;
        }
        System.out.println("Lines of userfile from: " + lines.hashCode() + " initialized to lines<String> list");

        int timer = Integer.parseInt(lines.get(3));

        int hours = timer / 3600;
        int minutes = (timer % 3600) / 60;
        int seconds = timer % 60;

        String remainingTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        if (timer > 0 && !isPaying) {
            SendMessage.sendMessage(event, "You must wait: " + remainingTime).queue();
            return;
        }
        /*fix this madness 
        Thread thr = new Thread() {
            @Override
            public void run() {
                int timer = 84900;
                List<String> threadList = SlopTools.getSlopLoan(lines, userFile);
                while (true) {
                    try {
                        if (Thread.interrupted()) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                        System.out.println(Thread.currentThread().toString());
                        Thread.sleep(1000);
                        timer--;
                        threadList.set(3, String.valueOf(timer));
                        fileCreator = new FileCreator(userFile, event, threadList.get(1), threadList.get(2),
                                threadList.get(3), true);
                    } catch (IOException | InterruptedException e) {

                        e.printStackTrace();
                    }
                }
            }
        };

        Thread thre = new Thread() {
            @Override
            public void run() {
                int timer = 84900;
                List<String> threadList = SlopTools.paySlopLoan(lines, userFile);
                while (true) {
                    if (Thread.interrupted()) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                    try {
                        System.out.println(Thread.currentThread().toString());
                        Thread.sleep(1000);
                        timer--;
                        threadList.set(3, String.valueOf(timer));
                        fileCreator = new FileCreator(userFile, event, threadList.get(1), threadList.get(2),
                                threadList.get(3), true);
                    } catch (IOException | InterruptedException e) {

                        e.printStackTrace();
                    }
                }
            }
        };

        Thread[] threads = { thr, thre };

        if (!isPaying) {
            thre.interrupt();

            threads[0].start();

        } else {
            thr.interrupt();
            
            threads[1].start();
        }*/

    }

    public void getData() {
        String name = event.getMember().getEffectiveName();
        try {
            List<String> data = GetMemberData.getData(name);
            SendMessage.sendMessage(event, "Name: " + data.get(0)
                    + "\nSlops: " + data.get(1)
                    + "\nSlop Debt: " + data.get(2)
                    + "\nTimer: " + data.get(3)).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void payLoan() {
        try {
            lines = SlopTools.paySlopLoan(lines, userFile);

            fileCreator = new FileCreator(userFile, event, lines.get(1), lines.get(2), lines.get(3), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
