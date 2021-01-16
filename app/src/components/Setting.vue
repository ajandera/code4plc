<template>
  <div id="setting">
    <div v-for="item in setting" class="row mt-2" v-bind:key="item">
      <div class="col-6">
        <input type="text" v-model="item.key" class="form-control">
      </div>
      <div class="col-6">
        <input type="text" v-model="item.value" class="form-control">
      </div>
    </div>
    <br>
    <button v-on:click="add" class="btn btn-success"><font-awesome-icon icon="plus" /></button>
  </div>
</template>

<script>

import axios from "axios";

export default {
  name: 'Setting',
  components: {},
  data: function() {
    return {
      setting: []
    }
  },
  methods: {
    save() {
      axios.post("java/api/v1/setting/save", {"setting": this.setting})
          .then(response => {
            this.message = response.data;
          });
    },
    add() {
      this.setting.push({'key': '', 'value': ''});
    }
  }
}
</script>
