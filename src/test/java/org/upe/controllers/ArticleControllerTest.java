package org.upe.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.upe.persistence.DAO.ArticleDAO;
import org.upe.persistence.DAO.EventDAO;
import org.upe.persistence.DAO.UserDAO;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.model.Article;
import org.upe.persistence.model.Event;
import org.upe.persistence.model.User;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ArticleControllerTest {
    @InjectMocks
    private ArticleController articleController;

    @Mock
    ArticleDAO articleDAO;
    @Mock
    EventDAO eventDAO;
    @Mock
    UserDAO userDAO;

    @Mock
    private static Article mockArticle;
    @Mock
    private static User mockUser;
    @Mock
    private static Event mockEvent;

    @Test
    void testCreateArticle() {
        when(articleDAO.create("Article", "Abstract", mockUser)).thenReturn(mockArticle);
        ArticleInterface result = articleController.createArticle(mockUser, "Article", "Abstract");
        assertNotNull(result);
    }

    @Test
    void testSubmitArticle() {
        when(articleDAO.getAll()).thenReturn(Collections.singletonList(mockArticle));
        when(articleDAO.findById(mockArticle.getId())).thenReturn(mockArticle);

        doNothing().when(mockArticle).addSubmittedIn(mockEvent);
        doNothing().when(mockEvent).addArticleOnEvent(mockArticle);
        when(articleDAO.update(mockArticle)).thenReturn(mockArticle);
        doNothing().when(eventDAO).update(mockEvent);


        boolean result = articleController.submitArticle(mockArticle, mockEvent);
        assertTrue(result);


        verify(mockArticle).addSubmittedIn(mockEvent);
        verify(articleDAO).update(mockArticle);
        verify(mockEvent).addArticleOnEvent(mockArticle);
        verify(eventDAO).update(mockEvent);
    }

    @Test
    void testDeleteArticle() {
        when(mockArticle.getId()).thenReturn(UUID.randomUUID());
        boolean result = articleController.deleteArticle(mockArticle);
        assertTrue(result);
        verify(articleDAO).delete(mockArticle.getId());
    }

}
