package com.codewithniteesh.blog;

import com.codewithniteesh.blog.entities.Role;
import com.codewithniteesh.blog.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.codewithniteesh.blog.config.ApiConstants.ADMIN_USER;
import static com.codewithniteesh.blog.config.ApiConstants.NORMAL_USER;

@SpringBootApplication
public class BlogAppApiApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(BlogAppApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public void run(String... args) throws Exception {
		// BCrypt generator
		System.out.println(passwordEncoder.encode("Abc@1234"));
		roleRepo.save(new Role(ADMIN_USER, "ROLE_ADMIN"));
		roleRepo.save(new Role(NORMAL_USER, "ROLE_NORMAL"));
		System.out.println("Roles successfully created");
	}

	//
}
