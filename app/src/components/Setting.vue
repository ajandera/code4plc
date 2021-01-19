<template>
  <div id="setting">
    <div class="row">
      <div class="col-12 mt-2">
        <div v-if="message" v-bind:class="messageClass">{{ message }}</div>
      </div>
    </div>
    <div v-for="item in setting" class="row mt-2" v-bind:key="item.key">
      <div class="col-5">
        <input type="text" v-model="item.key" class="form-control">
      </div>
      <div class="col-5">
        <input type="text" v-model="item.value" class="form-control">
      </div>
      <div class="col-2">
        <button v-on:click="remove(item)" class="btn btn-danger"><font-awesome-icon icon="times" /></button>
      </div>
    </div>
    <br>
    <button v-on:click="add" class="btn btn-warning"><font-awesome-icon icon="plus" /></button>
    <button v-on:click="save" class="btn btn-success">Save</button>
  </div>
</template>

<script>

import axios from "axios";

export default {
  name: 'Setting',
  components: {},
  data: function() {
    return {
      setting: [],
      messageClass: null,
      message: null
    }
  },
  mounted() {
    let local = window.localStorage.getItem("setting");
    if (local) {
      this.setting = JSON.parse(local);
    }
  },
  methods: {
    save() {
      if (this.loggedUser) {
        axios.post("java/api/v1/setting/save", {"setting": this.setting})
            .then(response => {
              this.message = response.data;
            });
      } else {
        window.localStorage.setItem("setting", JSON.stringify(this.setting));
        this.message = "Settings saved."
        this.messageClass = "alert alert-success";
      }
    },
    add() {
      this.setting.push({'key': '', 'value': ''});
    },
    remove(item) {
      var index = this.setting.indexOf(item);
      this.setting.splice(1, index);
    }
  }
}
</script>
