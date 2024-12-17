package org.upe.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.interfaces.UserInterface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
public class Certificate implements CertificateInterface {
    @Id @GeneratedValue
    private UUID id;

    // Relação User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    // Relação SubEvent
    @ManyToOne
    @JoinColumn(name = "subevent_id")
    private SubEvent subEvent;

    public Certificate() {}

    public Certificate(User user, SubEvent subEvent) {
        this.user = user;
        this.subEvent = subEvent;
    }


    public static class Builder {
        private UserInterface user;
        private SubEventInterface subEvent;

        public Builder withUser(UserInterface user) {
            this.user = user;
            return this;
        }

        public Builder withSubEvent(SubEventInterface subEvent) {
            this.subEvent = subEvent;
            return this;
        }

        public Certificate build() {
            return new Certificate((User)user, (SubEvent)subEvent);
        }
    }

    public static Session.Builder Builder() {
        return new Session.Builder();
    }
}
