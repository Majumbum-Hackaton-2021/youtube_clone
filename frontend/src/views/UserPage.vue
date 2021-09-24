<template>
  <Header/>
  <h1 class="title">Profile Page</h1>
    <div class="main-body">
      <div class="row gutters-sm">
        <div class="card">
          <div class="card-body">
            <div class="d-flex flex-column align-items-center text-center">
              <img :src="userInfos.image" alt="Admin" class="rounded-circle" width="150">
              <div class="mt-3">
                <h4>{{userInfos.firstname}} {{userInfos.lastname}}</h4>
                <p class="text-secondary mb-1">Allias : {{userInfos.nickname}}</p>
                <p class="text-secondary mb-1">Gender : {{userInfos.gender}}</p>
                <p class="text-muted font-size-sm">
                  <span :key="index2" v-for="(auth, index2) in userInfos.grantedAuthorities">
                    {{auth}} -
                  </span>
                  *
                </p>
                <button class="btn btn-primary"  v-if="mode!== 'edit'" @click="switchToEdit">Edit</button>
                <button class="btn btn-danger"  v-if="mode!== 'edit'" @click="logout">Logout</button>
              </div>
            </div>
          </div>
        </div>

        <div v-if="mode=== 'edit'">
          <div class="card mb-3">
            <div class="card-body">
              <div class="row">
                <div class="col-sm-3">
                  <h6 class="mb-0">Firstname</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  <input type="text" class="form-control form-row__input" name="firstname" :placeholder="userInfos.firstname" v-model="firstname" />
                </div>
              </div>
              <hr>
              <div class="row">
                <div class="col-sm-3">
                  <h6 class="mb-0">lastname</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  <input type="text" class="form-control form-row__input" name="lastname" :placeholder="userInfos.lastname" v-model="lastname" />
                </div>
              </div>
              <hr>
              <div class="row">
                <div class="col-sm-3">
                  <h6 class="mb-0">Nickname</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  <input type="text" class="form-control form-row__input" name="nickname" :placeholder="userInfos.nickname" v-model="nickname" />
                </div>
              </div>
              <hr>
              <div class="row">
                <div class="col-sm-3">
                  <h6 class="mb-0">Gender</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                   <select class="form-control form-row__input" v-model="gender" name="gender">
                          <option value="" disabled selected hidden>gender</option>
                          <option value="female">female</option>
                          <option value="male">male</option>
                    </select>
                </div>
              </div>
              <hr>
              <div class="row">
                <div class="col-sm-3">
                  <h6 class="mb-0">password</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  <input type="password" class="form-control form-row__input" placeholder="password" name="password" v-model="password"/>
                </div>
              </div>
              <hr>
              <div class="row">
                <div class="col-sm-3">
                  <h6 class="mb-0">New password</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  <input type="password" class="form-control form-row__input" placeholder="new password" name="password" v-model="newPassword"/>
                </div>
              </div>
              <hr>
              <div class="row">
                <div class="col-sm-12">
                  <div class="form-row error" style="color: red" v-if="updateStatus === 'edit-error'">
                    Error during Edit , check if all parameters are correct
                  </div>

                  <button class="btn btn-info"  @click="editProfile" :class="{'button--disabled' : !validatedFields}">
                    <span v-if="updateStatus === 'loading'" style="color: white">Edit in progress...</span>
                    <span v-else style="color: white">Edit</span>
                  </button>

                  <button class="btn btn-danger" @click="switchToProfile">Cancel</button>
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
export default {
  name: "UserPage",
  data(){
    return {
      mode : 'profile',
      firstname : '',
      lastname: '',
      nickname: '',
      password: '',
      gender: '',
      newPassword: '',
    }
  },
  components: {Header},
  mounted() {
    if(this.$store.state.user.id !== -1){
      this.$store.dispatch('getUserInfos')
    }
  },
  methods:{
    logout(){
      this.$store.commit('logout');
      this.$router.push("/");
    },
    switchToProfile() {
      this.mode = 'profile'
    },
    switchToEdit() {
      this.mode = 'edit'
    },
    editProfile(){

      console.log(this.gender[0])
      if(this.validatedFields){
        this.$store.dispatch('editProfile' , {
          id : this.userInfos.id,
          firstname : this.firstname,
          lastname : this.lastname,
          nickname : this.nickname,
          gender : this.gender,
          password : this.password,
          newPassword : this.newPassword
        }).then( (response) => {
          console.log(response)
          this.firstname = ''
          this.lastname = ''
          this.nickname = ''
          this.password = ''
          this.gender = ''
          this.newPassword = ''
          this.mode = 'profile'
        }).catch( (error) => {
          console.log(error)
        })
      }
    },
  },
  computed: {
    userInfos : function (){
      return this.$store.state.userInfos
    },
    isLoggedIn: function () {
      return this.$store.state.user.id !== -1
    },
    isAdmin: function () {
      return this.$store.state.userInfos.admin === true
    },
    updateStatus: function (){
      return this.$store.state.status
    },
    validatedFields: function () {
      return !!(this.firstname !== '' && this.lastname !== '' && this.nickname && this.password !== '' && this.newPassword !== '');
    },
  }
}
</script>

<style scoped>

.main-body {
  padding: 25px;
  margin-top: 50px;
  position: relative;
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
select{
  width:100%;
  height: 40px;
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