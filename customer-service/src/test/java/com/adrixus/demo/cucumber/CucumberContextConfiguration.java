package com.adrixus.demo.cucumber;

import io.cucumber.java.Before;

/*@SpringBootTest
@WebAppConfiguration
@ContextConfiguration(classes = CustomerserviceApp.class)*/
public class CucumberContextConfiguration {

    @Before
    public void setup_cucumber_spring_context() {
        // Dummy method so cucumber will recognize this class as glue
        // and use its context configuration.
    }

}
