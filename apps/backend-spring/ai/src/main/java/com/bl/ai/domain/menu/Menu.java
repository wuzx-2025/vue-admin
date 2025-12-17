package com.bl.ai.domain.menu;

import com.bl.ai.domain.common.AbstractEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu")
public class Menu extends AbstractEntity {

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "path", length = 500)
    private String path;

    @Column(name = "redirect", length = 500)
    private String redirect;

    @Column(name = "component", length = 1000)
    private String component;

    // 'catalog',
    // 'menu',
    // 'embedded',
    // 'link',
    // 'button',
    @Column(name = "type", length = 50)
    private String type;


     @Column(name = "auth_code", length = 500)
    private String authCode;
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Menu parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("order_number ASC")
    private List<Menu> children = new ArrayList<>();

    @Column(name = "order_number")
    private Integer orderNumber = 0;

    @Column(name = "status")
    private Integer status = 0;

    @Convert(converter = RouteMetaConverter.class)
    @Column(name = "meta", columnDefinition = "TEXT")
    private RouteMeta meta;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public String getRedirect() { return redirect; }
    public void setRedirect(String redirect) { this.redirect = redirect; }

    public String getComponent() { return component; }
    public void setComponent(String component) { this.component = component; }

    public Menu getParent() { return parent; }
    public void setParent(Menu parent) { this.parent = parent; }

    public List<Menu> getChildren() { return children; }
    public void setChildren(List<Menu> children) { this.children = children; }

    public Integer getOrderNumber() { return orderNumber; }
    public void setOrderNumber(Integer orderNumber) { this.orderNumber = orderNumber; }

    public RouteMeta getMeta() { return meta; }
    public void setMeta(RouteMeta meta) { this.meta = meta; }
    public String getAuthCode() { return authCode; }
    public void setAuthCode(String authCode) { this.authCode = authCode; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
