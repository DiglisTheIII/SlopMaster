package tools.gambling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import bin.util.FileIO;

import java.nio.file.Files;
import java.nio.file.Paths;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tools.CustomEmbed;
import util.SendMessage;

public class Blackjack extends ListenerAdapter {


    /*
     * Sorry if this code is hard to read/understand. I wasn't exactly sober when writing it.
     * It works though, and thats all that really matters for a gambling bot. I will try
     * and optimize it at some point, and I will definitely do better on the next gambling
     * game I end up adding (likely roulette).
     */


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        // Initializing the user playing, the messages for the commands, and the file of
        // the user playing.
        String[] message = event.getMessage().getContentRaw().split(" ");
        FileIO user = new FileIO(
                "bin\\member\\" + event.getGuild().getName() + "\\" + event.getMember().getEffectiveName() + ".txt");
        FileIO blackjack = new FileIO(
                "bin\\member\\" + event.getGuild().getName() + "\\" + event.getMember().getEffectiveName() + "_bj.txt");
        if (!blackjack.doesFileExist() && !event.getAuthor().isBot()) {
            blackjack.createFile();
        }

        // Creates first set of cards placed, the two dealer cards, and two player
        // cards, and what suite and card type they will associate with

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
        int bet;
        int postTotal; //The total after all cards are added together.

        ArrayList<String> cards = getCards();

        String[] suites = { "hearts", "spades", "diamonds", "clubs" };

        if (message[0].equals("m$muntjack")) {
            if (message.length == 1) {
                SendMessage.sendMessage(event, "You need to place a bet").queue();
                return;
            }
            bet = Integer.valueOf(message[1]);

            // Creating the actual cards to get a value from

            String dealerCard1 = cards.get(pickDealerCard1);
            String dealerSuite1 = suites[pickDealerSuite1];
            String dealerCard2 = cards.get(pickDealerCard2);
            String dealerSuite2 = suites[pickDealerSuite2];

            String playerCard1 = cards.get(pickPlayerCard1), playerCard2 = cards.get(pickPlayerCard2);
            String playerSuite1 = suites[pickPlayerSuite1], playerSuite2 = suites[pickPlayerSuite2];

            dealerTotal = cardSwitch(dealerCard1, dealerSuite1, event, false);
            postTotal = dealerTotal + cardSwitch(dealerCard2, dealerSuite2, event, true);

            playerTotal = cardSwitch(playerCard1, playerSuite1, event, false)
                    + cardSwitch(playerCard2, playerSuite2, event, false);

            updateBJFile(dealerTotal, postTotal, playerTotal, bet, blackjack);

            // If player goes over 21 on first draw, end game, no losses.
            if (playerTotal > 21) {
                SendMessage.sendMessage(event, "You went over 21. Busted! \nTotal: " + playerTotal).queue();
                return;
            }

            event.getChannel().sendMessageEmbeds(new CustomEmbed(
                    "---- Current game ----",
                    "Game Stats:",
                    "black",
                    "Dealer total: " + String.valueOf(dealerTotal), "\nYour total: " + String.valueOf(playerTotal),
                    "\nHit or Stand? (Remaining Munt Bucks: " + user.readFileLine(1) + ")").build()).queue();
        }

        if (message[0].equals("m$hit")) {
            hit(event, cards, suites);
        }

