package me.hjhng125.springdatajpa.board;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import jdk.nashorn.internal.objects.annotations.Getter;

@Entity
public class Board {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String description;

    private boolean best;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isBest() {
        return best;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBest(boolean best) {
        this.best = best;
    }
}
