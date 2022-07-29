package bg.softuni.PureWaterMiniCRM;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PureWaterMiniCrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(PureWaterMiniCrmApplication.class, args);
	}

}
