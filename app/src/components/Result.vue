<template>
  <div id="result">
    <br>
    <label for="resultView">Results from PLC emulator</label>
    <textarea v-model="result" id="resultView" class="form-control"></textarea>
    <div class="mt-3 float-right">
      <button v-if="running === false" v-on:click="run" class="btn btn-lg btn-primary">Run</button>
      <button v-if="running === true" v-on:click="stop" class="btn btn-lg btn-danger">Stop</button>
      <button v-on:click="clear" class="btn btn-lg btn-default">Clear</button>

    </div>
  </div>
</template>

<script>

export default {
  name: 'Result',
  data: function () {
    return {
      result: "",
      running: false,
      plc: null
    }
  },
  methods: {
    run() {
      let count = 1;
      this.running = true;
      this.plc = setInterval(() => {
        this.result += "PLC running cycle " + count + "\n";
        console.log(this.result);
        count++;
      }, 1000);
    },
    stop() {
      clearInterval(this.plc)
      this.running = false;
    },
    clear() {
      this.result = "";
    }
  }
}

</script>

<style lang="css" scoped>

#result {

}

#resultView {
  height: 600px;
}

</style>
