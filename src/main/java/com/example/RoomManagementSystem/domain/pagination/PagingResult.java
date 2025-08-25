package com.example.RoomManagementSystem.domain.pagination;

import java.util.Collection;

public class PagingResult<T> {

    private Collection<T> content;
    private Integer totalPages;
    private Long totalElements;
    private Integer pageSize;
    private Integer pageNumber;
    private Boolean empty;
}
