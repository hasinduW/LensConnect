package backend;

import backend.model.UserModel;
import backend.model.PostModel;
import backend.repository.PostRepository;
import backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner seedData(PostRepository posts, UserRepository users) {
		return args -> {
			if (posts.count() == 0) {
				posts.save(new PostModel("My very first post!"));
				posts.save(new PostModel("Spring + mysql is up and running."));
				posts.save(new PostModel("Click the ♥ to like this."));
			}
			if (users.count() == 0) {
				users.save(new UserModel("alice"));
				users.save(new UserModel("Tom"));
			}
		};
	}
}
