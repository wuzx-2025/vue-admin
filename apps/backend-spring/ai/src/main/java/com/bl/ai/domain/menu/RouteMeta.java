package com.bl.ai.domain.menu;

import java.util.List;
import java.util.Map;

/**
 * Route metadata corresponding to front-end RouteMeta interface.
 */
public class RouteMeta {

    private String activeIcon;
    private String activePath;
    private Boolean affixTab;
    private Integer affixTabOrder;
    private List<String> authority;
    private String badge;
    private String badgeType;
    private String badgeVariants;
    private Boolean fullPathKey;
    private Boolean hideChildrenInMenu;
    private Boolean hideInBreadcrumb;
    private Boolean hideInMenu;
    private Boolean hideInTab;
    private String icon;
    private String iframeSrc;
    private Boolean ignoreAccess;
    private Boolean keepAlive;
    private String link;
    private Boolean loaded;
    private Integer maxNumOfOpenTab;
    private Boolean menuVisibleWithForbidden;
    private Boolean noBasicLayout;
    private Boolean openInNewWindow;
    private Integer order;
    private Map<String, Object> query;
    private String title;

    public RouteMeta() {}

    // getters and setters
    public String getActiveIcon() { return activeIcon; }
    public void setActiveIcon(String activeIcon) { this.activeIcon = activeIcon; }

    public String getActivePath() { return activePath; }
    public void setActivePath(String activePath) { this.activePath = activePath; }

    public Boolean getAffixTab() { return affixTab; }
    public void setAffixTab(Boolean affixTab) { this.affixTab = affixTab; }

    public Integer getAffixTabOrder() { return affixTabOrder; }
    public void setAffixTabOrder(Integer affixTabOrder) { this.affixTabOrder = affixTabOrder; }

    public List<String> getAuthority() { return authority; }
    public void setAuthority(List<String> authority) { this.authority = authority; }

    public String getBadge() { return badge; }
    public void setBadge(String badge) { this.badge = badge; }

    public String getBadgeType() { return badgeType; }
    public void setBadgeType(String badgeType) { this.badgeType = badgeType; }

    public String getBadgeVariants() { return badgeVariants; }
    public void setBadgeVariants(String badgeVariants) { this.badgeVariants = badgeVariants; }

    public Boolean getFullPathKey() { return fullPathKey; }
    public void setFullPathKey(Boolean fullPathKey) { this.fullPathKey = fullPathKey; }

    public Boolean getHideChildrenInMenu() { return hideChildrenInMenu; }
    public void setHideChildrenInMenu(Boolean hideChildrenInMenu) { this.hideChildrenInMenu = hideChildrenInMenu; }

    public Boolean getHideInBreadcrumb() { return hideInBreadcrumb; }
    public void setHideInBreadcrumb(Boolean hideInBreadcrumb) { this.hideInBreadcrumb = hideInBreadcrumb; }

    public Boolean getHideInMenu() { return hideInMenu; }
    public void setHideInMenu(Boolean hideInMenu) { this.hideInMenu = hideInMenu; }

    public Boolean getHideInTab() { return hideInTab; }
    public void setHideInTab(Boolean hideInTab) { this.hideInTab = hideInTab; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public String getIframeSrc() { return iframeSrc; }
    public void setIframeSrc(String iframeSrc) { this.iframeSrc = iframeSrc; }

    public Boolean getIgnoreAccess() { return ignoreAccess; }
    public void setIgnoreAccess(Boolean ignoreAccess) { this.ignoreAccess = ignoreAccess; }

    public Boolean getKeepAlive() { return keepAlive; }
    public void setKeepAlive(Boolean keepAlive) { this.keepAlive = keepAlive; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public Boolean getLoaded() { return loaded; }
    public void setLoaded(Boolean loaded) { this.loaded = loaded; }

    public Integer getMaxNumOfOpenTab() { return maxNumOfOpenTab; }
    public void setMaxNumOfOpenTab(Integer maxNumOfOpenTab) { this.maxNumOfOpenTab = maxNumOfOpenTab; }

    public Boolean getMenuVisibleWithForbidden() { return menuVisibleWithForbidden; }
    public void setMenuVisibleWithForbidden(Boolean menuVisibleWithForbidden) { this.menuVisibleWithForbidden = menuVisibleWithForbidden; }

    public Boolean getNoBasicLayout() { return noBasicLayout; }
    public void setNoBasicLayout(Boolean noBasicLayout) { this.noBasicLayout = noBasicLayout; }

    public Boolean getOpenInNewWindow() { return openInNewWindow; }
    public void setOpenInNewWindow(Boolean openInNewWindow) { this.openInNewWindow = openInNewWindow; }

    public Integer getOrder() { return order; }
    public void setOrder(Integer order) { this.order = order; }

    public Map<String, Object> getQuery() { return query; }
    public void setQuery(Map<String, Object> query) { this.query = query; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
