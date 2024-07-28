package com.example.mowitnow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class CustomMowerItemReaderTest {

    private CustomMowerItemReader customMowerItemReader;

    @Value("classpath:input.txt")
    private ClassPathResource inputFile;

    @BeforeEach
    public void setUp() throws Exception {
        customMowerItemReader = new CustomMowerItemReader(inputFile.getPath());
        customMowerItemReader.read();//open(new ExecutionContext());
    }

    @Test
    public void testRead() throws Exception {
        // Read first line (lawn dimensions)
        MowerInstruction mowerInstruction = customMowerItemReader.read();
        assertNotNull(mowerInstruction);
        assertEquals(new Lawn(5, 5), customMowerItemReader.getLawn());

        // Read next mower instruction
        mowerInstruction = customMowerItemReader.read();
        assertNotNull(mowerInstruction);
        assertEquals(1, mowerInstruction.getMower().getX());
        assertEquals(2, mowerInstruction.getMower().getY());
        assertEquals(Mower.Direction.N, mowerInstruction.getMower().getDirection());

        // Read next mower moves
        mowerInstruction = customMowerItemReader.read();
        assertNotNull(mowerInstruction);
        assertEquals("GAGAGAGAA", mowerInstruction.getInstructions());

    }

    @Test
    public void testLawnInitialization() throws Exception {
        customMowerItemReader.read();//open(new ExecutionContext());
        Lawn lawn = customMowerItemReader.getLawn();
        assertNotNull(lawn);
        assertEquals(5, lawn.getMaxX());
        assertEquals(5, lawn.getMaxY());
    }

    @Test
    public void testEndOfFile() throws Exception {
        // Skipping reading all lines to reach EOF
        for (int i = 0; i < 3; i++) {
            customMowerItemReader.read();
        }

        MowerInstruction mowerInstruction = customMowerItemReader.read();
        assertEquals(null, mowerInstruction);
    }
}
