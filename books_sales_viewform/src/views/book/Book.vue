<template>
  <div>
    <div class="button_list" style="margin-bottom: 20px">
      <el-button type="primary" @click="openAddBookDialog"> 新增书 </el-button>
      <el-button type="primary" @click="openAddCategoryDialog">
        新增分类
      </el-button>
      <el-select v-model="option" placeholder="请选择" class="searchSelect">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-input v-model="search" placeholder="请输入搜索" class="searchInput"></el-input>
      <el-button type="primary" @click="searchHandler" searchButton>搜索</el-button>
    </div>
    <book-table ref="table"></book-table>
    <add-book-dialog ref="addBookDialog"></add-book-dialog>
    <add-category-dialog ref="addCategoryDialog"></add-category-dialog>
  </div>
</template>

<script>
import AddBookDialog from "./AddBookDialog";
import BookTable from "./BookTable.vue";
import AddCategoryDialog from "./AddCategoryDialog";
export default {
  components: {
    AddBookDialog,
    BookTable,
    AddCategoryDialog,
  },
  data() {
    return {
      option:1,
      search:'',

      options:[{
        label:'书名模糊搜索',
        value:1
      },
      {
        label:'编号模糊搜索',
        value:2
      }]
    };
  },

  mounted() {},

  methods: {
    openAddBookDialog() {
      this.$refs.addBookDialog.dialogVisible = true;
    },
    openAddCategoryDialog() {
      this.$refs.addCategoryDialog.dialogVisible = true;
    },
    searchHandler(){
        this.$refs.table.search=this.search
        this.$refs.table.option=this.option
        this.$refs.table.config.page=1
        this.$refs.table.isSearchPage=1
        this.$refs.table.getSearchPageData();
    }

  
  },
};
</script>

<style lang="scss" scoped>
.searchSelect{
  margin-left: 20px;
  width: 200px;
}
.searchInput{
  width: 300px;
}
</style>