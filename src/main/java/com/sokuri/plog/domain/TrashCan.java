package com.sokuri.plog.domain;

import com.sokuri.plog.domain.eums.TrashType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "trash")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrashCan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trash_id")
    private Long id;

    @NotBlank
    private String gu;

    @Column
    private String roadName;

    @Column
    private String detail;

    @Column
    private String spot;

    @Column(columnDefinition = "GEOMETRY")
    private Point geolocation;

    @Enumerated(EnumType.STRING)
    @Column
    private TrashType type;
}
