<template>
<div class="MainBox">
<slot></slot>
</div>
</template>

<script>
export default {
  name: "MainBox",
}
</script>

<style scoped>
.MainBox{
  height:calc(100% - 20px);
  width: calc(100% - 180px);
  position: fixed;
  top:20px;
  left: 180px;
}
</style>
