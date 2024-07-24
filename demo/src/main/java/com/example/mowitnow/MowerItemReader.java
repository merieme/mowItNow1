package com.example.mowitnow;
// MowerItemReader.java
import com.example.mowitnow.Mower;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;

import java.util.Iterator;
import java.util.List;

public class MowerItemReader extends FlatFileItemReader<Mower> {
    private final Iterator<Mower> mowerIterator;

    public MowerItemReader(List<Mower> mowers) {
        this.mowerIterator = mowers.iterator();
    }

    @Override
    public Mower read() {
        return mowerIterator.hasNext() ? mowerIterator.next() : null;
    }
}



