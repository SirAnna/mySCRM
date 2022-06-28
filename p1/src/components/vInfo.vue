<template>
  <div style="position: relative;left:calc(25% - 10px);top:17%">
    <input type="text" class="myInput" @change="Listen(0,nameLegal)" :placeholder="d_name" ref="vip_name"/>
    <div class="TinyBox" :style="sty[0]">{{tips[0]}}</div>
    <input type="text" class="myInput" @change="Listen(1,phoneLegal)" :placeholder="d_phone" ref="vip_phone_number"/>
    <div class="TinyBox" :style="sty[1]">{{tips[1]}}</div>
    <input type="text" class="myInput" @change="Listen(2,emailLegal)" :placeholder="d_email" ref="vip_email"/>
    <div class="TinyBox" :style="sty[2]">{{tips[2]}}</div>
    <input type="text" class="myInput" @change="Listen(3,addrLegal)" :placeholder="d_addr" ref="vip_address"/>
    <div class="TinyBox" :style="sty[3]">{{tips[3]}}</div>
    <input type="text" class="myInput" :placeholder="d_carLic" ref="vip_car_license"/>
    <br><br>
    <select class="myInput" v-model="d_sex" ref="vip_sex">
      <option value=-1 hidden="hidden" selected>请选择性别</option>
      <option value=0 selected>男性</option>
      <option value=1 selected>女性</option>
    </select>
    <br><br>
    <div class="fontChar" style="font-size: 23px; width: 14%;display: inline-block;text-align: center">
      生日:
    </div>
    <input type="text" ref="by" class="myInput2" @change="Listen(4,dateLegal)" :placeholder="d_birth[0]"/>
    <input type="text" ref="bm" class="myInput2" @change="Listen(4,dateLegal)" :placeholder="d_birth[1]" style="left: -5px"/>
    <input type="text" ref="bd" class="myInput2" @change="Listen(4,dateLegal)" :placeholder="d_birth[2]" style="left: -10px"/>
    <div class="TinyBox" :style="sty[4]">{{tips[4]}}</div>
    <br><br>
    <button class="myButton" :style="butSty" @mouseover="butOver" @mouseleave="butLeave" @mousedown="sub">
      提交
    </button>
  </div>
</template>

