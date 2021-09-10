<template>
  <router-view/>
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap');

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Roboto', sans-serif;
  overflow-y: scroll;
}

.material-icons {
  color: rgb(96, 96, 96);
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 70px;
  padding: 15px;
}

.header__left {
  display: flex;
  align-items: center;
}

.header__left img {
  width: 100px;
  margin-left: 15px;
}

.header i {
  padding: 0 7px;
  cursor: pointer;
}

.header__search form {
  border: 1px solid #ddd;
  height: 35px;
  margin: 0;
  padding: 0;
  display: flex;
}

.header__search input {
  width: 500px;
  padding: 10px;
  margin: 0;
  border-radius: 0;
  border: none;
  height: 100%;
}

.header__search button {
  padding: 0;
  margin: 0;
  height: 100%;
  border: none;
  border-radius: 0;
}

.mainBody {
  height: calc(100vh - 70px);
  display: flex;
}

.sidebar {
  height: 100%;
  min-width: 190px;
  background-color: white;
}

.sidebar__categories {
  width: 100%;
  display: flex;
  flex-direction: column;
  margin-bottom: 15px;
  margin-top: 15px;
}

.sidebar__category {
  display: flex;
  align-items: center;
  padding: 12px 25px;
}

.sidebar__category span {
  margin-left: 15px;
}

.sidebar__category:hover {
  background: #e5e5e5;
  cursor: pointer;
}

.sidebar::-webkit-scrollbar {
  display: none;
}

hr {
  height: 1px;
  background-color: #e5e5e5;
  border: none;
}


.videos {
  background-color: #f9f9f9;
  width: 100%;
  height: 100%;
  padding: 15px 15px;
  border-top: 1px solid #ddd;
}



.author img {
  object-fit: cover;
  border-radius: 50%;
  height: 40px;
  width: 40px;
  margin-right: 10px;
}

.video__details {
  display: flex;
  margin-top: 10px;
}

.title {
  display: flex;
  flex-direction: column;
}

.title h3 {
  color: rgb(3, 3, 3);
  line-height: 18px;
  font-size: 14px;
  margin-bottom: 6px;
}

.title a,
span {
  text-decoration: none;
  color: rgb(96, 96, 96);
  font-size: 14px;
}

h1 {
  font-size: 20px;
  margin-bottom: 10px;
  color: rgb(3, 3, 3);
}

@media (max-width: 425px) {
  .header__search {
    display: none;
  }

  .header__icons .material-icons {
    display: none;
  }

  .header__icons .display-this {
    display: inline;
  }

  .sidebar {
    display: none;
  }
}

@media (max-width: 768px) {
  .header__search {
    display: none;
  }

  .sidebar {
    display: none;
  }

  .show-sidebar {
    display: inline;
    position: fixed;
    top: 4.4rem;
    height: auto;
  }
}

@media (max-width: 941px) {
  .header__search input {
    width: 300px;
  }
}
</style>
