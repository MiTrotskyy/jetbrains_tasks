import java.util.regex.Pattern;

public class Matches {
    public static void main(String[] args) {
        String text = "";
        String regex = "";
        System.out.println(matches(text, regex));
    }
    public static boolean matches(String text, String regex) {
        try {
            return Pattern.compile(regex).matcher(text).matches();
        } catch (Exception e) {
            return false;
        }
    }
}
