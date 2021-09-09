<template>
<Header/>
  <div class="mainBody">
    <h1>Watch</h1>
    <AsideMenu/>
    <div class="video">
      <video>
        <source src="#" type="#">
      </video>
      <h3>
        {{title}}
      </h3>
      <a :href="'watch?id='+id">{{channel}}</a>
      <span>{{views}} Views • {{edit }}</span>
      <span>{{likes}} Likes </span>
      <span> Teilen </span>
      <span> Speichern</span>
    </div>
    <div class="commentaire">
      <span>{{username}} {{currentDate() }} </span>
      <p>{{comments}}</p>

    </div>
  </div>
</template>

<script>
import Header from "../components/Header";
import AsideMenu from "../components/AsideMenu";

export default {
  name: "Watch",
  components: {
    Header,
    AsideMenu
  },
  props:{
    likes:Number,
    username:String,
    comments:String
  },
  methods:{
    currentDate(){
      const current = new Date();
      const date = `${current.getDate()}/${current.getMonth()+1}/${current.getFullYear()}`;
      return date;

    }
  }

}
</script>

<style scoped>
.mainBody{
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  background-color: black;
  text-decoration: white;
  align-items: center;

}

</style>