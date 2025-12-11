package Days;

import Util.PassageCounts;
import Util.Path;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day11 implements DailyChallenge {
    File inputFile;
    private static final String DAC = "dac";
    private static final String FFT = "fft";

    public Day11(File inputFile) throws FileNotFoundException {
        new Scanner(inputFile);
        this.inputFile = inputFile;
    }

    public long Part1(boolean debug) {
        long ans = 0L;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            Map<String, List<String>> cableMap = new HashMap<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(" ");
                String sourceDevice = tokens[0].substring(0, tokens[0].length()-1); // remove trailing ":"
                List<String> destinationDevices = new ArrayList<>(Arrays.asList(tokens).subList(1, tokens.length));
                cableMap.put(sourceDevice, destinationDevices);
            }
            if (debug) {
                System.out.println("cableMap: " + cableMap);
            }
            Queue<String> queue = new LinkedList<>();
            queue.add("you");
            while (!queue.isEmpty()) {
                String currentDevice = queue.poll();
                if (currentDevice.equals("out")) {
                    ans++;
                    continue;
                }
                queue.addAll(cableMap.get(currentDevice));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }

    public long Part2(boolean debug) {
        long ans = 0L;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            Map<String, List<String>> cableMap = new HashMap<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(" ");
                String sourceDevice = tokens[0].substring(0, tokens[0].length()-1); // remove trailing ":"
                List<String> destinationDevices = new ArrayList<>(Arrays.asList(tokens).subList(1, tokens.length));
                cableMap.put(sourceDevice, destinationDevices);
            }
            if (debug) {
                System.out.println("cableMap: " + cableMap);
            }
            Map<String, PassageCounts> dacAndFftCountForDataPathsPassingThroughDevice = new HashMap<>();
            dacAndFftCountForDataPathsPassingThroughDevice.put("svr", new PassageCounts(1, 0, 0, 0));
            Queue<String> queue = new LinkedList<>();
            queue.add("svr");
            Set<String> visited = new HashSet<>();
            while (!queue.isEmpty()) {
                String currentDevice = queue.poll();
                if (visited.contains(currentDevice)) {
                    continue;
                }
                visited.add(currentDevice);
                PassageCounts currentDevicePassageCounts = dacAndFftCountForDataPathsPassingThroughDevice.get(currentDevice);
                if (currentDevice.equals("dac")) {
                    currentDevicePassageCounts.addToDacAndFftCount(currentDevicePassageCounts.getFftCount());
                    currentDevicePassageCounts.setFftCount(0L);
                    currentDevicePassageCounts.addToDacCount(currentDevicePassageCounts.getNeitherCount());
                    currentDevicePassageCounts.setNeitherCount(0L);
                } else if (currentDevice.equals("fft")) {
                    currentDevicePassageCounts.addToDacAndFftCount(currentDevicePassageCounts.getDacCount());
                    currentDevicePassageCounts.setDacCount(0L);
                    currentDevicePassageCounts.addToFftCount(currentDevicePassageCounts.getNeitherCount());
                    currentDevicePassageCounts.setNeitherCount(0L);
                }
                if (!cableMap.containsKey(currentDevice)) {
                    continue;
                }
                for (String destinationDevice : cableMap.get(currentDevice)) {
                    PassageCounts destinationDevicePassageCounts = dacAndFftCountForDataPathsPassingThroughDevice.computeIfAbsent(destinationDevice, a -> new PassageCounts());
                    destinationDevicePassageCounts.addNeitherCount(currentDevicePassageCounts.getNeitherCount());
                    destinationDevicePassageCounts.addToDacCount(currentDevicePassageCounts.getDacCount());
                    destinationDevicePassageCounts.addToFftCount(currentDevicePassageCounts.getFftCount());
                    destinationDevicePassageCounts.addToDacAndFftCount(currentDevicePassageCounts.getDacAndFftCount());
                }
                queue.addAll(cableMap.get(currentDevice));
            }
            if (debug) {
                System.out.println("dacAndFftCountForDataPathsPassingThroughDevice: " + dacAndFftCountForDataPathsPassingThroughDevice);
            }
            ans = dacAndFftCountForDataPathsPassingThroughDevice.get("out").getDacAndFftCount();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }

    private PassageCounts pathsFromDeviceThroughDacAndFftToOut(Path currentDataPath, Map<String, PassageCounts> memo, Map<String, List<String>> cableMap, boolean debug) {
        if (memo.containsKey(currentDataPath.getCurrentDevice())) {
            if (debug) {
                System.out.println("memo: '" + currentDataPath.getCurrentDevice() + ": " + memo.get(currentDataPath.getCurrentDevice()) + "'");
            }
            // if current contains dac,
            if (currentDataPath.hasVisited(DAC)) {
                memo.get(currentDataPath.getCurrentDevice()).setDacCount(memo.get(currentDataPath.getCurrentDevice()).getDacCount() + 1);
            }
            if (currentDataPath.hasVisited(FFT)) {
                memo.get(currentDataPath.getCurrentDevice()).setFftCount(memo.get(currentDataPath.getCurrentDevice()).getFftCount() + 1);
            }
            return memo.get(currentDataPath.getCurrentDevice());
        }
        if (currentDataPath.getCurrentDevice().equals("out")) {
            if (debug) {
                System.out.println("reached 'out' currentDataPath: " + currentDataPath);
            }
            if (currentDataPath.hasVisited(DAC) && currentDataPath.hasVisited(FFT)) {
                return new PassageCounts(0, 0, 0, 1);
            }
            if (currentDataPath.hasVisited(DAC)) {
                return new PassageCounts(0, 1, 0, 0);
            }
            if (currentDataPath.hasVisited(FFT)) {
                return new PassageCounts(0, 0, 1, 0);
            }
            return new PassageCounts(1, 0, 0, 0);
        }
        PassageCounts ans = new PassageCounts();
        for (String destinationDevice : cableMap.get(currentDataPath.getCurrentDevice())) {
            if (currentDataPath.hasVisited(destinationDevice)) {
                continue;
            }
            Path destinationDevicePath = new Path(currentDataPath);
            destinationDevicePath.visit(destinationDevice);
            PassageCounts pathsFromDestinationDeviceThroughDacAndFftToOut = pathsFromDeviceThroughDacAndFftToOut(destinationDevicePath, memo, cableMap, debug);
            ans.setDacCount(ans.getDacCount() + pathsFromDestinationDeviceThroughDacAndFftToOut.getDacCount());
            ans.setFftCount(ans.getFftCount() + pathsFromDestinationDeviceThroughDacAndFftToOut.getFftCount());
            ans.setDacAndFftCount(ans.getDacAndFftCount() + pathsFromDestinationDeviceThroughDacAndFftToOut.getDacAndFftCount());
        }
        return ans;
    }
}
