package pl.java.scalatech.nbp;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Test;

@Slf4j
public class FileSplitterTest extends CommonCreateCamelContext {
    @Test
    public void shouldReadAndParseXmlFileWork() throws Exception {
        createContextWithGivenRoute(new MyRouteXmlBuilder(), 1000);
    }

    class MyRouteXmlBuilder extends RouteBuilder {
        @Override
        public void configure() {
            from("file:incoming?noop=true").split().tokenizeXML("pozycja").streaming().convertBodyTo(String.class)
                    .log(LoggingLevel.INFO, "myCamel", "body is ${body}");

        }
    }
}
