package pl.java.scalatech.spring_camel.route;

import org.apache.camel.builder.RouteBuilder;

public class PopulateServiceFromEndpointRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        //from("direct:unmarshalReady").beanRef(ref)
    }

}
