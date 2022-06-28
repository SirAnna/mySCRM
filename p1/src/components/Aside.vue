<template>
<div>
  <div class="topBox">
    &nbsp;&nbsp;SCRM
  </div>
  <div class="asideBox">
    <div class="myLab" @mouseover="over(0)" @mouseleave="leave(0)" @mousedown="clic(0)" :style="sty[0]">
      ·会员注册{{msg[0]}}
    </div>
    <div class="myLab" @mouseover="over(1)" @mouseleave="leave(1)" @mousedown="clic(1)" :style="sty[1]">
      ·查看会员{{msg[1]}}
    </div>
    <div class="myLab" @mouseover="over(2)" @mouseleave="leave(2)" @mousedown="clic(2)" :style="sty[2]">
      ·流水录入{{msg[2]}}
    </div>
    <div class="myLab" @mouseover="over(3)" @mouseleave="leave(3)" @mousedown="clic(3)" :style="sty[3]">
      ·查询流水{{msg[3]}}
    </div>
    <div class="myLab" @mouseover="over(4)" @mouseleave="leave(4)" @mousedown="clic(4)" :style="sty[4]">
      ·积分规则{{msg[4]}}
    </div>
  </div>
</div>
</template>

<script>
export default {
  name: "Aside",
  data(){
    return{
      now:1,
      sty_ori: "background:#177CA0;" +
        "box-shadow:black 0 0 5px 1px;cursor:pointer",
      sty:['','','','',''],
      msg:['','','','',''],
      url:['/SignUp','/SeeUsr','/billLog','/seeBills','/myRules']
    }
  },
  mounted() {
    this.init();
  },
  methods:{
    init:function (){
      if(this.now>=0&&this.now<this.sty.length){
        this.$set(this.sty,this.now,'background:#1891B7');
        this.$set(this.msg,this.now,'>');
        this.$router.push(this.url[this.now]);
      }
    },
    over:function (s){
      if(this.now!==s){
        this.$set(this.sty,s,this.sty_ori);
        this.$set(this.msg,s,'>');
      }
    },
    leave:function (s){
      if(this.now!==s){
        this.$set(this.sty,s,'');
        this.$set(this.msg,s,'');
      }
    },
    clic:function (s){
      if(this.now!==s){
        this.now=s;
        this.$set(this.sty,s,'background:#1891B7');
        for(let i=0;i<this.sty.length;i++)
          if(s!==i){
            this.$set(this.sty,i,'');
            this.$set(this.msg,i,'');
          }
        this.$router.push(this.url[s]);
      }
    }
  }
}
</script>

<style scoped>
.asideBox{
  position: fixed;
  top:20px;
  left:0;
  background: #144F70;
  width: 180px;
  height: 100%;
}
.myLab{
  height:60px;
  width:100%;
  background: #144F70;
  text-align: center;
  line-height: 60px;
  font-size: 22px;
  color: aliceblue;
}
.topBox{
  position: fixed;
  top:0;
  left:0;
  height:20px;
  width:100%;
  background: #144F70;
  font-size:12px;
  color:#1891B7;
}
</style>
