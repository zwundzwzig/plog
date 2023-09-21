package com.ploging.plog.domain;

import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "images")
@Getter
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    @GenericGenerator(name = "sequence", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "image_id", columnDefinition = "CHAR(36)")
    private UUID id; // 식별자 id

    @Nullable
    private String url;

}
