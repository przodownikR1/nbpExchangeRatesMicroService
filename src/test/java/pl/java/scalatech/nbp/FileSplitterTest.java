package pl.java.scalatech.nbp;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.model.ModelCamelContext;
import org.junit.Test;

@Slf4j
public class FileSplitterTest {
    @Test
    public void shouldReadAndParseXmlFileWork() throws Exception {
        SimpleRegistry registry = new SimpleRegistry();
        registry.put("fileContentReader", new FileContentLoader());
        ModelCamelContext context = new DefaultCamelContext(registry);
        context.addRoutes(new MyRouteXmlBuilder());
        context.start();
        context.setTracing(true);
        Thread.sleep(1000);
        context.getRouteDefinitions().forEach(route -> {
            log.info(route.toString());
            route.getInputs().forEach(in -> log.info("Input: " + in.getUri()));
            route.getOutputs().forEach(out -> log.info("Out: " + out.getClass()));
        });
        context.stop();

    }

    class MyRouteXmlBuilder extends RouteBuilder {
        @Override
        public void configure() {
            from("file:incoming?noop=true").split().tokenizeXML("pozycja").streaming().convertBodyTo(String.class)
                    .log(LoggingLevel.INFO, "myCamel", "body is ${body}");

        }
    }
}
