package com.sokuri.plog.domain.relations.user.composite;

import com.sokuri.plog.domain.Event;
import com.sokuri.plog.domain.User;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
public class UserEventId implements Serializable {
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "event_id")
  private Event event;
}
