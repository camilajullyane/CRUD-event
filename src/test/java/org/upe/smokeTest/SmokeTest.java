 package org.upe.smokeTest;


 import javafx.scene.control.Button;
 import javafx.stage.Stage;
 import org.junit.jupiter.api.Test;
 import org.testfx.framework.junit5.ApplicationTest;
 import org.upe.ui.StartApp;
 import java.io.IOException;

 import static org.assertj.core.api.Assertions.assertThat;
 import static org.junit.jupiter.api.Assertions.assertNotNull;


 public class SmokeTest extends ApplicationTest  {
     private Stage stage;

     @Override
     public void start(Stage stage) throws IOException {
         this.stage = stage;
         new StartApp().start(stage);
     }

     @Test
     public void testAppStart() {
         assertNotNull(stage, "A aplicação não foi iniciada corretamente.");
         assertThat(stage.getTitle()).isEqualTo("Even2");
         assertThat(stage.isShowing()).isTrue();

         // Verifique se um botão específico está presente
         Button someButton = lookup("#signInButton").query();
         assertNotNull(someButton, "O botão específico não foi encontrado.");
         assertThat(someButton.isVisible()).isTrue();

     }


 }
