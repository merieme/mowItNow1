package com.example.mowitnow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.ItemWriter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MowerItemWriterTest {

    private MowerItemWriter mowerItemWriter;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mowerItemWriter = new MowerItemWriter();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testWrite() throws Exception {
        Mower mower1 = new Mower(1, 2, Mower.Direction.N);
        Mower mower2 = new Mower(3, 4, Mower.Direction.E);
        List<Mower> mowers = Arrays.asList(mower1, mower2);

        mowerItemWriter.write(mowers);

        String expectedOutput = "1 2 N\r\n3 4 E\r\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}