package com.sokuri.plog.repository;

import com.sokuri.plog.domain.relations.image.CommunityImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommunityImageRepository extends JpaRepository<CommunityImage, UUID> {
}
