<template>
  <div class="row video_container">
      <VideoBox v-for="video in getVideos" :key="video.id" :title="video.title"
      :channel="video.author" :views="video.views" :edit="video.dayPosted" :thumbnail="video.thumbnail"
                :channelThumbnail="video.channelThumbnail"
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
  overflow-y: scroll;
  justify-content: left;
}
</style>