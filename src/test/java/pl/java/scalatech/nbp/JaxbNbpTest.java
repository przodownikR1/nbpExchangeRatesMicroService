package pl.java.scalatech.nbp;

import java.io.File;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.junit.Ignore;
import org.junit.Test;

import pl.java.scalatech.spring_camel.nbp.Tabela_kursow;

@Slf4j
public class JaxbNbpTest extends CommonCreateCamelContext {
    @Test
    @Ignore
    public void shouldManualUnmarshalWork() throws Exception {
        createContextWithGivenRoute(new MyRouteMethodXmlBuilder(), 2000);
    }

    @Test
    @Ignore
    public void shouldUnmarshalWork() throws Exception {
        createContextWithGivenRoute(new MyRouteConverterXmlBuilder(), 2000);
    }

    @Test
    public void shouldConsumerWork() throws InterruptedException, Exception {
        createContextWithGivenRoute(new MyRouteConsumerXmlBuilder(), 2000);
    }

    @Test
    public void unmarshallTest() throws JAXBException {
        File xmlFile = new File("incoming/cur.xml");
        JAXBContext jc = JAXBContext.newInstance(pl.java.scalatech.spring_camel.nbp.Tabela_kursow.class);
        Unmarshaller u = jc.createUnmarshaller();
        Tabela_kursow tk = (Tabela_kursow) u.unmarshal(xmlFile);
        log.info(tk.toString());
        log.info("dolar : {}", tk.getPozycje().stream().filter(t -> t.getKod_waluty().equals("USD")).findFirst());
        log.info(" : {}", tk.getPozycje().stream().map(t -> t.getKod_waluty()).sorted().collect(Collectors.toList()));

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

    class MyRouteConsumerXmlBuilder extends RouteBuilder {
        @Override
        public void configure() {
            JaxbDataFormat jxb = new JaxbDataFormat("pl.java.scalatech.spring_camel.nbp");
            ConsumerTemplate ct = getContext().createConsumerTemplate();
            from("file:incoming/test?noop=true").routeId("jaxbUnmarshallRoute").unmarshal(jxb).to("direct:end");
            //Tabela_kursow tk = ct.receiveBody("direct:end", Tabela_kursow.class);
            from("direct:end").log(LoggingLevel.INFO, "myCamel", "+++  body  is ${body}");
        }
    }

}
