package Days;

import Util.DevicePathInfo;

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
        long ans;
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
            ans = pathsFromDeviceThroughDacAndFftToOut("svr", new HashMap<>(), cableMap, debug, false, false);
//            Map<String, DevicePathInfo> dacAndFftCountForDataPathsPassingThroughDevice = new HashMap<>();
//            dacAndFftCountForDataPathsPassingThroughDevice.put("svr", new DevicePathInfo(1, 0, 0, 0));
//            Queue<String> queue = new LinkedList<>();
//            queue.add("svr");
//            Set<String> visited = new HashSet<>();
//            while (!queue.isEmpty()) {
//                String currentDevice = queue.poll();
//                if (visited.contains(currentDevice)) {
//                    continue;
//                }
//                visited.add(currentDevice);
//                DevicePathInfo currentDeviceDevicePathInfo = dacAndFftCountForDataPathsPassingThroughDevice.get(currentDevice);
//                if (currentDevice.equals("dac")) {
//                    currentDeviceDevicePathInfo.addToDacAndFftCount(currentDeviceDevicePathInfo.getFftCount());
//                    currentDeviceDevicePathInfo.setFftCount(0L);
//                    currentDeviceDevicePathInfo.addToDacCount(currentDeviceDevicePathInfo.getNeitherCount());
//                    currentDeviceDevicePathInfo.setNeitherCount(0L);
//                } else if (currentDevice.equals("fft")) {
//                    currentDeviceDevicePathInfo.addToDacAndFftCount(currentDeviceDevicePathInfo.getDacCount());
//                    currentDeviceDevicePathInfo.setDacCount(0L);
//                    currentDeviceDevicePathInfo.addToFftCount(currentDeviceDevicePathInfo.getNeitherCount());
//                    currentDeviceDevicePathInfo.setNeitherCount(0L);
//                }
//                if (!cableMap.containsKey(currentDevice)) {
//                    continue;
//                }
//                for (String destinationDevice : cableMap.get(currentDevice)) {
//                    DevicePathInfo destinationDeviceDevicePathInfo = dacAndFftCountForDataPathsPassingThroughDevice.computeIfAbsent(destinationDevice, a -> new DevicePathInfo());
//                    destinationDeviceDevicePathInfo.addNeitherCount(currentDeviceDevicePathInfo.getNeitherCount());
//                    destinationDeviceDevicePathInfo.addToDacCount(currentDeviceDevicePathInfo.getDacCount());
//                    destinationDeviceDevicePathInfo.addToFftCount(currentDeviceDevicePathInfo.getFftCount());
//                    destinationDeviceDevicePathInfo.addToDacAndFftCount(currentDeviceDevicePathInfo.getDacAndFftCount());
//                }
//                queue.addAll(cableMap.get(currentDevice));
//            }
//            if (debug) {
//                System.out.println("dacAndFftCountForDataPathsPassingThroughDevice: " + dacAndFftCountForDataPathsPassingThroughDevice);
//            }
//            ans = dacAndFftCountForDataPathsPassingThroughDevice.get("out").getDacAndFftCount();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }

    private long pathsFromDeviceThroughDacAndFftToOut(String currentDevice, Map<DevicePathInfo, Long> memo, Map<String, List<String>> cableMap, boolean debug,
                                                      boolean visitedDac, boolean visitedFft) {
        DevicePathInfo hashKey = new DevicePathInfo(currentDevice, visitedDac, visitedFft);
        if (memo.containsKey(hashKey)) {
            if (debug) {
                System.out.println("memo: '" + hashKey + " " + memo.get(hashKey) +"'");
            }
            return memo.get(hashKey);
        }
        if (currentDevice.equals("out")) {
            return visitedDac && visitedFft ? 1 : 0;
        }
        if (currentDevice.equals("dac")) {
            visitedDac = true;
        }
        if (currentDevice.equals("fft")) {
            visitedFft = true;
        }
        long ans = 0L;
        for (String destinationDevice : cableMap.get(currentDevice)) {
            long paths = pathsFromDeviceThroughDacAndFftToOut(destinationDevice, memo, cableMap, debug,
                    visitedDac, visitedFft);
            ans += paths;
        }
        memo.put(hashKey, ans);
        return ans;
    }
}
