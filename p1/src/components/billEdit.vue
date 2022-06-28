<template>
<bill-log
  :bill_id="info.bill_id"
  :d_id="info.vip_id"
  :d_trans="info.transaction"
  :d_pay="info.pay"
  :d_com="info.commodity"
  :d_trans_date="info.trans_date.split('-')"
  :d_typ="info.pay_type"
/>
</template>

<script>
import billLog from "./billLog";
export default {
  name: "billEdit",
  components:{billLog},
  data(){
    return{
      qs:require("qs"),
      info:[]
    }
  },
  mounted(){
    let info=this.$route.params.info;
    this.info=this.qs.parse(decodeURIComponent(info));
  }
}
</script>

<style scoped>

</style>
