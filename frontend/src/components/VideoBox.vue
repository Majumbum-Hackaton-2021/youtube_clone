<template>
  <div class="video">
    <div class="video__thumbnail">
      <img :src="'http://localhost:8090/videos/image?imageName='+thumbnail" alt="" />
    </div>
    <div class="video__details">
      <div class="author">
        <img :src="channelThumbnail" alt="" />
      </div>
      <div class="title">
        <h3>
          {{title}}
        </h3>
        <a :href="'watch?id='+id">{{channel}}</a>
        <span>{{views}} Views • {{edit }}</span>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "VideoBox",
  props: {
    thumbnail: String,
    id: String,
    title: String,
    channel: String,
    channelThumbnail: String,
    edit: String,
    views: String
  },

}
</script>

<style scoped>

</style>