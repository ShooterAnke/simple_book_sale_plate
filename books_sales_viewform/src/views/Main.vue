<template>
  <el-container style="height: 100%">
    <el-aside width="auto">
      <common-aside></common-aside>
    </el-aside>
    <el-container>
      <el-header>
        <common-head></common-head>
      </el-header>
      <common-tab></common-tab>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import CommonTab from "@/components/Common-tab.vue";
import CommonAside from "@/components/Common-aside.vue";
import CommonHead from "@/components/Common-head.vue";
export default {
  components: {
    CommonTab,
    CommonAside,
    CommonHead,
  },
  data() {
    return {};
  },

  mounted() {},

  methods: {},
};
</script>

<style lang="scss" scoped>
.el-header {
  background-color: #333;
}
.el-main {
  padding-top: 0px;
}
</style>