        if (message[0].equals("m$stand")) {
            stand(event);
        }

    }

    public void updateBJFile(int dealerTotal, int postTotal, int playerTotal, int bet, FileIO blackJackFile) {
        String[] data = {dealerTotal + "\n", postTotal + "\n", playerTotal + "\n", String.valueOf(bet)};

        blackJackFile.writeToFile(data);
    }

    public ArrayList<String> getCards() {
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

        return cards;
    }

    protected void hit(MessageReceivedEvent event, ArrayList<String> cards, String[] suites) {

        int tempCardPick = ThreadLocalRandom.current().nextInt(0, 13);
        int tempSuitePick = ThreadLocalRandom.current().nextInt(0, 4);
        String tempCard = cards.get(tempCardPick);
        String tempSuite = suites[tempSuitePick];

        FileIO user = new FileIO(
            "bin\\member\\" + event.getGuild().getName() + "\\" + event.getMember().getEffectiveName() + ".txt");
        FileIO blackjack = new FileIO(
                "bin\\member\\" + event.getGuild().getName() + "\\" + event.getMember().getEffectiveName() + "_bj.txt");

            int playerTotal = Integer.valueOf(blackjack.readFileLine(2));
            int playerMunts = Integer.valueOf(user.readFileLine(1));
            int bet = Integer.valueOf(blackjack.readFileLine(3));


            playerTotal = playerTotal + cardSwitch(tempCard, tempSuite, event, false);
            SendMessage.sendMessage(event, "Dealer placed a " + tempCard + " of " + tempSuite
                    + "\nYour total: " + playerTotal).queue();

            if (playerTotal > 21) {
                playerMunts = playerMunts - bet;
                SendMessage.sendMessage(event, "Bust! You went over 21. Remaining munts: " + playerMunts).queue();

                user.modifyLineInFile(String.valueOf(playerMunts), 1);
                return;
            }

            blackjack.modifyLineInFile(String.valueOf(playerTotal), 2);
    }

    protected void stand(MessageReceivedEvent event) {
        FileIO user = new FileIO(
            "bin\\member\\" + event.getGuild().getName() + "\\" + event.getMember().getEffectiveName() + ".txt");
        FileIO blackjack = new FileIO(
            "bin\\member\\" + event.getGuild().getName() + "\\" + event.getMember().getEffectiveName() + "_bj.txt");
        try {
            ArrayList<String> info = (ArrayList<String>) Files.readAllLines(Paths.get(blackjack.getURI()));
            int dealerTotal = Integer.valueOf(info.get(1));
            int playerTotal = Integer.valueOf(info.get(2));
            int bet = Integer.valueOf(info.get(3));
            int change;

            SendMessage.sendMessage(event, "Dealer Total: " + dealerTotal).queue();
            if ((playerTotal > dealerTotal) && playerTotal <= 21) {
                change = Integer.valueOf(user.readFileLine(1)) + bet;
                user.modifyLineInFile(String.valueOf(change), 1);
                SendMessage.sendMessage(event, "You win: " + bet + " Munt Bucks!").queue();
                return;
            } else if (dealerTotal > 21) {
                change = Integer.valueOf(user.readFileLine(1)) + bet;
                user.modifyLineInFile(String.valueOf(change), 1);
                SendMessage.sendMessage(event, "You win: " + bet + " Munt Bucks!").queue();
                return;
            } else if (playerTotal < dealerTotal) {
                change = Integer.valueOf(user.readFileLine(1)) - bet;
                user.modifyLineInFile(String.valueOf(change), 1);
                SendMessage.sendMessage(event, "You lose: " + bet + " Munt Bucks!").queue();
                return;
            } else if (playerTotal > 21) {
                change = Integer.valueOf(user.readFileLine(1)) - bet;
                user.modifyLineInFile(String.valueOf(change), 1);
                SendMessage.sendMessage(event, "You lose: " + bet + " Munt Bucks!").queue();
                return;
            } else if (playerTotal == dealerTotal) {
                SendMessage.sendMessage(event, "You and the dealer have the same value. No winner or loser.").queue();
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
                    return 10;
                case "queen":
                    return 10;
                case "king":
                    return 10;
                case "ace":
                    return 10;
                case "two":
                    return 2;
                case "three":
                    return 3;
                case "four":
                    return 4;
                case "five":
                    return 5;
                case "six":
                    return 6;
                case "seven":
                    return 7;
                case "eight":
                    return 8;
                case "nine":
                    return 9;
                case "ten":
                    return 10;
                default:
                    return 0;
            }
        }
    }
}
