package com.ploging.plog.utils;

import com.ploging.plog.domain.Community;
import com.ploging.plog.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Slf4j
@RequiredArgsConstructor
class BaseTimeEntityTest {

    @Autowired
    CommunityRepository communityRepository;

    @Test
    @Transactional
    void BaseTimeEntity_등록() {
        // given
        LocalDateTime now = LocalDateTime.of(2023, 7, 6, 0, 0, 0);
        communityRepository.save(Community.builder()
                .title("title")
                .location("location")
                .description("description")
                .build());

        // when
        List<Community> communityList = communityRepository.findAll();

        // then
        Community community = communityList.get(0);

        System.out.println(">>>>>> createdDate = " + community.getCreateDate());
        System.out.println(">>>>>> modifiedDate = " + community.getModifiedDate());

        Assertions.assertThat(community.getCreateDate()).isAfter(now);
        Assertions.assertThat(community.getModifiedDate()).isAfter(now);
    }

}