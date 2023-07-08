package com.ploging.plog.domain;

import com.ploging.plog.domain.eums.TrashType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "trash")
@Getter
@NoArgsConstructor
public class TrashCan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private UUID id; // 식별자 id

    @NotBlank
    private String gu;

    @NotBlank
    private String roadName;

    @NotBlank
    private String detailAddress;

    @Column
    private String detailSpot;

    @Enumerated(EnumType.STRING)
    @Column
    private TrashType type;

}
