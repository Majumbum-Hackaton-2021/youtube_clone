<template>
  <div class="container col-12 col-md-6 col-lg-4" style="margin: 0">
    <span v-if="isModerator" class="material-icons close" @click="deleteVideo">close</span>
    <router-link :to="{name: 'Watch', params: { id: videoId}}">
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
            {{channel}}
            <span>{{views}} Views • {{edit}}</span>
          </div>
        </div>
      </div>
    </router-link>
  </div>
</template>

<script>

export default {
  name: "VideoBox",
  props: {
    thumbnail: String,
    videoId: Number,
    title: String,
    channel: String,
    channelThumbnail: String,
    edit: String,
    views: String
  },
  computed: {
    isModerator: function () {
      return this.$store.state.userInfos.moderator === true
    },
  },
  methods:{
    deleteVideo(){
      if(confirm("Do you really want to delete this video ?"))
        this.$store.dispatch('deleteVideo', this.videoId)
    }
  },

}
</script>

<style scoped>

.video{
  padding: 15px;
}
a {
  text-decoration: none;
  text-underline: none;
}

.video__thumbnail {
  width: 100%;
  max-height: 179px;
  position: relative;
  overflow: hidden;
}

.video__thumbnail img {
  object-fit: fill;
  height: 100%;
  width: 100%;
}

.close{
  z-index: 1;
  position: absolute;
  left: 0;
  color: red;
  cursor: pointer;
}
</style>