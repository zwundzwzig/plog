package com.ploging.plog.domain;

import com.ploging.plog.domain.utils.BaseTimeEntity;
import com.ploging.plog.domain.utils.StringListConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "feeds")
@Getter
@NoArgsConstructor
public class Feed extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "feed_id", columnDefinition = "BINARY(16) DEFAULT UUID()")
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

    @Convert(converter = StringListConverter.class)
    private List<String> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "feed")
    @ToString.Exclude
    @Setter
    private Set<FeedHashtag> hashtags = new HashSet<>();

}
