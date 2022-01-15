<template>
  <div>
    <div class="functionArea">
      <el-input
        placeholder="模糊搜索书名"
        class="searchInput"
        v-model="search"
      ></el-input>
      <el-button type="primary" @click="searchHandler">搜索</el-button>
    </div>
    <div class="dataArea">
      <el-row v-loading="loading">
        <el-col
          :span="span"
          v-for="(item, index) in cardData"
          :key="index"
          class="cardCol"
        >
          <el-card shadow="hover" class="card">
            <img
              :src="item.coverImage"
              class="cardImage"
              @click="clickHandler(item)"
              style="cursor: pointer"
            />
            <div>
              <div class="cardName">书名：{{ item.name }}</div>
              <div class="cardPrice">价格：{{ item.price }}元</div>
              <div class="cardCount">库存：{{ item.count }}</div>
              <el-button
                type="primary"
                class="cardButton"
                @click="addCartHandler(item)"
                >加入购物车</el-button
              >
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <el-pagination
      class="pager"
      layout="prev, pager, next"
      v-if="paging"
      :total="config.total"
      :current-page.sync="config.page"
      :page-size="24"
      @current-change="changePage"
    >
    </el-pagination>
    <book-info ref="dialog"></book-info>
  </div>
</template>

<script>
import {
  getPageDataApi,
  getAllDataByIdApi,
  getSearchPageDataApi,
} from "@/api/bookApi.js";
import BookInfo from "./BookInfo";
import { addCart } from "@/api/cartApi.js";
export default {
  components: {
    BookInfo,
  },
  data() {
    return {
      span: 5,
      loading: true,
      paging: true,
      search: "",
      isSearchPage: 0,
      config: {
        total: 0,
        page: 1,
      },
      cardData: [],
    };
  },

  mounted() {
    this.getPageData();
  },

  methods: {
    getPageData() {
      getPageDataApi(this.$store.state.user.token, this.config.page, 24).then(
        (res) => {
          if (res.data.code === 0) {
            this.cardData = res.data.data.tableData;
            this.config.total = res.data.data.total;
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
    clickHandler(item) {
      getAllDataByIdApi(this.$store.state.user.token, item.id).then((res) => {
        if (res.data.code === 0) {
          this.$refs.dialog.formData = res.data.data.formData;
          this.$refs.dialog.fileList = res.data.data.fileList;
          this.$refs.dialog.dialogVisible = true;
        } else {
          this.$message({
            message: res.data.msg,
            type: "error",
          });
        }
      });
    },
    getSearchPageData() {
      getSearchPageDataApi(
        this.$store.state.user.token,
        1,
        this.search,
        this.config.page,
        24
      ).then((res) => {
        if (res.data.code === 0) {
          this.cardData = res.data.data.tableData;
          this.config.total = res.data.data.total;
          this.loading = false;
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
    changePage() {
      if (this.isSearchPage === 1) {
        this.getSearchPageData();
      } else {
        this.getPageData();
      }
    },
    searchHandler() {
      (this.config.page = 1), (this.isSearchPage = 1);
      this.getSearchPageData();
    },
    addCartHandler(item) {
      let num = 0;
      this.$prompt("请输入购买数量", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /^[0-9]+.?[0-9]*/,
        inputErrorMessage: "输入格式不正确",
      })
        .then(({ value }) => {
          num = value;
          addCart(this.$store.state.user.token, {
            userId: "",
            bookId: item.id,
            count: num,
            allPay: num * item.price,
          }).then((res) => {
            if (res.data.code === 0) {
              this.$message({
                message: res.data.msg,
                type: "success",
              });
            } else {
              this.$message({
                message: res.data.msg,
                type: "error",
              });
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消输入",
          });
        });
    },
  },
};
</script>

<style lang="scss" scoped>
.cardCol {
  margin-right: 40px;
  margin-top: 20px;
}
.dataArea {
  margin-top: 30px;
}
.searchInput {
  margin-left: 30px;
  width: 180px;
  margin-right: 20px;
}
.cardImage {
  width: 70%;
  height: 300px;
}
.cardName {
  font-size: 20px;
  color: rgb(65, 163, 0);
}
.cardPrice {
  font-size: 20px;
  margin-top: 10px;
  color: rgb(92, 0, 0);
}
.cardCount {
  font-size: 20px;
  margin-top: 10px;
  color: rgb(32, 0, 70);
}
.cardButton {
  margin-left: 200px;
  width: 100px;
}
.card{
  height: 450px;
}
</style>