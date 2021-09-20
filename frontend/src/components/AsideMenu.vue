<template>
  <div class="sidebar">
    <div class="sidebar__categories">
      <router-link to="/">
        <div class="sidebar__category">
          <i class="material-icons">home</i>
          <span>Home</span>
        </div>
      </router-link>
    </div>
    <div class="sidebar__categories">
      <router-link to="/savedVideo" v-if="isLoggedIn">
        <div class="sidebar__category" >
          <i class="material-icons">library_add_check</i>
          <span>Saved Video</span>
        </div>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "AsideMenu",
  computed: {
    isLoggedIn: function () {
      return this.$store.state.user.id !== -1
    },
  }
}
</script>

<style scoped>
a{
  text-decoration: none;
  text-underline: none;
}
</style>