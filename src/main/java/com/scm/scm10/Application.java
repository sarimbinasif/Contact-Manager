package com.scm.scm10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		 Dotenv dotenv = Dotenv.load();

        System.setProperty("GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"));
        System.setProperty("GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET"));
        System.setProperty("GITHUB_CLIENT_ID", dotenv.get("GITHUB_CLIENT_ID"));
        System.setProperty("GITHUB_CLIENT_SECRET", dotenv.get("GITHUB_CLIENT_SECRET"));

        System.setProperty("cloudinary.cloud_name", dotenv.get("CLOUDINARY_CLOUD_NAME"));
        System.setProperty("cloudinary.api_key", dotenv.get("CLOUDINARY_API_KEY"));
        System.setProperty("cloudinary.api_secret", dotenv.get("CLOUDINARY_API_SECRET"));

        System.setProperty("EMAIL_HOST", dotenv.get("EMAIL_HOST"));
        System.setProperty("EMAIL_PORT", dotenv.get("EMAIL_PORT"));
        System.setProperty("EMAIL_USERNAME", dotenv.get("EMAIL_USERNAME"));
        System.setProperty("EMAIL_PASSWORD", dotenv.get("EMAIL_PASSWORD"));
        System.setProperty("EMAIL_PASSWORD", dotenv.get("EMAIL_PROTOCOL"));


		SpringApplication.run(Application.class, args);
	}

}
