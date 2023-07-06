package com.ploging.plog.domain;

import com.ploging.plog.domain.eums.RecruitStatus;
import com.ploging.plog.repository.CommunityRepository;
import com.ploging.plog.utils.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "communities")
@Getter
@NoArgsConstructor
public class Community extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    @GenericGenerator(name = "sequence", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "community_id", columnDefinition = "BINARY(16)")
    private UUID id; // 식별자 id

    @NotBlank
    private String title;

    @Column
    private String image;

    @NotBlank
    private String location;

    @Column
    private String description;

    @Column
    private String owner;

    @Column
    private RecruitStatus status;

    @Builder
    public Community(String title, String location, String description) {
        this.title = title;
        this.location = location;
        this.description = description;
    }

}
