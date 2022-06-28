<template>

</template>

<script>
export default {
  name: "blank",
  data(){
    this.$router.replace({
      path:this.$route.params.src,
      name:this.$route.params.src
    })
    return{

    }
  }
}
</script>

<style scoped>

</style>
