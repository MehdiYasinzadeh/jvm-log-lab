package ir.mehdiyasinzadeh.parsing;

import ir.mehdiyasinzadeh.domain.LogEvent;
import ir.mehdiyasinzadeh.domain.LogLevel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LogParser {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ParseResult parse(String line) {
        String[] split = line.split(" - ", 2);

        if (split.length != 2) {
            return new ParseResult.Failure(
                    line,
                    "Malformed log structure"
            );
        }

        String[] details = split[0].split(" ");

        if (details.length < 4) {
            return new ParseResult.Failure(
                    line,
                    "Malformed log details"
            );
        }

        LogLevel level;

        try {
            level = LogLevel.valueOf(details[2]);
        } catch (IllegalArgumentException e) {
            return new ParseResult.Failure(
                    line,
                    "Unknown log level: " + details[2]
            );
        }

        try {
            String dateTime = details[0] + " " + details[1];
            LocalDateTime time = LocalDateTime.parse(dateTime, FORMATTER);

            return new ParseResult.Success(
                    new LogEvent(
                            time,
                            level,
                            details[3],
                            split[1]
                    )
            );

        } catch (DateTimeParseException e) {
            return new ParseResult.Failure(
                    line,
                    "Invalid timestamp"
            );
        }
    }
}
