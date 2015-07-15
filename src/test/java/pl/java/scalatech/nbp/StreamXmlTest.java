package pl.java.scalatech.nbp;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.model.ModelCamelContext;
import org.junit.Test;

@Slf4j
public class StreamXmlTest {
    @Test
    public void shouldReadAndParseXmlFileWork() throws Exception {
        SimpleRegistry registry = new SimpleRegistry();
        registry.put("fileContentReader", new FileContentLoader());
        ModelCamelContext context = new DefaultCamelContext(registry);
        context.addRoutes(new MyRouteSimpleBuilder());
        context.start();
        context.setTracing(true);
        Thread.sleep(2000);
        context.getRouteDefinitions().forEach(route -> {
            log.info(route.toString());
            route.getInputs().forEach(in -> log.info("Input: " + in.getUri()));
            route.getOutputs().forEach(out -> log.info("Out: " + out.getClass()));
        });

        context.stop();
    }

    class MyRouteSimpleBuilder extends RouteBuilder {
        @Override
        public void configure() {
            onException(java.io.FileNotFoundException.class).handled(true);
            from("stream:url?url=https://www.nbp.pl/Kursy/KursyA.html").to("seda:xmlProc");
            from("seda:xmlProc").beanRef("fileContentReader").setId("READ_STREAM_ROUTE");
        }

    }
}//Powyższa tabela w formacie .xml  https://www.nbp.pl/Kursy/KursyA.html
