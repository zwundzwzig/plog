package com.sokuri.plog.domain.repository.community;

import com.sokuri.plog.domain.entity.Community;
import com.sokuri.plog.domain.relations.image.CommunityImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommunityImageRepository extends JpaRepository<CommunityImage, UUID> {
  void deleteAllByCommunity(Community community);
}
