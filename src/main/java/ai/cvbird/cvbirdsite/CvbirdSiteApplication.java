package ai.cvbird.cvbirdsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CvbirdSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(CvbirdSiteApplication.class, args);
	}

}
