package com.example.RoomManagementSystem.domain.pagination;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationUtils {

    public static Pageable getPageable(PaginationRequest paginationRequest) {
        return PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getPageSize(),
                paginationRequest.getDirection(), paginationRequest.getSortBy());
    }
}
