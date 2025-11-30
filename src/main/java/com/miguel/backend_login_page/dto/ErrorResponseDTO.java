package com.miguel.backend_login_page.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
    String message,
    String error,
    int status,
    LocalDateTime timestamp
) { }
