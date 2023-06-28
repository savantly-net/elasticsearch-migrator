package net.savantly.elasticsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "spring.elasticsearch")
public class ElasticsearchClientConfig extends ReactiveElasticsearchConfiguration {

    @Getter
    @Setter
    private String hosts = "localhost:9200";

	@Override
	public ClientConfiguration clientConfiguration() {
		return ClientConfiguration.builder()           
			.connectedTo(hosts)
			.build();
	}
}