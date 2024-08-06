package kr.co.mz.order_paperbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderPaperBatchApplication {

  public static void main(String[] args) {
    SpringApplication.run(OrderPaperBatchApplication.class, args);
  }
  /* Job 실행에 필요한 것.
   * 1. build.gradle 의존성
   *  - batch
   * 2. 설정을 마친 Job Class
   * 3. DB 연결 yml 등
   * 4. 연결한 DB에 Batch Run 에 필요한 table schema 세팅 : 공식문서에 있음
   *
JobLauncherCommandLineRunner 실행:
Spring Boot는 JobLauncherCommandLineRunner를 통해 애플리케이션 시작 시 모든 Job 빈을 자동으로 실행합니다.
JobLauncherCommandLineRunner는 ApplicationRunner 인터페이스를 구현하여 애플리케이션 실행 후
run() 메서드를 호출합니다. run() 메서드에서 JobLauncher를 사용하여 구성된 모든 Job을 실행합니다.
   */
}
