package com.bookstore.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {
    private Integer id;
    private String name;
    private String author;

    @JsonProperty("published_year")
    private Integer publishedYear;

    @JsonProperty("book_summary")
    private String bookSummary;
}
