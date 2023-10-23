package com.sokuri.plog.repository;

import com.sokuri.plog.domain.Community;
import com.sokuri.plog.domain.eums.RecruitStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommunityRepository extends JpaRepository<Community, UUID> {
  List<Community> findCommunitiesByStatusIs(RecruitStatus recruiting);
  Optional<Community> findByTitle(String title);
}
