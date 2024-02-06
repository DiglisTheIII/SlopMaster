package tools.gambling;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedWriter;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tools.GetMemberData;
import tools.SlopTools;
import util.SendMessage;

public class Blackjack extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String activeUser = event.getMember().getEffectiveName();
        String[] message = event.getMessage().getContentRaw().split(" ");
        String command = message[0];
        String commandPref = message[0].substring(0, 2).equals("s$") ? "s$" : "";
        command = message[0].substring(2);
        File userFile = new File("bin\\member\\" + event.getMember().getEffectiveName() + "\\");
        List<String> userData;

        int pickDealerCard1 = ThreadLocalRandom.current().nextInt(0, 13);
        int pickDealerSuite1 = ThreadLocalRandom.current().nextInt(0, 4);
        int pickDealerCard2 = ThreadLocalRandom.current().nextInt(0, 13);
        int pickDealerSuite2 = ThreadLocalRandom.current().nextInt(0, 4);
        int pickPlayerCard1 = ThreadLocalRandom.current().nextInt(0, 13);
        int pickPlayerSuite1 = ThreadLocalRandom.current().nextInt(0, 4);
        int pickPlayerCard2 = ThreadLocalRandom.current().nextInt(0, 13);
        int pickPlayerSuite2 = ThreadLocalRandom.current().nextInt(0, 4);
        int dealerTotal = 0;
        int playerTotal = 0;

        ArrayList<String> cards = new ArrayList<String>();
        cards.add("jack");
        cards.add("king");
        cards.add("queen");
        cards.add("ace");
        cards.add("two");
        cards.add("three");
        cards.add("four");
        cards.add("five");
        cards.add("six");
        cards.add("seven");
        cards.add("eight");
        cards.add("nine");
        cards.add("ten");

        String[] suites = { "hearts", "spades", "diamonds", "clubs" };
        if (message[0].equals("s$blackjack")) {
            if(message.length == 1) {
                SendMessage.sendMessage(event, "You need to place a bet").queue();
                return;
            }
            int bet = Integer.valueOf(message[1]);

            String dealerCard1 = cards.get(pickDealerCard1);
            String dealerSuite1 = suites[pickDealerSuite1];
            String dealerCard2 = cards.get(pickDealerCard2);
            String dealerSuite2 = suites[pickDealerSuite2];

            String playerCard1 = cards.get(pickPlayerCard1), playerCard2 = cards.get(pickPlayerCard2);
            String playerSuite1 = suites[pickPlayerSuite1], playerSuite2 = suites[pickPlayerSuite2];

            dealerTotal = cardSwitch(dealerCard1, dealerSuite1, event, false);
            int postTotal = dealerTotal + cardSwitch(dealerCard2, dealerSuite2, event, true);

            playerTotal = cardSwitch(playerCard1, playerSuite1, event, false)
                    + cardSwitch(playerCard2, playerSuite2, event, false);

            File blackJackFile = new File("bin\\member\\" + event.getMember().getEffectiveName() + "\\blackjack.txt");

            try {
                FileWriter fw = new FileWriter(blackJackFile);
                if (blackJackFile.exists()) {
                    blackJackFile.delete();
                    blackJackFile.createNewFile();
                } else {
                    blackJackFile.createNewFile();
                }
                fw.write(dealerTotal + "\n" + postTotal + "\n" + playerTotal + "\n" + String.valueOf(bet));
                fw.flush();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (playerTotal > 21) {
                SendMessage.sendMessage(event, "You went over 21. Busted! \nTotal: " + playerTotal).queue();

                return;
            }
            try {
                userData = GetMemberData.getData(event.getMember().getEffectiveName());

                SendMessage.sendMessage(event, "Dealer total: " + String.valueOf(dealerTotal)
                        + "\nYour total:" + String.valueOf(playerTotal)
                        + "\nHit or Stand? (Remaining slops: " + userData.get(1)).queue();
            } catch (IOException e) {

            }
        }

        if (message[0].equals("s$hit")) {
            hit(event, cards, suites);
        }

        if(message[0].equals("s$stand")) {
            stand(event);
        }

    }

    public void hit(MessageReceivedEvent event, ArrayList<String> cards, String[] suites) {

        int tempCardPick = ThreadLocalRandom.current().nextInt(0, 13);
        int tempSuitePick = ThreadLocalRandom.current().nextInt(0, 4);
        String tempCard = cards.get(tempCardPick);
        String tempSuite = suites[tempSuitePick];

        File blackjack = new File("bin\\member\\" + event.getMember().getEffectiveName() + "\\blackjack.txt");
        File userFile = new File("bin\\member\\" + event.getMember().getEffectiveName() + "\\" + event.getMember().getEffectiveName() + ".txt");

        try {

            List<String> userData = GetMemberData.getData(event.getMember().getEffectiveName());
            ArrayList<String> user = (ArrayList<String>) Files.readAllLines(Paths.get(blackjack.toURI()));
            int dealerTotal = Integer.valueOf(user.get(1));
            int playerTotal = Integer.valueOf(user.get(2));
            int playerSlops = Integer.valueOf(userData.get(1));
            int bet = Integer.valueOf(user.get(3));

            BufferedWriter writer = new BufferedWriter(new FileWriter(blackjack));

            playerTotal = playerTotal + cardSwitch(tempCard, tempSuite, event, false);
            SendMessage.sendMessage(event, "Dealer placed a " + tempCard + " of " + tempSuite
                    + "Your total: " + playerTotal).queue();

            if (playerTotal > 21) {
                writer.close();
                playerSlops = playerSlops - bet;
                SendMessage.sendMessage(event, "Bust! You went over 21. Remaining slops: " + playerSlops).queue();

                new SlopTools(new File("bin\\member\\" + event.getMember().getEffectiveName() + "\\" + event.getMember().getEffectiveName() + ".txt")).updateSlops(playerSlops);
                return;
            }

            user.set(2, String.valueOf(playerTotal));

            blackjack.delete();
            for (String s : user) {
                writer.write(s + "\n");
                writer.flush();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void stand(MessageReceivedEvent event) {
        File blackjack = new File("bin\\member\\" + event.getMember().getEffectiveName() + "\\blackjack.txt");
        File user = new File("bin\\member\\" + event.getMember().getEffectiveName() + "\\" + event.getMember().getEffectiveName() + ".txt");
        try {
            ArrayList<String> info = (ArrayList<String>) Files.readAllLines(Paths.get(blackjack.toURI()));
            ArrayList<String> userInfo = (ArrayList<String>) Files.readAllLines(Paths.get(user.toURI()));

            int dealerTotal = Integer.valueOf(info.get(1));
            int playerTotal = Integer.valueOf(info.get(2));
            int bet = Integer.valueOf(info.get(3));
            int change;
            SlopTools slopTools = new SlopTools(user);

            if((playerTotal > dealerTotal) && playerTotal <= 21) {
                change = Integer.valueOf(userInfo.get(1)) + bet;
                slopTools.updateSlops(change);
                SendMessage.sendMessage(event, "You win: " + bet + " slops!").queue();
                return;
            } else if(dealerTotal > 21) {
                change = Integer.valueOf(userInfo.get(1)) + bet;
                slopTools.updateSlops(change);
                SendMessage.sendMessage(event, "You win: " + bet + " slops!").queue();
                return;
            } else if(playerTotal < dealerTotal) {
                change = Integer.valueOf(userInfo.get(1)) - bet;
                slopTools.updateSlops(change);
                SendMessage.sendMessage(event, "You lose: " + bet + " slops!").queue();
                return;
            } else if(playerTotal > 21) {
                change = Integer.valueOf(userInfo.get(1)) - bet;
                slopTools.updateSlops(change);
                SendMessage.sendMessage(event, "You lose: " + bet + " slops!").queue();
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static int cardSwitch(String card, String suite, MessageReceivedEvent event, boolean isSecondDeal) {

        if (!isSecondDeal) {
            switch (card) {
                case "jack":
                    SendMessage.sendMessage(event, "Dealer placed a jack: 10").queue();
                    return 10;
                case "queen":
                    SendMessage.sendMessage(event, "Dealer placed a queen: 10").queue();
                    return 10;
                case "king":
                    SendMessage.sendMessage(event, "Dealer placed a king: 10").queue();
                    return 10;
                case "ace":
                    SendMessage.sendMessage(event, "Dealer placed an ace: 10").queue();
                    return 10;
                case "two":
                    SendMessage.sendMessage(event, "Dealer placed a two of " + suite + ": 2").queue();
                    return 2;
                case "three":
                    SendMessage.sendMessage(event, "Dealer placed a three of " + suite + ": 3").queue();
                    return 3;
                case "four":
                    SendMessage.sendMessage(event, "Dealer placed a four of " + suite + ": 4").queue();
                    return 4;
                case "five":
                    SendMessage.sendMessage(event, "Dealer placed a five of " + suite + ": 5").queue();
                    return 5;
                case "six":
                    SendMessage.sendMessage(event, "Dealer placed a six of " + suite + ": 6").queue();
                    return 6;
                case "seven":
                    SendMessage.sendMessage(event, "Dealer placed a seven of " + suite + ": 7").queue();
                    return 7;
                case "eight":
                    SendMessage.sendMessage(event, "Dealer placed an eight of " + suite + ": 8").queue();
                    return 8;
                case "nine":
                    SendMessage.sendMessage(event, "Dealer placed a nine of " + suite + ": 9").queue();
                    return 9;
                case "ten":
                    SendMessage.sendMessage(event, "Dealer placed a ten of " + suite + ": 10").queue();
                    return 10;
                default:
                    return 0;
            }
        } else {
            switch (card) {
                case "jack":
                    // SendMessage.sendMessage(event, "Dealer placed a jack: 10").queue();
                    return 10;
                case "queen":
                    // SendMessage.sendMessage(event, "Dealer placed a queen: 10").queue();
                    return 10;
                case "king":
                    // SendMessage.sendMessage(event, "Dealer placed a king: 10").queue();
                    return 10;
                case "ace":
                    // SendMessage.sendMessage(event, "Dealer placed an ace: 10").queue();
                    return 10;
                case "two":
                    // SendMessage.sendMessage(event, "Dealer placed a two of " + suite + ":
                    // 2").queue();
                    return 2;
                case "three":
                    // SendMessage.sendMessage(event, "Dealer placed a three of " + suite + ":
                    // 3").queue();
                    return 3;
                case "four":
                    // SendMessage.sendMessage(event, "Dealer placed a four of " + suite + ":
                    // 4").queue();
                    return 4;
                case "five":
                    // SendMessage.sendMessage(event, "Dealer placed a five of " + suite + ":
                    // 5").queue();
                    return 5;
                case "six":
                    // SendMessage.sendMessage(event, "Dealer placed a six of " + suite + ":
                    // 6").queue();
                    return 6;
                case "seven":
                    // .sendMessage(event, "Dealer placed a seven of " + suite + ": 7").queue();
                    return 7;
                case "eight":
                    // SendMessage.sendMessage(event, "Dealer placed an eight of " + suite + ":
                    // 8").queue();
                    return 8;
                case "nine":
                    // SendMessage.sendMessage(event, "Dealer placed a nine of " + suite + ":
                    // 9").queue();
                    return 9;
                case "ten":
                    // SendMessage.sendMessage(event, "Dealer placed a ten of " + suite + ":
                    // 10").queue();
                    return 10;
                default:
                    return 0;
            }
        }
    }
}
