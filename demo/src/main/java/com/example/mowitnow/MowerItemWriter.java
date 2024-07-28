package com.example.mowitnow;// MowerItemWriter.java

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MowerItemWriter implements ItemWriter<Mower> {
    @Override
    public void write(List<? extends Mower> mowers) {
        for (Mower mower : mowers) {
            System.out.println(mower);
        }
    }
}
