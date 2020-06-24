package io.skyfallsdk.command.parameter.argument;

import java.util.Arrays;

// This entire class could probably be improved in a million ways
public class ArgumentSpecification {

    private ArgumentState[] states;
    private boolean allowInfinite;

    public ArgumentSpecification() {
        this.states = new ArgumentState[0];
    }

    public int getMinArgs() {
        int requiredArgs = 0;
        for (int i = 0; i < this.getStates().length; i++) {
            ArgumentState state = this.getStates()[i];
            if (state != ArgumentState.REQUIRED) {
                continue;
            }

            requiredArgs = (i + 1);
        }

        return requiredArgs;
    }

    public int getMaxArgs() {
        return this.isAllowInfinite() ? -1 : this.getStates().length;
    }

    public ArgumentState[] getStates() {
        return states;
    }

    public boolean isAllowInfinite() {
        return allowInfinite;
    }

    public void setAllowInfinite(boolean allowInfinite) {
        this.allowInfinite = allowInfinite;
    }

    public void setState(int index, ArgumentState state) {
        this.ensureSize(index);
        this.getStates()[index] = state;
    }

    public ArgumentState getState(int index) {
        this.ensureSize(index);
        return this.getStates()[index];
    }

    private void ensureSize(int index) {
        if (this.getStates().length > index) {
            return;
        }

        this.states = Arrays.copyOf(this.getStates(), index + 1);

        ArgumentState[] argumentStates = this.getStates();
        for (int i = 0; i < argumentStates.length; i++) {
            ArgumentState argumentState = argumentStates[i];
            if (argumentState != null) {
                continue;
            }

            argumentStates[i] = ArgumentState.IGNORED;
        }
    }

    public void merge(ArgumentSpecification specification) {
        ArgumentState[] specificationStates = specification.getStates();
        this.ensureSize(specificationStates.length - 1);

        for (int i = 0; i < specificationStates.length; i++) {
            ArgumentState state = specificationStates[i];
            if (state == ArgumentState.IGNORED) {
                continue;
            }

            ArgumentState localState = this.states[i];
            if (localState == ArgumentState.IGNORED) {
                this.states[i] = state;
                continue;
            }

            if (localState == state || localState == ArgumentState.REQUIRED) {
                continue;
            }

            // Has to be optional at this point
            this.states[i] = state;
        }

        this.allowInfinite = this.allowInfinite || specification.allowInfinite;
    }
}
