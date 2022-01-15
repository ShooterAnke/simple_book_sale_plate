<template>
  <div>
    <el-dialog title="新增分类" :visible.sync="dialogVisible">
      <el-form :model="formData" class="addBookForm">
        <el-form-item :label-width="width" label="新增分类名：">
          <el-input v-model="formData.name" placeholder="分类名"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelHandler">取 消</el-button>
        <el-button type="primary" @click="submitHandler">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addCategoryApi} from '@/api/bookApi.js'
export default {
  data() {
    return {
        width:'120',
        dialogVisible:false,
        formData:{
            name:''
        }
    };
  },

  mounted() {},

  methods: {
      
         submitHandler(){
          this.dialogVisible=false
          addCategoryApi(this.$store.state.user.token,this.formData.name).then((res)=>{
              if(res.data.code===0){
                  this.$message({
                      message:res.data.msg,
                      type:'success'
                  })
                  location.reload()
              }
              else{
                  this.$message({
                      message:res.data.msg,
                      type:'error'
                  })
              }
          })
      },
      cancelHandler(){
          this.dialogVisible=false
      },
   
  },
};
</script>

<style lang="scss" scoped>
</style>