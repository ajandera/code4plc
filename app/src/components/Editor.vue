<template>
    <div id="editor">
        <div class="message" v-if="message">{{ message }}</div>
        <br>
        <label for="codeEditor">Write your code in Structured text</label>
        <textarea v-model="code" id="codeEditor" class="form-control"></textarea>
        <div class="mt-3 float-right">
          <input type="checkbox" v-mode="autosave"> Autosave <button v-on:click="save" class="btn btn-lg btn-success">Save</button>
        </div>
    </div>
</template>

<script>

import axios from "axios";

export default {
  name: 'Editor',
  components: {},
  data: function() {
    return {
      code:  null,
      message: null,
      autosave: false
    }
  },
  methods: {
    save() {
      axios.post("java/api/v1/app/save", {"code": this.code})
          .then(response => {
            this.message = response.data;
          });
    }
  }
}
</script>

<style lang="css" scoped>

#editor {

}

#codeEditor {
  height: 600px;
}

</style>
