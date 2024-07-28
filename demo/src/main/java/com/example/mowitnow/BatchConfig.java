package com.example.mowitnow;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@EnableBatchProcessing
@ComponentScan
public class BatchConfig {

    @Value("${input.file.path}")
    private String pathToFile;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MowerItemWriter writer;

    @Autowired
    private MowerItemProcessor processor;

    @Bean
    public Job mowerJob() throws Exception {
        return jobBuilderFactory.get("mowerJob")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean
    public Step step() throws Exception {
        return stepBuilderFactory.get("step")
                .<MowerInstruction, Mower>chunk(1)
                .reader(reader())
                .processor(processor)
                .writer(writer)

                .build();
    }


    @Bean
    @Scope(value = "job", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public CustomMowerItemReader reader() throws Exception {
        return new CustomMowerItemReader(pathToFile);
    }

    @Bean
    @JobScope
    public Lawn lawn(CustomMowerItemReader reader) {
        return reader.getLawn();
    }

}