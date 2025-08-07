package com.back.domain;

public class WiseSaying {
    static int lastId = 0;
    int id;
    String content;
    String author;

    public WiseSaying(String content, String author) {
        this.id = ++lastId;
        this.content = content;
        this.author = author;
    }

    public static int getLastId() {
        return lastId;
    }

    public static void setLastId(int lastId) {
        WiseSaying.lastId = lastId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
