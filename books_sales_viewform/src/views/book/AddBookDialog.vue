<template>
  <el-dialog title="新增图书" :visible.sync="dialogVisible">
    <el-form :model="formData" class="addBookForm">
      <el-form-item :label-width="width" label="书名：">
        <el-input v-model="formData.name" placeholder="书名"></el-input>
      </el-form-item>
      <el-form-item :label-width="width" label="分类：">
        <el-select v-model="formData.category" placeholder="请选择" filterable>
          <el-option
            v-for="item in selectOptions"
            :key="item.id"
            :label="item.name"
            :value="item.name"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item :label-width="width" label="总数：">
        <el-input v-model="formData.count" placeholder="总数"></el-input>
      </el-form-item>
      <el-form-item :label-width="width" label="图书编号：">
        <el-input v-model="formData.bookNum" placeholder="图书编号"></el-input>
      </el-form-item>
      <el-form-item :label-width="width" label="出版商：">
        <el-input v-model="formData.publisher" placeholder="出版商"></el-input>
      </el-form-item>
      <el-form-item :label-width="width" label="单价：">
        <el-input v-model="formData.price" placeholder="单价"></el-input>
      </el-form-item>
      <el-form-item :label-width="width" label="描述">
        <el-input
          type="textarea"
          :rows="4"
          placeholder="请输入内容"
          v-model="formData.detail"
        >
        </el-input>
      </el-form-item>
      <el-form-item :label-width="width" label="封面图:">
        <el-upload
          class="avatar-uploader"
          action="http://localhost:8080/api/book/pri/uploadImage"
          :headers="header"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <img
            v-if="formData.coverImage"
            :src="formData.coverImage"
            class="avatar"
          />
          <el-button v-else type="primary">点击上传</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item :label-width="width" label="书的详细图片：">
        <el-upload
          class="upload-demo"
          action="http://localhost:8080/api/book/pri/uploadImage"
          :headers="header"
          :on-remove="handleRemove"
          :on-success="handleInfoImageSuccess"
          :file-list="fileList"
          list-type="picture"
        >
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">
            只能上传jpg/png文件，且不超过500kb
          </div>
        </el-upload>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancelHandler">取 消</el-button>
      <el-button type="primary" @click="submitHandler">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { addBookApi, addBookInfoImageApi, getCategoryListApi } from "@/api/bookApi.js";
export default {
  data() {
    return {
      header: {
        token: this.$store.state.user.token,
      },
      width: "120px",
      dialogVisible: false,
      formData: {
        name: "",
        category: "",
        count: "",
        publisher: "",
        price: "",
        bookNum: "",
        coverImage: "",
        detail: "",
      },
      fileList: [],
      selectOptions: [],
    };
  },

  mounted() {
    this.getSelectOptions();
  },

  methods: {
    getSelectOptions() {
      getCategoryListApi(this.$store.state.user.token).then((res) => {
        if (res.data.code === 0) {
          this.selectOptions =
            res.data.data.selectOptions;
        } else {
          this.$message({
            message: res.data.msg,
            type: "error",
          });
        }
      });
    },

    cancelHandler() {
      this.dialogVisible = false;
    },
    submitHandler() {
      this.dialogVisible = false;
      addBookApi(this.$store.state.user.token, this.formData).then((res) => {
        if (res.data.code === 0) {
          for (let i = 0; i < this.fileList.length; i++) {
            this.fileList[i].bookId = res.data.data.id;
          }
          addBookInfoImageApi(this.$store.state.user.token, this.fileList).then(
            (res) => {
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
                  duration: 5000,
                  type: "error",
                  center: true,
                });
              }
            }
          );
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
    handleRemove(file) {
      let index=0;
      for(let i=0;i<this.fileList.length;i++){
        if(this.fileList[i].name===file.name)
        {
          index=i;
          break; 
        }
      }
      this.fileList.splice(index,1);
    },
    handleAvatarSuccess(res, file) {
      if (res.code === 0) {
        this.coverImageUrl = URL.createObjectURL(file.raw);
        this.formData.coverImage = res.data.url;
      } else {
        this.$message({
          message: res.msg,
          type: "error",
          duration: 5000,
          center: true,
        });
      }
    },
    handleInfoImageSuccess(res) {
      if (res.code === 0) {
        this.fileList.push({ name: res.data.name, url: res.data.url });
      } else {
        this.$message({
          message: res.msg,
          type: "error",
          duration: 5000,
          center: true,
        });
      }
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isPNG = file.type === "image/png";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG && !isPNG) {
        this.$message.error("上传头像图片只能是 JPG或PNG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return (isJPG || isPNG) && isLt2M;
    },
  },
};
</script>

<style lang="scss" scoped>
.addBookForm {
  el-input {
    width: 300px;
  }
  .avatar {
    width: 150px;
    height: 200px;
  }
}
</style>