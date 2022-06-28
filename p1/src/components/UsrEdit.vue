<template>
<v-info
  :vip_id="info.vip_id"
  :d_name="info.vip_name"
  :d_addr="info.vip_address"
  :d_birth="info.vip_birth.split('-')"
  :d_sex="info.vip_sex"
  :d_phone="info.vip_phone_number"
  :d_email="info.vip_email"
  :d_car-lic="info.vip_car_license"
  :post-url="'http://localhost:8000/SCRM_war_exploded/VIPUpd'"
/>
</template>

<script>
import VInfo from "./vInfo";
export default {
  name: "UsrEdit",
  components: {VInfo},
  data(){
    return{
      qs:require("qs"),
      info:[]
    }
  },
  mounted() {
    let info_=this.$route.params.info;
    this.info=this.qs.parse(decodeURIComponent(info_));
  }
}
</script>

<style scoped>

</style>
