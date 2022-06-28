<template>
<div class="fontChar">
  <div class="topBox">
    &nbsp;
    <input type="text" class="myInput" ref="searchBox" placeholder="请输入关键字" @change="Search"/>
    <select class="myInput" style="width: 20%" ref="payType" @change="Search">
      <option value=-1 selected>全部支付方式</option>
      <option value=0>微信</option>
      <option value=1>支付宝</option>
      <option value=2>国内储蓄卡</option>
      <option value=3>国内信用卡</option>
      <option value=4>paypal</option>
    </select>
    &nbsp;
    <input v-model="onlyNotAcc" type="checkbox" style="width: 15px;height: 15px" @change="Search"/>只看未记账&nbsp;
    <input v-model="onlyAcc" type="checkbox" style="width: 15px;height: 15px" @change="Search"/>只看已记账<br>
    &nbsp;
    <input v-model="checkByTime" type="checkbox" style="width: 15px;height: 15px" @change="Search"/>按时间筛选
    &nbsp;
    <div style="display: inline-block;width:80%">
      <div v-if="!checkByTime" style="background: rgba(255,255,255,0.7);position: absolute;height:50%;width: 100%"></div>
      <input type="text" class="myInput" ref="sy" placeholder="年" style="width: 12%" @change="Search"/>
      <input type="text" class="myInput" ref="sm" placeholder="月" style="width: 12%" @change="Search"/>
      <input type="text" class="myInput" ref="sd" placeholder="日" style="width: 12%" @change="Search"/>
      &nbsp;至&nbsp;
      <input type="text" class="myInput" ref="ty" placeholder="年" style="width: 12%" @change="Search"/>
      <input type="text" class="myInput" ref="tm" placeholder="月" style="width: 12%" @change="Search"/>
      <input type="text" class="myInput" ref="td" placeholder="日" style="width: 12%" @change="Search"/>
    </div>
  </div>
  <div class="formBox">
    <el-table :data="td_dat" height="95%">
      <el-table-column prop="bill_id" width="90px" label="流水号"/>
      <el-table-column prop="vip_id" width="90px" label="会员编号"/>
      <el-table-column prop="transaction" label="交易额" :formatter="transFormat"/>
      <el-table-column prop="pay" label="支付额" :formatter="payFormat"/>
      <el-table-column show-overflow-tooltip prop="commodity" label="商品" :formatter="comFormat"/>
      <el-table-column prop="trans_date" label="交易日期"/>
      <el-table-column prop="pay_type" label="支付方式" :formatter="payTypeFormat"/>
      <el-table-column prop="is_accounted" label="是否记账" :formatter="accFormat"/>
      <el-table-column label="操作" width="150px">
        <template slot-scope="scope">
          <el-button :type="'primary'" :size="'mini'" v-if="scope.row.is_accounted===0" @click="bEdit(scope.$index)">编辑</el-button>
          <el-button :type="'danger'" :size="'mini'" v-if="scope.row.is_accounted===0" @click="bDel(scope.$index)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</div>
</template>

<script>
export default {
  name: "seeBills",
  data(){
    return{
      checkByTime:false,
      onlyNotAcc:false,
      onlyAcc:false,
      urlHead:'http://localhost:8000/SCRM_war_exploded/',
      qs:require('qs'),
      axios:require("axios").default,
      td_dat:[]
    }
  },
  mounted() {
    this.Search();
  },
  methods:{
    autoFormat:function (s,len){
      while(s.length<len)
        s='0'+s;
      return s;
    },
    Search:function () {
      let sPara=this.$refs.searchBox.value;
      let pay_type=this.$refs.payType.value;
      if(pay_type==='-1')
        pay_type='ig';
      let acc
      if(this.onlyNotAcc)
        acc='0';
      if(this.onlyAcc)
        acc='1';
      if((this.onlyAcc&&this.onlyNotAcc)
        ||(!this.onlyNotAcc&&!this.onlyAcc))
        acc='ig';
      let time="";
      if(this.checkByTime&&this.dateLegal()){
        let t=this.getTime();
        for(let i in t) {
          if (i % 3 === 0)
            t[i] = this.autoFormat(t[i], 4);
          else
            t[i] = this.autoFormat(t[i],2);
        }
        time=t[0]+'-'+t[1]+'-'+t[2]+'>>'+t[3]+'-'+t[4]+'-'+t[5];
      }
      else {
        time = 'ig';
      }
      let param={
        sPara:sPara,
        pay_type:pay_type,
        acc:acc,
        time:time
      }
      this.axios.post(
        this.urlHead+'bSearch',
        this.qs.stringify(param)
      ).then(res=>{
        this.td_dat =res.data.Bills;
      });
    },
    getTime:function (){
      return[
        this.$refs.sy.value,
        this.$refs.sm.value,
        this.$refs.sd.value,
        this.$refs.ty.value,
        this.$refs.tm.value,
        this.$refs.td.value
      ];
    },
    isNumber:function (s){
      return !isNaN(Number(s));
    },
    dateLegal:function (){
      let time=this.getTime();
      for(let i in time)
        if(!this.isNumber(time[i])||Number(time[i])===0)
          return false;
      return true;
    },
    transFormat:function (r,c){
      if(r.transaction==='*请输入交易额')
        return '*未填写';
      return r.transaction;
    },
    payFormat:function (r,c){
      if(r.pay==='*请输入支付额')
        return '*未填写';
      return r.pay;
    },
    comFormat:function (r,c){
      if(r.commodity==='*请输入商品')
        return '*未填写';
      return  r.commodity;
    },
    payTypeFormat:function (r,c){
      if(r.pay_type===-1)
        return '*未选择';
      if(r.pay_type===0)
        return '微信';
      if(r.pay_type===1)
        return '支付宝';
      if(r.pay_type===2)
        return '国内储蓄卡';
      if(r.pay_type===3)
        return '国内信用卡';
      if(r.pay_type===4)
        return 'paypal';
    },
    accFormat:function (r,c){
      if(r.is_accounted===0)
        return '未记账';
      return '已记账';
    },
    bEdit:function (which){
      let param=encodeURIComponent(
        this.qs.stringify(this.td_dat[which])
      );
      this.$router.push('/billEdit/'+param);
    },
    bDel:function (which) {
      confirm("确定要删除流水" + this.td_dat[which].bill_id + "吗?")
      {
        let param = this.qs.stringify(
          {'bill_id': this.td_dat[which].bill_id}
        );
        let url = this.urlHead + 'bDel';
        this.axios.post(url, param).then(result=>{
          this.Search();
        })
      }
    }
  }
}
</script>

<style scoped>
.topBox{
  position: absolute;
  height: 20%;
  width: 100%;
  border-bottom: solid 2px #999999;
  line-height: 50px;
}
.formBox{
  position: absolute;
  height:80%;
  width: 100%;
  top:20%;
}
.myInput{
  width:30%;
  height:35px;
  font-size: 22px;
  color:#666666
}
input::-webkit-input-placeholder{
  color: #999999;
}
.fontChar{
  color: #999999;
  font-size: 22px;
}
</style>
