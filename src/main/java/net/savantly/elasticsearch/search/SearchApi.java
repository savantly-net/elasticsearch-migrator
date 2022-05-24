package net.savantly.elasticsearch.search;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.savantly.elasticsearch.evolution.MigrationRecord;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchApi {

    final private ElasticsearchOperations elasticsearchRestTemplate;

    @GetMapping("/migration")
    public SearchHits<MigrationRecord> getLogDatasByHost(String host) {
        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .build();
        return elasticsearchRestTemplate.search(query, MigrationRecord.class);
    }

}
