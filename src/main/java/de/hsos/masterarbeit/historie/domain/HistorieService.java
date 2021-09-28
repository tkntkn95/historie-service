package de.hsos.masterarbeit.historie.domain;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.config.SaslConfigs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@SpringBootApplication
public class HistorieService {

	@Autowired
	public Producer<String,String> producer;


	public static void main(String[] args) {
		SpringApplication.run(HistorieService.class, args);
	}

	@Bean
	public ArticlesStore articlesStore() {
		return new ArticlesStore();
	}

	@Bean
	public TaskExecutor executor() {
		return new ThreadPoolTaskExecutor();
	}

	@Bean
	public KafkaListenerRunner runner() {
		return new KafkaListenerRunner();
	}

	@Bean
	public Producer<String,String> producer() {
		Properties props = new Properties();
		props.put(BOOTSTRAP_SERVERS_CONFIG, "articleservice.servicebus.windows.net:9093");
		props.put(ACKS_CONFIG, "all");
		props.put(RETRIES_CONFIG, 0);
		props.put(BATCH_SIZE_CONFIG, 32000);
		props.put(LINGER_MS_CONFIG, 100);
		props.put(BUFFER_MEMORY_CONFIG, 33554432);
		props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
		props.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"$ConnectionString\" password=\"Endpoint=sb://articleservice.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=rQgOzPQExZtmeAHqx+62v5qZmZfuz5+UfEiwAc1DURI=\";");

        return new KafkaProducer<String,String>(props);
	}

}
