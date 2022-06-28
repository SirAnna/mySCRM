<template>
  <div style="position: relative;left:calc(25% - 10px);top:17%">
    <div v-if="is_acc" style="position: absolute;width: 100%;height: 100%;background:rgba(255,255,255,0.4)"/>
    <input type="text" class="myInput" @change="Listen_axi(0,idLegal())" :placeholder="d_id" ref="vip_id"/>
    <div class="TinyBox" :style="sty[0]">{{tips[0]}}</div>
    <input type="text" class="myInput" @change="Listen(1,comLegal)" :placeholder="d_com" ref="commodity"/>
    <div class="TinyBox" :style="sty[1]">{{tips[1]}}</div>
    <input type="text" class="myInput" @change="Listen(2,transLegal)" :placeholder="d_trans" ref="transaction"/>
    <div class="TinyBox" :style="sty[2]">{{tips[2]}}</div>
    <input type="text" class="myInput" @change="Listen(3,payLegal)" :placeholder="d_pay" ref="pay"/>
    <div class="TinyBox" :style="sty[3]">{{tips[3]}}</div>
    <br><br>
    <select class="myInput" v-model="d_typ" @change="Listen(5,typLegal)" ref="pay_typ">
      <option value=-1 hidden="hidden" selected>请选择支付方式</option>
      <option value=0 selected>微信</option>
      <option value=1 selected>支付宝</option>
      <option value=2 selected>国内储蓄卡</option>
      <option value=3 selected>国内信用卡</option>
      <option value=4 selected>paypal</option>
    </select>
    <div class="TinyBox" :style="sty[5]">{{tips[5]}}</div>
    <br><br>
    <div class="fontChar" style="font-size: 23px; width: 12%;display: inline-block;text-align: center">
      交易日期
    </div>
    <input type="text" ref="by" class="myInput2" @change="Listen(4,dateLegal)" :placeholder="d_trans_date[0]"/>
    <input type="text" ref="bm" class="myInput2" @change="Listen(4,dateLegal)" :placeholder="d_trans_date[1]" style="left: -5px"/>
    <input type="text" ref="bd" class="myInput2" @change="Listen(4,dateLegal)" :placeholder="d_trans_date[2]" style="left: -10px"/>
    <div class="TinyBox" :style="sty[4]">{{tips[4]}}</div>
    <br><br>
    <button class="myButton" :style="butSty[0]" @mouseover="butOver(0)" @mouseleave="butLeave(0)" @mousedown="sub1">
      保存
    </button>
    <button class="myButton" :style="butSty[1]" @mouseover="butOver(1)" @mouseleave="butLeave(1)" @mousedown="sub2">
      记账
    </button>
    <div v-if="is_acc" style="color: #4C4C4C">已记账</div>
  </div>
</template>

