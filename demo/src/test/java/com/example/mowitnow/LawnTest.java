package com.example.mowitnow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LawnTest {

    @Test
    public void testLawnInitialization() {
        // Initialiser une pelouse avec des dimensions spécifiques
        Lawn lawn = new Lawn(5, 5);

        // Vérifier que les dimensions sont correctement initialisées
        assertEquals(5, lawn.getMaxX());
        assertEquals(5, lawn.getMaxY());
    }

    @Test
    public void testGetMaxX() {
        Lawn lawn = new Lawn(3, 7);
        assertEquals(3, lawn.getMaxX());
    }

    @Test
    public void testGetMaxY() {
        Lawn lawn = new Lawn(3, 7);
        assertEquals(7, lawn.getMaxY());
    }
}
