package com.example.mowitnow;

import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@JobScope
public class MowerItemProcessor implements ItemProcessor<MowerInstruction, Mower> {
    private final Lawn lawn;


    @Autowired
    public MowerItemProcessor(Lawn lawn) {
        this.lawn = lawn;
    }


    @Override
    public Mower process(MowerInstruction mowerInstruction) throws Exception {

        Mower mower = mowerInstruction.getMower();
        String instructions = mowerInstruction.getInstructions();

        for (char instruction : instructions.toCharArray()) {
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




