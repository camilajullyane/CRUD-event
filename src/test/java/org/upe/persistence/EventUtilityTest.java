package org.upe.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventUtilityTest {

    private static final String TEST_CSV_FILE_PATH = "DB/test_event.csv";

    @BeforeEach
    void setUp() throws IOException {
        // Criação de um arquivo CSV temporário para fins de teste
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_CSV_FILE_PATH))) {
            writer.write("id,ownerCPF,name,date,local,organization,description,attendeesList,articleList\n");
            writer.write("1,123456789,Sample Event 1,01/01/2024,Local 1,Org 1,Description 1,987654321,\n");
            writer.write("2,987654321,Sample Event 2,02/01/2024,Local 2,Org 2,Description 2,,\n");
        }
        // Atualizar o caminho do CSV para os testes
        EventUtility.CSV_FILE_PATH = TEST_CSV_FILE_PATH;
    }

    @Test
    void createEvent() {
        String ownerCPF = "123456789";
        String name = "New Event";
        String date = "03/01/2024";
        String local = "New Local";
        String organization = "New Organization";
        String description = "New Description";

        EventInterface newEvent = EventUtility.createEvent(ownerCPF, name, date, local, organization, description);

        assertNotNull(newEvent, "O evento deve ser criado");
        assertEquals(ownerCPF, newEvent.getOwnerCPF(), "O CPF do proprietário deve ser '123456789'");
        assertEquals(name, newEvent.getName(), "O nome do evento deve ser 'New Event'");
        assertEquals(date, newEvent.getDate(), "A data do evento deve ser '03/01/2024'");
        assertEquals(local, newEvent.getLocal(), "O local do evento deve ser 'New Local'");
        assertEquals(organization, newEvent.getOrganization(), "A organização do evento deve ser 'New Organization'");
        assertEquals(description, newEvent.getDescription(), "A descrição do evento deve ser 'New Description'");

        // Verificar se o evento foi salvo corretamente no arquivo CSV
        ArrayList<Event> events = EventUtility.getAllEvents();
        assertTrue(events.stream().anyMatch(event -> event.getId().equals(newEvent.getId())), "O novo evento deve estar presente na lista de eventos");
    }



    @Test
    void getAllEvents() {
        ArrayList<Event> events = EventUtility.getAllEvents();
        assertEquals(2, events.size(), "O número de eventos deve ser 2");

        EventInterface event1 = events.get(0);
        assertEquals("1", event1.getId(), "O ID do primeiro evento deve ser '1'");
        assertEquals("Sample Event 1", event1.getName(), "O nome do primeiro evento deve ser 'Sample Event 1'");

        EventInterface event2 = events.get(1);
        assertEquals("2", event2.getId(), "O ID do segundo evento deve ser '2'");
        assertEquals("Sample Event 2", event2.getName(), "O nome do segundo evento deve ser 'Sample Event 2'");
    }

    @Test
    void getAllEventsByUser() {
        ArrayList<Event> userEvents = EventUtility.getAllEventsByUser("123456789");
        assertEquals(1, userEvents.size(), "O número de eventos do usuário deve ser 1");
        assertEquals("Sample Event 1", userEvents.get(0).getName(), "O nome do evento do usuário deve ser 'Sample Event 1'");
    }

    @Test
    void getEventsIn() {
        ArrayList<Event> eventsIn = EventUtility.getEventsIn("987654321");
        assertEquals(1, eventsIn.size(), "O número de eventos que o usuário está participando deve ser 1");
        assertEquals("Sample Event 1", eventsIn.get(0).getName(), "O nome do evento em que o usuário está participando deve ser 'Sample Event 1'");
    }


    @Test
    void getEventById() {
        EventInterface event = EventUtility.getEventById("1");
        assertNotNull(event, "O evento deve ser encontrado");
        assertEquals("Sample Event 1", event.getName(), "O nome do evento deve ser 'Sample Event 1'");

        EventInterface nonExistentEvent = EventUtility.getEventById("999");
        assertNull(nonExistentEvent, "O evento não existente deve retornar nulo");
    }

    @Test
    void updateEvent() {
        Event updatedEvent = new Event("1", "123456789", "Updated Event", "01/01/2024", "Updated Local", "Updated Org", "Updated Description", "987654321","article1");
        assertTrue(EventUtility.updateEvent(updatedEvent), "O evento deve ser atualizado com sucesso");

        EventInterface event = EventUtility.getEventById("1");
        assertNotNull(event, "O evento deve ser encontrado após atualização");
        assertEquals("Updated Event", event.getName(), "O nome do evento deve ser atualizado para 'Updated Event'");
        assertEquals("Updated Local", event.getLocal(), "O local do evento deve ser atualizado para 'Updated Local'");
    }

    @Test
    void updateEventLocal() {
        assertTrue(EventUtility.updateEventLocal("1", "New Local"), "O local do evento deve ser atualizado com sucesso");

        Event event = EventUtility.getEventById("1");
        assertNotNull(event, "O evento deve ser encontrado após a atualização");
        assertEquals("New Local", event.getLocal(), "O local do evento deve ser 'New Local'");
    }

    @Test
    void updateEventName() {
        EventUtility.updateEventName("1", "New Event Name");
        Event event = EventUtility.getEventById("1");
        assertNotNull(event, "O evento deve ser encontrado após a atualização");
        assertEquals("New Event Name", event.getName(), "O nome do evento deve ser 'New Event Name'");
    }

    @Test
    void updateEventDescription() {
        assertTrue(EventUtility.updateEventDescription("1", "New Description"), "A descrição do evento deve ser atualizada com sucesso");

        EventInterface event = EventUtility.getEventById("1");
        assertNotNull(event, "O evento deve ser encontrado após a atualização");
        assertEquals("New Description", event.getDescription(), "A descrição do evento deve ser 'New Description'");
    }

    @Test
    void updateEventOrganization() {
        assertTrue(EventUtility.updateEventOrganization("1", "New Organization"), "A organização do evento deve ser atualizada com sucesso");

        EventInterface event = EventUtility.getEventById("1");
        assertNotNull(event, "O evento deve ser encontrado após a atualização");
        assertEquals("New Organization", event.getOrganization(), "A organização do evento deve ser 'New Organization'");
    }

    @Test
    void deleteEvent() {
        assertTrue(EventUtility.deleteEvent("2"), "O evento deve ser deletado com sucesso");

        EventInterface event = EventUtility.getEventById("2");
        assertNull(event, "O evento deletado deve ser nulo");
    }

    @Test
    void addAttendeeOnList() {
        EventUtility.addAttendeeOnList("987654321", "1");
        EventInterface event = EventUtility.getEventById("1");

        assertNotNull(event, "O evento deve ser encontrado após adicionar o participante");
        assertTrue(Arrays.asList(event.getAttendeesList()).contains("987654321"), "O participante deve ser adicionado à lista de participantes");
    }

}
