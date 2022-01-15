<template>
  <div class="container">
    <div class="register_body">
      <h1 class="logo">注册</h1>
      <el-form :model="model" :rules="rules">
        <el-form-item prop="username">
          <el-input
            v-model="model.username"
            placeholder="用户名"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
          show-password
            v-model="model.password"
            placeholder="密码"
          ></el-input>
        </el-form-item>
        <el-form-item prop="phone">
          <el-input
            v-model="model.phone"
            placeholder="手机号"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item prop="captcha" class="captcha">
          <el-input
            class="captcha_input"
            v-model="model.captcha"
            placeholder="验证码"
            clearable
          ></el-input>
          <img :src="captchaImg" class="captcha_img" @click="getCaptcha"/>
        </el-form-item>
        <el-form-item class="button_form">
          <el-button type="primary" @click="onSubmit">注册</el-button>
          <el-button type="primary" plain @click="onSubmit_login"
            >去登陆</el-button
          >
        </el-form-item>
      </el-form>
    </div>
    <Footer></Footer>
  </div>
</template>

<script>
import Footer from "@/components/Footer";
import { registerApi, checkCaptchaApi,getCaptchaImage } from "@/api/getData.js";
export default {
  components: {
    Footer,
  },
  data() {
    return {
      captchaImg: '',
      model: {
        username: "",
        password: "",
        phone: "",
        captcha: "",
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 5, max: 10, message: "长度为5到10个字符", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 5, max: 15, message: "长度为5到15个字符", trigger: "blur" },
        ],
        phone: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          { min: 0, max: 12, message: "请输入正确的手机号", trigger: "blur" },
        ],
        captcha: [{ required: true, message: "请输入验证码", trigger: "blur" }],
      },
    };
  },

  mounted() {
    this.getCaptcha()
  },

  methods: {
    getCaptcha(){
        getCaptchaImage().then((res)=>{
          this.captchaImg=window.URL.createObjectURL(res.data)
        })
    },
    onSubmit() {
      checkCaptchaApi(this.model.captcha).then((res1) => {
        if (res1.data.code === 0) {
          registerApi(
            this.model.username,
            this.model.password,
            this.model.phone
          ).then((res2) => {
            if (res2.data.code === 0) {
              this.$message({
                message: res2.data.msg,
                type: "success",
                duration: 3000,
                center: true,
              });
              this.$router.push({name:'login'})
            } else {
              this.$message({
                message: res2.data.msg,
                type: "error",
                duration: 3000,
                center: true,
              });
            }
          });
        }
        else{
          this.message({
            message: res1.data.msg,
            type:"error",
            duration: 3000,
            center: true

          })
        }
      });
    },
    onSubmit_login() {
      this.$router.push("/login");
    },
  },
};
</script>

<style lang="scss" scoped>
.captcha {
  position: relative;
}
.captcha_img {
  position: absolute;
  width: 180px;
  height: 40px;
}
.captcha_input {
  margin-right: 20px;
  position: relative;
  width: 100px;
}
.container {
  position: relative;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: url("../../pic/background2.png") no-repeat 0;
  background-position: center bottom;
  background-size: cover;
}
.register_body {
  width: 300px;
  height: 400px;
  margin-top: -250px;
  margin-left: -150px;
  position: absolute;
  top: 50%;
  left: 50%;
}
.logo {
  display: block;
  text-align: center;
  font-weight: 700;
  font-size: 50px;
  margin-bottom: 20px;
  color: #fff;
  height: 50px;
  text-shadow: 2px 2px 3px rgba(0, 0, 0, 0.5);
}
.button_form {
  padding-left: 80px;
}
</style>