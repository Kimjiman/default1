package com.example.basicarch.module.menu.converter;

import com.example.basicarch.base.converter.TypeConverter;
import com.example.basicarch.module.menu.entity.Menu;
import com.example.basicarch.module.menu.model.MenuModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring"
        , uses = {TypeConverter.class}
)
public interface MenuConverter {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "createId", target = "createId")
    @Mapping(source = "createTime", target = "createTime", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "updateId", target = "updateId")
    @Mapping(source = "updateTime", target = "updateTime", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "useYn", target = "useYn", qualifiedByName = "stringToYn")
    MenuModel toModel(Menu menu);

    @Mapping(target = "rowNum", ignore = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "createId", target = "createId")
    @Mapping(source = "createTime", target = "createTime", qualifiedByName = "stringToLocalDateTime")
    @Mapping(source = "updateId", target = "updateId")
    @Mapping(source = "updateTime", target = "updateTime", qualifiedByName = "stringToLocalDateTime")
    @Mapping(source = "useYn", target = "useYn", qualifiedByName = "ynToString")
    Menu toEntity(MenuModel menuModel);

    List<MenuModel> toModelList(List<Menu> menuList);

    List<Menu> toEntityList(List<MenuModel> menuModelList);
}
