package com.ploging.plog.service;

import com.ploging.plog.domain.Community;
import com.ploging.plog.repository.CommunityRepository;
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
