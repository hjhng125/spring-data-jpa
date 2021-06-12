package me.hjhng125.springdatajpa.study;

import org.springframework.context.event.EventListener;

/**
 * ApplicationListener를 구현하거나
 * @EventListener를 붙여주면 eventListener로 동작함. */
public class StudyEventListener
//    implements ApplicationListener<StudyEvent>
{

    @EventListener
    public void onApplicationEvent(StudyEvent studyEvent) {
        System.out.println("========================");
        System.out.println(studyEvent.getStudy().getName() + " is published!");
        System.out.println("========================");
    }
}
