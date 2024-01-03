package com.sokuri.plog.domain.repository.event;

import com.sokuri.plog.domain.entity.Event;
import com.sokuri.plog.domain.relations.image.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventImageRepository extends JpaRepository<EventImage, UUID> {
  void deleteAllByEvent(Event event);
}
