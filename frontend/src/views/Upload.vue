<template>
  <Header/>
  <div class="mainBody">
    <AsideMenu/>
    <div class="main-body">
      <h1 class="title">Upload new Video</h1>

      <div class="row gutters-sm">
          <div class="card mb-9">
            <div class="card-body">
              <div class="row">
                <div class="col-sm-3">
                  <h6 class="mb-0">Title</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  <input type="text" class="form-control form-row__input" name="title"  v-model="title" />
                </div>
              </div>
              <hr>
              <div class="row">
                <div class="col-sm-3">
                  <h6 class="mb-0">Description</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  <input type="text" class="form-control form-row__input" name="description"  v-model="description" />
                </div>
              </div>
              <hr>
              <div class="row">
                <div class="col-sm-3">
                  <h6 class="mb-0">Upload Video</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  <input class="form-control form-row__input" ref="uploadVideoBtn" type="file" id="video" @change="onVideoSelected"/>
                </div>
              </div>
              <hr>

              <div class="row">
                <div class="col-sm-3">
                  <h6 class="mb-0">Upload Thumbnail</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  <input class="form-control form-row__input" ref="uploadThumbBtn" type="file" id="thumbnail-img" />
                </div>
              </div>
              <hr>
              <div class="row">
                <div class="col-sm-12">

                  <button class="btn btn-info"  :class="{'button--disabled' : !validatedFields}">
                    <span style="color: white" @click="submitAll">upload</span>
                  </button>
                </div>
              </div>
            </div>
          </div>
      </div>
    </div>
  </div>

</template>

<script>
import Header from "@/components/Header";
import AsideMenu from "@/components/AsideMenu";
import axios from "axios";
export default {
  name: "Upload",
  components: {AsideMenu, Header},
  data(){
    return {
      title: '',
      description: '',
      files: '',
    }
  },
  methods:{
    submitAll(){
      let file = this.$refs.uploadVideoBtn.files[0]
      let formData = new FormData()
      formData.append('file', file)
      console.log(file)
      let videoName = file.name
      axios.post("http://localhost:8090/upload/",formData).then(response =>{
        console.log(response)
      })

      file = this.$refs.uploadThumbBtn.files[0]
      formData = new FormData()
      formData.append('file', file)
      console.log(file)
      let thumbName = file.name
      axios.post("http://localhost:8090/upload/",formData).then(response =>{
        console.log(response)
      })
      let video = {
        title : this.title,
        author: this.$store.state.userInfos.nickname,
        description: this.description,
        thumbnail: thumbName,
        channelThumbnail: this.$store.state.userInfos.image,
        videoLink: videoName
      }
      axios.post("http://localhost:8090/videos/addVideo",video).then(response =>{
        console.log(response)
      })
    }
  },
  computed: {
    userInfos : function (){
      return this.$store.state.userInfos
    },
    validatedFields: function () {
      return !!(this.title !== '' && this.description !== '');
    },
  }
}
</script>

<style scoped>

.main-body {
  padding: 25px;
  margin-top: 50px;
  position: relative;
  width: 100%;
}

.btn-danger{
  margin-left: 10px;
}
.card {
  width: 70%;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  box-shadow: 0 1px 3px 0 rgba(0,0,0,.1), 0 1px 2px 0 rgba(0,0,0,.06);
  display: flex;
  flex-direction: column;
  min-width: 0;
  word-wrap: break-word;
  background-color: #fff;
  background-clip: border-box;
  border: 0 solid rgba(0,0,0,.125);
  border-radius: .25rem;
}

.card-body {
  flex: 1 1 auto;
  min-height: 1px;
  padding: 1rem;
}

.gutters-sm {
  margin-right: -8px;
  margin-left: -8px;
}

.gutters-sm>.col, .gutters-sm>[class*=col-] {
  padding-right: 8px;
  padding-left: 8px;
}
.mb-3, .my-3 {
  margin-bottom: 1rem!important;
}

.bg-gray-300 {
  background-color: #e2e8f0;
}
.h-100 {
  height: 100%!important;
}
.shadow-none {
  box-shadow: none!important;
}

.button--disabled{
  cursor: not-allowed;
  background: #cecece;
}
.title{
  text-align: center;
  font-size: 32px;
  font-weight: 500;
}
</style>