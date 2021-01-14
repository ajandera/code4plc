<template>
  <div id="setting">

    <Header/>

    <button @click="show" class="btn" type="button">
      Show setting
    </button>

    <modal name="setting" :width="605" :height="500" :adaptive="false">
        <div class="modal-dialog">
          <!-- Modal content-->
          <div class="modal-content">
            <div class="modal-header">
              <h3 class="modal-title">Setting</h3>
              <button @click="hide" type="button" class="close" data-dismiss="modal">×</button>
            </div>

          </div>
        </div>
    </modal>
  </div>
</template>

<script>

import axios from "axios";

export default {
  name: 'Setting',
  components: {},
  data: function() {
    return {
      setting: {}
    }
  },
  mounted() {
    axios.get("java/api/v1/setting/load")
      .then(response => {
        this.setting = response.data;
      });
  },
  methods: {
    show() {
      this.$modal.show('setting')
    },
    hide() {
      this.$modal.hide('setting')
    },
    save() {
      axios.post("java/api/v1/setting/save", {"setting": this.setting})
          .then(response => {
            this.message = response.data;
          });
    }
  }
}
</script>