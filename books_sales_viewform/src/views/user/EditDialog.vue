<template>
<div>
  <el-dialog title="修改信息" :visible.sync="dialogFormVisible">
    <el-form :model="user">
      <el-form-item label="用户名" :label-width="width">
        <el-input v-model="user.username" disabled></el-input>
      </el-form-item>
      <el-form-item label="修改密码" :label-width="width">
        <el-button type="primary" @click="changePassword">修改密码</el-button>
      </el-form-item>
      <el-form-item label="手机号" :label-width="width">
          <el-input v-model="user.phone"></el-input>
      </el-form-item>
      
      <el-form-item label="地址" :label-width="width">
          <el-input v-model="user.address"></el-input>
      </el-form-item>
      <el-form-item label="头像url" :label-width="width">
          <el-input v-model="user.headImage"></el-input>
      </el-form-item>
      
      <el-form-item label="昵称" :label-width="width">
          <el-input v-model="user.name"></el-input>
      </el-form-item>
      
      <el-form-item label="权限等级" :label-width="width" >
          <el-input v-model="user.power" ></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancelHandler">取 消</el-button>
      <el-button type="primary" @click="submitHandler"
        >提交</el-button
      >
    </div>
  </el-dialog>
  <password-dialog ref="dialog" ></password-dialog>
</div>
</template>

<script>
import {updateByUsername} from '@/api/getData.js'
import PasswordDialog from './passwordDialog.vue'
export default {
  components:{
    PasswordDialog
  },
    props:{
        user:{
            type:Object,
            required:true
        }
    },
  data() {
    return {
        width:'120px',
        dialogFormVisible:false
    };
  },

  mounted() {},

  methods: {
      submitHandler(){
          this.dialogFormVisible=false;
          console.log(this.user);
          updateByUsername(this.$store.state.user.token,this.user).then((res)=>{
              if(res.data.code===0){
                  this.$message({
                      message:res.data.msg,
                      type:'success',
                      center:true,
                      duration:5000
                  })
                  location.reload()
              }
              else{
                  this.$message({
                      message:res.data.msg,
                      type:'error',
                      center:true,
                      duration:5000
                  })
              }
          })
      },
      cancelHandler(){
          this.dialogFormVisible=false;
      },
      changePassword(){
        this.$refs.dialog.dialogFormVisible=true
        this.$refs.dialog.username=this.user.username
      }
  },
};
</script>

<style lang="scss" scoped>
</style>