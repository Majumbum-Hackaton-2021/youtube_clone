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
      <input v-model="email" class="form-row__input" type="email" placeholder="Email"/>
    </div>
    <div class="form-row" v-if="mode === 'register'">
      <input v-model="firstname" class="form-row__input" type="text" placeholder="firstname"/>
      <input v-model="lastname" class="form-row__input" type="text" placeholder="lastname"/>
      <input v-model="nickname" class="form-row__input" type="text" placeholder="nickname"/>
      <input v-model="gender" class="form-row__input" type="text" placeholder="gender"/>
    </div>

    <div class="form-row">
      <input v-model="password" class="form-row__input" type="password" placeholder="password"/>
    </div>

    <div class="form-row">
      <button class="button" @click="login" :class="{'button--disabled' : !validatedFields}" v-if="mode === 'login'">
        <span>Login</span>
      </button>
      <button class="button" @click="createAccount" :class="{'button--disabled' : !validatedFields}" v-else>
      
        <span>Register</span>
      </button>
    </div>
  </div>


</template>

<script>
import Header from "../components/Header";
export default {
  name : "Authentification",
  components: {
    Header,
  }, 

  data: function () {
    return {
      mode: 'login',
      email: '',
      firstname: '',
      lastname: '',
      nickname: '',
      gender : '',
      password: '',
    }
  },
  computed:{
    validatedFields: function () {
      if(this.mode === 'register'){
        return !!(this.email !== '' && this.firstname !== '' && this.lastname !== '' && this.nickname && this.gender !== '' && this.password !== '');
      } else {
        return this.email !== '' && this.password !== '';
      }
    },
   
  },
  
  
  methods: {
    switchToCreateAccount() {
      this.mode = 'register'
    },
    switchToLogin() {
      this.mode = 'login'
    },
  }
}   
</script>

<style scoped>
.form-row {
  display: flex;
  margin: 16px 0px;
  gap:16px;
  flex-wrap: wrap;
}
.form-row__input {
  padding:8px;
  border: none;
  border-radius: 8px;
  background:#f2f2f2;
  font-weight: 500;
  font-size: 16px;
  flex:1;
  min-width: 100px;
  color: black;
}
.form-row__input::placeholder {
  color:#aaaaaa;
}
.error{
  color: red;
}
.card {
  position: relative;
  left: 50%;
  bottom: 50%;
  transform: translate(-50%, 50%);
  max-width: 100%;
  width: 540px;
  background:white;
  border-radius: 16px;
  padding:32px;
}
.card__title {
  text-align:center;
  font-weight: 800;
}
.card__subtitle {
  text-align: center;
  color:#666;
  font-weight: 500;
}
.button {
  background: #2196F3;
  color:white;
  border-radius: 8px;
  font-weight: 800;
  font-size: 15px;
  border: none;
  width: 100%;
  padding: 16px;
  transition: .4s background-color;
}
.card__action {
  color:#2196F3;
  text-decoration: underline;
}
.card__action:hover {
  cursor:pointer;
}
.button:hover {
  cursor:pointer;
  background: #1976D2;
}
.button--disabled {
  background:#cecece;
  color:#ececec
}
.button--disabled:hover {
  cursor:not-allowed;
  background:#cecece;
}
</style>