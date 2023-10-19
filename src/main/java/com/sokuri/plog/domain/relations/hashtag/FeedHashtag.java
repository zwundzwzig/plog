package com.sokuri.plog.domain.relations.hashtag;

import com.sokuri.plog.domain.Feed;
import com.sokuri.plog.domain.Hashtag;
import com.sokuri.plog.domain.converter.StringToUuidConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "feed_hashtags")
@Getter
@NoArgsConstructor
public class FeedHashtag {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
    @Convert(converter = StringToUuidConverter.class)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;
}
