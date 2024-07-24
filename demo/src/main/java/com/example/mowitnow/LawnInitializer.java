package com.example.mowitnow;

import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LawnInitializer {

    public static Lawn initializeLawn(Resource resource) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String firstLine = reader.readLine();
            if (firstLine != null) {
                String[] dimensions = firstLine.split(" ");
                int maxX = Integer.parseInt(dimensions[0]);
                int maxY = Integer.parseInt(dimensions[1]);
                return new Lawn(maxX, maxY);
            } else {
                throw new IOException("The file is empty, cannot initialize Lawn.");
            }
        }
    }
}
