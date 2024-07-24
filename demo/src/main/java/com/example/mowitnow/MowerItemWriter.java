package com.example.mowitnow;// MowerItemWriter.java
import com.example.mowitnow.Mower;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class MowerItemWriter implements ItemWriter<Mower> {
    @Override
    public void write(List<? extends Mower> mowers) {
        for (Mower mower : mowers) {
            System.out.println(mower);
        }
    }
}
