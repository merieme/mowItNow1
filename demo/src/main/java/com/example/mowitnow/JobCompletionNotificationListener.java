package com.example.mowitnow;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobCompletionNotificationListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        // Logic before the job starts
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // Logic after the job ends
    }
}