<script>
export default {
  name: "billLog",
  props:{
    d_id:{
      type:String,
      default:'*请输入会员编号'
    },
    d_com:{
      type:String,
      default: '*请输入商品'
    },
    d_trans:{
      type:String,
      default: '*请输入交易额'
    },
    d_pay:{
      type:String,
      default: '*请输入支付额'
    },
    d_typ:{
      type:String,
      default:'-1'
    },
    d_trans_date:{
      type:Array,
      default:()=>{
        let d=new Date;
        return [d.getFullYear(),d.getMonth()+1,d.getDate()];
      }
    },
    bill_id:{
      type:String,
      default:'DEFAULT'
    }
  },
  data(){
    return{
      butSty_Dst:"background:#39b1d6;box-shadow:gray 0 0 5px 1px;" +
        "cursor:pointer",
      butSty:['',''],
      tips:[
        this.gt(this.d_id,'*请输入会员编号'),
        this.gt(this.d_com,'*请输入商品'),
        this.gt(this.d_trans,'*请输入交易额'),
        this.gt(this.d_pay,'*请输入支付额'),
        '√',
        this.gt(this.d_typ,'-1')
      ],
      sty:[
        this.gs(this.d_id,'*请输入会员编号'),
        this.gs(this.d_com,'*请输入商品'),
        this.gs(this.d_trans,'*请输入交易额'),
        this.gs(this.d_pay,'*请输入支付额'),
        'color:green;',
        this.gs(this.d_typ,'-1')
      ],
      qs:require('qs'),
      axios:require('axios').default,
      urlHead:'http://localhost:8000/SCRM_war_exploded/',
      is_acc:false
    }
  },
  methods:{
    butOver:function (which){
      this.$set(this.butSty,which,this.butSty_Dst);
    },
    butLeave:function (which){
      this.$set(this.butSty,which,'');
    },
    isNumber:function (s){
      return !isNaN(Number(s));
    },
    get_vip_id:function(){
      let id=this.$refs.vip_id.value;
      if(id==null||id.length===0)
        id=this.d_id;
      return id;
    },

    /////todo
    idLegal:async function (){
      let v_id=this.get_vip_id();
      if(!this.isNumber(v_id))
        return false;
      let url=this.urlHead+'vExist';
      let param=this.qs.stringify({'vip_id':v_id});
      let r;
      await this.axiJud(url,param).then(res=>{
        r=res;
      })
      return r;
    },
    comLegal:function (){
      return this.d_com!=='*请输入商品'
        ||this.$refs.commodity.value.length!==0;
    },
    transLegal:function (){
      let tr=this.$refs.transaction.value;
      return (this.d_trans!=='*请输入交易额'&&tr.length===0)
            ||(this.isNumber(tr)&&Number(tr)>0);
    },
    payLegal:function (){
      let tr=this.$refs.pay.value;
      return (this.d_pay!=='*请输入支付额'&&tr.length===0)
        ||(this.isNumber(tr)&&Number(tr)>0);
    },
    typLegal:function (){
      return this.$refs.pay_typ.value!=='-1';
    },
    /////!todo

    axiJud:async function(url,param){
      let flag;
      await this.axios.post(url,param).then(res=>{
        flag=res.data.flag;
      });
      return flag===0;
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
    Listen_axi:function (which,isLegal){
      isLegal.then(res=> {
        if (res) {
          this.$set(this.tips, which, '√');
          this.$set(this.sty, which, 'color:green;');
        } else {
          this.$set(this.tips, which, '*');
          this.$set(this.sty, which, '');
        }
      })
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

    /////todo
    isNew:function (){
      return this.bill_id === 'DEFAULT';
    },
    getData:function (){
      let v_id=this.$refs.vip_id.value;
      if(v_id.length===0) v_id=this.d_id;
      let com=this.$refs.commodity.value;
      if(com.length===0) com=this.d_com;
      let trans=this.$refs.transaction.value;
      if(trans.length===0) trans=this.d_trans;
      let pay=this.$refs.pay.value;
      if(pay.length===0) pay=this.d_pay;
      let pay_typ=this.$refs.pay_typ.value;

      let y=this.$refs.by.value;
      if(y.length===0) y=this.d_trans_date[0];
      y=this.autoFormat(y,4);
      let m=this.$refs.bm.value;
      if(m.length===0) m=this.d_trans_date[1];
      m=this.autoFormat(m,2);
      let d=this.$refs.bd.value;
      if(d.length===0) d=this.d_trans_date[2];
      d=this.autoFormat(d,2);
      let trans_date=y+'-'+m+'-'+d;
      return{
        bill_id: this.bill_id,
        vip_id:v_id,
        transaction:trans,
        pay:pay,
        commodity:com,
        trans_date:trans_date,
        pay_type:pay_typ,
        is_accounted:0
      };
    },
    //save
    async sub1() {
      let dat = this.getData();
      if (this.is_acc) {
        alert('流水已记账，不能再修改！');
        return;
      }
      if(dat.vip_id==="*请输入会员编号"){
        alert('请至少输入会员编号');
        return;
      }
      if(this.tips[4]==='*'){
        alert('请输入正确格式的时间');
        return;
      }
      let url;
      if(this.isNew()) {
        url = this.urlHead + "billsSav";
        await this.axios.post(
          url,
          this.qs.stringify(dat)
        ).then(res => {
          if (res.data.flag === 0) {
            alert("已保存");
            this.bill_id = res.data.bill_id;
          } else alert("保存失败: " + res.data.err);
        }).catch(err => {
          alert("保存失败: " + err);
        });
      }
      else{
        url =this.urlHead + "billsUpd";
        this.axios.post(
          url,
          this.qs.stringify(dat)
        ).then(res=>{
          if(res.data.flag===0)
            alert("已保存");
          else alert("保存失败: " +res.data.err);
        }).catch(err=>{
          alert("保存失败: "+err);
        });
      }
    },

    //accounted
    sub2(){
      if(!this.isAllOK()) {
        alert('请填写每个带\'*\'的必填项');
        return;
      }
      if(confirm('确定要记账吗？记账后不可再修改')) {
        let dat = this.getData();
        dat.is_accounted = 1;
        let url;
        if (this.isNew())
          url = this.urlHead + 'billsSav';
        else
          url = this.urlHead + 'billsUpd';
        this.axios.post(
          url,
          this.qs.stringify(dat)
        ).then(res => {
          if (res.data.flag === 0) {
            alert("已记账");
            this.is_acc=true;
          }
          else alert("记账失败: " + res.data.err);
        }).catch(err => {
          alert("记账失败: " + err);
        });
      }
    }
    /////!todo

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
  height:40px;
  font-size: 22px;
  color:#666666;
  display: inline-block;
  width: 12%;
  text-align: center;
}
.myButton{
  width:25%;
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
