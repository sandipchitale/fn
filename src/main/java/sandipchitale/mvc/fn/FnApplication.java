package sandipchitale.mvc.fn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.function.*;

import static org.springframework.web.servlet.function.RouterFunctions.route;

@SpringBootApplication
public class FnApplication {

	@Controller
	static class GreetingController {

		ServerResponse greet(ServerRequest request) {
			return ServerResponse
					.ok()
					.body("Hello " + request.param("who").get() + "!");
		}

		ServerResponse uppercase(ServerRequest request) {
			return ServerResponse
					.ok()
					.body(request.param("word").get().toUpperCase());
		}

		boolean hasParam(ServerRequest request) {
			return request.params().containsKey("who");
		}

		@Bean
		static RouterFunction<ServerResponse> greetRoutes(GreetingController greetingController) {
			return route()
					.path("/", (RouterFunctions.Builder builder) -> builder
						.GET("/greet",
								RequestPredicates.param("who", ((s) -> s != null)),
								greetingController::greet))
					.build();
		}

		@Bean
		static RouterFunction<ServerResponse> uppercaseRoutes(GreetingController greetingController) {
			return route()
					.path("/", (RouterFunctions.Builder builder) -> builder
							.GET("/uppercase",
									RequestPredicates.param("word", ((s) -> s != null)),
									greetingController::uppercase))
							.build();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(FnApplication.class, args);
	}

}
