package pl.java.scalatech.nbp;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.builder.RouteBuilder;
import org.junit.Test;

@Slf4j
public class StreamXmlTest extends CommonCreateCamelContext {
    @Test
    public void shouldReadAndParseXmlFileWork() throws Exception {
        createContextWithGivenRoute(new MyRouteSimpleBuilder(), 2000);
    }

    class MyRouteSimpleBuilder extends RouteBuilder {
        @Override
        public void configure() {
            onException(java.io.FileNotFoundException.class).handled(true);
            from("stream:url?url=http://www.nbp.pl/kursy/xml/LastA.xml").to("seda:xmlProc");
            from("seda:xmlProc").beanRef("fileContentReader").setId("READ_STREAM_ROUTE");
        }

    }
}//Powyższa tabela w formacie .xml  https://www.nbp.pl/Kursy/KursyA.html
//http://www.nbp.pl/kursy/xml/LastA.xml