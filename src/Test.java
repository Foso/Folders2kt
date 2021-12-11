import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static Comparator<String> naturalOrdering() {
        final Pattern compile = Pattern.compile("(\\d+)|(\\D+)");
        return (s1, s2) -> {
            final Matcher matcher1 = compile.matcher(s1);
            final Matcher matcher2 = compile.matcher(s2);
            while (true) {
                final boolean found1 = matcher1.find();
                final boolean found2 = matcher2.find();
                if (!found1 || !found2) {
                    return Boolean.compare(found1, found2);
                } else if (!matcher1.group().equals(matcher2.group())) {
                    if (matcher1.group(1) == null || matcher2.group(1) == null) {
                        return matcher1.group().compareTo(matcher2.group());
                    } else {
                        return Integer.valueOf(matcher1.group(1)).compareTo(Integer.valueOf(matcher2.group(1)));
                    }
                }
            }
        };
    }
}
