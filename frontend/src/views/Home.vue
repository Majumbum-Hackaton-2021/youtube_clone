<template>
  <Header/>
  <div class="mainBody">
    <AsideMenu/>
    <VideoContainer/>
  </div>
</template>

<script>

import AsideMenu from "../components/AsideMenu";
import VideoContainer from "../components/VideoContainer";
import Header from "../components/Header";
export default {
  name: 'Home',
  components: {
    Header,
    VideoContainer,
    AsideMenu
  }
}
</script>
