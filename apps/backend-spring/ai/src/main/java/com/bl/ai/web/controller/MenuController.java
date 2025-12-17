package com.bl.ai.web.controller;

import com.bl.ai.dto.menu.MenuDto;
import com.bl.ai.service.menu.MenuService;

import jakarta.websocket.server.PathParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<MenuDto> create(@RequestBody MenuDto dto) {
        MenuDto res = menuService.create(dto);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuDto> update(@PathVariable Long id, @RequestBody MenuDto dto) {
        MenuDto res = menuService.update(id, dto);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDto> get(@PathVariable Long id) {
        MenuDto res = menuService.getById(id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<MenuDto>> tree() {
        return ResponseEntity.ok(menuService.listTree());
    }
  @GetMapping("/path-exists")
    public ResponseEntity<Boolean> pathExists(@PathParam("path")  String path,@PathParam("id")  Long id) {
        return ResponseEntity.ok(menuService.pathExists(path,id));
    }
    @GetMapping("/name-exists")
    public ResponseEntity<Boolean> nameExists(@PathParam("name")  String name,@PathParam("id")  Long id) {
        return ResponseEntity.ok(menuService.nameExists(name,id));
    }
}
