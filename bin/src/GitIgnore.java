import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GitIgnore {
    
    public static String token;

    public static String getToken() throws IOException {
        setToken();
        return GitIgnore.token;
    }

    public static void setToken() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("bin\\src\\gitignore.txt"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while(line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String token = sb.toString().replaceAll(" ", "");
            GitIgnore.token = token;
        } finally {
            br.close();
        }

    }

}
