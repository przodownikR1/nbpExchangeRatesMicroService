package pl.java.scalatech.nbp;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.model.ModelCamelContext;
import org.junit.Test;

import pl.java.scalatech.spring_camel.service.nbp.impl.NBPServiceImpl;

@Slf4j
public class JaxbNbpTest {
    @Test
    public void shouldManualUnmarshalWork() throws Exception {
        createContextWithGivenRoute(new MyRouteMethodXmlBuilder());

    }

    @Test
    public void shouldUnmarshalWork() throws Exception {
        createContextWithGivenRoute(new MyRouteConverterXmlBuilder());
    }

    private void createContextWithGivenRoute(RouteBuilder route) throws Exception, InterruptedException {
        SimpleRegistry registry = new SimpleRegistry();
        registry.put("fileContentReader", new FileContentLoader());
        registry.put("nbp", new NBPServiceImpl());
        ModelCamelContext context = new DefaultCamelContext(registry);
        context.addRoutes(route);
        context.start();
        context.setTracing(true);
        Thread.sleep(2000);
        context.stop();
    }

    class MyRouteMethodXmlBuilder extends RouteBuilder {
        @Override
        public void configure() {
            from("file:incoming/test?noop=true").routeId("jaxbUnmarshallRoute").transform().simple("${header.CamelFilePath}")
                    .beanRef("fileContentReader", "file2XmlAsString").log(LoggingLevel.INFO, "myCamel", "body is ${body}");
        }
    }

    class MyRouteConverterXmlBuilder extends RouteBuilder {
        @Override
        public void configure() {
            JaxbDataFormat jxb = new JaxbDataFormat("pl.java.scalatech.spring_camel.nbp");
            from("file:incoming/test?noop=true").routeId("jaxbUnmarshallRoute").unmarshal(jxb).beanRef("nbp");

        }
    }

}
