package pl.java.scalatech.nbp;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.model.ModelCamelContext;

import pl.java.scalatech.spring_camel.service.nbp.impl.NBPServiceImpl;

public abstract class CommonCreateCamelContext {

    protected void createContextWithGivenRoute(RouteBuilder route, int timeWork) throws Exception, InterruptedException {
        SimpleRegistry registry = new SimpleRegistry();
        registry.put("fileContentReader", new FileContentLoader());
        registry.put("nbp", new NBPServiceImpl());
        ModelCamelContext context = new DefaultCamelContext(registry);
        context.addRoutes(route);
        context.start();
        context.setTracing(true);
        Thread.sleep(timeWork);
        context.stop();
    }
}
