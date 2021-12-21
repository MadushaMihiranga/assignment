package com.treinetic.assignment;

import com.treinetic.assignment.entity.Role;
import com.treinetic.assignment.repository.RoleRepository;
import com.treinetic.assignment.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentApplication.class, args);
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
