
package com.redhat;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResourceß;


@SpringBootApplication
//@CamelZipkin
@ComponentScan({"com.redhat"})
public class Application extends RouteBuilder {


  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void configure() throws Exception {


    from("timer://foo?period=5000")
        .setBody().simple("Hello World at ${date:now:YYYY-MM-dd hh:mm:ss,SSS}")
        .log("first >>> ${body}")
        .to("direct:second");

    from("direct:second")
        .log("second >>> ${body}");
  }
}
