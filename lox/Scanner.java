package com.treewalkinterpreter.lox;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Scanner{
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    Scanner(String source){
        this.source = source;
    }

    List<Token> scanTokens(){
        while (!isAtEnd()){
            start = current;
            scanToken();
        }

        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }

    boolean isAtEnd(){
        return current >= source.length();
    }

    private void scanToken(){
        char c = advance();
        switch(c){
            // Single-character tokens except Slash (treat comments)
            case '(': addToken(TokenType.LEFT_PAREN); break;
            case ')': addToken(TokenType.RIGHT_PAREN); break;
            case '{': addToken(TokenType.RIGHT_BRACE); break;
            case '}': addToken(TokenType.RIGHT_BRACE); break;
            case ',': addToken(TokenType.COMMA); break;
            case '.': addToken(TokenType.DOT); break;
            case '-': addToken(TokenType.MINUS); break;
            case '+': addToken(TokenType.PLUS); break;
            case ';': addToken(TokenType.SEMICOLON); break;
            // One or two character tokens
            case '!':
                      addToken((match('=')) ? TokenType.BANG_EQUAL : TokenType.BANG); break;
            case '=':
                      addToken((match('=')) ? TokenType.EQUAL_EQUAL : TokenType.EQUAL); break;
            case '>':
                      addToken((match('=')) ? TokenType.GREATER_EQUAL : TokenType.GREATER); break;
            case '<':
                      addToken((match('=')) ? TokenType.LESS_EQUAL : TokenType.LESS); break;
            case '"': string(); break;
            default:
                        Lox.error(line, "Unexpected character."); break;
        }
    }
    
    private void addToken(TokenType type){
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal){
        tokens.add(new Token(type, source.substring(start, current), literal, line));
    }

    private char advance(){
        return source.charAt(current++);
    }

    private char peek(){
        return source.charAt(current);
    }

    private boolean match(char expected){
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    private void string(){
        String str = "";
        while(!isAtEnd() && peek() != '"'){
            if (peek() == '\n') line++;
            advance();
        }
        
        if (isAtEnd()){
            Lox.error(line, "Unterminated string.");
            return;
        }

        // The closing ".
        advance();
        addToken(TokenType.STRING, source.substring(start+1, current-1));
    }
}

