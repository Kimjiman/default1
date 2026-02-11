package com.example.default1.module.user.converter;

import com.example.default1.base.converter.TypeConverter;
import com.example.default1.module.user.model.UserModel;
import com.example.default1.module.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * packageName    : com.example.default1.module.code.converter
 * fileName       : CodeMapper
 * author         : KIM JIMAN
 * date           : 25. 8. 11. 월요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 8. 11.     KIM JIMAN      First Commit
 */
@Mapper(
        componentModel = "spring"
        , uses = {TypeConverter.class}
)
public interface UserConverter {
    @Mapping(source = "createTime", target = "createTime", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "updateTime", target = "updateTime", qualifiedByName = "localDateTimeToString")
    UserModel toModel(User user);

    @Mapping(target = "rowNum", ignore = true)
    @Mapping(source = "createTime", target = "createTime", qualifiedByName = "stringToLocalDateTime")
    @Mapping(source = "updateTime", target = "updateTime", qualifiedByName = "stringToLocalDateTime")
    User toEntity(UserModel userModel);

    List<UserModel> toModelList(List<User> userList);

    List<User> toEntityList(List<UserModel> userModelList);
}
