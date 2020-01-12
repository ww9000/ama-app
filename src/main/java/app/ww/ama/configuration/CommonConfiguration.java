package app.ww.ama.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

@Configuration
@ComponentScan("app.ww.ama.common")
public class CommonConfiguration {

	@Bean
	public Gson getGson() {
		return new Gson();
	}
}
