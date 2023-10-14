package com.sokuri.plog.domain;

import com.sokuri.plog.domain.utils.StringToUuidConverter;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.UUID;

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

    @Nullable
    private String foreignId;

}
