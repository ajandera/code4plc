<template>
  <div id="editor">
    <div class="row">
      <div class="col-12 mt-2">
        <div v-if="message" v-bind:class="messageClass">{{ message }}</div>
      </div>
    </div>
    <br>
    <label for="codeEditor">Write your application code:</label>
    <prism-editor class="my-editor" v-model="code" :highlight="highlighter" id="codeEditor" line-numbers></prism-editor>
    <div class="mt-3 float-right">
      <input type="checkbox" v-model="autoSave"> Autosave
      <button v-on:click="save" class="btn btn-lg btn-success">Save</button>
    </div>
  </div>
</template>

<script>

import axios from "axios";

// import Prism Editor
import { PrismEditor } from 'vue-prism-editor';
import 'vue-prism-editor/dist/prismeditor.min.css'; // import the styles somewhere

// import highlighting library (you can use any library you want just return html string)
import { highlight, languages } from 'prismjs/components/prism-core';
import "prismjs/components/prism-clike";
import "prismjs/components/prism-javascript";
import "prismjs/themes/prism.css";

export default {
  name: 'Editor',
  components: {
    PrismEditor
  },
  data() {
    return {
      messageClass: null,
      code: '',
      message: null,
      autoSave: false,
      lineNumbers: true
    }
  },
  mounted() {
    let localApp = window.localStorage.getItem("app");
    if (localApp) {
      this.code = JSON.parse(localApp);
    }
  },
  methods: {
    save() {
      if (this.loggedUser) {
        axios.post("java/api/v1/app/save", {"code": this.code})
            .then(response => {
              this.message = response.data;
            });
      } else {
        window.localStorage.setItem("app", JSON.stringify(this.code));
        this.message = "Application saved."
        this.messageClass = "alert alert-success";
      }
    },
    highlighter(code) {
      return highlight(code, languages.js); // languages.<insert language> to return html with markup
    },
  }
}
</script>

<style lang="css" scoped>

#editor {

}

#codeEditor {
  height: 600px;
}

/* required class */
.my-editor {
  /* we dont use `language-` classes anymore so thats why we need to add background and text color manually */
  background: #2d2d2d;
  color: #ccc;

  /* you must provide font-family font-size line-height. Example: */
  font-family: Fira code, Fira Mono, Consolas, Menlo, Courier, monospace;
  font-size: 14px;
  line-height: 1.5;
  padding: 5px;
}

/* optional class for removing the outline */
.prism-editor__textarea:focus {
  outline: none;
}

</style>
