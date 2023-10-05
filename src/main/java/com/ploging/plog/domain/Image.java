package com.ploging.plog.domain;

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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "image_id", columnDefinition = "BINARY(16) DEFAULT UUID()")
    private UUID id; // 식별자 id

    @Nullable
    private String url;

}
