<template>
  <div id="result">
    <div id="editor">
      <br>
      <label for="resultView">Results from PLC emulator</label>
      <textarea v-model="result" id="resultView" class="form-control"></textarea>
    </div>
  </div>
</template>

<script>

export default {
  name: 'Result',
  data: function() {
    return {
      result: null
    }
  },
}

</script>

<style lang="css" scoped>

#result {

}

#resultView {
  height: 600px;
}

</style>
