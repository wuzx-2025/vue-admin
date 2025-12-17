package com.bl.ai.dto.menu;

import com.bl.ai.domain.menu.RouteMeta;

import java.util.ArrayList;
import java.util.List;

public class MenuDto {
    private Long id;
    private Long pid;
    private String name;
    private String path;
    private String redirect;
    private String component;
    private Integer orderNumber;
    private Integer status;
    private String type;
    private String authCode;
    private RouteMeta meta;
    private List<MenuDto> children = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPid() { return pid; }
    public void setPid(Long pid) { this.pid = pid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public String getRedirect() { return redirect; }
    public void setRedirect(String redirect) { this.redirect = redirect; }

    public String getComponent() { return component; }
    public void setComponent(String component) { this.component = component; }

    public Integer getOrderNumber() { return orderNumber; }
    public void setOrderNumber(Integer orderNumber) { this.orderNumber = orderNumber; }

    public RouteMeta getMeta() { return meta; }
    public void setMeta(RouteMeta meta) { this.meta = meta; }

    public List<MenuDto> getChildren() { return children; }
    public void setChildren(List<MenuDto> children) { this.children = children; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getAuthCode() { return authCode; }
    public void setAuthCode(String authCode) { this.authCode = authCode; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

}
