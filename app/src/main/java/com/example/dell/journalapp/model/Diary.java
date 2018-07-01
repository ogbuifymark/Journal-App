package com.example.dell.journalapp.model;

import com.j256.ormlite.field.DatabaseField;

public class Diary {

    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private String diaryContent;
    @DatabaseField
    private Long dateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiaryContent() {
        return diaryContent;
    }

    public void setDiaryContent(String diaryContent) {
        this.diaryContent = diaryContent;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }
}
