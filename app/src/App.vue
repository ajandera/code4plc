<template>
  <div id="app">
    <Header/>
    <section class="main">
      <div class="container-fluid">
        <div class="row">
          <div class="col-7">
            <ul class="nav nav-tabs" id="mainNavList">
              <li class="nav-item" v-for="tab in tabs" :key="tab" @click="selected = tab;">
                <a v-bind:class="[selected === tab ? 'nav-link active' : 'nav-link']" href="#">{{ tab }}</a>
              </li>
            </ul>
            <component :is="selected" class="tab"></component>
          </div>
          <div class="col-5">
            <ul class="nav nav-tabs" id="sideNavList">
              <li class="nav-item" v-for="tab in plcTabs" :key="tab" @click="selectedPlc = tab;">
                <a v-bind:class="[selectedPlc === tab ? 'nav-link active' : 'nav-link']" href="#">{{ tab }}</a>
              </li>
            </ul>
            <component :is="selectedPlc" class="tab"></component>
          </div>
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
  data: function () {
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

#app {

}

.main {
  margin-top: 30px;
}
</style>
