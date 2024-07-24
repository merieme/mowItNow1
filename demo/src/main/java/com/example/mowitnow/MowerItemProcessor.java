package com.example.mowitnow;
// MowerItemProcessor.java
import org.springframework.batch.item.ItemProcessor;

public class MowerItemProcessor implements ItemProcessor<Mower, Mower> {
    private final Lawn lawn;

    public MowerItemProcessor(Lawn lawn) {
        this.lawn = lawn;
    }

    @Override
    public Mower process(Mower mower) throws Exception {
        for (char instruction : mower.getInstructions().toCharArray()) {
            switch (instruction) {
                case 'G':
                    mower.turnLeft();
                    break;
                case 'D':
                    mower.turnRight();
                    break;
                case 'A':
                    mower.moveForward(lawn.getMaxX(), lawn.getMaxY());
                    break;
                default:
                    throw new IllegalArgumentException("Unknown instruction: " + instruction);
            }
        }
        return mower;
    }

}




