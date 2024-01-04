package mightytony.sideproject.dayoffmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DayOffManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DayOffManagerApplication.class, args);
    }

}
