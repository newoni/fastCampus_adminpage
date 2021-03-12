package com.example.study.model.newtwork;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class Pagination {

    private Integer totalPages;
    private Long totalElements;
    private Integer currentPage;
    private Integer currentElements;
}
