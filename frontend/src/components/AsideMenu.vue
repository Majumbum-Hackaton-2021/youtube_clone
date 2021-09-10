<template>
  <div class="sidebar">
    <div class="sidebar__categories">
      <router-link to="/">
        <div class="sidebar__category">
          <i class="material-icons">home</i>
          <span>Home</span>
        </div>
      </router-link>
      <div class="sidebar__category">
        <i class="material-icons">local_fire_department</i>
        <span>Trending</span>
      </div>
      <div class="sidebar__category">
        <i class="material-icons">subscriptions</i>
        <span>Subcriptions</span>
      </div>
    </div>
    <hr />
    <div class="sidebar__categories">
      <div class="sidebar__category">
        <i class="material-icons">library_add_check</i>
        <span>Library</span>
      </div>
      <div class="sidebar__category">
        <i class="material-icons">history</i>
        <span>History</span>
      </div>
      <div class="sidebar__category">
        <i class="material-icons">play_arrow</i>
        <span>Your Videos</span>
      </div>
      <div class="sidebar__category">
        <i class="material-icons">watch_later</i>
        <span>Watch Later</span>
      </div>
      <div class="sidebar__category">
        <i class="material-icons">thumb_up</i>
        <span>Liked Videos</span>
      </div>
    </div>
    <hr />
  </div>
</template>

<script>
export default {
  name: "AsideMenu"
}
</script>

<style scoped>
a{
  text-decoration: none;
  text-underline: none;
}
</style>