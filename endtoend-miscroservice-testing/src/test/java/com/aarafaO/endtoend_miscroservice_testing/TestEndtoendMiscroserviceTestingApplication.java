package com.aarafaO.endtoend_miscroservice_testing;

import org.springframework.boot.SpringApplication;

public class TestEndtoendMiscroserviceTestingApplication {

	public static void main(String[] args) {
		SpringApplication.from(EndtoendMiscroserviceTestingApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
