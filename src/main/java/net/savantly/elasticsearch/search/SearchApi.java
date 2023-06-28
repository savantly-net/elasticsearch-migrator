package net.savantly.elasticsearch.search;

import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.client.elc.Queries;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        Query query = new NativeQueryBuilder()
                .withQuery(Queries.matchAllQuery()._toQuery())
                .build();
        return elasticsearchRestTemplate.search(query, MigrationRecord.class);
    }

    @GetMapping("/es/simple")
    public SearchHits<Object> getSearchResults(@RequestParam("index") String[] indices, @RequestParam("q") String qString) {
        Query query = new NativeQueryBuilder()
                .withQuery(Queries.queryStringQuery("*", qString, 0f)._toQuery())
                .build();
        return elasticsearchRestTemplate.search(query, Object.class, IndexCoordinates.of(indices));
    }

}
