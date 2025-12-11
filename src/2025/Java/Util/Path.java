package Util;

import java.util.HashSet;
import java.util.Set;

public class Path {
    private String currentDevice;
    private final Set<String> visitedDevices;

    public Path(String currentDevice) {
        this.currentDevice = currentDevice;
        this.visitedDevices = new HashSet<>();
    }

    public Path(Path path) {
        this.currentDevice = path.currentDevice;
        this.visitedDevices = new HashSet<>(path.visitedDevices);
    }

    public String getCurrentDevice() {
        return currentDevice;
    }

    public void visit(String newDevice) {
        this.visitedDevices.add(this.currentDevice);
        this.currentDevice = newDevice;
    }

    public boolean hasVisited(String device) {
        return this.visitedDevices.contains(device);
    }

    @Override
    public String toString() {
        return "Path{" +
                "currentDevice='" + currentDevice + '\'' +
                ", visitedDevices=" + visitedDevices +
                '}';
    }
}
