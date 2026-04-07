package za.co.entelect.java_devcamp.configs;

import org.springframework.boot.webservices.client.WebServiceTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import za.co.entelect.java_devcamp.soap.CreditClient;
import za.co.entelect.java_devcamp.soap.SoapMessageSender;

@Configuration
public class SoapConfig {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package configured in the build.gradle/pom.xml
        marshaller.setContextPath("za.co.entelect.java_devcamp.wsdl");
        return marshaller;
    }

    @Bean
    public CreditClient creditClient(WebServiceTemplateBuilder builder, Jaxb2Marshaller marshaller, SoapMessageSender sender) {
        CreditClient client = new CreditClient();
        builder = builder.setMarshaller(marshaller).setUnmarshaller(marshaller);

        client.setWebServiceTemplate(builder.build());
        client.setDefaultUri("http://localhost:8083/CreditCheck");
        client.getWebServiceTemplate().setMessageSender(sender);
        return client;
    }

}
