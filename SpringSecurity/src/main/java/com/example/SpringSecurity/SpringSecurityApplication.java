package com.example.SpringSecurity;

import com.example.SpringSecurity.entity.Role;
import com.example.SpringSecurity.entity.User;
import com.example.SpringSecurity.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringSecurityApplication implements CommandLineRunner {

	@Autowired
	private UserRepo userRepo;

	public static void main(String[] args) {

		SpringApplication.run(SpringSecurityApplication.class, args);
	}
	public void run(String... args){
		User adminact=userRepo.findByRole(Role.ADMIN);
		if(adminact==null){
			User user=new User();
			user.setEmail("admin@gmail.com");
			user.setName("admin");
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));;
			userRepo.save(user);
		}

	}
}
