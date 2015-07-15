package pl.java.scalatech.nbp;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Test;

@Slf4j
public class ReadAndParseXmlFileTest extends CommonCreateCamelContext {
    @Test
    public void shouldReadAndParseXmlFileWork() throws Exception {
        createContextWithGivenRoute(new MyRouteXmlParseBuilder(), 2000);
    }

    class MyRouteXmlParseBuilder extends RouteBuilder {
        @Override
        public void configure() {
            onException(java.io.FileNotFoundException.class).handled(true);
            from("file:incoming?noop=true").choice().when(header(Exchange.FILE_NAME).endsWith(".xml")).process(exchange -> {
                log.info("+++ xml processing");
            }).to("file:outbox/xml").when(header(Exchange.FILE_NAME).regex("^.*(csv|csl)$")).process(exchange -> {
                log.info("+++ csv processing");
            }).to("jms:outbox/csv").otherwise().process(exchange -> {
                log.info("+++ other processing");
            }).to("file:outbox/other").setId("Content_ROUTE");

            from("file:xml?noop=true").process(exchange -> {
                log.info("in : {}", exchange.getIn().getBody());
                log.info("header {}", exchange.getIn().getHeaders());
                log.info("out : {}", exchange.getOut());

            }).split(body().tokenize("pozycja")).streaming().to("log:pl.java.scalatech?level=INFO").setId("ProcessXML_ROUTE");

        }
    }

}