package com.speakapp.userservice.mappers;

import com.speakapp.userservice.dtos.FriendRequestGetDTO;
import com.speakapp.userservice.entities.UserFriend;
import org.mapstruct.Mapper;

@Mapper(uses = AppUserMapper.class)
public interface FriendsMapper {

    FriendRequestGetDTO toGetDTO(UserFriend userFriend);

}
