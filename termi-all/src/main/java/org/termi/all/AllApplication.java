package org.termi.all;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages= "org.termi")
@EnableJpaRepositories(basePackages={"org.termi.common.repository"})
@EntityScan("org.termi.common.po")
public class AllApplication {

	public static void main(String[] args) {
		SpringApplication.run(AllApplication.class, args);
	}
}