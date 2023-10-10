package com.sokuri.plog.domain;

import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.domain.utils.BaseTimeEntity;
import com.sokuri.plog.domain.utils.StringListConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
@Table(name = "communities")
@Getter
@NoArgsConstructor
public class Community extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "community_id", columnDefinition = "BINARY(16) DEFAULT UUID()")
    private UUID id; // 식별자 id

    @NotBlank
    private String title;

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> images = new ArrayList<>();

    @NotBlank
    private String location;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User organizer;

    @Enumerated(EnumType.STRING)
    private RecruitStatus status;

    @Column(columnDefinition = "INT DEFAULT 100")
    private int maxParticipants;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int currentParticipants;

    @Builder
    public Community(String title, String location, String description) {
        this.title = title;
        this.location = location;
        this.description = description;
    }

}
