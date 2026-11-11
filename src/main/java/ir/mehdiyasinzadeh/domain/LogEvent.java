package ir.mehdiyasinzadeh.domain;

import java.time.LocalDateTime;

public record LogEvent(LocalDateTime timestamp,
                       LogLevel level,
                       String source,
                       String message) {
}
