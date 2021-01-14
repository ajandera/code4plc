<template>
  <div id="app">
    <Header/>
    <component :is="Setting"></component>
    <component :is="Editor"></component>
    <component :is="Result"></component>
  </div>
</template>

<script>
import Editor from './components/Editor.vue';
import Result from './components/Result.vue'
import Setting from './components/Setting.vue'
import Sign from './components/Sign.vue'

export default {
  name: 'App',
  components: {
    Editor,
    Result,
    Setting,
    Sign
  }
}
</script>

<style lang="css" scoped>

#app {

}

</style>
