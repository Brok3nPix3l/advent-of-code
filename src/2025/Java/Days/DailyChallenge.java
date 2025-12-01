package Days;

public interface DailyChallenge {
    default int Part1() {
        return Part1(false);
    }
    public int Part1(boolean debug);
    default int Part2() {
        return Part2(false);
    }
    public int Part2(boolean debug);
}
