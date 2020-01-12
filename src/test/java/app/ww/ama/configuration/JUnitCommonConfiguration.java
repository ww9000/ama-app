package app.ww.ama.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
public class JUnitCommonConfiguration {

	@Bean
	public Gson getGson() {
		return new GsonBuilder().serializeNulls().create();
	}

}
