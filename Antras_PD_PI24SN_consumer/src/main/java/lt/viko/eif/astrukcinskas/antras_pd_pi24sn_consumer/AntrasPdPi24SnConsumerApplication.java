package lt.viko.eif.astrukcinskas.antras_pd_pi24sn_consumer;

import com.example.consumingwebservice.wsdl.GetManagersResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AntrasPdPi24SnConsumerApplication {

//    Logger logger = LoggerFactory.getLogger(AntrasPdPi24SnConsumerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AntrasPdPi24SnConsumerApplication.class, args);
    }

//    @Bean
//    ApplicationRunner lookup(ManagerClient managerClient) {
//        return args -> {
//            managerClient.printAllManagers();
//        };
//    }
}
