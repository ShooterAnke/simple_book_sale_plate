
export default {
  state: {
    isCollapse: false,
    currentMenu: null,
    menu: [],
    tabsList: [
      {
        path: '/',
        name: 'home',
        label: '首页',
        icon: 'home'
      }
    ]
  },
  mutations: {
    setMenu(state, val) {
      state.menu = val;
      localStorage.setItem("menu", JSON.stringify(val))
    },
    clearMenu(state) {
      state.menu = []
      localStorage.clear('menu')
    },

    addRouter(state, router) {
      if (!localStorage.getItem('menu')) {
        return
      }
      let menu = JSON.parse(localStorage.getItem('menu'))
      state.menu = menu
      let currentMenu = [
        {
          path: '/',
          component: () => import('@/views/Main'),
          children: []
        }
      ]
      menu.forEach(item => {
        if (item.children.length > 0) {
          item.children = item.children.map(item => {
            let path=item.component.split('/')
            let path1=path[0]
            let path2=path[1]
            item.component = ()=>import(`@/views/${path1}/${path2}`)
            return item
          })
          currentMenu[0].children.push(...item.children)
        } else {
       
          let path=item.component.split('/')
          let path1=path[0]
          let path2=path[1]+'.vue'
          item.component = ()=>import(`@/views/${path1}/${path2}`)
          currentMenu[0].children.push(item)
        }
      })
      currentMenu[0].children.push(
        {
          path:'/personal',
          name:'personal',
          component:()=>import('@/views/personal/personal')
      }
      )
      router.addRoutes(currentMenu)
    },
    selectMenu(state, val) {
      if (val.name !== 'home') {
        state.currentMenu = val
        let result = state.tabsList.findIndex(item => item.name === val.name)
        result === -1 ? state.tabsList.push(val) : ''
        localStorage.setItem('tagList', JSON.stringify(state.tabsList))
      }
      else {
        state.currentMenu = null
      }
    },
    addTabs(state) {
      if (localStorage.getItem('tagList')) {
        let tagList = JSON.parse(localStorage.getItem('tagList'))
        state.tagsList = tagList
      }
    },
    closeTab(state, val) {
      let result = state.tabsList.findIndex(item => item.name === val.name)
      state.tabsList.splice(result, 1)
      localStorage.setItem('tagList', JSON.stringify(state.tabsList))
    },
    collapseMenu(state) {
      state.isCollapse = !state.isCollapse
    }

  },
  actions: {}
}
