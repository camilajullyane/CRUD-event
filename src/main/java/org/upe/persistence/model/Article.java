package org.upe.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
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
    private List<Event> submittedIn = new ArrayList<>();

    private Article(String title, String articleAbstract, User user, List<Event> submittedIn) {
        this.title = title;
        this.articleAbstract = articleAbstract;
        this.user = user;
        this.submittedIn = submittedIn;
    }

    public Article() {}

    public List<EventInterface> getSubmittedIn() {
        return new ArrayList<>(submittedIn);
    }

    public void addSubmittedIn(EventInterface event) {
        submittedIn.add((Event) event);
    }

    public UserInterface getUser() {
        return user;
    }

    public static class ArticleBuilder {
        private String title;
        private String articleAbstract;
        private User user;
        private List<Event> submittedIn = new ArrayList<>();

        public ArticleBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public ArticleBuilder withArticleAbstract(String articleAbstract) {
            this.articleAbstract = articleAbstract;
            return this;
        }

        public ArticleBuilder withUser(User user) {
            this.user = user;
            return this;
        }

        public ArticleBuilder withSubmittedIn(List<Event> submittedIn) {
            this.submittedIn = submittedIn;
            return this;
        }

        public Article build() {
            return new Article(title, articleAbstract, user, submittedIn);
        }
    }

    public static ArticleBuilder builder() {
        return new ArticleBuilder();
    }
}
