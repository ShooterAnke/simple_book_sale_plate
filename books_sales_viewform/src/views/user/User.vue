<template>
  <div class="main">
    <div class="search_div">
      <el-select v-model="searchOption" placeholder="完整搜索">
        <el-option
          v-for="(item, index) in selectData"
          :key="index"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
      <el-input v-model="searchInput" placeholder="请输入用户名"> </el-input>
      <el-button type="primary" @click="searchHandler"> 搜索 </el-button>
    </div>
    <Table ref="table"></Table>
  </div>
</template>

<script>
import Table from "./table.vue";
export default {
  components: {
    Table,
  },
  data() {
    return {
        searchOption:1,
      searchInput: "",
      selectData: [
        {
          label: "完整搜索",
          value: 1,
        },
        {
          label: "模糊搜索",
          value: 2,
        },
      ],
    };
  },

  mounted() {},

  methods: {
      searchHandler(){
          this.$refs.table.searchPageData(this.searchOption,this.searchInput)
      }
  },
};
</script>

<style lang="scss" scoped>
.search_div {
  .el-select {
    width: 120px;
  }
  .el-input {
    width: 200px;
  }
}
</style>