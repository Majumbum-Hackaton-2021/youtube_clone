<template>
  <Header/>
  <div class="mainBody">
    <AsideMenu/>
    <div>
      <h2>Saved Videos</h2>
      <div class="row video_container">
        <div class="header__search">
          <input type="text" placeholder="Search" v-model="search" />
          <i class="material-icons">search</i>
        </div>

        <div class="wrapper">
          <div v-for="video in filteredVideos" :key="video.id">
            <VideoBox  :title="video.title"
                      :channel="video.author" :views="video.views" :edit="video.dayPosted" :thumbnail="video.thumbnail"
                      :channelThumbnail="video.channelThumbnail" :videoId="video.id"
            />
            <p class="remove" @click="removeFromSaved(video.id)">Remove from saved</p>
          </div>
        </div>

      </div>
    </div>

  </div>
</template>

<script>
import Header from "@/components/Header";
import AsideMenu from "@/components/AsideMenu";
import VideoBox from "@/components/VideoBox";
import axios from "axios";

export default {
  name: "SavedVideo",
  components: {
    VideoBox,
    Header,
    AsideMenu
  },
  data() {
    return {
      search : ''
    }
  },
  methods:{
    removeFromSaved(videoId){
      let that = this
      axios.get('http://localhost:8090/videos/removeSavedVideo?videoId='+videoId+'&userId='+this.$store.state.userInfos.id).then((response) => {
        console.log(response)
        this.$store.dispatch("fetchSavedVideos" , that.$store.state.userInfos.id)
      }).catch((error) =>{
        console.log(error)
      })
    }
  },
  mounted() {
    if(this.$store.state.user.id !== -1){
      this.$store.dispatch('getUserInfos')
    }

    if(this.$store.state.userInfos.id === undefined)
      this.$router.push("/")

    this.$store.dispatch("fetchSavedVideos" , this.$store.state.userInfos.id)
  },
  computed:{
    getVideos: function (){
      return this.$store.state.savedVideos
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
.remove{
  color: red;
  cursor: pointer;
}

.video_container{
  justify-content: left;
  max-height: 283px;
}

.wrapper{
  text-align: -webkit-center;
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