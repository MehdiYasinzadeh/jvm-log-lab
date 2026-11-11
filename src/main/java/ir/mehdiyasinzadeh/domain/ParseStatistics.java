package ir.mehdiyasinzadeh.domain;

import java.util.Map;

/**
 * @author Mehdi Yasinzadeh
 * @since 10/27/2026
 */
public record ParseStatistics(
        long total,
        long successCount,
        long failureCount,
        Map<String, Long> failuresByReason
) {
}