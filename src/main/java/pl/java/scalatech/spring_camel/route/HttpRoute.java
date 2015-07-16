package pl.java.scalatech.spring_camel.route;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.EndpointRegistry;
import org.springframework.stereotype.Component;

import pl.java.scalatech.spring_camel.eip.RecipientListBean;

@Component
@Slf4j
public class HttpRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        EndpointRegistry<String> registry = getContext().getEndpointRegistry();
        RecipientListBean rl = new RecipientListBean("file://outbox?fileName=${date:now:yyyyMMddHHmmss}.xml", "stream:out");
        from("timer://nbp?fixedRate=true&delay=0&period=1800000").routeId("httpProcessingRoute").to("http://www.nbp.pl/kursy/xml/LastA.xml")
                .convertBodyTo(String.class).recipientList(method(rl, "route")).setId("HttpRoute");

    }

}