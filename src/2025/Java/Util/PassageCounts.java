package Util;

public class PassageCounts {
    private long neitherCount;
    private long dacCount;
    private long fftCount;
    private long dacAndFftCount;

    public PassageCounts() {
        this.neitherCount = 0;
        this.dacCount = 0;
        this.fftCount = 0;
        this.dacAndFftCount = 0;
    }

    public PassageCounts(long neitherCount, long dacCount, long fftCount, long dacAndFftCount) {
        this.neitherCount = neitherCount;
        this.dacCount = dacCount;
        this.fftCount = fftCount;
        this.dacAndFftCount = dacAndFftCount;
    }

    public long getNeitherCount() {
        return neitherCount;
    }

    public void setNeitherCount(long neitherCount) {
        this.neitherCount = neitherCount;
    }

    public void addNeitherCount(long neitherCount) {
        this.neitherCount += neitherCount;
    }

    public long getDacCount() {
        return dacCount;
    }

    public void setDacCount(long dacCount) {
        this.dacCount = dacCount;
    }

    public long getFftCount() {
        return fftCount;
    }

    public void setFftCount(long fftCount) {
        this.fftCount = fftCount;
    }

    public long getDacAndFftCount() {
        return dacAndFftCount;
    }

    public void setDacAndFftCount(long dacAndFftCount) {
        this.dacAndFftCount = dacAndFftCount;
    }

    public void addToDacCount(long dacCount) {
        this.dacCount += dacCount;
    }
    public void addToFftCount(long fftCount) {
        this.fftCount += fftCount;
    }
    public void addToDacAndFftCount(long fftCount) {
        this.dacAndFftCount += fftCount;
    }

    @Override
    public String toString() {
        return "PassageCounts{" +
                "neitherCount=" + neitherCount +
                ", dacCount=" + dacCount +
                ", fftCount=" + fftCount +
                ", dacAndFftCount=" + dacAndFftCount +
                '}';
    }
}
