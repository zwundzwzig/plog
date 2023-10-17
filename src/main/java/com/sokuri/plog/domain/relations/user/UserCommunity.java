package com.sokuri.plog.domain.relations.user;

import com.sokuri.plog.domain.Community;
import com.sokuri.plog.domain.User;
import com.sokuri.plog.domain.relations.user.composite.UserCommunityId;
import com.sokuri.plog.domain.relations.user.composite.UserEventId;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_communities")
@Getter
@NoArgsConstructor
public class UserCommunity {
    @EmbeddedId
    private UserCommunityId id;
}