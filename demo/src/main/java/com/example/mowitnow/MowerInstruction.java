package com.example.mowitnow;

public class MowerInstruction {
    private Mower mower;
    private String instructions;

    public MowerInstruction(Mower mower, String instructions) {
        this.mower = mower;
        this.instructions = instructions;
    }

    public Mower getMower() {
        return mower;
    }

    public void setMower(Mower mower) {
        this.mower = mower;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
