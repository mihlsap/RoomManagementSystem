package com.example.RoomManagementSystem.domain.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingResult<T> {

    private Collection<T> content;
    private Integer totalPages;
    private Long totalElements;
    private Integer pageSize;
    private Integer pageNumber;
    private Boolean empty;
}
