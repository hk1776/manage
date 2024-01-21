package com.example.manage.web.item.form;

public class MemoUpdateForm {
    private String writer;
    private String content;

    public MemoUpdateForm(String writer, String content) {
        this.writer = writer;
        this.content = content;
    }

    public MemoUpdateForm() {
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
