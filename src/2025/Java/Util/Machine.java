package Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static Util.Misc.hashCodeForBooleanArray;

public class Machine {
    private int targetIndicatorLightState;
    private int currentIndicatorLightState;
    private List<Integer> buttons;
    private long buttonsPressed;
    private Set<Integer> seen;

    public Machine(boolean[] targetIndicatorLightState, boolean[] currentIndicatorLightState, List<List<Integer>> buttons) {
        int binaryNumberLength = targetIndicatorLightState.length;
        this.targetIndicatorLightState = hashCodeForBooleanArray(targetIndicatorLightState);
        this.currentIndicatorLightState = hashCodeForBooleanArray(currentIndicatorLightState);
        this.buttons = new ArrayList<>();
        for (List<Integer> button : buttons) {
            int buttonValue = 0;
            for (Integer integer : button) {
                buttonValue += (int) Math.pow(2, (binaryNumberLength - 1) - integer);
            }
            this.buttons.add(buttonValue);
        }
        this.buttonsPressed = 0L;
        this.seen = new HashSet<>();
    }

    public Machine(Machine machine) {
        this.targetIndicatorLightState = machine.targetIndicatorLightState;
        this.currentIndicatorLightState = machine.currentIndicatorLightState;
        this.buttons = new ArrayList<>(machine.buttons);
        this.buttonsPressed = machine.buttonsPressed;
        this.seen = new HashSet<>(machine.seen);
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

    public List<Integer> getButtons() {
        return buttons;
    }

    public void setButtons(List<Integer> buttons) {
        this.buttons = buttons;
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
        return "Machine{" +
                "targetIndicatorLightState=" + targetIndicatorLightState +
                ", currentIndicatorLightState=" + currentIndicatorLightState +
                ", buttons=" + buttons +
                ", buttonsPressed=" + buttonsPressed +
                ", seen=" + seen +
                '}';
    }

    public void pressButton(int buttonIndex) {
        currentIndicatorLightState ^= this.buttons.get(buttonIndex);
        this.buttonsPressed++;
    }
}
