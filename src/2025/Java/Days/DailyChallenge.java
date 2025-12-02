package Days;

public interface DailyChallenge {
    default long Part1() {
        return Part1(false);
    }
    public long Part1(boolean debug);
    default long Part2() {
        return Part2(false);
    }
    public long Part2(boolean debug);
}
