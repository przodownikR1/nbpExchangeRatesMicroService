package pl.java.scalatech.nbp;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.builder.RouteBuilder;
import org.junit.Test;

@Slf4j
public class ReadFileXmlGuavaTest extends CommonCreateCamelContext {
    @Test
    public void shouldReadAndParseXmlFileWork() throws Exception {
        createContextWithGivenRoute(new MyRouteXmlParseBuilder(), 1000);
    }

    class MyRouteXmlParseBuilder extends RouteBuilder {
        @Override
        public void configure() {
            onException(java.io.FileNotFoundException.class).handled(true);
            from("file:incoming/test?noop=true").to("direct:reader");
            from("direct:reader").beanRef("fileContentReader").setId("READ_CONTENT_FILE_ROUTE");

        }
    }
}
