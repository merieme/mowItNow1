package com.example.mowitnow;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Value("${input.file.path}")
    private String pathToFile;


    @Bean
    public JobBuilderFactory jobBuilderFactory(JobRepository jobRepository) {
        return new JobBuilderFactory(jobRepository);
    }

    @Bean
    public StepBuilderFactory stepBuilderFactory(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilderFactory(jobRepository, transactionManager);
    }

    @Bean
    @JobScope
    public FlatFileItemReader<Mower> reader() throws IOException {
        if (pathToFile == null || pathToFile.isEmpty()) {
            throw new IllegalArgumentException("Path must not be null or empty");
        }

        Resource resource = new ClassPathResource(pathToFile);

        // Skip the first line which is used for Lawn dimensions
        FlatFileItemReader<Mower> reader = new FlatFileItemReader<>();
        reader.setResource(resource);
        reader.setLinesToSkip(1); // Skip the first line with lawn dimensions
        reader.setLineMapper(lineMapper());
        return reader;
    }

    @Bean
    public LineMapper<Mower> lineMapper() {
        DefaultLineMapper<Mower> lineMapper = new DefaultLineMapper<>();

        LineTokenizer lineTokenizer = new LineTokenizer() {
            @Override
            public FieldSet tokenize(String line) {
                // Check if the line contains a space (indicating position and direction) or not (instructions)
                if (line.matches("\\d+ \\d+ [NESW]")) {
                    DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
                    tokenizer.setDelimiter(" ");
                    tokenizer.setNames("x", "y", "direction");
                    return tokenizer.tokenize(line);
                } else {
                    // Handle instruction lines separately
                    return new DefaultFieldSet(new String[] {line}, new String[] {"instructions"});
                }
            }
        };

        BeanWrapperFieldSetMapper<Mower> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Mower.class);
        fieldSetMapper.setCustomEditors(Map.of(
                Mower.Direction.class, new PropertyEditorSupport() {
                    @Override
                    public void setAsText(String text) throws IllegalArgumentException {
                        setValue(Mower.Direction.valueOf(text));
                    }
                }
        ));

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    @JobScope
    public ItemProcessor<Mower, Mower> processor() throws IOException {
        if (pathToFile == null || pathToFile.isEmpty()) {
            throw new IllegalArgumentException("Path must not be null or empty");
        }

        Resource resource = new ClassPathResource(pathToFile);

        Lawn lawn = LawnInitializer.initializeLawn(resource);
        return new MowerItemProcessor(lawn);
    }

    @Bean
    public ItemWriter<Mower> writer() {
        return items -> {
            for (Mower item : items) {
                System.out.println("Final Position: " + item.getX() + " " + item.getY() + " " + item.getDirection());
            }
        };
    }

    @Bean
    public Step step(StepBuilderFactory stepBuilderFactory,
                     FlatFileItemReader<Mower> reader,
                     ItemProcessor<Mower, Mower> processor,
                     ItemWriter<Mower> writer) {
        return stepBuilderFactory.get("step")
                .<Mower, Mower>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job mowItNowJob(JobBuilderFactory jobBuilderFactory, Step step, JobExecutionListener listener) {
        return jobBuilderFactory.get("mowItNowJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step)
                .end()
                .build();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobCompletionNotificationListener();
    }
}
