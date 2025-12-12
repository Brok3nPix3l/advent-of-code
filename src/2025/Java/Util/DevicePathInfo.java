package Util;

import java.util.Objects;

public class DevicePathInfo {
    String device;
    boolean visitedDac;
    boolean visitedFft;

    public DevicePathInfo(String device, boolean visitedDac, boolean visitedFft) {
        this.device = device;
        this.visitedDac = visitedDac;
        this.visitedFft = visitedFft;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public boolean isVisitedDac() {
        return visitedDac;
    }

    public void setVisitedDac(boolean visitedDac) {
        this.visitedDac = visitedDac;
    }

    public boolean isVisitedFft() {
        return visitedFft;
    }

    public void setVisitedFft(boolean visitedFft) {
        this.visitedFft = visitedFft;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DevicePathInfo that = (DevicePathInfo) o;
        return visitedDac == that.visitedDac && visitedFft == that.visitedFft &&
                Objects.equals(device, that.device);
    }

    @Override
    public int hashCode() {
        return Objects.hash(device, visitedDac, visitedFft);
    }

    @Override
    public String toString() {
        return "DevicePathInfo{" + "device='" + device + '\'' + ", visitedDac=" + visitedDac + ", visitedFft=" +
                visitedFft + '}';
    }
}
