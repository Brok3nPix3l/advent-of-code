package Days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day11 implements DailyChallenge {
    File inputFile;

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
        throw new RuntimeException("Not implemented yet");
//        try (Scanner scanner = new Scanner(this.inputFile)) {
//            while (scanner.hasNextLine()) {
//            }
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }
}
