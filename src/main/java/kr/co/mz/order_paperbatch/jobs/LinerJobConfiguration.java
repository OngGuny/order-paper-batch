package kr.co.mz.order_paperbatch.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class LinerJobConfiguration {

  @Bean
  public Step step(JobRepository jobRepository,
      PlatformTransactionManager platformTransactionManager) {
    return new StepBuilder("시작의일보", jobRepository)
        .tasklet((contribution, chunkContext) -> {
          System.out.println("'저벅'");
          return RepeatStatus.FINISHED;
        }, platformTransactionManager)
        .allowStartIfComplete(true)
        .build();
  }

  @Bean
  public Job linerJob(JobRepository jobRepository,
      PlatformTransactionManager transactionManager) {
    return new JobBuilder("linerJob", jobRepository).start(step(jobRepository, transactionManager))
        .build();
  }
}
