<template>
<div>
  <div class="TopBox">
    &nbsp;&nbsp;
    <input type="text" class="myInput" ref="SearchBox" @change="Search" placeholder="请输入关键词"/>
  </div>
  <div style="position: absolute;height:calc(100% - 60px);width:100%">
    <el-table :data="td_dat" height="95%">
      <el-table-column width="50px" prop="vip_id" label="编号"/>
      <el-table-column prop="vip_name" label="姓名"/>
      <el-table-column width="50px" prop="vip_sex" label="性别" :formatter="sexToChar"/>
      <el-table-column prop="vip_birth" label="生日"/>
      <el-table-column prop="vip_phone_number" label="手机号"/>
      <el-table-column show-overflow-tooltip prop="vip_email" label="电子邮件"/>
      <el-table-column show-overflow-tooltip prop="vip_address" label="住址"/>
      <el-table-column show-overflow-tooltip prop="vip_car_license" label="车牌号"/>
      <el-table-column width="60px" prop="vip_points" label="积分"/>
      <el-table-column width="260px" label="操作">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="seeLed(scope.$index)">
            查看台账
          </el-button>
          <el-button size="mini" type="primary" @click="to(scope.$index)">
            编辑
          </el-button>
          <el-button size="mini" type="primary" @click="del(scope.$index)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</div>
</template>

<script>
export default {
  name: "SearchUser",
  data(){
    return{
      td_dat:[],
      qs:require('qs'),
      axios:require("axios").default
    }
  },
  mounted() {
    this.Search()
  },
  methods:{
    seeLed(idx){
      this.$router.push('/seeLedger/'+this.td_dat[idx].vip_id);
    },
    to(idx){
      let para=encodeURIComponent(
        this.qs.stringify(this.td_dat[idx])
      );
      this.$router.push('/UsrEdit/'+para);
    },
    del(idx){
      if(confirm("确定要删除会员"+this.td_dat[idx].vip_name+"吗?")) {
        let param = {'vip_id': this.td_dat[idx].vip_id};
        this.axios.post(
          'http://localhost:8000/SCRM_war_exploded/vDel',
          this.qs.stringify(param)
        );
        this.Search();
      }
    },
    sexToChar:function (r,c){
      let v=r.vip_sex;
      if(v===0)
        return '男';
      if(v===1)
        return '女';
      return '保密';
    },
    Search(){
      let sPara={
        'sPara':this.$refs.SearchBox.value
      };
      this.axios.post(
        'http://localhost:8000/SCRM_war_exploded/vSearch',
        this.qs.stringify(sPara)
      ).then((result)=>{
        this.td_dat=result.data.Users;
      })
    }
  }
}
</script>

<style scoped>
.TopBox{
  width:100%;
  height: 60px;
  line-height: 60px;
}
.TipBlock{
  background: #1891B7;
  height:10px;
  width:10px;
  margin-top: 9px;
  display: inline-block;
}
.fontChar{
  color: #999999;
  font-size: 17px;
}
.myInput{
  width:50%;
  height:40px;
  font-size: 22px;
  color:#666666
}
input::-webkit-input-placeholder{
  color: #999999;
}
</style>