<script>
export default {
  name: "vInfo",
  props:{
    d_name:{
      type:String,
      default:'*会员姓名'
    },
    d_phone:{
      type:String,
      default: '*会员手机号'
    },
    d_email:{
      type:String,
      default: '*会员邮箱'
    },
    d_addr:{
      type:String,
      default: '*会员住址'
    },
    d_carLic:{
      type:String,
      default: '会员车牌号'
    },
    d_sex:{
      type:String,
      default:'-1'
    },
    d_birth:{
      type:Array,
      default:()=>{
        let d=new Date;
        return [d.getFullYear(),d.getMonth()+1,d.getDate()];
      }
    },
    vip_id:{
      type:String,
      default:"ignore"
    },
    postUrl:{
      type:String,
      default:'http://localhost:8000/SCRM_war_exploded/VIPReg'
    }
  },
  data(){
    return{
      butSty:'',
      tips:[
        this.gt(this.d_name,'*会员姓名'),
        this.gt(this.d_phone,'*会员手机号'),
        this.gt(this.d_email,'*会员邮箱'),
        this.gt(this.d_addr,'*会员住址'),
        '√'
      ],
      sty:[
        this.gs(this.d_name,'*会员姓名'),
        this.gs(this.d_phone,'*会员手机号'),
        this.gs(this.d_email,'*会员邮箱'),
        this.gs(this.d_addr,'*会员住址'),
        'color:green;'
      ],
    }
  },
  methods:{
    butOver:function (){
      this.butSty="background:#39b1d6;box-shadow:gray 0 0 5px 1px;" +
        "cursor:pointer";
    },
    butLeave:function (){
      this.butSty='';
    },
    isNumber:function (s){
      if(s.length<=0)
        return false;
      for(let c in s)
        if(isNaN(Number(s[c])))
          return false;
      return true;
    },
    nameLegal:function (){
      return this.d_name!=='*会员姓名'||this.$refs.vip_name.value.length>0;
    },
    phoneLegal:function (){
      let ps=this.$refs.vip_phone_number.value;
      return (ps.length===0&&this.d_phone!=='*会员手机号')||this.isNumber(ps);
    },
    emailLegal:function () {
      let em=this.$refs.vip_email.value;
      return (em.length===0&&this.d_email!=='*会员邮箱')||
        em.match(/^\w+@\w+\.\w+$/i);
    },
    addrLegal:function (){
      return this.d_addr!=='*会员住址'||this.$refs.vip_address.value.length>0;
    },
    dateLegal:function (){
      let y=this.$refs.by.value;
      let m=this.$refs.bm.value;
      let d=this.$refs.bd.value;
      return (y.length===0||this.isNumber(y))
              &&(m.length===0||this.isNumber(m))
              &&(d.length===0||this.isNumber(d));
    },
    gt:function (a,b){
      return a===b?'*':'√';
    },
    gs:function (a,b){
      return a===b?'':'color:green;';
    },
    Listen:function (which,isLegal){
      if(isLegal()){
        this.$set(this.tips,which,'√');
        this.$set(this.sty,which,'color:green;');
      }
      else{
        this.$set(this.tips,which,'*');
        this.$set(this.sty,which,'');
      }
    },
    isAllOK:function (){
      for(let i in this.tips)
        if(this.tips[i]==='*')
          return false;
      return true;
    },
    autoFormat:function (s,len){
      while(s.length<len)
        s='0'+s;
      return s;
    },
    sub(){
      if(!this.isAllOK()) {
        alert('请填写每个带\'*\'的必填项');
        return;
      }
      if(confirm('确定要提交吗？')){
        let vip_name=this.$refs.vip_name.value;
        if(vip_name.length===0) vip_name=this.d_name;

        let y=this.$refs.by.value;
        if(y.length===0) y=this.d_birth[0];
        y=this.autoFormat(y,4);
        let m=this.$refs.bm.value;
        if(m.length===0) m=this.d_birth[1];
        m=this.autoFormat(m,2);
        let d=this.$refs.bd.value;
        if(d.length===0) d=this.d_birth[2];
        d=this.autoFormat(d,2);
        let birth=y+'-'+m+'-'+d;

        let vip_pn=this.$refs.vip_phone_number.value;
        if(vip_pn.length===0) vip_pn=this.d_phone;
        let vip_email=this.$refs.vip_email.value;
        if(vip_email.length===0) vip_email=this.d_email;
        let vip_address=this.$refs.vip_address.value;
        if(vip_address.length===0) vip_address=this.d_addr;
        let vip_car_license=this.$refs.vip_car_license.value;
        if(vip_car_license.length===0&&this.d_carLic!=='会员车牌号') vip_car_license=this.d_carLic;
        let info={
          'vip_id':this.vip_id,
          'vip_name':vip_name,
          'vip_sex':this.$refs.vip_sex.value,
          'vip_birth':birth,
          'vip_phone_number':vip_pn,
          'vip_email':vip_email,
          'vip_address':vip_address,
          'vip_car_license':vip_car_license
        };
        const axios=require("axios").default;
        const qs=require("qs");
        axios.post(
          this.postUrl,
          qs.stringify(info)
        ).then((result)=>{
          if(result.data.flag===0)
            alert('提交成功');
          else
            alert('提交失败: '+result.data.err);
        }).catch((error)=>{
          alert('提交失败: '+error);
        })
      }
    }
  }
}
</script>

<style scoped>
.myInput{
  width:50%;
  height:40px;
  font-size: 22px;
  color:#666666
}
.myInput2{
  position: relative;
  height:40px;
  font-size: 22px;
  color:#666666;
  display: inline-block;
  width: 12%;
  text-align: center;
}
.myButton{
  width:50%;
  height:40px;
  font-size: 22px;
  border:0;
  color: aliceblue;
  background: #1891b7;
}
.fontChar{
  color: #999999;
  font-size: 17px;
}
.TinyBox{
  width: 20px;
  height: 20px;
  color: red;
  display: inline-block;
}
input::-webkit-input-placeholder{
  color: #999999;
}
</style>
