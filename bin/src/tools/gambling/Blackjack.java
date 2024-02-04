package tools.gambling;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import util.SendMessage;

public class Blackjack extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String activeUser = event.getMember().getEffectiveName();
        String[] message = event.getMessage().getContentRaw().split(" ");
        String command = message[0];
        String commandPref = message[0].substring(0, 2).equals("s$") ? "s$" : "";
        command = message[0].substring(2);

        if (message[0].equals("s$blackjack")) {
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

            String dealerCard1 = cards.get(pickDealerCard1);
            String dealerSuite1 = suites[pickDealerSuite1];
            String dealerCard2 = cards.get(pickDealerCard2);
            String dealerSuite2 = suites[pickDealerSuite2];

            String playerCard1 = cards.get(pickPlayerCard1), playerCard2 = cards.get(pickPlayerCard2);
            String playerSuite1 = suites[pickPlayerSuite1], playerSuite2 = suites[pickPlayerSuite2];

            dealerTotal = cardSwitch(dealerCard1, dealerSuite1, event) + cardSwitch(dealerCard2, dealerSuite2, event);

            playerTotal = cardSwitch(playerCard1, playerSuite1, event) + cardSwitch(playerCard2, playerSuite2, event);

            File blackJackFile = new File("bin\\member\\" + event.getMember().getEffectiveName() + "\\blackjack.txt");

            try {
                FileWriter fw = new FileWriter(blackJackFile);
                if(blackJackFile.exists()) {
                    blackJackFile.delete();
                    blackJackFile.createNewFile();
                } else {
                    blackJackFile.createNewFile();
                }
                fw.write(dealerTotal + "\n" + playerTotal);
                fw.flush();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            SendMessage.sendMessage(event, "Dealer total: " + String.valueOf(dealerTotal)
                    + "\nYour total:" + String.valueOf(playerTotal)
                    + "\nHit or Stand?").queue();

            
        }

    }

    public int hit(MessageReceivedEvent event, ArrayList<String> cards, String[] suites, int playerTotal,
            int dealerTotal) {

        int tempCardPick = ThreadLocalRandom.current().nextInt(0, 13);
        int tempSuitePick = ThreadLocalRandom.current().nextInt(0, 4);
        String tempCard = cards.get(tempCardPick);
        String tempSuite = suites[tempSuitePick];

        playerTotal = playerTotal + cardSwitch(tempCard, tempSuite, event);

        SendMessage.sendMessage(event, "Dealer total: " + String.valueOf(dealerTotal)
                + "\nYour total:" + String.valueOf(playerTotal)
                + "\nHit or Stand?").queue();

        return playerTotal;

    }

    protected static int cardSwitch(String card, String suite, MessageReceivedEvent event) {
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
    }
}
