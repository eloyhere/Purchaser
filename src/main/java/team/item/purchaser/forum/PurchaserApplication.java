package team.item.purchaser.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;

@SpringBootApplication
public class PurchaserApplication {

	public static void main(String[] args) {
		SpringApplication.run(PurchaserApplication.class, args);
	}

}
