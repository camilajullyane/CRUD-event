package org.upe.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SubEventUtilityTest {

    private static final String TEST_CSV_FILE_PATH = "DB/test_subevent.csv";

    @BeforeEach
    void setUp() throws IOException {
        // Criação de um arquivo CSV temporário para fins de teste
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_CSV_FILE_PATH))) {
            writer.write("id,parentEventID,name,date,local,description,attendeesList,speaker\n");
            writer.write("1,101,SubEvent 1,01/01/2022,Local 1,Description 1,123#456,Speaker 1\n");
            writer.write("2,102,SubEvent 2,02/02/2022,Local 2,Description 2,789,Speaker 2\n");
        }
        // Atualizar o caminho do CSV para os testes
        SubEventUtility.CSV_FILE_PATH = TEST_CSV_FILE_PATH;
    }




    @Test
    void getSubEventById() {
        SubEvent subEvent = SubEventUtility.getSubEventById("1");
        assertNotNull(subEvent, "O subevento com o ID '1' deve existir");
        assertEquals("SubEvent 1", subEvent.getName(), "O nome do subevento deve ser 'SubEvent 1'");

        SubEvent nonExistentSubEvent = SubEventUtility.getSubEventById("non-existent-id");
        assertNull(nonExistentSubEvent, "O subevento com o ID 'non-existent-id' não deve existir");
    }



    @Test
    void deleteSubEvent() {
        boolean isDeleted = SubEventUtility.deleteSubEvent("1");

        assertTrue(isDeleted, "O subevento deve ser deletado com sucesso");

        SubEvent deletedSubEvent = SubEventUtility.getSubEventById("1");
        assertNull(deletedSubEvent, "O subevento com o ID '1' não deve mais existir");

        ArrayList<SubEvent> actualSubEvents = SubEventUtility.getAllSubEvents();
        assertEquals(1, actualSubEvents.size(), "O número de subeventos deve ser 1 após a deleção do subevento");
    }

    @Test
    void updateSubEventName() {
        boolean isUpdated = SubEventUtility.updateSubEventName("1", "New SubEvent Name");

        assertTrue(isUpdated, "O nome do subevento deve ser atualizado com sucesso");

        SubEvent subEvent = SubEventUtility.getSubEventById("1");
        assertEquals("New SubEvent Name", subEvent.getName(), "O nome do subevento deve ser 'New SubEvent Name'");
    }

    @Test
    void updateSubEventDate() {
        boolean isUpdated = SubEventUtility.updateSubEventDate("1", "05/05/2023");

        assertTrue(isUpdated, "A data do subevento deve ser atualizada com sucesso");

        SubEvent subEvent = SubEventUtility.getSubEventById("1");
        assertEquals("05/05/2023", subEvent.getDate(), "A data do subevento deve ser '05/05/2023'");
    }

    @Test
    void updateSubEventLocal() {
        boolean isUpdated = SubEventUtility.updateSubEventLocal("1", "New Local");

        assertTrue(isUpdated, "O local do subevento deve ser atualizado com sucesso");

        SubEvent subEvent = SubEventUtility.getSubEventById("1");
        assertEquals("New Local", subEvent.getLocal(), "O local do subevento deve ser 'New Local'");
    }

    @Test
    void updateSubEventDescription() {
        boolean isUpdated = SubEventUtility.updateSubEventDescription("1", "New Description");

        assertTrue(isUpdated, "A descrição do subevento deve ser atualizada com sucesso");

        SubEvent subEvent = SubEventUtility.getSubEventById("1");
        assertEquals("New Description", subEvent.getDescription(), "A descrição do subevento deve ser 'New Description'");
    }


}
