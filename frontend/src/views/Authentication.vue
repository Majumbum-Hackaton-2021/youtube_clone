<template>
<Header/>
   <div class="card">
     <h1 class="card__title" v-if="mode === 'login'">Login</h1>
     <h1 class="card__title" v-else>Register</h1>
     <p class="card__subtitle" v-if="mode === 'login'">Don't you have an account yet?
       <span class="card__action" @click="switchToCreateAccount()">Create an account</span></p>
     <p class="card__subtitle" v-else>Do you already have an account?
       <span class="card__action" @click="switchToLogin()">Login</span></p>

     <div class="form-row">
           <button class="icon"><i class="fa fa-envelope" aria-hidden="true"></i></button><input v-model="email" class="form-row__input" type="email" placeholder="E-mail"/>
     </div>
     
     <div class="form-row" v-if="mode === 'register'">
        <button class="icon"><i class="fa fa-user" aria-hidden="true"></i></button>
        <input v-model="firstname" class="form-row__input" type="text" placeholder="firstname"/>

        <button class="icon"><i class="fas fa-user"></i></button>
        <input v-model="lastname" class="form-row__input" type="text" placeholder="lastname"/>

        <button class="icon"><i class="fa fa-user" aria-hidden="true"></i></button>
        <input v-model="nickname" class="form-row__input" type="text" placeholder="nickname"/>

       <button class="icon"><i class="fas fa-venus-mars"></i></button>
       <select v-model="gender" class="form-row__input1">
       <option value="" disabled selected hidden>gender</option>
       <option value="female">female</option>
       <option value="male">male</option>
     </select>
     </div>
     <div class="form-row">
        <button class="icon"><i class="fa fa-key" aria-hidden="true"></i></button><input v-model="password" class="form-row__input" type="password" placeholder="password"/>
     </div>
     <hr>
     <div class="form-row error" v-if="mode === 'login' && updateStatus === 'error_login'">
       Invalid email address and/or password
     </div>
     <div class="form-row error" v-if="mode === 'register' && updateStatus === 'error_create'">
       Email address already in use
     </div>

     <div class="form-row">
       <button class="button auth-btn" @click="login" :class="{'button--disabled' : !validatedFields}" v-if="mode === 'login'">
         <span v-if="updateStatus === 'loading'">Connection in progress...</span>
         <span v-else>Login</span>
       </button>
       <button class="button auth-btn" @click="createAccount" :class="{'button--disabled' : !validatedFields}" v-else>
         <span v-if="updateStatus === 'loading'">Creation in progress...</span>
         <span v-else>Register</span>
       </button>
     </div>
   </div>
</template>

<script>
import Header from "../components/Header";
export default {
  name : "Authentication",
  components: {Header},
  data: function () {
    return {
      mode: 'login',
      email: '',
      firstname: '',
      lastname: '',
      nickname: '',
      gender: '',
      password: '',
    }
  },
  computed:{
    validatedFields: function () {
      if(this.mode === 'register'){
        return !!(this.email !== '' && this.firstname !== '' && this.lastname !== '' && this.nickname && this.password !== '' && this.gender !== '');
      } else {
        return this.email !== '' && this.password !== '';
      }
    },
    updateStatus: function (){
      return this.$store.state.status
    },
  },
  mounted() {
    if(this.$store.state.user.id !== -1){
      this.$router.push('/userpage')
    }
  },
  methods: {
    switchToCreateAccount() {
      this.mode = 'register'
    },
    switchToLogin() {
      this.mode = 'login'
    },
    createAccount(){
      const self = this;
      this.$store.dispatch('createAccount' , {
        email : this.email,
        firstname : this.firstname,
        lastname : this.lastname,
        nickname : this.nickname,
        gender : this.gender,
        password : this.password
      }).then( (response) => {
        console.log(response)
        self.login()
      }).catch( (error) => {
        console.log(error)
      })
    },
    login(){
      const self = this;
      this.$store.dispatch('login' , {
        email : this.email,
        password : this.password
      }).then( (response) => {
        console.log(response)
        self.$router.push('userpage')
      }).catch( (error) => {
        console.log(error)
      })
    },
  }
}   
</script>

<style scoped>
.button span{
  color: white;
  font-size: 20px;
}

.auth-btn{
  background: #FF0000;
}

.form-row__input
{
  padding:8px;
  border: none;
  background:#f2f2f2;
  font-weight: 500;
  font-size: 16px;
  min-width: 100px;
}

.form-row__input::placeholder 
{
  color:#aaaaaa;
  padding-left: 4px;
}

select{
  padding-left: 6px;

}
.error
{
  color: red;
}

.card {
  border: 1px solid #fafafa;
  position: relative;
  left: 50%;
  bottom: 10%;
  transform: translate(-50%, 10%);
  max-width: 100%;
  width: 580px;
  border-radius: 16px;
  padding:32px;
  box-shadow: 0px 2px 8px rgba(0,0,0,0.1);
  background-color: #fafafa;
   
}

.card__title {
  text-align:center;
  font-weight: bold;
  font-size:40px;
}

.card__subtitle {
  text-align: center;
  color:#666;
  margin-bottom:15px;
}

 hr
{
  color:solid gainsboro ;
  margin-bottom:15px;
  margin-top:15px;
}

.button {
  outline: none;
  color:white;
  border-radius: 8px;
  font-weight: 800;
  font-size: 15px;
  border: none;
  width: 100%;
  padding: 16px;
  transition: .4s 
}

.card__action 
{
  color:#2196F3;
}


.card__action:hover {
  cursor:pointer;
  text-decoration: underline;
}

.button--disabled {
  color:#ececec;
  font-size:14px;
  background: #aaaaaa !important;
}

input{
  height: 36px;
  width:90%;
  border-top-right-radius: 20px;
  border-bottom-right-radius:20px;
  border:none;
  margin: 8px 0px;

}

.form-row__input1{
  width:90%;
  border-top-right-radius: 20px;
  border-bottom-right-radius:20px;
  border:none;
  background:#f2f2f2;
  margin: 8px 0px;
  height: 40px;
}

.form-row__input1:focus{
  outline-color:#FF0000;

}

input:focus{
  outline-color:#FF0000;
}

.icon 
{
 outline: none;
 font-weight: 500;
 font-size: 16px;
 height: 36px;
 width: 10%;
 border:none;
 color:#f2f2f2;
 border-top-left-radius:20px;
 border-bottom-left-radius:20px;
 margin: 8px 0px;
 background-color:#aaaaaa;
}

.icon:hover
{
 background-color:#aaaaaa70;
}



</style>