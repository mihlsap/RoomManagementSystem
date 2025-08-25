package com.example.RoomManagementSystem.domain.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Sort;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequest {

    @Builder.Default
    private int pageNumber = 1;

    @Builder.Default
    private int pageSize = 10;

    @Builder.Default
    private String sortBy = "id";

    @Builder.Default
    private Sort.Direction direction = Sort.Direction.ASC;
}
