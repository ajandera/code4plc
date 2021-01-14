<template>
    <div id="editor">
        <Header/>
        <div class="message" v-if="message">{{ message }}</div>
        <label for="codeEditor"></label>
        <textarea v-model="code" id="codeEditor"></textarea>
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
      message: null
    }
  },
  mounted() {
    axios.get("java/api/v1/app/load")
        .then(response => {
            this.code = response.data;
        });
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

}

</style>