package io.c0dr.fileuploader.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * FileUploder SpringBoot alkalmazás indító osztály
 *
 * @author krisztian
 */

@Configuration
@ComponentScan(basePackages = {"io.c0dr.fileuploader"})
@SpringBootApplication
public class FileUploaderApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileUploaderApplication.class, args);
    }

}
