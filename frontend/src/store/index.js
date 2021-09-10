import { createStore } from 'vuex'
const axios = require('axios')
const instance = axios.create({
  baseURL: "http://localhost:8090"
})

let user = localStorage.getItem('user')
if(!user){
  user = {
    id: -1,
    token: "",
  }
}else {
  try{
    user = JSON.parse(user)
    instance.defaults.headers.common['Authorization'] = user.token
  }catch(ex){
    user = {
      id: -1,
      token: "",
    }
  }
}
const store = createStore({
  state: {
    status: '',
    user: user,
    userInfos:{
    },
    users: [],
    article: {},
    articles: []
  },
  mutations: {
    setStatus: (state , status) => {
      state.status = status
    },
    logUser: (state, user) =>{
      instance.defaults.headers.common['Authorization'] = user.token
      localStorage.setItem('user', JSON.stringify(user))
      state.user = user
    },
    userInfos: (state , userInfos) => {
      state.userInfos = userInfos
      if(userInfos.grantedAuthorities !== undefined){

        state.userInfos.grantedAuthorities.forEach((e)=>{
          if(e === 'ADMIN')
            userInfos["admin"]=true
          if(e === 'MODERATOR')
            userInfos["moderator"]=true
        })
      }
    },
    loadUsers: (state, users) =>{
      state.users = users
    },
    logout: (state) =>{
      state.user = {
        id: -1,
        token: '',
      }
      localStorage.removeItem('user');
    }
  },
  actions: {
    createAccount: ({commit} , userInfos) => {
      return new Promise( (resolve , reject) => {
        commit('setStatus', 'loading')
        instance.post('/api/auth/register' , {
          firstname : userInfos.firstname,
          lastname : userInfos.lastname,
          nickname : userInfos.nickname,
          password : userInfos.password,
          gender : userInfos.gender,
          email : userInfos.email
        }).then((response) => {
          commit('setStatus', 'created')
          resolve(response)
        }).catch((error) =>{
          commit('setStatus', 'error_create')
          reject(error)
        })
      })
    },
    login: ({commit} , userInfos) => {
      return new Promise( (resolve , reject) => {
        commit('setStatus', 'loading')
        instance.post('/api/auth/login' , {
          password : userInfos.password,
          email : userInfos.email
        }).then((response) => {
          commit('setStatus', '')
          var user = {
            id : response.data.id,
            token : response.data.accessToken
          }
          commit('logUser' , user)

          // i must getUserInfos directly
          instance.post('/api/auth/info').
          then((response) => {
            commit('userInfos', response.data)
          }).catch((error) =>{
            commit('setStatus', '')
            console.log(error)

          })
          resolve(response)
        }).catch((error) =>{
          commit('setStatus', 'error_login')
          reject(error)
        })
      })
    },
    getUserInfos:  ({commit}) => {
      instance.post('/api/auth/info').
      then((response) => {
        commit('userInfos', response.data)
      }).catch((error) =>{
        commit('setStatus', '')
        console.log(error)

      })
    },
    getAllUsers:  ({commit}) => {
      instance.post('/api/auth/users').
      then((response) => {
        commit('loadUsers', response.data)
      }).catch((error) =>{
        console.log(error)
      })
    },
    deleteUser: ({commit} , userId) => {
      instance.post('/api/auth/deleteUser/'+ userId).
      then((response) => {
        commit('loadUsers', response.data)
      }).catch((error) =>{
        console.log(error)
      })
    },
    editProfile: ({commit} , userForm) => {
      return new Promise( (resolve , reject) => {
        commit('setStatus', 'loading')
        instance.post('/api/auth/editUser/'+ userForm.id , {
          firstname : userForm.firstname,
          lastname : userForm.lastname,
          nickname : userForm.nickname,
          gender : userForm.gender,
          password : userForm.password,
          newPassword: userForm.newPassword
        }).then((response) => {
          commit('userInfos', response.data)
          commit('setStatus', ' ')
          resolve(response)
        }).catch((error) =>{
          commit('setStatus' , 'edit-error')
          reject(error)
        })
      })
    },
  }
})
export default store;