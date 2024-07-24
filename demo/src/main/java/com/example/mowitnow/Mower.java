package com.example.mowitnow;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;

public class Mower {
    private int x;
    private int y;
    private Direction direction;
    private String instructions;

    public enum Direction {
        N, E, S, W
    }

    public Mower(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public Mower(int x, int y, Direction direction, String instructions) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.instructions = instructions;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getInstructions() {
        return instructions;
    }

    public void turnLeft() {
        switch (direction) {
            case N -> direction = Direction.W;
            case W -> direction = Direction.S;
            case S -> direction = Direction.E;
            case E -> direction = Direction.N;
        }
    }

    public void turnRight() {
        switch (direction) {
            case N -> direction = Direction.E;
            case E -> direction = Direction.S;
            case S -> direction = Direction.W;
            case W -> direction = Direction.N;
        }
    }

    public void moveForward(int maxX, int maxY) {
        switch (direction) {
            case N -> {
                if (y < maxY) y++;
            }
            case E -> {
                if (x < maxX) x++;
            }
            case S -> {
                if (y > 0) y--;
            }
            case W -> {
                if (x > 0) x--;
            }
        }
    }

    @Override
    public String toString() {
        return x + " " + y + " " + direction;
    }


}
