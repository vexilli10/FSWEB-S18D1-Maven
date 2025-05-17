package com.workintech.sqldmljoins;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// src/main/java/.../SqlDmlJoinsApplication.java
@SpringBootApplication
public class SqlDmlJoinsApplication {

	static {
		java.util.Locale.setDefault(java.util.Locale.US);
	}

	public static void main(String[] args) {
		SpringApplication.run(SqlDmlJoinsApplication.class, args);
	}
}

