<template>
<div>
  <div class="fontChar" style="position: absolute;width: 100%;height:50px;line-height: 50px">
    &nbsp;<div class="TipBlock"/>&nbsp;查看台账
  </div>
  <div style="position: absolute;width: 100%;height: calc(100% - 50px);top:50px">
    <el-table :data="td_dat" height="95%">
      <el-table-column :prop="'led_id'" label="台账号"/>
      <el-table-column :prop="'deta_points'" label="积分变动值"/>
      <el-table-column :prop="'date_get'" label="变动日期"/>
    </el-table>
  </div>
</div>
</template>

<script>
export default {
  name: "seeLedger",
  data(){
    return{
      td_dat:[],
      qs:require('qs'),
      axios:require('axios').default,
      url:'http://localhost:8000/SCRM_war_exploded/getLedger'
    }
  },
  mounted() {
    this.getLed();
  },
  methods:{
    getLed(){
      let param=this.qs.stringify(
        {vip_id:this.$route.params.vip_id}
      );
      this.axios.post(this.url,param).then(res=>{
        this.td_dat=res.data.ledgers;
      })
    }
  }
}
</script>

<style scoped>
.fontChar{
  color: #999999;
  font-size: 22px;
}
.TipBlock{
  background: #1891B7;
  height:10px;
  width:10px;
  margin-top: 9px;
  display: inline-block;
}
</style>
