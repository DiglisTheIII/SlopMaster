package commands;

import java.io.File;
import java.io.FileWriter;
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
    File timerFile;
    File user;
    List<String> lines;

    public MemberCommands(MessageReceivedEvent event) {
        //Boilerplate variable initialization
        this.event = event;
        userFile = new File("bin\\member\\" + event.getMember().getEffectiveName() + "\\");
        timerFile = new File("bin\\member\\" + event.getMember().getEffectiveName() + "\\" + "timer.txt");
        user = new File(userFile.getAbsolutePath() + "\\" + event.getMember().getEffectiveName() + ".txt");

        try {
            if (user.exists()) {
                lines = Files.readAllLines(Paths.get(user.toURI()));
            } else {
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Creates user folder and file.
    public void createNewFile() {
        try {
            fileCreator = new FileCreator(userFile, event, "", "", "", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getSlopLoan() {
        if (!userFile.exists()) {
            SendMessage.sendMessage(event, "You must register with s$reg first").queue();
            return;
        }
        System.out.println("Lines of userfile from: " + lines.hashCode() + " initialized to lines<String> list");

        int hours = 0, minutes = 0, seconds = 0, timer = 0;

        List<String> userInfo;
        try {
            //Setup member list, timer list, and timer variable
            File userInfoFile = new File("bin\\member\\" + event.getMember().getEffectiveName() + "\\" + event.getMember().getEffectiveName() + ".txt");
            List<String> timerList = Files.readAllLines(Paths.get(timerFile.toURI()));
            userInfo = Files.readAllLines(Paths.get(userInfoFile.toURI()));
            timer = Integer.parseInt(timerList.get(timerList.size() - 1));

            //Setup timer in hours:minutes:seconds
            hours = timer / 3600;
            minutes = (timer % 3600) / 60;
            seconds = timer % 60;

            String remainingTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            int slopDebt = Integer.parseInt(userInfo.get(2));
            if (timer > 0 && slopDebt > 0) {
                SendMessage.sendMessage(event, "You must wait: " + remainingTime).queue();
                return;
            }

            //Sets the loan amount + current amount currently owned
            int ran = Integer.parseInt(userInfo.get(1))
                    + Integer.parseInt(event.getMessage().getContentRaw().split(" ")[1]);

            lines.set(1, String.valueOf(ran));
            lines.set(2, event.getMessage().getContentRaw().split(" ")[1]);

            //Deletes the user file, then recreates it to overwrite any changed information, in this scenario, slops.
            userFile.delete();
            userFile.createNewFile();
            FileWriter fw = new FileWriter(user);

            fw.write(event.getMember().getEffectiveName() + "\n" + lines.get(1) + "\n" + lines.get(2));
            fw.flush();
            fw.close();

            //Start timer file, I need to have it overwrite to one line only, as after a while and after 30+ members it will add up on space.
            new Thread() {
                @Override
                public void run() {
                    int timer = 86400;
                    try {
                        FileWriter tw = new FileWriter(timerFile, false);
                        while (timer > 0) {
                            timerFile.delete();
                            timerFile.createNewFile();
                            timer--;
                            Thread.sleep(1000);
                            tw.write(String.valueOf(timer) + "\n");
                            tw.flush();
                        }
                        tw.close();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Standard getter. Gets data from userFile and parses it to a RestAction<?> message event
    public void getData() {
        String name = event.getMember().getEffectiveName();
        try {
            List<String> data = GetMemberData.getData(name);
            SendMessage.sendMessage(event, "Name: " + data.get(0)
                    + "\nSlops: " + data.get(1)
                    + "\nSlop Debt: " + data.get(2)).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void payLoan() {
        try {
            lines = SlopTools.paySlopLoan(lines, user);

            //Deletes userFile for overwrite
            File newUser = new File(user.getAbsolutePath());
            if (user.exists()) {
                user.delete();
                newUser.createNewFile();
            }

            FileWriter fw = new FileWriter(newUser);
            fw.write(lines.get(0) + "\n" + lines.get(1) + "\n" + lines.get(2));
            fw.flush();
            fw.close();

            List<String> data = GetMemberData.getData(event.getMember().getEffectiveName());

            SendMessage.sendMessage(event, "Loan paid, new balance: "
                    + "\nSlops: " + data.get(1)
                    + "\nSlop Debt: " + data.get(2)).queue();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
