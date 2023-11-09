package com.sokuri.plog.domain;

import com.sokuri.plog.domain.converter.StringToUuidConverter;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "images")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "image_id", columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
    @Convert(converter = StringToUuidConverter.class)
    private UUID id;

    @Nullable
    private String url;
}
