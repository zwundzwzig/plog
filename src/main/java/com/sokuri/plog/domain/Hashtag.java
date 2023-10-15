package com.sokuri.plog.domain;

import com.sokuri.plog.domain.utils.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "hashtags")
@Getter
@NoArgsConstructor
public class Hashtag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "hashtag_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String name;

    @OneToMany(mappedBy = "hashtag")
    @ToString.Exclude
    private Set<FeedHashtag> feeds = new HashSet<>();

}
