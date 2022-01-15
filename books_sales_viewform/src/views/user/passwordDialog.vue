<template>
  <el-dialog title="修改密码" :visible.sync="dialogFormVisible">
    <el-form>
      <el-form-item label="新密码" :label-width="width">
        <el-input v-model="password" ></el-input>
      </el-form-item>
      <el-form-item class="captcha">
        <el-input
          class="captcha_input"
          v-model="captcha"
          placeholder="验证码"
          clearable
        ></el-input>
        <img :src="captchaImg" class="captcha_img" @click="getCaptcha" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancelHandler">取 消</el-button>
      <el-button type="primary" @click="submitHandler">提交</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { checkCaptchaApi, getCaptchaImage ,updatePassword} from "@/api/getData.js";
export default {

  data() {
    return {
      username:'',
      captchaImg: "",
      password: "",
      captcha: "",
      dialogFormVisible: false,
      width: "120px",
    };
  },

  mounted() {
    this.getCaptcha()
  },

  methods: {
    cancelHandler() {
      this.dialogFormVisible = false;
    },
    submitHandler() {
      this.dialogFormVisible = false;
      checkCaptchaApi(this.captcha).then((res) => {
        if (res.data.code === 0) {
            updatePassword(this.$store.state.user.token,this.username,this.password).then((res)=>{
                if(res.data.code===0){
                   this.$message({
                    message:res.data.msg,
                    type:'success',
                    duration:5000,
                    center:true
                  })

                }else
                {
                  this.$message({
                    message:res.data.msg,
                    type:'error',
                    duration:5000,
                    center:true
                  })
                }
            })
       } else {
          this.$message({
            message: res.data.msg,
            type:"error",
            duration:5000,
            center:true
          });
        }
      });
    },
    getCaptcha() {
      getCaptchaImage().then((res) => {
        this.captchaImg = window.URL.createObjectURL(res.data);
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.captcha {
  position: relative;
  left: 300px;
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
</style>