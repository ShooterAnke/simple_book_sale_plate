import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store';
import ElementUI from 'element-ui'

import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/scss/reset.scss'
Vue.config.productionTip = false
Vue.use(ElementUI);

router.beforeEach((to,from,next)=>{
  store.commit('getToken')
  store.commit('addTabs')
  let token=localStorage.getItem('token');
  if(!token&&to.name!=='login'&&to.name!=='register'){
    next({name:'login'})
  }
  else{
    next();
  }
})



new Vue({
  router,
  store,
  render: h => h(App),
  created(){
    store.commit('addRouter',router)
  }
}).$mount('#app')
