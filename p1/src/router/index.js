import Vue from 'vue'
import Router from 'vue-router'
import Aside from "../components/Aside";
import UsrEdit from "../components/UsrEdit";
import vInfo from "../components/vInfo"
import SearchUser from "../components/SearchUser";
import blank from "../components/blank";
import billLog from "../components/billLog";
import seeBills from "../components/seeBills";
import billEdit from "../components/billEdit";
import myRules from "../components/myRules";
import seeLedger from "../components/seeLedger";

Vue.use(Router)

export default new Router({
  routes: [
    {path:'/',name:'Aside' ,component:Aside},
    {path:'/UsrEdit/:info',name:'UsrEdit',component:UsrEdit},
    {path:'/SignUp',name:'vInfo',component:vInfo},
    {path:'/SeeUsr',name:'SearchUser',component:SearchUser},
    {path:'/blank/:src',name:'blank',component:blank},
    {path:'/billLog',name:'billLog',component:billLog},
    {path:'/seeBills',name:'seeBills',component:seeBills},
    {path:'/billEdit/:info',name:'billEdit',component:billEdit},
    {path:'/myRules',name:'myRules',component:myRules},
    {path:'/seeLedger/:vip_id',name:'seeLedger',component:seeLedger}
  ]
})
