package me.sebprunier.demo.testcontainers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

@SpringBootApplication
public class TestcontainersDemoApplication {

    private final Logger logger = LoggerFactory.getLogger(TestcontainersDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TestcontainersDemoApplication.class, args);
    }

    @Value("${executor.parallelism-min}")
    private int parallelismMin;
    @Value("${executor.parallelism-max}")
    private int parallelismMax;
    @Value("${executor.parallelism-factor}")
    private double parallelismFactor;

    @Bean
    public Executor taskExecutor() {
        int parallelism = Math.min(
                Math.max(
                        Double.valueOf(Math.ceil(Runtime.getRuntime().availableProcessors() * parallelismFactor)).intValue(),
                        parallelismMin
                ),
                parallelismMax
        );
        logger.info("Custom executor parallelismMin is: {}", parallelismMin);
        logger.info("Custom executor parallelismMax is: {}", parallelismMax);
        logger.info("Custom executor parallelismFactor is: {}", parallelismFactor);
        logger.info("Custom executor parallelism is: {}", parallelism);

        return new ForkJoinPool(
                parallelism,
                ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        logger.error("Uncaught error in thread: " + t.getName(), e);
                    }
                },
                true
        );
    }

}
