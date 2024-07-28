package com.example.mowitnow;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
@Scope("job")
public class CustomMowerItemReader implements ItemReader<MowerInstruction> {

    private BufferedReader reader;
    private Lawn lawn;

    public CustomMowerItemReader(@Value("#{jobParameters['input.file.path']}") String pathToFile) throws Exception {
        Resource resource = new ClassPathResource(pathToFile);
        if (!resource.exists()) {
            throw new IllegalStateException("Input resource does not exist: " + resource.getFilename());
        }
        this.reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

        // Read the first line with lawn dimensions
        String[] dimensions = this.reader.readLine().split(" ");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        this.lawn = new Lawn(width, height);
    }

    @Override
    public MowerInstruction read() throws Exception {
        String mowerPosition = reader.readLine();
        String mowerInstructions = reader.readLine();

        if (mowerPosition == null || mowerInstructions == null) {
            return null;
        }

        String[] positionTokens = mowerPosition.split(" ");
        int x = Integer.parseInt(positionTokens[0]);
        int y = Integer.parseInt(positionTokens[1]);
        Mower.Direction direction = Mower.Direction.valueOf(positionTokens[2]);

        return new MowerInstruction(new Mower(x, y, direction), mowerInstructions);
    }

    public Lawn getLawn() {
        return lawn;
    }
}
