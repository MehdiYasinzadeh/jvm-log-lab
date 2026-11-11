package ir.mehdiyasinzadeh.ingestion;

import ir.mehdiyasinzadeh.statistics.LogCounter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogReader {

    public List<String> read(String path) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        List<String> logs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                logs.add(line);
            }
        }
        return logs;
    }

    public List<String> reads(String path) throws IOException {
        List<String> logs = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        LogCounter counter = new LogCounter();

        try (var files = Files.list(Path.of(path))) {
            for (Path file : files
                    .filter(Files::isRegularFile)
                    .toList()) {

                threads.add(new Thread(() -> {
                    String fileName = file.getFileName().toString();
                    String currentThreadName = Thread.currentThread().getName();
                    int lineFile = 0;
                    try {
                        lineFile = Files.readAllLines(file).size();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("thread " + currentThreadName + " start for processing: " + fileName
                            + " with " + lineFile + " line");
                    for (int i = 0; i < 10_000; i++) {
                        counter.increment();
                    }
                }));
            }
        }
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Counter: " + counter.getCount());
        return logs;
    }
}
