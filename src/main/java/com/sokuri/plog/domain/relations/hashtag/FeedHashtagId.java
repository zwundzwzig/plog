package com.sokuri.plog.domain.relations.hashtag;

import com.sokuri.plog.domain.Feed;
import com.sokuri.plog.domain.Hashtag;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
public class FeedHashtagId implements Serializable {
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "feed_id")
  private Feed feed;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "hashtag_id")
  private Hashtag hashtag;
}
