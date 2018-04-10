/**
 * Copyright 2005-2016 Red Hat, Inc.
 * <p>
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.redhat;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.zipkin.ZipkinTracer;
import org.apache.camel.zipkin.starter.CamelZipkin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;
import zipkin2.reporter.AsyncReporter;
import zipkin2.Span;
import zipkin2.reporter.Reporter;

import java.awt.image.renderable.ContextualRenderedImageFactory;

/**
 * A spring-boot application that includes a Camel route builder to setup the Camel routes
 */
@SpringBootApplication
//@CamelZipkin
@ImportResource({"classpath:spring/camel-context.xml"})
@ComponentScan({"com.redhat"})
public class Application extends RouteBuilder {

//  final
//  ZipkinTracer zipkin;
//
//  @Autowired
//  public Application(ZipkinTracer zipkin) {
//    this.zipkin = zipkin;
//    OkHttpSender sender = OkHttpSender.create("http://127.0.0.1:9411/api/v2/spans");
//    zipkin.setSpanReporter(AsyncReporter.create(sender));
//  }

  // must have a main method spring-boot can run
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
