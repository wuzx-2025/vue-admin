package com.bl.ai.repository.menu;

import com.bl.ai.domain.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByOrderByOrderNumberAsc();
    boolean existsByParent_Id(Long parentId);

    boolean existsByPathAndIdNot(String path, Long id);

    boolean existsByNameAndIdNot(String name, Long id);
}
