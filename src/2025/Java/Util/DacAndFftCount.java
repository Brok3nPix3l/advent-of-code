package Util;

public class DacAndFftCount {
    private long dacCount;
    private long fftCount;
    private long dacAndFftCount;

    public DacAndFftCount(long dacCount, long fftCount, long dacAndFftCount) {
        this.dacCount = dacCount;
        this.fftCount = fftCount;
        this.dacAndFftCount = dacAndFftCount;
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

    @Override
    public String toString() {
        return "DacAndFftCount{" +
                "dacCount=" + dacCount +
                ", fftCount=" + fftCount +
                ", dacAndFftCount=" + dacAndFftCount +
                '}';
    }
}
