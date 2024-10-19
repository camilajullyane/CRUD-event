package org.upe.persistence.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.upe.persistence.model.Event;
import org.upe.persistence.interfaces.EventInterface;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventUtilityTest {
    private EventUtility eventUtility;

    @BeforeEach
    void setUp() throws IOException {
        eventUtility = new EventUtility();
        eventUtility.setCsvFilePath("DB/test_event.csv");

        // Populate the CSV file with test data
        try (FileWriter writer = new FileWriter("DB/test_event.csv")) {
            writer.write("id,ownerCPF,name,date,local,organization,description,attendeesList,articleList\n");
            writer.write("1,123456789,Event One,2023-10-01,Location One,Org One,Description One,,\n");
            writer.write("2,987654321,Event Two,2023-11-01,Location Two,Org Two,Description Two,,\n");
        }
    }

    @Test
    void testGetAllEvents() {
        List<Event> events = eventUtility.getAllEvents();
        assertNotNull(events, "The event list should not be null");
        assertFalse(events.isEmpty(), "The event list should not be empty");
    }

    @Test
    void testGetAllEventsByUser() {
        List<Event> events = eventUtility.getAllEventsByUser("123456789");
        assertNotNull(events, "The event list should not be null");
        assertEquals(1, events.size(), "The user should have one event");
    }

    @Test
    void testGetEventsIn() {
        eventUtility.addAttendeeOnList("123456789", "1");
        List<Event> events = eventUtility.getEventsIn("123456789");
        assertNotNull(events, "The event list should not be null");
        assertEquals(1, events.size(), "The user should be an attendee of one event");
    }

    @Test
    void testCreateEvent() {
        EventInterface newEvent = eventUtility.createEvent("123456789", "New Event", "2023-12-01", "New Location", "New Org", "New Description");
        assertNotNull(newEvent, "The new event should not be null");
        assertEquals("New Event", newEvent.getName(), "The event name should match");
    }

    @Test
    void testGetEventById() {
        Event event = eventUtility.getEventById("1");
        assertNotNull(event, "The event should not be null");
        assertEquals("Event One", event.getName(), "The event name should match");
    }

    @Test
    void testUpdateEventLocal() {
        boolean result = eventUtility.updateEventLocal("1", "Updated Location");
        assertTrue(result, "The event location should be updated successfully");
        Event event = eventUtility.getEventById("1");
        assertEquals("Updated Location", event.getLocal(), "The event location should match the new location");
    }

    @Test
    void testUpdateEventName() {
        boolean result = eventUtility.updateEventName("1", "Updated Name");
        assertTrue(result, "The event name should be updated successfully");
        Event event = eventUtility.getEventById("1");
        assertEquals("Updated Name", event.getName(), "The event name should match the new name");
    }

    @Test
    void testUpdateEventDescription() {
        boolean result = eventUtility.updateEventDescription("1", "Updated Description");
        assertTrue(result, "The event description should be updated successfully");
        Event event = eventUtility.getEventById("1");
        assertEquals("Updated Description", event.getDescription(), "The event description should match the new description");
    }

    @Test
    void testUpdateEventOrganization() {
        boolean result = eventUtility.updateEventOrganization("1", "Updated Organization");
        assertTrue(result, "The event organization should be updated successfully");
        Event event = eventUtility.getEventById("1");
        assertEquals("Updated Organization", event.getOrganization(), "The event organization should match the new organization");
    }

    @Test
    void testUpdateEventDate() {
        boolean result = eventUtility.updateEventDate("1", "2023-12-31");
        assertTrue(result, "The event date should be updated successfully");
        Event event = eventUtility.getEventById("1");
        assertEquals("2023-12-31", event.getDate(), "The event date should match the new date");
    }

    @Test
    void testDeleteEvent() {
        boolean result = eventUtility.deleteEvent("1");
        assertTrue(result, "The event should be deleted successfully");
        Event event = eventUtility.getEventById("1");
        assertNull(event, "The event should be null after deletion");
    }

    @Test
    void testAddAttendeeOnList() {
        eventUtility.addAttendeeOnList("123456789", "2");
        Event event = eventUtility.getEventById("2");
        assertTrue(Arrays.asList(event.getAttendeesList()).contains("123456789"), "The user should be an attendee of the event");
    }

    @Test
    void testDeleteAttendeeOnList() {
        eventUtility.addAttendeeOnList("123456789", "2");
        eventUtility.deleteAttendeeOnList("123456789", "2");
        Event event = eventUtility.getEventById("2");
        assertFalse(Arrays.asList(event.getAttendeesList()).contains("123456789"), "The user should not be an attendee of the event");
    }

    @Test
    void testAddArticleOnList() {
        boolean result = eventUtility.addArticleOnList("article123", "2");
        assertTrue(result, "The article should be added successfully");
        Event event = eventUtility.getEventById("2");
        assertTrue(Arrays.asList(event.getArticleList()).contains("article123"), "The article should be in the event's article list");
    }

    @Test
    void testUpdateNonExistentEvent() {
        boolean result = eventUtility.updateEventLocal("999", "Non-Existent Location");
        assertFalse(result, "The update should fail for a non-existent event");
    }

    @Test
    void testDeleteNonExistentEvent() {
        boolean result = eventUtility.deleteEvent("999");
        assertFalse(result, "The delete should fail for a non-existent event");
    }

    @Test
    void testAddAttendeeToNonExistentEvent() {
        eventUtility.addAttendeeOnList("123456789", "999");
        Event event = eventUtility.getEventById("999");
        assertNull(event, "The event should not exist");
    }

    @Test
    void testDeleteAttendeeFromNonExistentEvent() {
        eventUtility.deleteAttendeeOnList("123456789", "999");
        Event event = eventUtility.getEventById("999");
        assertNull(event, "The event should not exist");
    }

    @Test
    void testAddArticleToNonExistentEvent() {
        boolean result = eventUtility.addArticleOnList("article123", "999");
        assertFalse(result, "The add article should fail for a non-existent event");
    }
}