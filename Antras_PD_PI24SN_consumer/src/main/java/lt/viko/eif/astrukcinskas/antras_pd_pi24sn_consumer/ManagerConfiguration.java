package lt.viko.eif.astrukcinskas.antras_pd_pi24sn_consumer;

import org.springframework.boot.webservices.client.WebServiceTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ManagerConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package configured in the build.gradle/pom.xml
        marshaller.setContextPath("com.example.consumingwebservice.wsdl");
        return marshaller;
    }

    @Bean
    public ManagerClient countryClient(WebServiceTemplateBuilder builder, Jaxb2Marshaller marshaller) {
        builder = builder.setMarshaller(marshaller).setUnmarshaller(marshaller);

        ManagerClient manager = new ManagerClient();
        manager.setWebServiceTemplate(builder.build());
        manager.setDefaultUri("http://localhost:8081/services");
        return manager;
    }

}
