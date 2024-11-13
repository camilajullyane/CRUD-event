package org.upe.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.UserInterface;

import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
public class Article implements ArticleInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String articleAbstract;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User user;
    @ManyToMany
    @JoinTable(
            name = "article_event",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> event;

    public Article(String title, String articleAbstract, UserInterface user) {
        this.title = title;
        this.articleAbstract = articleAbstract;
        this.user = (User) user;
    }

    public Article() {}
}
