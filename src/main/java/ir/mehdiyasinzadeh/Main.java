package ir.mehdiyasinzadeh;


import ir.mehdiyasinzadeh.domain.ParseStatistics;
import ir.mehdiyasinzadeh.ingestion.LogReader;
import ir.mehdiyasinzadeh.parsing.LogParser;
import ir.mehdiyasinzadeh.parsing.ParseResult;
import ir.mehdiyasinzadeh.statistics.ParseStatisticsCalculator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    static void main() {
        LogReader logReader = new LogReader();
        List<String> logs;
        try {
            logs = logReader.reads("D:\\level-up\\jvm-log-lab\\src\\main\\resources\\logs");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LogParser parser = new LogParser();
        List<ParseResult> parseResults = logs.stream()
                .filter(log -> !log.isBlank())
                .map(parser::parse)
                .toList();

        System.out.println("---------------------------");
        ParseStatisticsCalculator calculator =
                new ParseStatisticsCalculator();

        ParseStatistics statistics =
                calculator.calculate(parseResults);

        System.out.println(statistics);

    }
}
