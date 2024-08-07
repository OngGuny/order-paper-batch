package kr.co.mz.order_paperbatch.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MergedJobConfiguration {
// 롬복이 Qualifier 얘는 복사하지 않아서 @requiredars 못쓴다.
// 또 그냥 qualifier 를 쓰면 생성단계에서 주입구가 없어서(메소드 파라미터가 없어서) JobConfiguration 클래스 초기화가 안됨
  // 그래서 결국 생성자 직접 qualifier 넣어서 선언해줘야함.
//  @Qualifier("linerJob")
//  private final Job linerJob;
//  @Qualifier("jdbcReaderAndBatchWriterSampleJob")
//  private final Job jdbcReaderAndBatchWriterSampleJob;

  // 스프링 컨테이너가 어떤 Step 을 사용해야할지 모른다. 그래서 qualifier 로 알려주는것.
  private final Job linerJob;
  private final Job jdbcReaderAndBatchWriterSampleJob;

  public MergedJobConfiguration(@Qualifier("linerJob") Job linerJob,
      @Qualifier("jdbcReaderAndBatchWriterSampleJob") Job jdbcReaderAndBatchWriterSampleJob) {
    this.linerJob = linerJob;
    this.jdbcReaderAndBatchWriterSampleJob = jdbcReaderAndBatchWriterSampleJob;
  }


  @Bean
  public Job mergedJob(JobRepository jobRepository,
      @Qualifier("jobStepJobStep1")
      Step jobStepJobStep1,
      @Qualifier("jobStepJobStep2")
      Step jobStepJobStep2) {
    return new JobBuilder("mergedJob", jobRepository)
        .start(jobStepJobStep1)
        .next(jobStepJobStep2)
        .build();
  }

  @Bean
  public Step jobStepJobStep1(JobRepository jobRepository, JobLauncher jobLauncher
  ) {
    return new JobStepBuilder(new StepBuilder("mergedJobStepJobStep1", jobRepository))
        .job(linerJob)
        .launcher(jobLauncher)
        .allowStartIfComplete(true)
        .build();
  }

  @Bean
  public Step jobStepJobStep2(JobRepository jobRepository, JobLauncher jobLauncher
  ) {
    return new JobStepBuilder(new StepBuilder("mergedJobStepJobStep1", jobRepository))
        .job(jdbcReaderAndBatchWriterSampleJob)
        .launcher(jobLauncher)
        .allowStartIfComplete(true) // 이거 없으면 딱 한번만 실행된다.
        .build();
  }

// Job 실행에 파라미터가 필요 없기에 설정하지않음.
//  @Bean
//  public DefaultJobParametersExtractor jobParametersExtractor() {
//    var extractor = new DefaultJobParametersExtractor();
//    extractor.setKeys(new String[]{"input.file"});
//    return extractor;
//  }


}
