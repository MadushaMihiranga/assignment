package com.treinetic.assignment;

import com.treinetic.assignment.entity.Role;
import com.treinetic.assignment.entity.User;
import com.treinetic.assignment.repository.RoleRepository;
import com.treinetic.assignment.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class AssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentApplication.class, args);
	}



	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner runner(UserRepository userRepository, RoleRepository roleRepository){
		return args -> {

			if (!roleRepository.findRoleByName("Student").isPresent()){
				Role student = new Role(null,"Student","STUDENT");
				roleRepository.save(student);
			}

			if (!roleRepository.findRoleByName("Admin").isPresent()){
				Role admin = new Role(null,"Admin","ADMIN");
				roleRepository.save(admin);
			}

			//userRepository.delete(userRepository.findByUsername("admin@treinetic.com").get());

			if (!userRepository.findByUsername("admin@treinetic.com").isPresent()){
				List<Role> roles= new ArrayList<>();
				roles.add(roleRepository.findRoleByName("Admin").get());
				User user = new User(
						null,
						null,
						null,
						"admin@treinetic.com",
						null,
						true,
						true,
						passwordEncoder().encode("iAmAdmin"),
						null,
						null,
						roles
				);
				userRepository.save(user);
			}


			/*Student student = new Student(
					"Madusha",
					"Mihiranga",
					"madushamihiranga@outlook.com",
					"0769863333",
					false
			);
			repository.insert(student);*/
		};
	}

}
