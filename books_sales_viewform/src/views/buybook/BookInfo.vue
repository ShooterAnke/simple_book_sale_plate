<template>
  <div>
       <el-dialog title="查看图书" :visible.sync="dialogVisible">
      <el-form :model="formData" class="seeBookForm">
        <el-form-item :label-width="width" >
          <span>书名：{{formData.name}}</span>
        </el-form-item>
        <el-form-item :label-width="width" >
          <span>分类：{{formData.category}}</span>
        </el-form-item>
        <el-form-item :label-width="width" >
          <span>库存：{{formData.count}}</span>
        </el-form-item>
        <el-form-item :label-width="width" >
          <span>编号：{{formData.bookNum}}</span>
        </el-form-item>
        <el-form-item :label-width="width" >
           <span>出版商：{{formData.publisher}}</span>
        </el-form-item>
        <el-form-item :label-width="width" >
           <span>单价：{{formData.price}}</span>
        </el-form-item>
        <el-form-item :label-width="width" >
           <span>详细：{{formData.detail}}</span>
        </el-form-item>
        <el-form-item :label-width="width" >
            <div>封面图：</div>
            <img :src="formData.coverImage"/>
        </el-form-item>
        <el-form-item :label-width="width">
            <div>详细图：</div>
            <img v-for="(item,index) in fileList" :key="index" :src="item.url"/>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
    data(){
        return{
            width:'120',
            dialogVisible:false,
            formData:[],
            fileList:[]
        }
    }
}
</script>

<style lang="scss" scoped>
.seeBookForm{
    span,div{
        font-size: 20px;
    }
    img{
        width: 300px;
        height: 450px;
    }

}
   
</style>