package kr.co.mz.order_paperbatch.jobs;

import javax.sql.DataSource;
import kr.co.mz.order_paperbatch.entity.OrderByEntity;
import kr.co.mz.order_paperbatch.mapper.OrderByEntityRowMapper;
import kr.co.mz.order_paperbatch.processor.OrderByIncreaseProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JdbcReaderAndBatchWriterSampleJobConfiguration {

  @Bean
  public JdbcBatchItemWriter<OrderByEntity> itemWriter(DataSource dataSource) {
    var sql = "UPDATE order_by SET position=:position WHERE id=:id";
    return new JdbcBatchItemWriterBuilder<OrderByEntity>()
        .dataSource(dataSource)
        .sql(sql)
        .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
        .assertUpdates(true)
        .build();
  }

  @Bean
  public JdbcCursorItemReader<OrderByEntity> itemReader(DataSource dataSource) {
    var sql = "SELECT id,position FROM order_by";
    return new JdbcCursorItemReaderBuilder<OrderByEntity>()
        .dataSource(dataSource)
        .name("orderByEntityItemReader")
        .sql(sql)
        .rowMapper(new OrderByEntityRowMapper())
        .build();
  }


  @Bean
  public Job jdbcReaderAndBatchWriterSampleJob(JobRepository jobRepository,
      PlatformTransactionManager platformTransactionManager,
      ItemReader<OrderByEntity> itemReader, JdbcBatchItemWriter<OrderByEntity> itemWriter) {

    return new JobBuilder("jdbcReaderAndBatchWriterSampleJob", jobRepository)
        .start(
            new StepBuilder("step1", jobRepository)
                .<OrderByEntity, OrderByEntity>chunk(2, platformTransactionManager)
                .reader(itemReader)
                // 설정한 sql 에 따른 결과를 rowMapper 를 통해 Item 으로 만듦.
                .processor(new OrderByIncreaseProcessor())
                // 가져온 Item 의 데이터를 메소드를 통해 데이터를 변경함.
                // 여기서는 read 한 resultSet 을, entity 로 변환 후, Item 으로 반환했기 때문에, entity의 메소드를 사용, 데이터 변경
                .writer(itemWriter)
                // 데이터가 변경된 entity를 Item으로 받음.
                // writer 내부에서 변경된 entity 를 새로운 entity 로 반환하고,
                // 이 새로운 entity 를 설정한 sql 에 따라 새로 저장(writer) 함
                // 이 작업에서는 Update 구문을 사용해 writer 함.
                .build())

        .build();

  }


}
