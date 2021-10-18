<template>
  <div class="hello">
    <p>{{ msg }}</p>
  </div>
</template>

<script>
import props from "@/support/props.js";

export default {
  name: "HelloWorld",
  data() {
    return {
      msg: "loading ...",
    };
  },
  async created() {
    let response = await fetch(props.testUrl);
    if (!response.ok) {
      console.warn("HTTP error: " + response.status);
      return;
    }
    let json = await response.json();
    this.msg = json.currencies.join(",");
  },
};
</script>
