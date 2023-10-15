package com.sokuri.plog.domain;

import com.sokuri.plog.domain.dto.FeedsResponse;
import com.sokuri.plog.domain.dto.RecruitingCommunitiesResponse;
import com.sokuri.plog.domain.utils.BaseTimeEntity;
import com.sokuri.plog.domain.utils.StringListConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "feeds")
@Getter
@NoArgsConstructor
public class Feed extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "feed_id", columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
    private UUID id;

    //    @ManyToMany
//    @JoinTable(
//            name = "feed_images",
//            joinColumns = @JoinColumn(name = "feed_id"),
//            inverseJoinColumns = @JoinColumn(name = "image_id")
//    )
//    private List<Image> images;

//    @ElementCollection
//    @CollectionTable(name = "images", joinColumns = @JoinColumn(name = "image_id"))
//    @Column(name = "image_url")
//    private List<String> images = new ArrayList<>();

//    @Convert(converter = StringListConverter.class)
//    private List<String> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "feed", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Setter
    private Set<FeedHashtag> hashtags = new HashSet<>();

    public FeedsResponse toResponse() {
        return FeedsResponse.builder()
                .userId(user.getNickname())
                .createdAt(LocalDate.from(getCreateDate()))
//                .thumbnail(images.toString())
                .build();
    }

}
