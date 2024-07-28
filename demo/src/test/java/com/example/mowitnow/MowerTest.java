package com.example.mowitnow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MowerTest {

    private Mower mower;

    @BeforeEach
    public void setUp() {
        // Initialiser la tondeuse à une position de départ pour chaque test
        mower = new Mower(0, 0, Mower.Direction.N);
    }

    @Test
    public void testTurnLeft() {
        mower.turnLeft();
        assertEquals(Mower.Direction.W, mower.getDirection());

        mower.turnLeft();
        assertEquals(Mower.Direction.S, mower.getDirection());

        mower.turnLeft();
        assertEquals(Mower.Direction.E, mower.getDirection());

        mower.turnLeft();
        assertEquals(Mower.Direction.N, mower.getDirection());
    }

    @Test
    public void testTurnRight() {
        mower.turnRight();
        assertEquals(Mower.Direction.E, mower.getDirection());

        mower.turnRight();
        assertEquals(Mower.Direction.S, mower.getDirection());

        mower.turnRight();
        assertEquals(Mower.Direction.W, mower.getDirection());

        mower.turnRight();
        assertEquals(Mower.Direction.N, mower.getDirection());
    }

    @Test
    public void testMoveForward() {
        // Tester le mouvement vers le nord
        mower.moveForward(5, 5);
        assertEquals(0, mower.getX());
        assertEquals(1, mower.getY());

        // Changer de direction vers l'est et tester le mouvement
        mower.turnRight();
        mower.moveForward(5, 5);
        assertEquals(1, mower.getX());
        assertEquals(1, mower.getY());

        // Changer de direction vers le sud et tester le mouvement
        mower.turnRight();
        mower.moveForward(5, 5);
        assertEquals(1, mower.getX());
        assertEquals(0, mower.getY());

        // Changer de direction vers l'ouest et tester le mouvement
        mower.turnRight();
        mower.moveForward(5, 5);
        assertEquals(0, mower.getX());
        assertEquals(0, mower.getY());
    }

    @Test
    public void testToString() {
        assertEquals("0 0 N", mower.toString());

        mower.turnRight();
        mower.moveForward(5, 5);
        assertEquals("1 0 E", mower.toString());

        mower.turnRight();
        mower.moveForward(5, 5);
        assertEquals("1 0 S", mower.toString());
    }
}
