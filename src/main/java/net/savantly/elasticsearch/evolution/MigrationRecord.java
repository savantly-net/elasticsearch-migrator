package net.savantly.elasticsearch.evolution;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "es_evolution")
public class MigrationRecord {

    @Id
    private String id;

    @Field(type = FieldType.Text, name = "version")
    private String version;

    @Field(type = FieldType.Boolean, name = "locked")
    private Boolean locked;

    @Field(type = FieldType.Boolean, name = "success")
    private Boolean success;
}
