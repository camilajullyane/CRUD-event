package org.upe.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.upe.persistence.DAO.ArticleDAO;
import org.upe.persistence.DAO.EventDAO;
import org.upe.persistence.DAO.UserDAO;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.model.Article;
import org.upe.persistence.model.Event;
import org.upe.persistence.model.User;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArticleControllerTest {
    @InjectMocks
    private ArticleController articleController;

    @Mock
    ArticleDAO articleDAO;
    @Mock
    EventDAO eventDAO;
    @Mock
    UserDAO userDAO;

    private static Article mockArticle;
    private static User mockUser;
    private static Event mockEvent;

    @BeforeAll
    static void setUp() {
        mockArticle = Article.Builder()
                .withTitle("Article")
                .withArticleAbstract("Abstract")
                .build();

        mockUser = User.Builder()
                .withName("Pam Bessly")
                .withEmail("bessly@example.com")
                .build();

        mockEvent = Event.Builder().withName("Event").withDescription("Description")
                .withBeginDate(LocalDate.now())
                .withEndDate(LocalDate.now())
                .withLocal("Local")
                .withOrganization("Org")
                .withOwner(mockUser)
                .build();
    }

    @Test
    void testCreateArticle() {
        when(articleDAO.create("Article", "Abstract", mockUser)).thenReturn(mockArticle);
        ArticleInterface result = articleController.createArticle(mockUser, "Article", "Abstract");
        assertNotNull(result);
    }

//    @Test
//    void testSubmitArticle() {
//        when(articleDAO.getAll()).thenReturn(Collections.singletonList(mockArticle));
//        when(articleDAO.findById(mockArticle.getId())).thenReturn(mockArticle);
//
//
//        boolean result = articleController.submitArticle(mockArticle, mockEvent);
//
//        assertTrue(result);
//        verify(mockArticle).addSubmittedIn(mockEvent);
////        verify(articleDAO).update(mockArticle);
//        verify(mockEvent).addArticleOnEvent(mockArticle);
////        verify(eventDAO).update(mockEvent);
//    }
//
//    @Test
//    void testDeleteArticle() {
//        when(mockArticle.getId()).thenReturn(UUID.randomUUID());
//        boolean result = articleController.deleteArticle(mockArticle);
//        assertTrue(result);
//        verify(articleDAO).delete(mockArticle.getId());
//    }

}
