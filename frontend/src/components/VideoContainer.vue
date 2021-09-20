<template>

  <div class="row video_container">
    <div class="header__search">
        <input type="text" placeholder="Search" v-model="search" />
        <i class="material-icons">search</i>
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
      let videos = []
      Array.from(this.getVideos).forEach( elem => {
        videos.push(elem)
      })

      return videos.filter(elem => {
        return elem.title.toLowerCase().includes(that.search.toLowerCase())
      })
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
  display: flex;
  position: absolute;
  top: 15px;
  left: 50%;
  transform: translateX(-50%);
}

input[type=text]{
  height: 30px;
}
i{
  color: #9b9b9b;
  background: white;
  cursor: pointer;
  padding: 4px;
}
</style>