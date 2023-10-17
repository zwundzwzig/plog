package com.sokuri.plog.domain.relations.hashtag;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "feed_hashtags")
@Getter
@NoArgsConstructor
public class FeedHashtag {
    @EmbeddedId
    private FeedHashtagId id;
}
