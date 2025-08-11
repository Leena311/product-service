package com.example;

import java.time.LocalDateTime;

public record CategoryResponseDto(Long id, String name,  LocalDateTime createdDate) {

}
