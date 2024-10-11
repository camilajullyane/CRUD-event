package org.upe.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.upe.persistence.model.SubEvent;
import org.upe.persistence.repository.SubEventUtility;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubEventUtilityTest {

    private static final String testCsvFilePath = "DB/teste/test_subevent.csv";

    @BeforeEach
    void setUp() throws IOException {
        // Criação de um arquivo CSV temporário para fins de teste
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testCsvFilePath))) {
            writer.write("id,parentEventID,name,date,hour,local,description,speaker,attendeesList\n");
            writer.write("1,1001,SubEvent 1,01/01/2024,10:00,Local 1,Description 1,Speaker 1,123456789\n");
            writer.write("2,1002,SubEvent 2,02/01/2024,11:00,Local 2,Description 2,Speaker 2,\n");
            writer.write("3,1001,SubEvent 3,03/01/2024,12:00,Local 3,Description 3,Speaker 3,987654321\n");
        }
        // Atualizar o caminho do CSV para os testes
        SubEventUtility.setCsvFilePath(testCsvFilePath);
    }

    @Test
    void getAllSubEvents() {
        List<SubEvent> actualSubEvents = SubEventUtility.getAllSubEvents();

        assertEquals(3, actualSubEvents.size(), "O número de sub-eventos deve ser o mesmo");

        SubEvent subEvent1 = actualSubEvents.get(0);
        assertEquals("1", subEvent1.getId(), "O ID do primeiro sub-evento deve ser '1'");
        assertEquals("1001", subEvent1.getParentEventID(), "O ID do evento pai do primeiro sub-evento deve ser '1001'");
        assertEquals("SubEvent 1", subEvent1.getName(), "O nome do primeiro sub-evento deve ser 'SubEvent 1'");
        assertEquals("01/01/2024", subEvent1.getDate(), "A data do primeiro sub-evento deve ser '01/01/2024'");
        assertEquals("10:00", subEvent1.getHour(), "A hora do primeiro sub-evento deve ser '10:00'");
        assertEquals("Local 1", subEvent1.getLocal(), "O local do primeiro sub-evento deve ser 'Local 1'");
        assertEquals("Description 1", subEvent1.getDescription(), "A descrição do primeiro sub-evento deve ser 'Description 1'");
        assertEquals("Speaker 1", subEvent1.getSpeakers(), "O palestrante do primeiro sub-evento deve ser 'Speaker 1'");
        assertEquals("123456789",subEvent1.getAttendeesList()[0], "A lista de participantes do" +
                " primeiro sub-evento deve " +
            "ser'123456789'");

        SubEvent subEvent2 = actualSubEvents.get(1);
        assertEquals("2", subEvent2.getId(), "O ID do segundo sub-evento deve ser '2'");
        assertEquals("1002", subEvent2.getParentEventID(), "O ID do evento pai do segundo sub-evento deve ser '1002'");
        assertEquals("SubEvent 2", subEvent2.getName(), "O nome do segundo sub-evento deve ser 'SubEvent 2'");
        assertEquals("02/01/2024", subEvent2.getDate(), "A data do segundo sub-evento deve ser '02/01/2024'");
        assertEquals("11:00", subEvent2.getHour(), "A hora do segundo sub-evento deve ser '11:00'");
        assertEquals("Local 2", subEvent2.getLocal(), "O local do segundo sub-evento deve ser 'Local 2'");
        assertEquals("Description 2", subEvent2.getDescription(), "A descrição do segundo sub-evento deve ser 'Description 2'");
        assertEquals("Speaker 2", subEvent2.getSpeakers(), "O palestrante do segundo sub-evento deve ser 'Speaker 2'");
        assertEquals(1, subEvent2.getAttendeesList().length, "A lista de participantes do segundo sub-evento deve ser " +
                "vazia");

        SubEvent subEvent3 = actualSubEvents.get(2);
        assertEquals("3", subEvent3.getId(), "O ID do terceiro sub-evento deve ser '3'");
        assertEquals("1001", subEvent3.getParentEventID(), "O ID do evento pai do terceiro sub-evento deve ser '1001'");
        assertEquals("SubEvent 3", subEvent3.getName(), "O nome do terceiro sub-evento deve ser 'SubEvent 3'");
        assertEquals("03/01/2024", subEvent3.getDate(), "A data do terceiro sub-evento deve ser '03/01/2024'");
        assertEquals("12:00", subEvent3.getHour(), "A hora do terceiro sub-evento deve ser '12:00'");
        assertEquals("Local 3", subEvent3.getLocal(), "O local do terceiro sub-evento deve ser 'Local 3'");
        assertEquals("Description 3", subEvent3.getDescription(), "A descrição do terceiro sub-evento deve ser 'Description 3'");
        assertEquals("Speaker 3", subEvent3.getSpeakers(), "O palestrante do terceiro sub-evento deve ser 'Speaker 3'");
        assertEquals("987654321", subEvent3.getAttendeesList()[0], "A lista de participantes do terceiro sub-evento deve ser " +
                "'987654321'");
    }



    @Test
    void getSubEventById() {
        SubEvent subEvent = SubEventUtility.getSubEventById("1");
        assertNotNull(subEvent, "O sub-evento com o ID '1' deve existir");
        assertEquals("SubEvent 1", subEvent.getName(), "O nome do sub-evento deve ser 'SubEvent 1'");

        SubEvent nonExistentSubEvent = SubEventUtility.getSubEventById("non-existent-id");
        assertNull(nonExistentSubEvent, "O sub-evento com o ID 'non-existent-id' não deve existir");
    }

    @Test
    void getSubEventByEvent() {
        String parentEventID = "1001";
        List<SubEvent> subEvents = SubEventUtility.getSubEventByEvent(parentEventID);

        assertNotNull(subEvents, "A lista de sub-eventos do evento pai não deve ser nula");
        assertEquals(2, subEvents.size(), "O número de sub-eventos do evento pai deve ser 2");

        SubEvent subEvent1 = subEvents.get(0);
        assertEquals("1", subEvent1.getId(), "O ID do primeiro sub-evento deve ser '1'");
        assertEquals("1001", subEvent1.getParentEventID(), "O ID do evento pai do primeiro sub-evento deve ser '1001'");
        assertEquals("SubEvent 1", subEvent1.getName(), "O nome do primeiro sub-evento deve ser 'SubEvent 1'");
        assertEquals("01/01/2024", subEvent1.getDate(), "A data do primeiro sub-evento deve ser '01/01/2024'");
        assertEquals("10:00", subEvent1.getHour(), "A hora do primeiro sub-evento deve ser '10:00'");
        assertEquals("Local 1", subEvent1.getLocal(), "O local do primeiro sub-evento deve ser 'Local 1'");
        assertEquals("Description 1", subEvent1.getDescription(), "A descrição do primeiro sub-evento deve ser 'Description 1'");
        assertEquals("Speaker 1", subEvent1.getSpeakers(), "O palestrante do primeiro sub-evento deve ser 'Speaker 1'");
        assertEquals("123456789", subEvent1.getAttendeesList()[0],"A lista de participantes do primeiro sub-evento deve ser " +
                "'123456789'");

        SubEvent subEvent2 = subEvents.get(1);
        assertEquals("3", subEvent2.getId(), "O ID do segundo sub-evento deve ser '3'");
        assertEquals("1001", subEvent2.getParentEventID(), "O ID do evento pai do segundo sub-evento deve ser '1001'");
        assertEquals("SubEvent 3", subEvent2.getName(), "O nome do segundo sub-evento deve ser 'SubEvent 3'");
        assertEquals("03/01/2024", subEvent2.getDate(), "A data do segundo sub-evento deve ser '03/01/2024'");
        assertEquals("12:00", subEvent2.getHour(), "A hora do segundo sub-evento deve ser '12:00'");
        assertEquals("Local 3", subEvent2.getLocal(), "O local do segundo sub-evento deve ser 'Local 3'");
        assertEquals("Description 3", subEvent2.getDescription(), "A descrição do segundo sub-evento deve ser 'Description 3'");
        assertEquals("Speaker 3", subEvent2.getSpeakers(), "O palestrante do segundo sub-evento deve ser 'Speaker 3'");
        assertEquals("987654321", subEvent2.getAttendeesList()[0],"A lista de participantes do segundo sub-evento deve ser " +
                "'987654321'");
    }



    @Test
    void deleteSubEvent() {
        boolean result = SubEventUtility.deleteSubEvent("2");
        assertTrue(result, "A exclusão do sub-evento deve ser bem-sucedida");

        SubEvent subEvent = SubEventUtility.getSubEventById("2");
        assertNull(subEvent, "O sub-evento com o ID '2' não deve existir após a exclusão");

        List<SubEvent> actualSubEvents = SubEventUtility.getAllSubEvents();
        assertEquals(2, actualSubEvents.size(), "O número de sub-eventos deve ser 2 após a exclusão do sub-evento");
    }



    @Test
    void updateSubEventName() {
        boolean result = SubEventUtility.updateSubEventName("1", "Updated Name");
        assertTrue(result, "A atualização do nome do sub-evento deve ser bem-sucedida");

        SubEvent subEvent = SubEventUtility.getSubEventById("1");
        assertEquals("Updated Name", subEvent.getName(), "O nome do sub-evento deve ser 'Updated Name'");
    }

    @Test
    void updateSubEventDate() {
        boolean result = SubEventUtility.updateSubEventDate("1", "06/01/2024");
        assertTrue(result, "A atualização da data do sub-evento deve ser bem-sucedida");

        SubEvent subEvent = SubEventUtility.getSubEventById("1");
        assertEquals("06/01/2024", subEvent.getDate(), "A data do sub-evento deve ser '06/01/2024'");
    }

    @Test
    void updateSubEventLocal() {
        boolean result = SubEventUtility.updateSubEventLocal("1", "Updated Local");
        assertTrue(result, "A atualização do local do sub-evento deve ser bem-sucedida");

        SubEvent subEvent = SubEventUtility.getSubEventById("1");
        assertEquals("Updated Local", subEvent.getLocal(), "O local do sub-evento deve ser 'Updated Local'");
    }

    @Test
    void updateSubEventDescription() {
        boolean result = SubEventUtility.updateSubEventDescription("1", "Updated Description");
        assertTrue(result, "A atualização da descrição do sub-evento deve ser bem-sucedida");

        SubEvent subEvent = SubEventUtility.getSubEventById("1");
        assertEquals("Updated Description", subEvent.getDescription(), "A descrição do sub-evento deve ser 'Updated Description'");
    }

    @Test
    void updateSubEventSpeaker() {
        boolean result = SubEventUtility.updateSubEventSpeaker("1", "Updated Speaker");
        assertTrue(result, "A atualização do palestrante do sub-evento deve ser bem-sucedida");

        SubEvent subEvent = SubEventUtility.getSubEventById("1");
        assertEquals("Updated Speaker", subEvent.getSpeakers(), "O palestrante do sub-evento deve ser 'Updated Speaker'");
    }
}
