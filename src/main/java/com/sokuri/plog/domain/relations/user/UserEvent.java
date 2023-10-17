package com.sokuri.plog.domain.relations.user;

import com.sokuri.plog.domain.Event;
import com.sokuri.plog.domain.User;
import com.sokuri.plog.domain.relations.user.composite.LikeId;
import com.sokuri.plog.domain.relations.user.composite.UserEventId;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_events")
@Getter
@NoArgsConstructor
public class UserEvent {
    @EmbeddedId
    private UserEventId id;
}