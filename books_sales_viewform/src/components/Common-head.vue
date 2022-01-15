<template>
  <header>
    <div class="left-content">
      <el-button
        plain
        icon="el-icon-menu"
        size="mini"
        @click="collapseMenu"
      ></el-button>
    </div>

    <div class="right-conent">
      <el-dropdown trigger="click" size="mini">
        <span class="el-dropdown-link">
          <img :src="userImg" class="user" />
          <span class="user_info">{{ userName }} </span>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="personal"> 个人中心 </el-dropdown-item>
          <el-dropdown-item @click.native="logOut"> 退出 </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </header>
</template>

<script>
import { mapState } from "vuex";
import { findByUsernameApi } from "@/api/getData.js";
export default {
  computed: {
    ...mapState({
      current: (state) => state.tab.currentMenu,
      /* user: (state) => state.user.currentUser, */
    }),
  },
  data() {
    return {
      userName: "",
      userImg: "",
    };
  },
  mounted() {
    this.findByUsername();
  },

  methods: {
    findByUsername() {
      let token = this.$store.state.user.token;
      findByUsernameApi(token).then((res) => {
        if (res.data.code === 0) {
          let user = res.data.data.user;
          this.userName = user.name;
          this.userImg = user.headImage;
        } else {
          this.$store.commit("clearToken");
          this.$store.commit("clearMenu");
          this.$message({
            message: res.data.msg,
            type: "error",
            duration: 3000,
            center: true,
          });
        }
      });
    },
    collapseMenu() {
      this.$store.commit("collapseMenu");
    },
    logOut() {
      console.log("logout");
      this.$store.commit("clearToken");
      this.$store.commit("clearMenu");
      location.reload();
    },
    personal(){
      this.$router.push({name:'personal'})
    }
  },
};
</script>

<style lang="scss" scoped>
header {
  display: flex;
  height: 100%;
  align-content: center;
  justify-content: space-between;
}
.left-content {
  display: flex;
  align-items: center;
  .el-button {
    margin-right: 20px;
  }
}
.el-dropdown {
  top: 50%;
  margin-top: -20px;
}
.el-dropdown-link {
  display: flex;
  justify-content: center;
}
.user {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
.user_info {
  margin-right: 10px;
  color: rgba(255, 205, 42, 0.63);
  font-size: 20px;
  padding-top: 12px;
}
.el-breadcrumb__item {
  .el-breadcrumb__inner {
    color: #666666;
    font-weight: normal;
  }
  &:last-child {
    .el-breadcrumb__inner {
      color: #ffffff;
    }
  }
}
</style>