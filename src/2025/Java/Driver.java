import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

public class Driver {
    public static void main(String[] args) {
        DailyChallenge dailyChallenge;
        try {
            dailyChallenge = new Day1(new File(Objects.requireNonNull(Driver.class.getClassLoader().getResource("day1.txt")).getFile()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(dailyChallenge.Part1());
    }
}
