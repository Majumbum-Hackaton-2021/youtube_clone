<template>
  <div class="container col-12 col-md-6 col-lg-4" style="margin: 0">
    <router-link to="watch">
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
            <span>{{views}} Views • {{edit }}</span>
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

.video{
  padding: 15px;
}
a {
  text-decoration: none;
  text-underline: none;
}

.video__thumbnail {
  width: 100%;
}

.video__thumbnail img {
  object-fit: cover;
  height: 100%;
  width: 100%;
}
</style>