package Util;

import java.util.*;

import static Util.Misc.hashCodeForBooleanArray;

public class Machine {
    private int targetIndicatorLightState;
    private int currentIndicatorLightState;
    private List<List<Integer>> buttons;
    private long buttonsPressed;
    private Set<Integer> seen;
    private Map<Integer,Integer> targetJoltageCounters;
    private Map<Integer,Integer> currentJoltageCounters;

    public Machine(boolean[] targetIndicatorLightState, boolean[] currentIndicatorLightState, List<List<Integer>> buttons,
                   Map<Integer, Integer> targetJoltageCounters, Map<Integer,Integer> currentJoltageCounters) {
        this.targetIndicatorLightState = hashCodeForBooleanArray(targetIndicatorLightState);
        this.currentIndicatorLightState = hashCodeForBooleanArray(currentIndicatorLightState);
        this.buttons = buttons;
        this.buttonsPressed = 0L;
        this.seen = new HashSet<>();
        this.targetJoltageCounters = targetJoltageCounters;
        this.currentJoltageCounters = new HashMap<>(currentJoltageCounters);
    }

    public Machine(Machine machine) {
        this.targetIndicatorLightState = machine.targetIndicatorLightState;
        this.currentIndicatorLightState = machine.currentIndicatorLightState;
        this.buttons = new ArrayList<>(machine.buttons);
        this.buttonsPressed = machine.buttonsPressed;
        this.seen = new HashSet<>(machine.seen);
        this.targetJoltageCounters = new HashMap<>(machine.targetJoltageCounters);
        this.currentJoltageCounters = new HashMap<>(machine.currentJoltageCounters);
    }

    public List<List<Integer>> getButtons() {
        return buttons;
    }

    public void setButtons(List<List<Integer>> buttons) {
        this.buttons = buttons;
    }

    public Map<Integer, Integer> getCurrentJoltageCounters() {
        return currentJoltageCounters;
    }

    public void setCurrentJoltageCounters(Map<Integer, Integer> currentJoltageCounters) {
        this.currentJoltageCounters = currentJoltageCounters;
    }

    public Map<Integer, Integer> getTargetJoltageCounters() {
        return targetJoltageCounters;
    }

    public void setTargetJoltageCounters(Map<Integer, Integer> targetJoltageCounters) {
        this.targetJoltageCounters = targetJoltageCounters;
    }

    public int getTargetIndicatorLightState() {
        return targetIndicatorLightState;
    }

    public void setTargetIndicatorLightState(int targetIndicatorLightState) {
        this.targetIndicatorLightState = targetIndicatorLightState;
    }

    public int getCurrentIndicatorLightState() {
        return currentIndicatorLightState;
    }

    public void setCurrentIndicatorLightState(int currentIndicatorLightState) {
        this.currentIndicatorLightState = currentIndicatorLightState;
    }

    public long getButtonsPressed() {
        return buttonsPressed;
    }

    public void setButtonsPressed(long buttonsPressed) {
        this.buttonsPressed = buttonsPressed;
    }

    public Set<Integer> getSeen() {
        return seen;
    }

    public void setSeen(Set<Integer> seen) {
        this.seen = seen;
    }

    @Override
    public String toString() {
        return "Machine{" + "targetIndicatorLightState=" + targetIndicatorLightState +
                ", currentIndicatorLightState=" + currentIndicatorLightState + ", buttons=" + buttons +
                ", buttonsPressed=" + buttonsPressed + ", seen=" + seen + ", targetJoltageCounters=" +
                targetJoltageCounters + ", currentJoltageCounters=" + currentJoltageCounters + '}';
    }

    public void pushButton(int i) {
        for (int joltageCounter : this.buttons.get(i)) {
            this.currentJoltageCounters.put(joltageCounter, this.currentJoltageCounters.getOrDefault(joltageCounter, 0) + 1);
        }
        this.buttonsPressed++;
    }
}
