<template>
  <div>
    <el-dialog title="伪支付" :visible.sync="dialogVisible" width="30%">
      <el-form>
        <el-form-item prop="captcha" class="captcha">
          <el-input
            class="captcha_input"
            v-model="captcha"
            placeholder="验证码"
            clearable
          ></el-input>
          <img :src="captchaImg" class="captcha_img" @click="getCaptcha" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelHandler">取 消</el-button>
        <el-button type="primary" @click="submitHandler">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { checkCaptchaApi, getCaptchaImage } from "@/api/getData.js";
import { cleanCart, addOrder } from "@/api/cartApi.js";
export default {
  data() {
    return {
      dialogVisible: false,
      captcha: "",
      captchaImg: "",
      cardData: [],
    };
  },

  mounted() {
    this.getCaptcha();
  },

  methods: {
    getCaptcha() {
      getCaptchaImage().then((res) => {
        this.captchaImg = window.URL.createObjectURL(res.data);
      });
    },
    submitHandler() {
      this.dialogVisible = false;
      checkCaptchaApi(this.captcha).then((res1) => {
        if (res1.data.code === 0) {
          cleanCart(this.$store.state.user.token).then((res) => {
            if (res.data.code === 0) {
              let op=[];
              for(let i=0;i<this.cardData.length;i++){
                op.push({
                  id:this.cardData[i].id,userId:this.cardData[i].userId,
                  bookId:this.cardData[i].bookId,count:this.cardData[i].count,
                  allPay:this.cardData[i].allPay,book:this.cardData[i].book
                })
              }
              addOrder(this.$store.state.user.token, op).then(
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
            } else {
              this.$message({
                message: res.data.msg,
                type: "error",
              });
            }
          });
          console.log("支付成功");
        } else {
          this.message({
            message: res1.data.msg,
            type: "error",
            duration: 3000,
            center: true,
          });
        }
      });
    },
    cancelHandler() {
      this.dialogVisible = false;
    },
  },
};
</script>

<style lang="scss" scoped>
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