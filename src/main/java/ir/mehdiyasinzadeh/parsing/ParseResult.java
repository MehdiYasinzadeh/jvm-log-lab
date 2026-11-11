package ir.mehdiyasinzadeh.parsing;


import ir.mehdiyasinzadeh.domain.LogEvent;

public sealed interface ParseResult permits ParseResult.Success, ParseResult.Failure {

    record Success(LogEvent event) implements ParseResult {
    }

    record Failure(String rawLine, String reason) implements ParseResult {
    }
}