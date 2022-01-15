<template>
  <div class="common-table">
    <el-table
      :data="tableData"
      border
      style="width: 100%; font-size: 20px"
      v-loading="loading"
    >
      <el-table-column label="序号" width="85">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{
            (config.page - 1) * 10 + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="头像" width="90">
        <template slot-scope="scope">
          <img :src="scope.row.headImage" class="head_img" />
        </template>
      </el-table-column>

      <el-table-column
        v-for="(item, index) in tableLabel"
        :key="index"
        :prop="item.prop"
        :label="item.label"
        :width="item.width"
      >
      </el-table-column>
      <el-table-column fixed="right" label="操作" min-width="150">
        <template slot-scope="scope">
          <el-button @click="editClick(scope.row)" type="primary" size="mini"
            >编辑</el-button
          >
          <el-button @click="deleteClick(scope.row)" type="danger" size="mini"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="pager"
      layout="prev, pager, next"
      :total="config.total"
      :current-page.sync="config.page"
      @current-change="changePage"
      :page-size="10"
    >
    </el-pagination>
    <edit-dialog ref="dialog" :user="dialogData"></edit-dialog>
  </div>
</template>

<script>
import { getUserPageDataApi, deleteByUsername,searchPage } from "@/api/getData.js";
import dateFormat from "@/utils/DateFormat.js";
import EditDialog from "./EditDialog.vue";
export default {
  components: {
    EditDialog,
  },
  data() {
    return {
      isSearchPage:0,
      searchConfig:{
        username:'',
        option:1,
      },
        loading:true,
      dialogData: {},
      config: {
        page: 1,
        total: 0,
        loading: false,
      },
      tableLabel: [
        {
          prop: "username",
          label: "用户名",
          width: "100",
        },
        {
          prop: "phone",
          label: "手机号",
          width: "200",
        },
        {
          prop: "address",
          label: "地址",
          width: "200",
        },
        {
          prop: "createTime",
          label: "创建时间",
          width: "250",
        },
        {
          prop: "updateTime",
          label: "更新时间",
          width: "250",
        },
        {
          prop: "name",
          label: "昵称",
          width: "100",
        },
        {
          prop: "power",
          label: "权限等级",
          width: "100",
        },
      ],
      tableData: [],
    };
  },

  mounted() {
    this.getPageData();
  },
  
  methods: {
    changePage(page) {
      this.config.page = page;
      if(this.isSearchPage===1){
        this.searchPage()
      }else{
        this.getPageData();
      }
      
    },
    getPageData() {
      getUserPageDataApi(
        this.$store.state.user.token,
        this.config.page,
        10
      ).then((res) => {
        if (res.data.code === 0) {
          this.config.total = res.data.data.total;
          this.tableData = res.data.data.tableData;
          this.formatDate();
          this.loading=false;
        } else {
          this.$message({
            message: res.data.msg,
            type: "error",
            duration: 3000,
            center: true,
          });
        }
      });
    },
    formatDate() {
      for (let i = 0; i < this.tableData.length; i++) {
        this.tableData[i].createTime = dateFormat(this.tableData[i].createTime);
        this.tableData[i].updateTime = dateFormat(this.tableData[i].updateTime);
      }
    },
    editClick(row) {
      this.$refs.dialog.dialogFormVisible = true;
      this.dialogData = row;
    },
    deleteClick(row) {
      console.log(row);
      this.$confirm("此操作将删除改用户，是否确认", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          deleteByUsername(this.$store.state.user.token, row.username).then(
            (res) => {
              if (res.data.code === 0) {
                this.$message({
                  message: res.data.msg,
                  type: "success",
                  center: true,
                  duration: 5000,
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
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    searchPage(){
       searchPage(this.$store.state.user.token,this.searchConfig.option,this.searchConfig.username,this.config.page,10).then((res)=>{
        if(res.data.code===0){
          this.tableData=res.data.data.tableData
          this.config.total=res.data.data.total
          this.formatDate();
        }
        else{
          this.$message({
            message:res.data.msg,
            type:'error',
            duration:5000,
            center:true
          })
        }
      })
    },
    searchPageData(option,username){
      this.searchConfig.option=option
      this.searchConfig.user=username
      this.isSearchPage=1;
      this.config.page=1;
      searchPage(this.$store.state.user.token,option,username,this.config.page,10).then((res)=>{
        if(res.data.code===0){
          this.config.total=res.data.data.total
          this.tableData=res.data.data.tableData
          this.formatDate();
          this.$message({
            message:res.data.msg,
            type:'success',
            duration:5000,
            center:true
          })
        }
        else{
          this.$message({
            message:res.data.msg,
            type:'error',
            duration:5000,
            center:true
          })
        }
      })
    }
  },
};
</script>

<style lang="scss" scoped>
el-table-column {
  text-align: center;
}
.head_img {
  border-radius: 50%;
  width: 70px;
  height: 70px;
}
</style>