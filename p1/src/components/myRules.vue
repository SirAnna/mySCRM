<template>
  <div class="fontChar">
    <div style="position:absolute; height:30%;width: 100%;border-bottom: solid 2px #999999">
      &nbsp;
      <input type="text" class="myInput" ref="m" style="top:5px" :placeholder="'平时的积分系数,当前为: '+get_m()"/><br>
      &nbsp;
      <input type="text" class="myInput" ref="n" style="top:10px" :placeholder="'生日的积分系数,当前为: '+get_n()"/><br>
      &nbsp;
      <input type="text" class="myInput" ref="changer" style="top:15px" placeholder="修改人签名"/><br>
      &nbsp;
      <button class="myButton" :style="butSty" @mouseover="butOver" @mouseleave="butLeave" @click="Reset">确认修改</button>
    </div>
    <div style="position: absolute;height:7%;width: 100%;top:30%">
      &nbsp;<div class="TipBlock"/>&nbsp;修改历史
    </div>
    <div style="position: absolute;height: 63%;width: 100%;top:37%">
      <el-table :data="td_dat" height="95%">
        <el-table-column prop="rule_id" label="规则号"/>
        <el-table-column prop="m" label="平时的积分系数"/>
        <el-table-column prop="n" label="生日时的积分系数"/>
        <el-table-column prop="changer" label="修改人"/>
        <el-table-column prop="change_date" label="修改日期"/>
      </el-table>
    </div>
  </div>
</template>
<script>
export default {
  name: "myRules",
  data(){
    return{
      butSty:'',
      td_dat:[],
      qs:require('qs'),
      axios:require('axios').default,
      urlHead:'http://localhost:8000/SCRM_war_exploded/'
    }
  },
  mounted() {
    this.Search();
  },
  methods:{
    get_m:function (){
      if(this.td_dat.length===0)
        return '未设定';
      return this.td_dat[0].m;
    },
    get_n:function (){
      if(this.td_dat.length===0)
        return '未设定';
      return  this.td_dat[0].n;
    },
    butOver:function (){
      this.butSty="background:#39b1d6;box-shadow:gray 0 0 5px 1px;" +
        "cursor:pointer";
    },
    butLeave:function (){
      this.butSty='';
    },
    isNumber:function (s){
      return !isNaN(Number(s));
    },
    pLegal:function (p){
      if(!this.isNumber(p.m))
        return false;
      if(!this.isNumber(p.n))
        return false;
      return p.changer.length !== 0;
    },
    Search:function (){
      let url=this.urlHead+'getRules';
      this.axios.post(url).then(res=>{
        this.td_dat=res.data.rules;
      });
    },
    Reset:function (){
      let param={
        m:this.$refs.m.value,
        n:this.$refs.n.value,
        changer:this.$refs.changer.value
      };
      if(!this.pLegal(param)){
        alert("请填写所有的项目");
        return;
      }
      if(confirm("确定要提交修改吗?")){
        let url=this.urlHead+'setRules';
        param=this.qs.stringify(param);
        this.axios.post(url,param).then(res=>{
          if(res.data.flag===0) {
            alert("修改成功");
            this.Search();
            this.$refs.m.value='';
            this.$refs.n.value='';
            this.$refs.changer.value='';
          }
          else alert("修改失败: "+res.data.err);
        }).catch(err=>{
          alert("修改失败: "+err);
        });
      }
    }
  }
}
</script>

<style scoped>
input::-webkit-input-placeholder{
  color: #999999;
}
.myButton{
  width:48%;
  height:20%;
  font-size: 22px;
  border:0;
  color: aliceblue;
  background: #1891b7;
  position: relative;
  top:20px;
}
.fontChar{
  color: #999999;
  font-size: 22px;
}
.myInput{
  width:48%;
  height:20%;
  font-size: 22px;
  color:#666666;
  position: relative;
}
.TipBlock{
  background: #1891B7;
  height:10px;
  width:10px;
  margin-top: 9px;
  display: inline-block;
}
</style>
