package com.bl.ai.service.menu.impl;

import com.bl.ai.domain.menu.Menu;
import com.bl.ai.dto.menu.MenuDto;
import com.bl.ai.exception.BusinessException;
import com.bl.ai.repository.menu.MenuRepository;
import com.bl.ai.service.menu.MenuService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public MenuDto create(MenuDto dto) {
        Menu menu = new Menu();
        mapDtoToEntity(dto, menu);
        // handle parent
        if (dto.getPid() != null) {
            Menu parent = menuRepository.findById(dto.getPid())
                    .orElseThrow(() -> new BusinessException(400, "Parent menu not found"));
            // prevent cycle
            if (isDescendant(parent, menu)) {
                throw new BusinessException(400, "Invalid parent selection: would create cycle");
            }
            menu.setParent(parent);
        }
        // tenantId will be filled by listener from TenantContext
        Menu saved = menuRepository.save(menu);
        return mapEntityToDto(saved);
    }

    @Override
    public MenuDto update(Long id, MenuDto dto) {
        Menu exist = menuRepository.findById(id).orElseThrow(() -> new BusinessException(404, "Menu not found"));
        mapDtoToEntity(dto, exist);
        if (dto.getPid() != null) {
            if (dto.getPid().equals(id)) {
                throw new BusinessException(400, "Menu cannot be its own parent");
            }
            Menu parent = menuRepository.findById(dto.getPid())
                    .orElseThrow(() -> new BusinessException(400, "Parent menu not found"));
            if (isDescendant(parent, exist)) {
                throw new BusinessException(400, "Invalid parent selection: would create cycle");
            }
            exist.setParent(parent);
        } else {
            exist.setParent(null);
        }
        Menu saved = menuRepository.save(exist);
        return mapEntityToDto(saved);
    }

    @Override
    public void delete(Long id) {
        Menu exist = menuRepository.findById(id).orElseThrow(() -> new BusinessException(404, "Menu not found"));
        // Prevent deletion if this menu has children
        if (menuRepository.existsByParent_Id(id)) {
            throw new BusinessException(400, "该菜单下有子菜单，不允许删除");
        }
        menuRepository.delete(exist);
    }

    @Override
    public MenuDto getById(Long id) {
        Menu m = menuRepository.findById(id).orElseThrow(() -> new BusinessException(404, "Menu not found"));
        return mapEntityToDto(m);
    }
    @Override
    public boolean pathExists(String path, Long id) {
        return menuRepository.existsByPathAndIdNot(path, id);
    }

    @Override
    public boolean nameExists(String name, Long id) {
        return menuRepository.existsByNameAndIdNot(name, id);
    }

    @Override
    public List<MenuDto> listTree() {
        List<Menu> all = menuRepository.findAllByOrderByOrderNumberAsc();
        // Build tree
        Map<Long, MenuDto> map = new LinkedHashMap<>();
        List<MenuDto> roots = new ArrayList<>();
        for (Menu m : all) {
            MenuDto dto = mapEntityToDto(m);
            map.put(m.getId(), dto);
        }
        for (Menu m : all) {
            MenuDto dto = map.get(m.getId());
            if (m.getParent() != null && m.getParent().getId() != null) {
                MenuDto parentDto = map.get(m.getParent().getId());
                if (parentDto != null) parentDto.getChildren().add(dto);
                else roots.add(dto); // fallback
            } else {
                roots.add(dto);
            }
        }
        return roots;
    }

    private void mapDtoToEntity(MenuDto dto, Menu e) {
        if (dto.getName() != null) e.setName(dto.getName());
        if (dto.getPath() != null) e.setPath(dto.getPath());
        if (dto.getRedirect() != null) e.setRedirect(dto.getRedirect());
        if (dto.getComponent() != null) e.setComponent(dto.getComponent());
        if (dto.getOrderNumber() != null) e.setOrderNumber(dto.getOrderNumber());
        if (dto.getMeta() != null) e.setMeta(dto.getMeta());
        if (dto.getAuthCode() != null) e.setAuthCode(dto.getAuthCode());
        if (dto.getType() != null) e.setType(dto.getType());
        if (dto.getStatus() != null) e.setStatus(dto.getStatus());
    }

    private MenuDto mapEntityToDto(Menu e) {
        MenuDto dto = new MenuDto();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setPath(e.getPath());
        dto.setRedirect(e.getRedirect());
        dto.setComponent(e.getComponent());
        dto.setOrderNumber(e.getOrderNumber());
        dto.setAuthCode(e.getAuthCode());
        dto.setType(e.getType());
        dto.setMeta(e.getMeta());
        dto.setStatus(e.getStatus());
        if (e.getParent() != null) dto.setPid(e.getParent().getId());
        // children mapping is performed in listTree
        return dto;
    }

    /**
     * Check if candidateParent is a descendant of candidateChild to avoid cycles.
     */
    private boolean isDescendant(Menu candidateParent, Menu candidateChild) {
        // traverse up from candidateParent and see if we meet candidateChild
        Menu cur = candidateParent;
        while (cur != null) {
            if (candidateChild.getId() != null && cur.getId() != null && cur.getId().equals(candidateChild.getId())) {
                return true;
            }
            cur = cur.getParent();
        }
        return false;
    }
}
