package com.sokuri.plog.domain.relations.user;

import com.sokuri.plog.domain.relations.user.composite.LikeId;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "likes")
@Getter
@NoArgsConstructor
public class Like {
    @EmbeddedId
    private LikeId id;
}
