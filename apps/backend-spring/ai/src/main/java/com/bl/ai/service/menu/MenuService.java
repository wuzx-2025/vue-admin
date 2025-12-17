package com.bl.ai.service.menu;

import com.bl.ai.dto.menu.MenuDto;

import java.util.List;

public interface MenuService {
    MenuDto create(MenuDto dto);
    MenuDto update(Long id, MenuDto dto);
    void delete(Long id);
    MenuDto getById(Long id);
    List<MenuDto> listTree();
    boolean pathExists(String path, Long id);
    boolean nameExists(String name, Long id);
}
