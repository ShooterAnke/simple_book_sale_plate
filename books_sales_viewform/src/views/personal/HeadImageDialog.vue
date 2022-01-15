<template>
  <el-dialog title="修改头像" :visible.sync="dialogVisible" >
    <el-upload
      class="avatar-uploader"
      action="http://localhost:8080/api/user/pri/uploadHeadImage"
      :headers="headers"
      :show-file-list="false"
      :on-success="handleAvatarSuccess"
      :before-upload="beforeAvatarUpload"
    >
      <img v-if="imageUrl" :src="imageUrl" class="avatar" />
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>

    <span slot="footer" class="dialog-footer">
      <el-button @click="cancelHandler">取 消</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      headers:{
        'token':this.$store.state.user.token
      },
      dialogVisible: false,
      imageUrl: "",
    };
  },

  mounted() {},

  methods: {
    cancelHandler() {
      this.dialogVisible = false;
    },
    handleAvatarSuccess(res,file) {
      this.$message({
        message:res.msg,
        type:'success',
        duration:5000,
        center:true
      })
     this.imageUrl = URL.createObjectURL(file.raw);
     this.dialogVisible=false
     location.reload();
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isPNG =file.type==="image/png";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG&&!isPNG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return (isJPG||isPNG) && isLt2M;
    },
  },
};
</script>

<style lang="scss" scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>