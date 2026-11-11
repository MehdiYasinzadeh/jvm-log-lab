package ir.mehdiyasinzadeh.statistics;

import ir.mehdiyasinzadeh.domain.ParseStatistics;
import ir.mehdiyasinzadeh.parsing.ParseResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Mehdi Yasinzadeh
 * @since 10/27/2026
 */
public class ParseStatisticsCalculator {

    public ParseStatistics calculate(List<ParseResult> parseResults) {

        long successCount = parseResults.stream()
                .filter(parseResult -> parseResult instanceof ParseResult.Success)
                .count();
        long failureCount = parseResults.stream()
                .filter(parseResult -> parseResult instanceof ParseResult.Failure)
                .count();
        System.out.println("Total: " + parseResults.size());
        System.out.println("Success: " + successCount);
        System.out.println("Failed: " + failureCount);
        List<ParseResult.Failure> failures = parseResults.stream()
                .filter(parseResult -> parseResult instanceof ParseResult.Failure)
                .map(parseResult -> (ParseResult.Failure) parseResult)
                .toList();

        Map<String, Long> failureCountByReason = failures.stream()
                .collect(Collectors.groupingBy(
                        ParseResult.Failure::reason, Collectors.counting()
                ));
        System.out.println("Failure map: " + failureCountByReason);
        return
                new ParseStatistics(parseResults.size(),successCount,failureCount,failureCountByReason);
    }
}
