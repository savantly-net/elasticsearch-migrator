package net.savantly.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories
public class MigratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MigratorApplication.class, args);
	}

	@Bean
    public ElasticsearchOperations elasticsearchTemplate(RestHighLevelClient esClient) {
        return new ElasticsearchRestTemplate(esClient);
    }
}
