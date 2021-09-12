<template>
  <div class="row video_container">
      <VideoBox v-for="video in getVideos" :key="video.id" :title="video.title"
      :channel="video.author" :views="video.views" :edit="video.dayPosted" :thumbnail="video.thumbnail"
                :channelThumbnail="video.channelThumbnail" :videoId="video.id"
      />
  </div>
</template>

<script>
import VideoBox from "./VideoBox";
export default {
  name: "VideoContainer",
  components: {VideoBox},
  created() {
    this.$store.dispatch("fetchVideos")
  },
  computed:{
    getVideos: function (){
      return this.$store.state.videos
    },
  }
}
</script>

<style scoped>
.video_container{
  justify-content: left;
  max-height: 283px;
}
</style>