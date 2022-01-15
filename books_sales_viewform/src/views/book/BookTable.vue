<template>
  <div>
    <el-table
      :data="tableData"
      border
      style="width: 100%; font-size: 20px"
      v-loading="loading"
    >
      <el-table-column label="序号" width="85">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{
            (config.page - 1) * 10 + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="封面图" width="200">
        <template slot-scope="scope">
          <img :src="scope.row.coverImage" class="cover_img" />
        </template>
      </el-table-column>

      <el-table-column
        v-for="(item, index) in tableLabel"
        :key="index"
        :prop="item.prop"
        :label="item.label"
        :width="item.width"
      >
      </el-table-column>
      <el-table-column fixed="right" label="操作" min-width="150">
        <template slot-scope="scope">
          <el-button @click="editClick(scope.row)" type="primary" size="mini"
            >编辑</el-button
          >
          <el-button @click="deleteClick(scope.row)" type="danger" size="mini"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="pager"
      layout="prev, pager, next"
      v-if="paging"
      :total="config.total"
      :current-page.sync="config.page"
      :page-size="10"
      @current-change="changePage"
    >
    </el-pagination>
    <edit-dialog ref="dialog"></edit-dialog>
  </div>
</template>

<script>
import {
  getPageDataApi,
  deleteBookByIdApi,
  getAllDataByIdApi,
  getSearchPageDataApi,
} from "@/api/bookApi.js";
import dateFormat from "@/utils/DateFormat.js";
import EditDialog from "./EditDialog";
export default {
  components: {
    EditDialog,
  },
  data() {
    return {
      option: 1,
      paging: true,
      search:'',
      isSearchPage: 0,
      config: {
        total: 0,
        page: 1,
      },
      loading: true,
      tableData: [],
      tableLabel: [
        {
          prop: "name",
          label: "书名",
          width: "200",
        },
        {
          prop: "category",
          label: "分类",
          width: "200",
        },

        {
          prop: "count",
          label: "库存数目",
          width: "100",
        },

        {
          prop: "publisher",
          label: "出版商",
          width: "200",
        },

        {
          prop: "price",
          label: "单价",
          width: "100",
        },
        {
          prop: "createTime",
          label: "创建时间",
          width: "300",
        },

        {
          prop: "updateTime",
          label: "更新时间",
          width: "300",
        },

        {
          prop: "bookNum",
          label: "图书编号",
          width: "200",
        },
      ],
    };
  },

  mounted() {
    this.getPageData();
  },

  methods: {
    changePage() {
      if (this.isSearchPage === 1) {
          this.getSearchPageData();
      } else {
        this.getPageData();
      }
    },
    formatDate() {
      for (let i = 0; i < this.tableData.length; i++) {
        this.tableData[i].createTime = dateFormat(this.tableData[i].createTime);
        this.tableData[i].updateTime = dateFormat(this.tableData[i].updateTime);
      }
    },
    getSearchPageData() {
      getSearchPageDataApi(
        this.$store.state.user.token,
        this.option,
        this.search,
        this.config.page,
        10
      ).then((res) => {
        if (res.data.code === 0) {
            this.tableData=res.data.data.tableData;
            this.config.total=res.data.data.total;
            this.formatDate();
            this.loading=false;
        } else {
          this.$message({
            message: res.data.msg,
            type: "error",
            duration: 5000,
            center: true,
          });
        }
      });
    },
    getPageData() {
      getPageDataApi(this.$store.state.user.token, this.config.page, 10).then(
        (res) => {
          if (res.data.code === 0) {
            this.tableData = res.data.data.tableData;
            this.config.total = res.data.data.total;
            this.formatDate();
            this.loading = false;
          } else {
            this.$message({
              message: res.data.msg,
              type: "error",
              duration: 5000,
              center: true,
            });
          }
        }
      );
    },
    deleteClick(row) {
      this.$confirm("此操作将永久删除该书信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          deleteBookByIdApi(this.$store.state.user.token, row.id).then(
            (res) => {
              if (res.data.code === 0) {
                this.$message({
                  type: "success",
                  message: res.data.msg,
                });
                location.reload();
              } else {
                this.$message({
                  type: "error",
                  message: res.data.msg,
                });
              }
            }
          );
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    editClick(row) {
      getAllDataByIdApi(this.$store.state.user.token, row.id).then((res) => {
        if (res.data.code === 0) {
          console.log(res);
          this.$refs.dialog.formData = res.data.data.formData;
          this.$refs.dialog.fileList = res.data.data.fileList;
          this.$refs.dialog.id = row.id;
          this.$refs.dialog.dialogVisible = true;
        } else {
          this.$message({
            message: res.data.msg,
            type: "error",
          });
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.cover_img {
  width: 100px;
  height: 100px;
}
</style>