package com.sokuri.plog.domain.relations.hashtag;

import com.sokuri.plog.domain.Feed;
import com.sokuri.plog.domain.Hashtag;
import com.sokuri.plog.domain.converter.StringToUuidConverter;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "feed_hashtags")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedHashtag {
    @Id
    @GeneratedValue(generator = "feed")
    @GenericGenerator(name = "feed", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
    @Convert(converter = StringToUuidConverter.class)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "feed_id")
    @Setter
    private Feed feed;

    @ManyToOne
    @Setter
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;
}
