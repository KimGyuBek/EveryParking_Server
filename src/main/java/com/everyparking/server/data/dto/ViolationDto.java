package com.everyparking.server.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 관리자 페이지에서 위약처리를 위한 DTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViolationDto {
    private String userId;
}
