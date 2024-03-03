package tools.gambling;
import java.util.Arrays;
import java.util.List;

public enum Items {
    
    MUNT_JUICE_BOTTLE(0, 100, "Munt Juice Bottle"),
    ACORN_BACTA_FLUID(1, 600, "Acorn Bacta Fluid"),
    THE_DEMON_OF_HATRED(2, 25000, "The Demon Of Hatred"),
    THE_HAND_OF_GOD(3, 65000, "The Hand Of God"),
    BIG_STINKY_FART_BOMB(4, 0, "Big Stinky Fart Bomb");

    private final int VALUE;
    private final int COST;
    private final String NAME;

    Items(final int newValue, final int cost, final String name) {
        VALUE = newValue;
        COST = cost;
        NAME = name;
    }

    public int getValue() {
        return this.VALUE;
    }
    public int getCost() {
        return this.COST;
    }

    private static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

    public String getItemName() {
        return this.NAME;
    }

    private static List<Items> getItems() {
        return Arrays.asList(Items.class.getEnumConstants());
    }

    public static Items getItem(int roll) {
        if(roll == 4) {
            return Items.BIG_STINKY_FART_BOMB;
        }

        String[] names = getNames(Items.class);
        for(int i = 0; i < names.length; i++) {
            if(roll == getItems().get(i).getValue()) {
                return getItems().get(i);
            }
        }
        return null;
    }

    public static Items getItemFromValue(int value) {
        for(int i = 0; i < getItems().size(); i++) {
            if(value == getItems().get(i).getValue()) {
                return getItems().get(i);
            }
        }
        return null;
    }

    public static String getNameFromValue(int value) {
        switch(value) {
            case 0:
                return "Munt Juice Bottle";
            case 1:
                return "Acorn Bacta Fluid";
            case 2:
                return "The Demon Of Hatred";
            case 3:
                return "The Hand Of God";
            case 4:
                return "Big Stinky Fart Bomb";
            default:
                return "";

        }
    }


 }
