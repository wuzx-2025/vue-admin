import type { RouteRecordRaw } from 'vue-router';
const routes: RouteRecordRaw[] = [
  {
    meta: {
      icon: 'ion:settings-outline',
      order: 9997,
      title: '系统管理',
    },
    name: 'System',
    path: '/system',
    children: [
      {
        path: '/system/menu',
        name: 'SystemMenu',
        meta: {
          icon: 'mdi:menu',
          title: '菜单管理',
        },
        component: () => import('#/views/system/menu/list.vue'),
      }
    ],
  },
];

export default routes;
