package pl.java.scalatech.spring_camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.stereotype.Component;

@Component
public class FileScannerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        JaxbDataFormat jxb = new JaxbDataFormat("pl.java.scalatech.spring_camel.nbp");
        from("file://outbox?noop=true").routeId("jaxbUnmarshallRoute").unmarshal(jxb).to("direct:unmarshalReady").setId("FileToJaxbRoute");
    }
}
