package Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Machine {
    private boolean[] targetIndicatorLightState;
    private boolean[] currentIndicatorLightState;
    List<List<Integer>> buttons;

    public Machine(boolean[] targetIndicatorLightState, boolean[] currentIndicatorLightState, List<List<Integer>> buttons) {
        this.targetIndicatorLightState = targetIndicatorLightState;
        this.currentIndicatorLightState = currentIndicatorLightState;
        this.buttons = buttons;
    }

    public Machine(Machine machine) {
        boolean[] newTargetIndicatorLightState = new boolean[machine.targetIndicatorLightState.length];
        for (int i = 0; i < machine.targetIndicatorLightState.length; i++) {
            newTargetIndicatorLightState[i] = machine.targetIndicatorLightState[i];
        }
        this.targetIndicatorLightState = newTargetIndicatorLightState;
        boolean[] newCurrentIndicatorLightState = new boolean[machine.currentIndicatorLightState.length];
        for (int i = 0; i < machine.currentIndicatorLightState.length; i++) {
            newCurrentIndicatorLightState[i] = machine.currentIndicatorLightState[i];
        }
        this.currentIndicatorLightState = newCurrentIndicatorLightState;
        this.buttons = new ArrayList<>(machine.getButtons());
    }

    public boolean[] getTargetIndicatorLightState() {
        return targetIndicatorLightState;
    }

    public void setTargetIndicatorLightState(boolean[] targetIndicatorLightState) {
        this.targetIndicatorLightState = targetIndicatorLightState;
    }

    public boolean[] getCurrentIndicatorLightState() {
        return currentIndicatorLightState;
    }

    public void setCurrentIndicatorLightState(boolean[] currentIndicatorLightState) {
        this.currentIndicatorLightState = currentIndicatorLightState;
    }

    public List<List<Integer>> getButtons() {
        return buttons;
    }

    public void setButtons(List<List<Integer>> buttons) {
        this.buttons = buttons;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "targetIndicatorLightState=" + Arrays.toString(targetIndicatorLightState) +
                ", currentIndicatorLightState=" + Arrays.toString(currentIndicatorLightState) +
                ", buttons=" + buttons +
                '}';
    }

    public void pressButton(int buttonIndex) {
        for (int i : this.buttons.get(buttonIndex)) {
            this.currentIndicatorLightState[i] = !this.currentIndicatorLightState[i];
        }
    }
}
