// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import "vue-easytable/libs/theme-default/index.css";
import VueEasytable from 'vue-easytable'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';



Vue.config.productionTip = false
/* eslint-disable no-new */
Vue.use(VueEasytable);
Vue.use(ElementUI);

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
