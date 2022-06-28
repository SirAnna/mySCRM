<template>
  <div id="app">
    <keep-alive>
      <Aside></Aside>
    </keep-alive>
    <MainBox>
      <router-view/>
    </MainBox>
  </div>
</template>

<script>
import Aside from "./components/Aside";
import MainBox from "./components/MainBox";
import vInfo from "./components/vInfo";
import SearchUser from "./components/SearchUser";


export default {
  name: 'App',
  components: {MainBox, Aside,vInfo,SearchUser},
  data(){
    return{
      col:[
        {field:'a',key:'a',title:'A'},
        {field:'b',key:'b',title:'B'}
      ],
      dat:[
        {'a':<button onClick="alert('ass)" >sub</button>
          ,'b':2},
        {'a':3,'b':4}
      ]
    }
  },
  methods:{
    testCLI:function (){
      const axios=require('axios').default;
      const qs=require('qs');
      let req={
        'sPara':'2021'
      };
      axios.post(
        'http://localhost:8000/SCRM_war_exploded/vSearch',
        qs.stringify(req)
      ).then(function(result){
        alert(result.data.Users.length);
      }).catch(function (error){
        alert(error);
      })
    }
  }
}
</script>

<style>
</style>
