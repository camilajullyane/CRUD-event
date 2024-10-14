package org.upe.persistence.repository.interfaces;

public interface ArticleInterface {
    String getName();
    String getArticleID();
    String getUserCPF();
    String getArticleAbstract();
    String toString(int position);
}
