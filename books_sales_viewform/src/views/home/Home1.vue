<template>
  <div class="home_info">
    <el-row>
      <el-col :span="11">
        <el-card shadow="hover" class="user_card">
          <img :src="user.headImage" class="user_image" />
          <div class="userInfo">
            <p class="name">昵称：{{ user.name }}</p>
            <p class="power">用户权限：{{ userPower }}</p>
            <p class="username">用户名：{{ user.username }}</p>
            <p class="phone">手机号：{{ user.phone }}</p>
            <p class="address">地址：{{ user.address }}</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { findByUsernameApi } from "@/api/getData.js";
export default {
  components: {},
  data() {
    return {
      chartData1: {
        xData: [
          "星期一",
          "星期二",
          "星期三",
          "星期四",
          "星期五",
          "星期六",
          "星期日",
        ],
        series: [
          {
            data: [100, 200, 300, 400, 500, 400, 300],
            type: "line",
          },
        ],
      },
      chartData2: {
        xData: [
          "星期一",
          "星期二",
          "星期三",
          "星期四",
          "星期五",
          "星期六",
          "星期日",
        ],
        series: [
          {
            data: [2313, 312, 4321, 523, 1431, 532, 312],
            type: "line",
          },
        ],
      },
      chartData3: {
        xData: [
          "星期一",
          "星期二",
          "星期三",
          "星期四",
          "星期五",
          "星期六",
          "星期日",
        ],
        series: [
          {
            data: [2312, 313, 3121, 523, 1431, 532, 312],
            type: "bar",
          },
        ],
      },
      user: {},
      userPower: "",
    };
  },

  mounted() {
    this.findByUsername();
  },

  methods: {
    findByUsername() {
      let token = this.$store.state.user.token;
      findByUsernameApi(token).then((res) => {
        if (res.data.code === 0) {
          this.user = res.data.data.user;
          if (this.user.power === 3) {
            this.userPower = "超级管理员";
          } else {
            if (this.user.power === 2) {
              this.userPower = "普通管理员";
            } else {
              this.userPower = "用户";
            }
          }
        } else {
          this.$message({
            message: res.data.msg,
            type: "error",
            duration: 3000,
            center: true,
          });
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.user_card {
  background: #b1e0ff;
}
.user_image {
  border-radius: 100%;
  width: 30%;
  margin-right: 50px;
  float: left;
}
.userInfo {
  p {
    font-size: 30px;
    margin-bottom: 20px;
  }
}
.title {
  font-size: 30px;
  font-weight: bold;
}
.echart_1 {
  height: 400px;
  margin-left: 20px;
  background: rgb(208, 253, 218);
}
.echart_2 {
  margin-top: -100px;
  height: 500px;
  background: rgb(255, 199, 199);
}
.echart_3 {
  margin-top: 10px;
  margin-left: 20px;
  background: rgb(255, 234, 206);
}
</style>