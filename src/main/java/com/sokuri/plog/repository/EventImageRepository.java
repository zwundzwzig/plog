package com.sokuri.plog.repository;

import com.sokuri.plog.domain.Event;
import com.sokuri.plog.domain.relations.image.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventImageRepository extends JpaRepository<EventImage, UUID> {
  void deleteAllByEvent(Event event);
}
