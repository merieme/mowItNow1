package com.example.mowitnow;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class JobLauncherController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job mowItNowJob;

    @RequestMapping(value = "/launchjob", method = RequestMethod.GET)
    public String handle() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("input.file.path", "classpath:input.txt")
                .toJobParameters();
        jobLauncher.run(mowItNowJob, jobParameters);
        return "Job Launched";
    }
}
