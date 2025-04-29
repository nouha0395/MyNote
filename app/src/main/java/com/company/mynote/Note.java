package com.company.mynote;

public class Note {
    private int id;
    private String title;
    private String content;
    private String subject;
    private String time;
    private boolean archived;

    // Constructor مع الأرشفة
    public Note(int id, String title, String content, String subject, String time, boolean archived) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.subject = subject;
        this.time = time;
        this.archived = archived;
    }

    // Constructor بدون أرشفة (للقيم الافتراضية)
    public Note(int id, String title, String content, String subject, String time) {
        this(id, title, content, subject, time, false);
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getSubject() { return subject; }
    public String getTime() { return time; }
    public boolean isArchived() { return archived; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setTime(String time) { this.time = time; }
    public void setArchived(boolean archived) { this.archived = archived; }
}
