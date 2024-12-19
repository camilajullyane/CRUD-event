package org.upe.smokeTest;


import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.utils.SceneLoader;
import org.upe.ui.StartApp;
import org.upe.utils.UserSession;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


public class SmokeTest extends ApplicationTest {
    private Stage stage;

    private StackPane signUpPage;

    private StackPane loginPage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        new StartApp().start(stage);
    }

    @BeforeEach
    public void setUp() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                start(stage);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        });
        latch.await();
    }

    @Test
    public void testAppStart() {
        assertNotNull(stage, "A aplicação não foi iniciada corretamente.");
        assertThat(stage.getTitle()).isEqualTo("Even2");
    }

    @Test
    public void testSignUpScreen() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/upe/ui/telaCadastro.fxml"));
        Parent root = loader.load();

        Platform.runLater(() -> {
            stage.setScene(new Scene(root));
            stage.show();
        });

        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(lookup("#signUpUser").query(), "Botão de cadastro não encontrado.");
    }

    @Test
    public void testHomeScreen() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/upe/ui/telaInicio.fxml"));
        Parent root = loader.load();

        Platform.runLater(() -> {
            stage.setScene(new Scene(root));
            stage.show();
        });

        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(lookup("#createEventButton").query(), "Botão de criar evento não encontrado.");
    }

    @Test
    public void testScheduleScreen() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/upe/ui/telaProgramacao.fxml"));
        Parent root = loader.load();

        Platform.runLater(() -> {
            stage.setScene(new Scene(root));
            stage.show();
        });

        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(lookup("#homeButton").query(), "Botão home não encontrado.");
    }

    @Test
    public void testSubmissionScreen() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/upe/ui/telaSubmissões.fxml"));
        Parent root = loader.load();

        Platform.runLater(() -> {
            stage.setScene(new Scene(root));
            stage.show();
        });

        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(lookup("#homeButton").query(), "Botão home não encontrado.");
    }

    @Test
    public void testSubscriptionScreen() throws Exception {
        UserInterface mockUser = mock(UserInterface.class);
        when(mockUser.getName()).thenReturn("testUser");

        try (MockedStatic<UserSession> mockedSession = mockStatic(UserSession.class)) {
            mockedSession.when(UserSession::getCurrentUser).thenReturn(mockUser);

            // Load the screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/upe/ui/telaInscricoes.fxml"));
            Parent root = loader.load();

            Platform.runLater(() -> {
                stage.setScene(new Scene(root));
                stage.show();
            });

            WaitForAsyncUtils.waitForFxEvents();

            assertNotNull(lookup("#homeButton").query(), "Botão home não encontrado.");
        }
    }

    @Test
    public void testMyEventsScreen () throws Exception {
        UserInterface mockUser = mock(UserInterface.class);
        when(mockUser.getName()).thenReturn("testUser");

        try (MockedStatic<UserSession> mockedSession = mockStatic(UserSession.class)) {
            mockedSession.when(UserSession::getCurrentUser).thenReturn(mockUser);

            // Load the screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/upe/ui/myEvents.fxml"));
            Parent root = loader.load();

            Platform.runLater(() -> {
                stage.setScene(new Scene(root));
                stage.show();
            });

            WaitForAsyncUtils.waitForFxEvents();

            assertNotNull(lookup("#homeButton").query(), "Botão home não encontrado.");
        }
    }

    @Test
    public void testCertificateScreen() throws Exception {
        UserInterface mockUser = mock(UserInterface.class);
        when(mockUser.getName()).thenReturn("testUser");

        try (MockedStatic<UserSession> mockedSession = mockStatic(UserSession.class)) {
            mockedSession.when(UserSession::getCurrentUser).thenReturn(mockUser);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/upe/ui/telaCertificado.fxml"));
            Parent root = loader.load();

            Platform.runLater(() -> {
                stage.setScene(new Scene(root));
                stage.show();
            });

            WaitForAsyncUtils.waitForFxEvents();

            assertNotNull(lookup("#homeButton").query(), "Botão home não encontrado.");
        }
    }

    @Test
    public void testCreateEventScreen() throws Exception {
        UserInterface mockUser = mock(UserInterface.class);
        when(mockUser.getName()).thenReturn("testUser");

        try (MockedStatic<UserSession> mockedSession = mockStatic(UserSession.class)) {
            mockedSession.when(UserSession::getCurrentUser).thenReturn(mockUser);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/upe/ui/telaCriandoEvento.fxml"));
            Parent root = loader.load();

            Platform.runLater(() -> {
                stage.setScene(new Scene(root));
                stage.show();
            });

            WaitForAsyncUtils.waitForFxEvents();

            assertNotNull(lookup("#eventName").query(), "TextField não encontrado.");        }
    }

    @Test
    public void testCreateSubEventScreen() throws Exception {
        UserInterface mockUser = mock(UserInterface.class);
        when(mockUser.getName()).thenReturn("testUser");

        try (MockedStatic<UserSession> mockedSession = mockStatic(UserSession.class)) {
            mockedSession.when(UserSession::getCurrentUser).thenReturn(mockUser);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/upe/ui/telaCriandoSubEvento.fxml"));
            Parent root = loader.load();

            Platform.runLater(() -> {
                stage.setScene(new Scene(root));
                stage.show();
            });

            WaitForAsyncUtils.waitForFxEvents();

            assertNotNull(lookup("#subEventName").query(), "TextField não encontrado.");
        }
    }

    @Test
    public void testCreateArticleScreen() throws Exception {
        UserInterface mockUser = mock(UserInterface.class);
        when(mockUser.getName()).thenReturn("testUser");

        try (MockedStatic<UserSession> mockedSession = mockStatic(UserSession.class)) {
            mockedSession.when(UserSession::getCurrentUser).thenReturn(mockUser);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/upe/ui/telaCriandoArtigo.fxml"));
            Parent root = loader.load();

            Platform.runLater(() -> {
                stage.setScene(new Scene(root));
                stage.show();
            });

            WaitForAsyncUtils.waitForFxEvents();

            assertNotNull(lookup("#articleText").query(), "TextField não encontrado.");
        }
    }

    @Test
    public void testSubEventScreen() throws Exception {
        UserInterface mockUser = mock(UserInterface.class);
        when(mockUser.getName()).thenReturn("testUser");

        EventInterface mockEvent = mock(EventInterface.class);
        when(mockEvent.getName()).thenReturn("Test Event");

        try (MockedStatic<UserSession> mockedSession = mockStatic(UserSession.class);
        MockedStatic<SceneLoader> mockedSceneLoader = mockStatic(SceneLoader.class)) {
            mockedSession.when(UserSession::getCurrentUser).thenReturn(mockUser);
            mockedSceneLoader.when(SceneLoader::getEventData).thenReturn(mockEvent);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/upe/ui/telaSubEvento.fxml"));
            Parent root = loader.load();

            Platform.runLater(() -> {
                stage.setScene(new Scene(root));
                stage.show();
            });

            WaitForAsyncUtils.waitForFxEvents();

            assertNotNull(lookup("#homeButton").query(), "Botão home não encontrado.");
        }
    }

}

