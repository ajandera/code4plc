<template>
  <div id="sign">
    <button @click="show" class="btn btn-outline-success" type="submit">Sign In / Sign Up</button>
    <modal name="sign" :width="600" :height="470" :adaptive="true">
        <div class="modal-dialog">
          <!-- Modal content-->
          <div class="modal-content">
            <div class="modal-header">
              <h3 class="modal-title">Sign in / Register</h3>
              <button @click="hide" type="button" class="close" data-dismiss="modal">×</button>
            </div>
            <div class="modal-body">
              <div class="row">
                <div class="col-6 borderRight">
                  <label for="username">Username</label>
                  <input type="text" v-model="username" class="form-control" id="username">
                  <label for="username">Password</label>
                  <input type="password" v-model="password" class="form-control" id="password">
                  <button class="float-right btn btn-success mt-3">Sign In</button>
                </div>
                <div class="col-6">
                  <p class="mb-3">For using database storing create your account.</p>
                  <label for="usernameNewUser">Username</label>
                  <input type="text" v-model="usernameNewUser" class="form-control" id="usernameNewUser">
                  <label for="passwordNewUser">Password</label>
                  <input type="password" v-model="passwordNewUser" class="form-control" id="passwordNewUser">
                  <label for="passwordCheckNewUser">Password for check</label>
                  <input type="password" v-model="passwordCheckNewUser" class="form-control" id="passwordCheckNewUser">
                  <button class="float-right btn btn-success mt-3">Sign Up</button>
                </div>
              </div>
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
      password: null,
      usernameNewUser: null,
      passwordNewUser: null,
      passwordCheckNewUser: null,
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
<style lang="css" scoped>

.borderRight {
  border-right: 1px solid silver;
}

</style>
