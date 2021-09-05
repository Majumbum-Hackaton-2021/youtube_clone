<template>
  <div class="video">
    <div class="video__thumbnail">
      <img :src="image" alt="" />
    </div>
    <div class="video__details">
      <div class="author">
        <img src="http://aninex.com/images/srvc/web_de_icon.png" alt="" />
      </div>
      <div class="title">
        <h3>
          {{title}}
        </h3>
        <a :href="link">{{channel}}</a>
        <span>{{views}} Views • {{edit }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "VideoBox",
  props: {
    image: String,
    link: String,
    title: String,
    channel: String,
    edit: String,
    views: String

  },
}
</script>

<style scoped>

</style>