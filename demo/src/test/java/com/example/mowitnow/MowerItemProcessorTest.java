package com.example.mowitnow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.item.ItemProcessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MowerItemProcessorTest {

    private Lawn mockLawn;
    private MowerItemProcessor mowerItemProcessor;
    private Mower mockMower;

    @BeforeEach
    public void setUp() {
        // Mocking Lawn and Mower
        mockLawn = mock(Lawn.class);
        mockMower = mock(Mower.class);

        // Setting up lawn dimensions
        when(mockLawn.getMaxX()).thenReturn(5);
        when(mockLawn.getMaxY()).thenReturn(5);

        // Creating the MowerItemProcessor with mocked Lawn
        mowerItemProcessor = new MowerItemProcessor(mockLawn);
    }

    @Test
    public void testProcess() throws Exception {
        // Mocking MowerInstruction
        MowerInstruction mockMowerInstruction = mock(MowerInstruction.class);
        when(mockMowerInstruction.getMower()).thenReturn(mockMower);
        when(mockMowerInstruction.getInstructions()).thenReturn("GAGAGA");

        // Processing the instruction
        Mower result = mowerItemProcessor.process(mockMowerInstruction);

        // Verifying the interactions
        verify(mockMower, times(3)).turnLeft();
        verify(mockMower, times(3)).moveForward(5, 5);

        // Asserting the returned mower
        assertEquals(mockMower, result);
    }

    @Test
    public void testUnknownInstruction() {
        // Mocking MowerInstruction with an unknown instruction
        MowerInstruction mockMowerInstruction = mock(MowerInstruction.class);
        when(mockMowerInstruction.getMower()).thenReturn(mockMower);
        when(mockMowerInstruction.getInstructions()).thenReturn("X");

        try {
            mowerItemProcessor.process(mockMowerInstruction);
        } catch (IllegalArgumentException e) {
            assertEquals("Unknown instruction: X", e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEmptyInstructions() throws Exception {
        // Mocking MowerInstruction with empty instructions
        MowerInstruction mockMowerInstruction = mock(MowerInstruction.class);
        when(mockMowerInstruction.getMower()).thenReturn(mockMower);
        when(mockMowerInstruction.getInstructions()).thenReturn("");

        // Processing the instruction
        Mower result = mowerItemProcessor.process(mockMowerInstruction);

        // Verifying no interactions
        verifyNoInteractions(mockMower);

        // Asserting the returned mower
        assertEquals(mockMower, result);
    }
}
