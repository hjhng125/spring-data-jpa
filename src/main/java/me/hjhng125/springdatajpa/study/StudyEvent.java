package me.hjhng125.springdatajpa.study;

import org.springframework.context.ApplicationEvent;

public class StudyEvent extends ApplicationEvent {

    private final Study study;

    public StudyEvent(Object source) {
        super(source);
        this.study = (Study) source;
    }

    public Study getStudy() {
        return study;
    }
}
