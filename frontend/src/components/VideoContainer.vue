<template>

  <div class="row video_container">
    <div class="header__search">
        <input type="text" placeholder="Search" v-model="search"/>
        <button><i class="material-icons">search</i></button>
    </div>

    <VideoBox v-for="video in filteredVideos" :key="video.id" :title="video.title"
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
  data() {
    return {
      search : ''
    }
  },
  created() {
    this.$store.dispatch("fetchVideos")
  },
  computed:{
    getVideos: function (){
      return this.$store.state.videos
    },
    filteredVideos(){
      let that = this
      let videos = this.getVideos.filter(elem => {
        return elem.title.toLowerCase().includes(that.search.toLowerCase())
      })

      return videos
    },
  }
}
</script>

<style scoped>
.video_container{
  justify-content: left;
  max-height: 283px;
}

.header__search{
  position: absolute;
  top: 15px;
  left: 50%;
  transform: translateX(-50%);
}
</style>