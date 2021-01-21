<template>
  <div id="editor">
    <div class="row mt-3">
      <div class="col-12">
        <label for="codeEditor">Write your application code:</label>
        <prism-editor class="my-editor" v-model="code" :highlight="highlighter" id="codeEditor"
                      line-numbers></prism-editor>
      </div>
    </div>
    <div class="row mt-3">
      <div class="col-6 mt-3">
        <div v-if="message" v-bind:class="messageClass">{{ message }}</div>
      </div>
      <div class="col-6 mt-3">
        <div class="float-right">
          <input type="checkbox" v-model="autoSave"> Autosave
          <button v-on:click="save" class="btn btn-lg btn-success">Save</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import axios from "axios";

// import Prism Editor
import {PrismEditor} from 'vue-prism-editor';
import 'vue-prism-editor/dist/prismeditor.min.css'; // import the styles somewhere

// import highlighting library (you can use any library you want just return html string)
import { highlight, languages } from 'prismjs/components/prism-core'
import "prismjs/components/prism-pascal"
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
      lineNumbers: true,
      autoSaveRun: null,
      loggedUser: window.localStorage.getItem("user")
    }
  },
  mounted() {
    let localApp = window.localStorage.getItem("app");
    if (localApp) {
      this.code = JSON.parse(localApp);
    }
  },
  watch: {
    autoSave: function (val) {
      if (val) {
        this.autoSaveRun = setInterval(() => {
          this.save();
        }, 5000);
      } else {
        clearInterval(this.autoSaveRun);
      }
    }
  },
  methods: {
    save() {
      if (this.loggedUser) {
        let id = window.localStorage.getItem("userId");
        axios.post("http://localhost:9000/api/v1/app/save", {"code": this.code, "user": id, "name": this.loggedUser})
            .then(response => {
              if (response.data.success) {
                this.message = response.data.message;
                this.messageClass = "alert alert-success";
              } else {
                this.message = response.data.message;
                this.messageClass = "alert alert-danger";
              }

              setTimeout(() => {
                this.message = null;
                this.messageClass = null;
              }, 1000);
            });
      } else {
        window.localStorage.setItem("app", JSON.stringify(this.code));
        this.message = "Application saved."
        this.messageClass = "alert alert-success";
        setTimeout(() => {
          this.message = null;
          this.messageClass = null;
        }, 1000);
      }
    },
    highlighter(code) {
      return highlight(code, languages.pascal);
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
  background: #fff;
  border: 1px solid silver;
  border-radius: 5px;
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
