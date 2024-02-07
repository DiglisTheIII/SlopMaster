package tools;

import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.Color;

public class CustomEmbed extends EmbedBuilder {
    
    public CustomEmbed(String title, String description, String color, String ... fields) {
        this.setTitle(title);
        this.setDescription(description);
        int iterator = 1;
        for(String s : fields) {
            this.addField(String.valueOf(iterator) + ". ", s, false);
            iterator++;
        }

        this.setColor(color(color));
    }

    protected Color color(String color) {
        switch(color.toLowerCase()) {
            case "black":
                return Color.BLACK;
            case "gray":
                return Color.GRAY;
            case "blue":
                return Color.BLUE;
            default:
                return Color.WHITE;
        }
        
    }
}
