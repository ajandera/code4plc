<template>
  <div id="app">
    <Header/>
    <section class="main mt-3">
      <div class="container-fluid">
        <div class="row">
          <div class="col-8">
            <ul class="nav nav-tabs" id="mainNavList">
              <li class="nav-item" v-for="tab in tabs" :key="tab" @click="selected = tab;">
                <a v-bind:class="[selected === tab ? 'nav-link active' : 'nav-link']" href="#">{{ tab }}</a>
              </li>
            </ul>
            <component :is="selected" class="tab"></component>
          </div>
          <div class="col-4">
            <ul class="nav nav-tabs" id="sideNavList">
              <li class="nav-item" v-for="tabPlc in plcTabs" :key="tabPlc" @click="selectedPlc = tabPlc;">
                <a v-bind:class="[selectedPlc === tabPlc ? 'nav-link active' : 'nav-link']" href="#">{{ tabPlc }}</a>
              </li>
            </ul>
            <component :is="selectedPlc" class="tab"></component>
          </div>
        </div>
      </div>
    </section>
    <section id="footer">
      <div class="row">
        <div class="col-12 text-center">
          <p>2021 &copy; Aleš Jandera, <a href="https://ajandera.com" target="_blank">ajandera.com</a></p>
        </div>
      </div>
    </section>
  </div>
</template>

<script>

import Editor from '@/components/Editor.vue';
import Result from '@/components/Result';
import Setting from '@/components/Setting';
import Header from "@/components/Header";
import Help from "@/components/Help";

export default {
  name: 'App',
  data() {
    return {
      tabs: ["Editor", "Help"],
      selected: "Editor",
      plcTabs: ["Result", "Setting"],
      selectedPlc: "Result"
    }
  },
  components: {
    Header,
    Editor,
    Result,
    Setting,
    Help
  }
}
</script>

<style lang="css" scoped>
@import "https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css";

html, body {
  height: 100%;
}

#app {
  min-height: 100%;
}

.main {
  overflow:auto;
  padding-bottom:20%; /* this needs to be bigger than footer height*/
}

#footer {
  position: relative;
  margin-top: -10%; /* negative value of footer height */
  height: 150px;
  clear:both;
  padding-top:20px;
}
</style>
