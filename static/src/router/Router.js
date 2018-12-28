/**
 * 路由管理
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from '../components/index/Index'
import Task from '../components/index/Task'
import List1 from '../components/index/List1'
import UserList from '../components/platform/access/user/User'
import MenuList from '../components/platform/access/menu/Menu.vue'
import RoleList from '../components/platform/access/role/Role.vue'
import OptlogList from '../components/platform/system/optlog/Optlog.vue'
import ExceptionLogList from '../components/platform/system/exceptionlog/ExceptionLog.vue'


Vue.use(VueRouter);

let router = new VueRouter({
    // mode: 'history',
    routes: [{
        path: '/',
        redirect: '/index/task'
    }, {
        path: '/index',
        name: 'Index',
        component: Index,
        meta: {
            title: '111'
        },
        children: [{
            path: '/index/task',
            name: 'Task',
            component: Task
        }, {
            path: '/platform/access/user/userList',
            name: 'UserList',
            component: UserList
        }, {
            path: '/platform/access/role/roleList',
            name: 'RoleList',
            component: RoleList
        }, {
            path: '/platform/access/menu/menuList',
            name: 'MenuList',
            component: MenuList
        }, {
            path: '/platform/system/optlog/optlogList',
            name: 'OptlogList',
            component: OptlogList
        }, {
            path: '/platform/system/exception/exceptionLogList',
            name: 'ExceptionLogList',
            component: ExceptionLogList
        }, {
            path: '/list1',
            name: 'List1',
            component: List1
        }]
    }]
});
router.beforeEach((to, from, next) => {
    if (to.meta.title) {
        document.title = to.meta.title;
    }
    console.log(to)
    next()

});
export default router