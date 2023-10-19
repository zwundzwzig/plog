package com.sokuri.plog.domain;

import com.sokuri.plog.domain.converter.StringToUuidConverter;
import com.sokuri.plog.domain.relations.image.CommunityImage;
import com.sokuri.plog.domain.relations.image.EventImage;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "images")
@Getter
public class Image {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "image_id", columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
    @Convert(converter = StringToUuidConverter.class)
    private UUID id;

    @Nullable
    private String url;

    @OneToOne(mappedBy = "image", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CommunityImage community;
    @OneToOne(mappedBy = "image", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private EventImage event;
}
