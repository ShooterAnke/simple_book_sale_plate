<template>
  <div>
    <div class="top">
      我的订单：
      <el-select v-model="option" placeholder="请选择" @change="searchHandler">
        <el-option
          v-for="item in selectLabel"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
    </div>
    <el-row style="margin-top: 30px">
      <el-col :span="span" v-for="(item, index) in cardData" :key="index">
        <el-card shadow="hover" class="card">
          <div class="user">
            <img :src="item.user.headImage" class="head_image" />
            <div class="info">
              <h1>姓名:{{ item.user.name }}</h1>
              <h1>地址:{{ item.user.address }}</h1>
              <h1>用户名:{{ item.user.username }}</h1>
              <h1>手机号:{{ item.user.phone }}</h1>
            </div>
          </div>
          <div class="order">
            <h1>订单号:{{ item.id }}</h1>
            <h1>创建时间:{{ item.createTime }}</h1>
            <h1>书本类型数:{{ item.count }}</h1>
            <h1>总价格{{ item.allPay }}</h1>
            <h1 v-if="item.isSend === 1&&c_option===1" style="color: red">状态:已发货</h1>
            <h1 v-if="item.isSend===0&&c_option===1" style="color: red">状态:待发货</h1>
            <h1 v-if="c_option===2" style="color: red">状态:已完成</h1>
          </div>
          <div class="bookOrder">
            <el-button type="primary" @click="infoHandler(item)"
              >订单详情</el-button
            >
            <el-button
              type="success"
              @click="confirmHandler(item)"
              v-if="item.isSend === 1&c_option===1"
              >确认收货</el-button
            >
            <el-button type="warning" @click="cancelHandler(item)" v-if="item.isSend===0&&c_option===1"
              >取消订单</el-button
            >
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-drawer title="订单详情" :visible.sync="drawer" :with-header="false">
      <el-card
        v-for="(item, index) in drawerData"
        :key="index"
        style="position: relative"
      >
        <img :src="item.coverImage" class="drawer-image" />
        <div class="drawer-info">
          <h1>书名:{{ item.book.name }}</h1>
          <h1>出版社:{{ item.book.publisher }}</h1>
          <h1>单价:{{ item.book.price }}</h1>
          <h1>分类:{{ item.book.category }}</h1>
          <h1>购买本数:{{ item.count }}</h1>
          <h1>总价格:{{ item.allPay }}</h1>
        </div>
      </el-card>
    </el-drawer>
  </div>
</template>

<script>
import { getOrderList, confirmOrder,cancelOrder,getFinishCardData } from "@/api/orderApi.js";
import formatDate from "@/utils/DateFormat.js";
export default {
  data() {
    return {
      drawer: false,
      span: 24,
      c_option:1,
      option: 1,
      drawerData: [],
      selectLabel: [
        {
          label: "未完成订单",
          value: 1,
        },
        {
          label: "已完成订单",
          value: 2,
        },
      ],
      cardData: [],
    };
  },

  mounted() {
    this.getCardData();
  },

  methods: {
    searchHandler(){
      if(this.option===1){
        this.getCardData();
      }
      else
      {
        this.getFinishData();
      }
      this.c_option=this.option
      
    },
    getFinishData(){
      getFinishCardData(this.$store.state.user.token).then((res)=>{
        if(res.data.code===0){
          this.cardData=res.data.data.cardData;
            for (let i = 0; i < this.cardData.length; i++) {
            this.cardData[i].createTime = formatDate(
              this.cardData[i].createTime
            );
          }
        }else{
          this.$message({
            message:res.data.msg,
            type:'error'
          })
        }
      })
    },
    cancelHandler(item) {
      this.$confirm("确认取消订单？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
            cancelOrder(this.$store.state.user.token,item.id,item.userId,item.orderBooks).then((res)=>{
                if(res.data.code===0){
                    this.$message({
                        message:res.data.msg,
                        type:'success'
                    })
                    location.reload();
                }else{
                    this.$message({
                        message:res.data.msg,
                        type:'error'
                    })
                }
            })
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消确认",
          });
        });
    },
    confirmHandler(item) {
      this.$confirm("确认收货？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          confirmOrder(this.$store.state.user.token, item.id, item.userId).then(
            (res) => {
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
            }
          );
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消确认",
          });
        });
    },
    infoHandler(item) {
      this.drawer = true;
      this.drawerData = item.orderBooks;
    },
    getCardData() {
      getOrderList(this.$store.state.user.token).then((res) => {
        if (res.data.code === 0) {
          this.cardData = res.data.data.cardData;
          for (let i = 0; i < this.cardData.length; i++) {
            this.cardData[i].createTime = formatDate(
              this.cardData[i].createTime
            );
          }
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
.drawer-image {
  width: 100px;
  height: 150px;
}
.drawer-info {
  position: absolute;
  left: 200px;
  top: 30px;
  h1 {
    margin-top: 10px;
  }
}
.bookOrder {
  position: absolute;
  right: 100px;
  bottom: 100px;
}
.top {
  font-size: 30px;
}
.card {
  width: 900px;
  margin-left: 300px;
  flex: auto;
  position: relative;
}
.user {
  margin-left: 20px;
  .head_image {
    width: 100px;
    height: 150px;
  }
  h1 {
    margin-top: 5px;
  }
}
.order {
  position: absolute;
  left: 200px;
  top: 10px;
  h1 {
    margin-bottom: 30px;
    font-size: 20px;
  }
}
</style>