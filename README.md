# Elasticsearch Migrator

Uses the `elasticsearch-evolution` library to migrate index mappings and other automated processes

## Quickstart

Docker -  

```
docker run -e SPRING_ELASTICSEARCH_URIS=http://elastic:9200 savantly/elasticsearch-migrator
```

To provide your own migration files, mount the folder containing your migration files, or copy them here using your own Docker image.   

```
/app/BOOT-INF/classes/es/migration
```

A rest API is provided to view the migrations -  

[http://localhost:8080/search/migration](http://localhost:8080/search/migration)