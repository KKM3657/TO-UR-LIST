package com.eminyidle.checklist.dto;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node(primaryLabel = "ACTIVITY")
public class Activity {
    @Id
    private String activity;

    @Relationship(type = "NEED")
    private List<Item> itemList;
}
