package Days;

import Util.Machine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import com.microsoft.z3.*;

public class Day10 implements DailyChallenge {
    private static final char ACTIVE_LIGHT_INDICATOR = '#';
    File inputFile;

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
                    buttons.add(Arrays.stream(parts[i].replaceAll("[()]", "").split(",")).map(Integer::parseInt)
                            .collect(Collectors.toList()));
                }
                Machine machine = new Machine(targetLightIndicatorState, currentLightIndicatorState, buttons,
                        new HashMap<>(), new HashMap<>());
                machines.add(machine);
            }
            if (debug) {
                System.out.println("Machines: " + machines);
            }
            for (int i = 0; i < machines.size(); i++) {
                Machine machine = machines.get(i);
//                long minPressesRequired = minButtonPressesToGetToTargetState(machine, debug);
//                ans += minPressesRequired;
                if (debug) {
//                    System.out.println("Min presses required for machine " + machine + ": " +
//                    minPressesRequired);
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
//    private long minButtonPressesToGetToTargetState(Machine machine, boolean debug) {
//        int targetIndicatorLightStateHash = machine.getTargetIndicatorLightState();
//        int maxButtonConfiguration = (int) Math.pow(2, machine.getButtons().size());
//        if (debug) {
//            System.out.println("maxButtonConfiguration: " + maxButtonConfiguration);
//        }
//        int minButtonPressesRequired = Integer.MAX_VALUE;
//        int binaryStringWidth = (int)(Math.log(maxButtonConfiguration) / Math.log(2));
//        for (int i = 0; i < maxButtonConfiguration; i++) {
//            // each bit corresponds to a coefficient in front of a button
//            // if a button is "active", XOR the product
//            int xorProduct = 0;
//            String binaryString = String.format("%" + binaryStringWidth + "s", Integer.toBinaryString(i))
//            .replace(' ', '0');
//            int setBitCount = 0;
//            for (int j = 0; j < binaryString.length(); j++) {
//                char c = binaryString.charAt(j);
//                if (c == '0') {
//                    continue;
//                }
//                xorProduct ^= machine.getButtons().get(machine.getButtons().size() - 1 - j);
//                setBitCount++;
//            }
//            if (debug) {
//                System.out.println("binaryString: " + binaryString + " xorProduct: " + xorProduct);
//            }
//            if (xorProduct == targetIndicatorLightStateHash) {
//                minButtonPressesRequired = Math.min(setBitCount, minButtonPressesRequired);
//            }
//        }
//        return minButtonPressesRequired;
//    }

    public long Part2(boolean debug) {
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
                    buttons.add(Arrays.stream(parts[i].replaceAll("[()]", "").split(",")).map(Integer::parseInt)
                            .collect(Collectors.toList()));
                }
                String joltageCountersRaw = parts[parts.length - 1];
                String joltageCountersStripped = joltageCountersRaw.replaceAll("[{}]", "");
                Map<Integer, Integer> targetJoltageCounterValueMap = new HashMap<>();
                Map<Integer, Integer> currentJoltageCounterValueMap = new HashMap<>();
                List<Integer> joltageCounterValues =
                        Arrays.stream(joltageCountersStripped.split(",")).map(Integer::valueOf).toList();
                for (int i = 0; i < joltageCounterValues.size(); i++) {
                    targetJoltageCounterValueMap.put(i, joltageCounterValues.get(i));
                }
                Machine machine = new Machine(targetLightIndicatorState, currentLightIndicatorState, buttons,
                        targetJoltageCounterValueMap, currentJoltageCounterValueMap);
                machines.add(machine);
            }
            if (debug) {
                System.out.println("Machines: " + machines);
            }
            for (Machine machine : machines) {
                try (Context context = new Context()) {
                    List<IntExpr> buttonExpressions = new ArrayList<>();
                    for (List<Integer> button : machine.getButtons()) {
                        buttonExpressions.add(context.mkIntConst(
                                button.stream().map(String::valueOf).collect(Collectors.joining(", "))));
                    }
                    Optimize optimize = context.mkOptimize();
                    // all buttons must be pressed 0 or more times
                    for (int i = 0; i < buttonExpressions.size(); i++) {
                        optimize.Add(context.mkGe(buttonExpressions.get(i), context.mkInt(0)));
                    }
                    // (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
                    // x + y + .. + w = 7
                    for (int i = 0; i < machine.getTargetJoltageCounters().size(); i++) {
                        List<IntExpr> currentJoltageCounterEquation = new ArrayList<>();
                        for (int j = 0; j < machine.getButtons().size(); j++) {
                            List<Integer> button = machine.getButtons().get(j);
                            if (button.contains(i)) {
                                currentJoltageCounterEquation.add(buttonExpressions.get(j));
                            }
                        }
                        optimize.Add(context.mkEq(context.mkAdd(currentJoltageCounterEquation.toArray(new IntExpr[0])), context.mkInt(machine.getTargetJoltageCounters().get(i))));
                    }
                    // ..
                    // goal is minimize x + y + z + ...
                    Optimize.Handle<IntSort> goal =
                            optimize.MkMinimize(context.mkAdd(buttonExpressions.toArray(new IntExpr[0])));
                    if (optimize.Check() != Status.SATISFIABLE) {
                        throw new RuntimeException("equation is unsatisfiable");
                    }
                    Expr<IntSort> valueExpression = goal.getValue();
                    if (!valueExpression.isNumeral()) {
                        throw new RuntimeException("value expression is not numeral");
                    }
                    IntNum intNum = (IntNum) valueExpression;
                    ans += intNum.getInt64();
                    if (debug) {
                        Model model = optimize.getModel();
                        for (IntExpr button : buttonExpressions) {
                            System.out.println(button.toString() + " = " + model.eval(button, false));
                        }
                    }
                    if (debug) {
                        System.out.println(
                                "Min presses required for machine " + machine + ": " + goal.getValue());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }

    // bfs
    // visited array is a set of maps where
    // key is button index
    // value is coefficient
    // on each iteration, queue up a new map where we press a given button once
    // if any joltage counter exceeds its respective target value, bail out
    // if all joltage counters match the target, sum the map values
    // return smallest sum of map values
    private long minButtonPressesToSetAllJoltageCountersToTargetValues(Machine machine, boolean debug) {
        Map<Integer, Integer> targetJoltageCounters = machine.getTargetJoltageCounters();
        Set<Map<Integer, Integer>> seen = new HashSet<>();
        Queue<Machine> queue = new LinkedList<>();
        queue.add(machine);
        long minButtonPresses = Long.MAX_VALUE;
        long maxButtonsPressedCount = 0L;
        while (!queue.isEmpty()) {
            Machine currentMachine = queue.poll();
            if (debug && currentMachine.getButtonsPressed() > maxButtonsPressedCount) {
                maxButtonsPressedCount = currentMachine.getButtonsPressed();
                System.out.println("Pressed " + maxButtonsPressedCount + " buttons");
            }
            if (seen.contains(currentMachine.getCurrentJoltageCounters())) {
                continue;
            }
            if (anyJoltageCounterExceeded(currentMachine.getCurrentJoltageCounters(), targetJoltageCounters)) {
                continue;
            }
            if (currentMachine.getCurrentJoltageCounters().equals(targetJoltageCounters)) {
                minButtonPresses = Math.min(currentMachine.getButtonsPressed(), minButtonPresses);
                continue;
            }
            seen.add(currentMachine.getCurrentJoltageCounters());
            // button in binary represents which joltage counters it will increment when pressed
            // can iterate through button as a string and increment the joltage counters where button.charAt(i)
            // == '1'
            for (int i = 0; i < currentMachine.getButtons().size(); i++) {
                Machine newMachine = new Machine(currentMachine);
                newMachine.pushButton(i);
                queue.add(newMachine);
            }
        }
        return minButtonPresses;
    }

    private boolean anyJoltageCounterExceeded(Map<Integer, Integer> currentJoltageCounters,
                                              Map<Integer, Integer> targetJoltageCounters) {
        for (int i = 0; i < targetJoltageCounters.size(); i++) {
            if (currentJoltageCounters.getOrDefault(i, 0) > targetJoltageCounters.get(i)) {
                return true;
            }
        }
        return false;
    }
}
