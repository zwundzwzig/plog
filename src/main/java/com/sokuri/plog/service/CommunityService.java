package com.sokuri.plog.service;

import com.sokuri.plog.domain.Community;
import com.sokuri.plog.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommunityService {

    private final CommunityRepository communityRepository;

    public void autoSaveDummyData(List<Community> communityList) {
        communityRepository.saveAll(communityList);
    }


}
