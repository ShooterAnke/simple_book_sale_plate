<template>
  <div>
    <el-form :model="user" class="user-form">
      <el-form-item>
        <img :src="user.headImage" class="head-image" />
        <el-button type="primary" @click="updateHeadImage">修改 </el-button>
      </el-form-item>
      <el-form-item label="用户名" :label-width="width">
        <el-input v-model="user.username" disabled></el-input>
      </el-form-item>
      <el-form-item label="手机号" :label-width="width">
        <el-input v-model="user.phone"></el-input>
        <el-button type="primary" @click="updatePhone">修改 </el-button>
      </el-form-item>

      <el-form-item label="地址" :label-width="width">
        <el-input v-model="user.address"></el-input>
        <el-button type="primary" @click="updateAddress">修改 </el-button>
      </el-form-item>
      <el-form-item label="昵称" :label-width="width">
        <el-input v-model="user.name"></el-input>
        <el-button type="primary" @click="updateName">修改 </el-button>
      </el-form-item>

      <el-form-item label="我是：" :label-width="width">
        <span class="whoami">{{ whoami }}</span>
      </el-form-item>
    </el-form>
    <head-image-dialog ref="dialog"></head-image-dialog>
  </div>
</template>

<script>
import {
  findByUsernameApi,
  updatePhoneApi,
  updateNameApi,
  updateAddressApi,
} from "@/api/getData.js";
import HeadImageDialog from './HeadImageDialog.vue'
export default {
    components:{
        HeadImageDialog
    },
  data() {
    return {
      width: "120px",
      user: {},
      whoami: "",
    };
  },

  mounted() {
    this.getUserData();
  },

  methods: {
    updateHeadImage() {
        this.$refs.dialog.dialogVisible=true;
    },
    getUserData() {
      findByUsernameApi(this.$store.state.user.token).then((res) => {
        if (res.data.code === 0) {
          this.user = res.data.data.user;
          if (this.user.power === 3) {
            this.whoami = "超级管理员";
          }
          if (this.user.power === 2) {
            this.whoami = "普通管理员";
          }
          if (this.user.power === 1) {
            this.whoami = "客户";
          }
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
    updatePhone() {
      updatePhoneApi(
        this.$store.state.user.token,
        this.user.username,
        this.user.phone
      ).then((res) => {
        if (res.data.code === 0) {
          this.$message({
            message: res.data.msg,
            type: "success",
            duration: 5000,
            center: true,
          });
          location.reload();
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
    updateName() {
      updateNameApi(
        this.$store.state.user.token,
        this.user.username,
        this.user.name
      ).then((res) => {
        if (res.data.code === 0) {
          this.$message({
            message: res.data.msg,
            type: "success",
            duration: 5000,
            center: true,
          });
          location.reload();
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
    updateAddress() {
      updateAddressApi(
        this.$store.state.user.token,
        this.user.username,
        this.user.address
      ).then((res) => {
        if (res.data.code === 0) {
          this.$message({
            message: res.data.msg,
            type: "success",
            duration: 5000,
            center: true,
          });
          location.reload();
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
  },
};
</script>

<style lang="scss" scoped>
.user-form {
  position: absolute;
  margin-top: -300px;
  margin-left: -150px;
  top: 50%;
  left: 50%;
  .el-input {
    width: 200px;
    margin-right: 10px;
  }
}
.whoami {
  color: red;
}
.head-image {
  border-radius: 50%;
  margin-left: 100px;
  width: 160px;
  height: 160px;
}
</style>