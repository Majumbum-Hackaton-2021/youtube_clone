<template>
<Header/>
  <div class="mainBody">
    <AsideMenu/>
    <div class="video-column">

      <video controls width="600" height="400" :src="link">
        Your browser does not support the video tag.
      </video>

      <h3 class="video-title">{{video.title}}</h3>

      <div class="video-info-main">
        <h4 class="view-counter">{{video.views}} Views</h4>

        <div class="video-info-right">
          <div class="likes-section">
            <i class="material-icons" id="like_btn" @click="updateLike('like_btn')">thumb_up</i>
            <span class="likes-counter">{{likes}}</span>
          </div>

          <div class="share-section">
            <i class="fas fa-plus"></i>
            <span>Save</span>
          </div>
        </div>
      </div>

      <div class="description-box">
        <img :src="video.channelThumbnail" alt="Profile Picture" class="profile-pic">

        <div class="description-div">
          <h5>{{video.author}}</h5>
          <p>Published on {{video.dayPosted}}</p>
          <p>{{video.description}}</p>
        </div>
      </div>

      <h5 style="font-weight: normal">{{commentsNumber}} Comments</h5>


      <div style="display: flex" v-if="isLoggedIn">
        <div class="form__group field">
          <input type="text" class="form__field" placeholder="Your Comment" name="comment" id='comment' v-model="comment" required />
          <label for="comment" class="form__label">Comment</label>
        </div>

        <div class="comment_btn_wrapper">
          <button class="btn btn-info" :class="{'button--disabled' : !validatedFields}" @click="addComment" :disabled="!validatedFields">Comment</button>
        </div>
      </div>

      <div class="container" style="margin: 0; padding: 0">
        <div class="dialogbox" v-for="comment in video.comments" :key="comment.id">
          <div class="body">
            <span class="tip tip-up"></span>
            <div class="message">
              <span v-if="isModerator || isMyComment(comment)" class="material-icons close" @click="deleteComment(comment.id)">close</span>
              <div class="image_container">
                <img :src="comment.authorImage">
                <span>{{comment.authorName}}</span>
              </div>
              <div class="text-container">
                <span>{{comment.comment}}</span>
              </div>
            </div>
          </div>
        </div>

        <br><br><br><br>
      </div>
    </div>
  </div>

</template>

<script>
import Header from "@/components/Header";
import AsideMenu from "@/components/AsideMenu";
import axios from "axios";
export default {
  name: "Watch",
  components: {AsideMenu, Header},
  props: {
    id : String,
  },
  data: () => ({
    video: Object,
    link : String,
    commentsNumber: Number,
    likes :0,
    comment : ''
  }),

  computed:{
    validatedFields: function () {
        return this.comment !== '';
      },
    isLoggedIn: function () {
      return this.$store.state.user.id !== -1
    },
    isModerator: function () {
      return this.$store.state.userInfos.moderator === true
    },
  },
  mounted() {
    let that = this

    if(this.isLoggedIn)
      this.$store.dispatch('getUserInfos')

    axios.get('http://localhost:8090/videos/view?id='+this.id).then((response) => {
      console.log(response)
    }).catch((error) =>{
      console.log(error)
    })

    axios.get('http://localhost:8090/videos/unique?id='+this.id).then((response) => {
      that.video = response.data
      that.commentsNumber = response.data.comments.length
      that.likes = response.data.likes
      that.link = 'http://localhost:8090/videos/videoFile?videoName='+response.data.videoLink
    }).catch((error) =>{
      console.log(error)
    })
  },

  methods: {
    isMyComment(comment){
      return this.$store.state.userInfos.nickname === comment.authorName
    },
    updateLike(like_btn){
      axios.get('http://localhost:8090/videos/like?id='+this.id).then((response) => {
        console.log(response)
      }).catch((error) =>{
        console.log(error)
      })
      let elem = document.getElementById(like_btn)
      elem.classList.add("liked")
      this.likes ++
    },
    addComment(){
      let that = this
      let authorName = this.$store.state.userInfos.nickname
      let authorImage = this.$store.state.userInfos.image

      axios.post('http://localhost:8090/videos/addComment', {
        authorName : authorName ,
        videoId : that.id,
        comment: that.comment,
        authorImage: authorImage
      }).then((response) => {
        console.log(response)

        axios.get('http://localhost:8090/videos/unique?id='+this.id).then((response) => {
          that.video = response.data
          that.commentsNumber = response.data.comments.length
          that.likes = response.data.likes
          that.link = 'http://localhost:8090/videos/videoFile?videoName='+response.data.videoLink
        }).catch((error) =>{
          console.log(error)
        })

      }).catch((error) =>{
        console.log(error)
      })

      this.comment = ''
    },
    deleteComment(id){
      let that = this
      if(confirm("Do you really want to delete this comment ? ")){
        axios.get('http://localhost:8090/videos/deleteComment?id='+id).then((response) => {
          that.video = response.data
          that.commentsNumber = response.data.comments.length
          that.likes = response.data.likes
          that.link = 'http://localhost:8090/videos/videoFile?videoName='+response.data.videoLink
        }).catch((error) =>{
          console.log(error)
        })
      }
    },
  }
}
</script>

<style scoped>
/deep/
.liked{
  color: #2196F3;
}

.close{
  right: 0;
  color: red;
  position: absolute;
  cursor: pointer;
  z-index: 1;
}
.mainBody{
  margin-bottom: 220px;
}
.video-column {
  margin-left: 30px;
  padding: 0 100px 100px;
  width: -webkit-fill-available;
  margin-bottom: 100px;

}

