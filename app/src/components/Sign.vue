<template>
  <div id="sign">

    <Header/>

    <button @click="show" class="btn" type="button">
      Sign in / Register
    </button>

    <modal name="sign" :width="605" :height="500" :adaptive="false">
        <div class="modal-dialog">
          <!-- Modal content-->
          <div class="modal-content">
            <div class="modal-header">
              <h3 class="modal-title">Sign in / Register</h3>
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
  name: 'Sign',
  components: {},
  data: function() {
    return {
      message: null,
      username: null,
      password: null
    }
  },
  methods: {
    show() {
      this.$modal.show('sign')
    },
    hide() {
      this.$modal.hide('sign')
    },
    login() {
      axios.post("java/api/v1/user/login", {"username": this.username, "password": this.password})
          .then(response => {
            this.message = response.data;
          });
    },
    register() {
      axios.post("java/api/v1/user/register", {"username": this.username, "password": this.password})
          .then(response => {
            this.message = response.data;
          });
    }
  }
}
</script>