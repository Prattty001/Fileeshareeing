package com.company.fileSharingManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(title = "File Sharing API", version = "1.0", description = "API documentation for File Sharing App")
)
@SpringBootApplication


public class FileSharingManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileSharingManagementApplication.class, args);
	}

}
