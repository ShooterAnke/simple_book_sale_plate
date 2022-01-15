<template>
  <div class="container">
    <div class="login_form">
      <div class="form_header">
        <h1 class="logo">登录</h1>
        <h2 class="explain">glance at the stars</h2>
      </div>
      <div class="form_body">
        <el-form :model="model" label-width="80px" class="form_body_form">
          <el-form-item class="form_user">
            <el-input
              v-model="model.username"
              prefix-icon="el-icon-user-solid"
              placeholder="用户名"
            ></el-input>
          </el-form-item>
          <el-form-item class="form_password">
            <el-input
              v-model="model.password"
              prefix-icon="el-icon-eleme"
              placeholder="密码"
              show-password
            ></el-input>
          </el-form-item>
          <el-form-item class="login_button">
            <el-button type="primary" @click="onSubmit">登陆</el-button>
            <el-button type="primary" plain @click="onSubmit_register"
              >去注册</el-button
            >
          </el-form-item>
        </el-form>
      </div>
    </div>
    <Footer></Footer>
  </div>
</template>

<script>
import Footer from "@/components/Footer";
import { loginApi } from "@/api/getData.js";
export default {
  components: { Footer },
  data() {
    return {
      model: {
        username: "",
        password: "",
      },
    };
  },
  methods: {
    onSubmit() {
      loginApi(this.model.username, this.model.password).then((res) => {
        if (res.data.code === 0) {

          this.$store.commit("clearMenu");
          this.$store.commit("setMenu", res.data.data.menus);
          this.$store.commit("setToken", res.data.data.token);
          this.$store.commit("addRouter", this.$router);
          this.$message({
            message: res.data.msg,
            type: "success",
            duration: 3000,
            center: true,
          });
          
        } else {
          this.$message({
            message: res.data.msg,
            type: "error",
            duration: 3000,
            center: true,
          });
        }
        this.$router.push({ path: "/home" });
      });
    },
    onSubmit_register() {
      this.$router.push("/register");
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  position: relative;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: url("../../pic/background.png") no-repeat 0;
  background-position: center bottom;
  background-size: cover;
}
.login_form {
  position: absolute;
  width: 100%;
  height: 500px;
  align-items: center;
  display: block;
  top: 50%;
  margin-top: -200px;
}
.logo {
  display: block;
  text-align: center;
  font-weight: 700;
  font-size: 50px;
  color: #fff;
  height: 50px;
  text-shadow: 2px 2px 3px rgba(0, 0, 0, 0.5);
  position: absolute;
  left: 50%;
  margin-left: -20px;
  top: -40px;
}
.explain {
  position: relative;
  width: 517px;
  height: 30px;
  font-size: 30px;
  font-weight: 400;
  color: #fff;
  text-shadow: 2px 2px 3px rgba(0, 0, 0, 0.5);
  text-align: center;
  position: absolute;
  left: 50%;
  margin-left: -218px;
  top: 20px;
}
.form_body {
  margin: 0 auto;
  width: 100%;
  display: flex;
  justify-content: center;
}
.form_body_form {
  font: italic blod 12px/20px arial, sans-serif;
  position: relative;
  top: 50px;
}
.form_user {
  position: absolute;
  top: 20px;
  left: 50%;
  margin-left: -150px;
  width: 300px;
  height: 60px;
  padding: 0;
  border: 0;
}
.form_password {
  position: absolute;
  top: 90px;
  left: 50%;
  margin-left: -150px;
  width: 300px;
  height: 60px;
  padding: 0;
  border: 0;
}
.login_button {
  position: absolute;
  top: 150px;
  left: 50%;
  margin-left: -125px;
  width: 300px;
  height: 45px;
  padding: 0;
  border: 0;
  align-items: center;
}
</style>