.video-title{
  font-weight: normal;
  line-height: 0.9rem;
  margin-top: 20px;
  margin-bottom: 20px;
}
.video-player {
  min-height:  315px;
  min-width: 600px;
  background: transparent;
  z-index: -1;
  height: 0;
  overflow: hidden;
  position: relative;
  padding-bottom: 56.25%;
  width: 100%;
}

.video-player video
{
  min-width: 100%;
  min-height: 100%;
  object-fit: fill;
  z-index: 2;
}

.video-player iframe {
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  position: absolute;
}

.video-info-main {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;

  color: #666;
  border-bottom: 1px solid #eee;
}

.video-info-right {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  margin-top: -15px;
}


.likes-section {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  margin-right: 25px;
}

.likes-section i {
  margin-right: 10px;
}

.likes-section p {
  font-size: 0.8rem;
  font-weight: normal;
}

.likes-section p:first-of-type {
  margin-right: 22px;
}

.view-counter {
  font-size: 1rem;
  font-weight: normal;
}

.share-section {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}

.share-section i {
  margin-right: 10px;
}

.share-section p {
  margin-right: 20px;
}

.profile-pic {
  border-radius: 100%;
  max-height: 60%;
  margin: 0 15px;
}

.description-box {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: flex-start;
  padding-right: 25%;
  border-bottom: 1px solid #eee;
}

.description-box img {
  margin-top: 15px;
  margin-left: 0;
  height: 48px;
}

.description-box h5 {
  margin-bottom: 0;
}

.description-box p:first-of-type {
  margin-top: 0;
  font-size: 0.8rem;
  font-weight: normal;
}

.description-box p:last-of-type {
  margin-top: 0;
  font-size: 0.9rem;
  font-weight: normal;
}

.description-div{
  margin-top: 18px;
}

/* comment */
.form__group {
  position: relative;
  padding: 15px 0 0;
  margin-top: 10px;
  width: 50%;
}
.form__field {
  font-family: inherit;
  width: 100%;
  border: 0;
  border-bottom: 2px solid #9b9b9b;
  outline: 0;
  font-size: 1.3rem;
  color: grey;
  padding: 7px 0;
  background: transparent;
  transition: border-color 0.2s;
}
.form__field::placeholder {
  color: transparent;
}
.form__field:placeholder-shown ~ .form__label {
  font-size: 1.3rem;
  cursor: text;
  top: 20px;
}
.form__label {
  position: absolute;
  top: 0;
  display: block;
  transition: 0.2s;
  font-size: 1rem;
  color: #9b9b9b;
}
.form__field:focus {
  padding-bottom: 6px;
  font-weight: 700;
  border-width: 3px;
  border-image: linear-gradient(to right, #11998e, #bbb9bd);
  border-image-slice: 1;
}
.form__field:focus ~ .form__label {
  position: absolute;
  top: 0;
  display: block;
  transition: 0.2s;
  font-size: 1rem;
  color: #11998e;
  font-weight: 700;
}
/* reset input */
.form__field:required, .form__field:invalid {
  box-shadow: none;
}

.comment_btn_wrapper{
  position: relative;
}
.btn-info{
  position: absolute;
  bottom: 0;
  margin-left: 25px;
}

/* Comment */

.tip {
  width: 0px;
  height: 0px;
  position: absolute;
  background: transparent;
  border: 10px solid #ccc;
}

.tip-up {
  top: -25px; /* Same as body margin top + border */
  left: 10px;
  border-right-color: transparent;
  border-left-color: transparent;
  border-top-color: transparent;
}

.tip-down {
  bottom: -25px;
  left: 10px;
  border-right-color: transparent;
  border-left-color: transparent;
  border-bottom-color: transparent;
}

.tip-left {
  top: 10px;
  left: -25px;
  border-top-color: transparent;
  border-left-color: transparent;
  border-bottom-color: transparent;
}

.tip-right {
  top: 10px;
  right: -25px;
  border-top-color: transparent;
  border-right-color: transparent;
  border-bottom-color: transparent;
}

.dialogbox .body {
  position: relative;
  max-width: 800px;
  height: auto;
  margin-top: 20px;
  padding: 5px;
  background-color: #DADADA;
  border-radius: 3px;
  border: 5px solid #ccc;
}

.message {
  display: flex;
  min-height: 30px;
  border-radius: 3px;
  font-family: Arial;
  font-size: 14px;
  line-height: 1.5;
  color: #797979;
  position: relative;
}

.message img{
  width: 60px;
  border-radius: 40px;
  border: 1px solid gray;
  background: white;
  margin-left: 5px;
  margin-right: 13px;
}

.text-container{
  width: 100%;
  position: relative;
}

.text-container span{
  position: absolute;
  top: 30%;
}
.image_container{
  display: flex;
  flex-direction: column;
  position: relative;
}

.image_container span{
  display: block;
  margin-left: 11px;
  margin-top: 5px;
  font-size: 18px;
}
@media only screen and (max-width: 1000px) {

  .video-column {
    width: 90%;
    margin-right: 4.8%;
    margin-left: 4.8%;
  }
}

.button--disabled {
  background:#cecece;
  color:#ececec
}
.button--disabled:hover {
  cursor:not-allowed;
  background:#cecece;
}

.comment--disabled:hover{
  cursor:not-allowed;
}

</style>