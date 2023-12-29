package com.treewalkinterpreter.lox;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Lox{
    public static void main(String[] args) throws IOException {
        if (args.length > 1){
            System.out.println("At most one argument");
            System.exit(64);
        } else if (args.length == 1){
            runFile(args[0]);
        } else{
            runPrompt();
        }
    }

    public static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
    }

    public static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for(;;){
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
        }
    }

    public static void run(String source){
        System.out.println("Running!\n");
    }
}
