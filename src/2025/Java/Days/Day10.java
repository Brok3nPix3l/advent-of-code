package Days;

import Util.Machine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day10 implements DailyChallenge {
    File inputFile;
    private static final char INACTIVE_LIGHT_INDICATOR = '.';
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
            // dfs recursively pressing each combination of buttons until target state is achieved
            // how to handle pressing the same button over and over and just infinitely toggling the state in a loop?
            // record each state observed, and if we repeat then terminate that traversal
            for (Machine machine : machines) {
                if (debug) {
                    System.out.println("Target light indicator state: " + Arrays.toString(machine.getTargetIndicatorLightState()));
                }
                Map<Integer,Long> minPressesFromState = new HashMap<>();
                long minPressesRequired = minButtonPressesToGetToTargetState(machine, minPressesFromState, debug);
                if (debug) {
                    System.out.println("Min presses from state: " + minPressesFromState);
                }
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

    private long minButtonPressesToGetToTargetState(Machine machine, Map<Integer,Long> minPressesFromState, boolean debug) {
        if (Arrays.equals(machine.getCurrentIndicatorLightState(), machine.getTargetIndicatorLightState())) {
            return 0;
        }
        if (minPressesFromState.containsKey(hashCodeForBooleanArray(machine.getCurrentIndicatorLightState()))) {
            if (minPressesFromState.get(hashCodeForBooleanArray(machine.getCurrentIndicatorLightState())) == -1L) {
                return Long.MAX_VALUE;
            }
            return minPressesFromState.get(hashCodeForBooleanArray(machine.getCurrentIndicatorLightState()));
        }
        minPressesFromState.put(hashCodeForBooleanArray(machine.getCurrentIndicatorLightState()), -1L);
        // determine which lights are in the wrong state
        // try pressing all buttons with at least one of those lights on it
//        boolean[] lightsToChange = new boolean[machine.getTargetIndicatorLightState().length];
//        for (int i = 0; i < machine.getTargetIndicatorLightState().length; i++) {
//            if (machine.getTargetIndicatorLightState()[i] != machine.getCurrentIndicatorLightState()[i]) {
//                lightsToChange[i] = true;
//            }
//        }
//        Set<Integer> buttonsToPress = new HashSet<>();
//        for (int i = 0; i < lightsToChange.length; i++) {
//            if (!lightsToChange[i]) {
//                continue;
//            }
//            for (int j = 0; j < machine.getButtons().size(); j++) {
//                if (machine.getButtons().get(j).contains(i)) {
//                    buttonsToPress.add(j);
//                }
//            }
//        }
//        if (debug) {
//            System.out.println("Current light indicator state: " + Arrays.toString(machine.getCurrentIndicatorLightState()));
//            System.out.println("Buttons to press: " + buttonsToPress);
//        }
//        for (int i : buttonsToPress) {
        long minButtonPressesRequired = Long.MAX_VALUE;
        for (int i = 0; i < machine.getButtons().size(); i++) {
            Machine newMachine = new Machine(machine);
            newMachine.pressButton(i);
            int newStateHash = hashCodeForBooleanArray(newMachine.getCurrentIndicatorLightState());
            // if minPressesFromState.get(newStateHash) == -1, that means another call stack is currently trying to solve this same problem
            // terminate and let the other one solve it
            if (minPressesFromState.containsKey(newStateHash)) {
                if (minPressesFromState.get(newStateHash) == -1L) {
                    continue;
                }
                minButtonPressesRequired = Math.min(minPressesFromState.get(newStateHash) + 1, minButtonPressesRequired);
            }
            long pressesRequired = minButtonPressesToGetToTargetState(newMachine, minPressesFromState, debug);
            minPressesFromState.put(newStateHash, pressesRequired);
            minButtonPressesRequired = Math.min(pressesRequired, minButtonPressesRequired);
        }
        return minButtonPressesRequired + 1;
    }

    private Integer hashCodeForBooleanArray(boolean[] array) {
        int hash = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i]) {
                hash |= (1 << i);
            }
        }
        return hash;
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
