<template>
  <div>
    <div style="font-size: 30px">购物车列表：</div>
    <el-row style="margin-top: 30px">
      <el-col :span="span" v-for="(item, index) in cardData" :key="index">
        <el-card shadow="hover" class="card">
          <img class="image" :src="item.book.coverImage" />
          <div class="content">
            <h1>书名:{{ item.book.name }}</h1>
            <h1>单价:{{ item.book.price }}</h1>
            <h1>总购买数:{{ item.count }}</h1>
            <h1>总价格:{{ item.allPay }}</h1>
            <h1>添加时间:{{ item.createTime }}</h1>
            <h1 v-if="item.count > item.book.count" style="color: red">
              警告！！！：库存没有这么的多，请更改需求(库存剩余{{
                item.book.count
              }})
            </h1>
          </div>
          <div class="button-content">
            <el-button type="primary" @click="updateHandler(item)"
              >修改</el-button
            >
            <el-button type="danger" @click="deleteHandler(item)"
              >删除</el-button
            >
          </div>
        </el-card>
      </el-col>
    </el-row>
    <div class="payInfo">
      <h1 style="font-size: 30px; margin-bottom: 20px">
        商品种类:<span style="color: red">{{ sumClass }}</span>
      </h1>
      <h1 style="font-size: 30px; margin-bottom: 20px">
        总金额:<span style="color: red">{{ sumPay }}元</span>
      </h1>
      <el-button type="primary" @click="payHandler">立即支付</el-button>
    </div>
    <pay-dialog ref="dialog"></pay-dialog>
  </div>
</template>

<script>
import {
  getCartByUserId,
  deleteCart,
  updateCart,
  stockCheck,
} from "@/api/cartApi.js";
import dateFormat from "@/utils/DateFormat.js";
import payDialog from "./payDialog.vue";
export default {
  components: {
    payDialog,
  },
  data() {
    return {
      span: 24,
      cardData: [],
      sumPay: 0,
      sumClass: 0,
    };
  },

  mounted() {
    this.getCardData();
  },

  methods: {
    payHandler() {
      let list = [];
      for (let i = 0; i < this.cardData.length; i++) {
        list.push({
          bookId: this.cardData[i].bookId,
          count: this.cardData[i].count,
        });
      }
      stockCheck(this.$store.state.user.token, list).then((res) => {
        if (res.data.code === 0) {
          this.$refs.dialog.dialogVisible = true;
          this.$refs.dialog.getCaptcha();
          this.$refs.dialog.cardData = this.cardData;
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
    deleteHandler(item) {
      this.$confirm("是否删除?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          deleteCart(this.$store.state.user.token, item.id).then((res) => {
            if (res.data.code === 0) {
              this.$message({
                message: res.data.msg,
                type: "success",
              });
              location.reload();
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
            message: "已取消删除",
          });
        });
    },
    updateHandler(item) {
      let num = 0;
      this.$prompt("请输入购买数量", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /^[0-9]+.?[0-9]*/,
        inputErrorMessage: "输入格式不正确",
      })
        .then(({ value }) => {
          num = value;
          item.count = num;
          item.allPay = num * item.book.price;
          updateCart(this.$store.state.user.token, {
            userId: item.userId,
            bookId: item.bookId,
            count: num,
            allPay: item.allPay,
          }).then((res) => {
            if (res.data.code === 0) {
              this.$message({
                message: res.data.msg,
                type: "success",
              });
              this.countAll();
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

    formatDate() {
      for (let i = 0; i < this.cardData.length; i++) {
        this.cardData[i].createTime = dateFormat(this.cardData[i].createTime);
      }
    },
    getCardData() {
      getCartByUserId(this.$store.state.user.token).then((res) => {
        if (res.data.code === 0) {
          this.cardData = res.data.data.cardData;
          this.formatDate();
          this.sumClass = this.cardData.length;
          this.countAll();
        } else {
          this.message({
            message: res.data.msg,
            type: "error",
          });
        }
      });
    },
    countAll() {
      for (let i = 0; i < this.cardData.length; i++) {
        this.sumPay += this.cardData[i].allPay;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.payInfo {
  position: fixed;
  right: 200px;
  bottom: 50%;
}
.card {
  margin-top: 10px;
  margin-left: 300px;
  height: 300px;
  width: 700px;
  position: relative;
}
.image {
  width: 200px;
  height: 300px;
}
.content {
  position: absolute;
  left: 300px;
  top: 0px;
  h1 {
    font-size: 20px;
    margin-top: 20px;
  }
}
.button-content {
  position: absolute;
  right: 20px;
  bottom: 20px;
}
</style>