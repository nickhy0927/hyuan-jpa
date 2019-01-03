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
import CreateMenu from '../components/platform/access/menu/CreaeteMenu.vue'
import Login from '../components/Login'

Vue.use(VueRouter);

let router = new VueRouter({
    mode: 'history',
    routes: [{
        path: '/',
        meta: {
            requiresAuth: true
        },
        redirect: '/index/task'
    }, {
        path: '/login',
        name: 'Login',
        meta: {
            requiresAuth: false
        },
        component: Login
    }, {
        path: '/platform/access/menu/menuCreate',
        name: 'CreateMenu',
        meta: {
            requiresAuth: true
        },
        component: CreateMenu
    }, {
        path: '/index',
        name: 'Index',
        meta: {
            requiresAuth: true
        },
        component: Index,
        children: [{
            path: '/index/task',
            name: 'Task',
            meta: {
                requiresAuth: true
            },
            component: Task
        }, {
            path: '/platform/access/user/userList',
            name: 'UserList',
            meta: {
                requiresAuth: true
            },
            component: UserList
        }, {
            path: '/platform/access/role/roleList',
            name: 'RoleList',
            meta: {
                requiresAuth: true
            },
            component: RoleList
        }, {
            path: '/platform/access/menu/menuList',
            name: 'MenuList',
            meta: {
                requiresAuth: true
            },
            component: MenuList
        }, {
            path: '/platform/system/optlog/optlogList',
            name: 'OptlogList',
            meta: {
                requiresAuth: true
            },
            component: OptlogList
        }, {
            path: '/platform/system/exception/exceptionLogList',
            name: 'ExceptionLogList',
            meta: {
                requiresAuth: true
            },
            component: ExceptionLogList
        }, {
            path: '/list1',
            name: 'List1',
            meta: {
                requiresAuth: true
            },
            component: List1
        }]
    }]
});
router.beforeEach((to, from, next) => {
    if (to.meta.title) {
        document.title = to.meta.title;
    }
    console.log('\n\n------ begin: oauth ------')
    console.log(to.meta.requiresAuth)
    console.log('------ end: oauth ------\n\n')
    next()
    /* if (to.matched.some(record => record.meta.requiresAuth)) {
        // this route requires auth, check if logged in
        // if not, redirect to login page.
        if (!auth.loggedIn()) {
            next({
                path: '/login',
                query: {
                    redirect: to.fullPath
                }
            })
        } else {
            next()
        }
    } else {
        next() // make sure to always call next()!
    } */
});
export default router