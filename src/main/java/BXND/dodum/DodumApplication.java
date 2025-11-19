package BXND.dodum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DodumApplication {

	public static void main(String[] args) {
		SpringApplication.run(DodumApplication.class, args);
	}

}
