package Days;

import Util.Machine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static Util.Misc.hashCodeForBooleanArray;

public class Day10 implements DailyChallenge {
    File inputFile;
    private static final char ACTIVE_LIGHT_INDICATOR = '#';

    public Day10(File inputFile) throws FileNotFoundException {
        new Scanner(inputFile);
        this.inputFile = inputFile;
    }

    public long Part1(boolean debug) {
        long ans = 0L;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            List<Machine> machines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                boolean[] targetLightIndicatorState = new boolean[parts[0].length() - 2];
                boolean[] currentLightIndicatorState = new boolean[parts[0].length() - 2];
                for (int i = 1; i < parts[0].length() - 1; i++) {
                    targetLightIndicatorState[i - 1] = parts[0].charAt(i) == ACTIVE_LIGHT_INDICATOR;
                }
                List<List<Integer>> buttons = new ArrayList<>();
                for (int i = 1; i < parts.length - 1; i++) {
                    buttons.add(Arrays.stream(parts[i].replaceAll("[()]", "").split(",")).map(Integer::parseInt).collect(Collectors.toList()));
                }
                Machine machine = new Machine(targetLightIndicatorState, currentLightIndicatorState, buttons);
                machines.add(machine);
            }
            if (debug) {
                System.out.println("Machines: " + machines);
            }
            for (int i = 0; i < machines.size(); i++) {
                Machine machine = machines.get(i);
                long minPressesRequired = minButtonPressesToGetToTargetState(machine, debug);
                ans += minPressesRequired;
                if (debug) {
                    System.out.println("Min presses required for machine " + machine + ": " + minPressesRequired);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }

    // determine the number of buttons
    // (2^# of buttons) - 1 is the total number of ways to press buttons
    // iterate over all of them and, if the XOR product equals the target, count the number of set bits
    // return the min number of set bits required
    private long minButtonPressesToGetToTargetState(Machine machine, boolean debug) {
        int targetIndicatorLightStateHash = machine.getTargetIndicatorLightState();
        int maxButtonConfiguration = (int) Math.pow(2, machine.getButtons().size());
        if (debug) {
            System.out.println("maxButtonConfiguration: " + maxButtonConfiguration);
        }
        int minButtonPressesRequired = Integer.MAX_VALUE;
        int binaryStringWidth = (int)(Math.log(maxButtonConfiguration) / Math.log(2));
        for (int i = 0; i < maxButtonConfiguration; i++) {
            // each bit corresponds to a coefficient in front of a button
            // if a button is "active", XOR the product
            int xorProduct = 0;
            String binaryString = String.format("%" + binaryStringWidth + "s", Integer.toBinaryString(i)).replace(' ', '0');
            int setBitCount = 0;
            for (int j = 0; j < binaryString.length(); j++) {
                char c = binaryString.charAt(j);
                if (c == '0') {
                    continue;
                }
                xorProduct ^= machine.getButtons().get(machine.getButtons().size() - 1 - j);
                setBitCount++;
            }
            if (debug) {
                System.out.println("binaryString: " + binaryString + " xorProduct: " + xorProduct);
            }
            if (xorProduct == targetIndicatorLightStateHash) {
                minButtonPressesRequired = Math.min(setBitCount, minButtonPressesRequired);
            }
        }
        return minButtonPressesRequired;
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
