import Vue from 'vue'
import VueMoment from 'vue-moment'
import VueRouter from 'vue-router'
import VueCookie from 'vue-cookies'
import VueSession from 'vue-session'
import VModal from 'vue-js-modal'

import App from './App.vue'
import Editor from './components/Editor.vue'
import Setting from './components/Setting.vue'
import Result from './components/Result.vue'
import Sign from './components/Sign.vue'

import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

library.add(fas)
Vue.use(VueMoment)
Vue.use(VueRouter)
Vue.use(VueCookie)
Vue.use(VueSession)
Vue.use(VModal)
Vue.config.productionTip = true
Vue.component('font-awesome-icon', FontAwesomeIcon)

const routes = [
  { path: '/', component: App },
  { path: '/editor', component: Editor },
  { path: '/setting', component: Setting },
  { path: '/result', component: Result },
  { path: '/sign', component: Sign },
]
const router = new VueRouter({
  mode: 'history',
  routes
})

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')