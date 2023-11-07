package com.sokuri.plog.repository;

import com.sokuri.plog.domain.TrashCan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrashRepository extends JpaRepository<TrashCan, Long> {
}
