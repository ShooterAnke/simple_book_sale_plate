<template>
  <el-menu
    :collapse="isCollapse"
    class="menu"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04d"
    :default-active="$route.path"
  >
    <h3 v-show="isCollapse">Anke</h3>
    <h3 v-show="!isCollapse">图书系统</h3>
    <el-menu-item
      v-for="item in noChildren"
      :key="item.path"
      @click="clickMenu(item)"
      :index="item.path"
    >
      <i :class="'el-icon-' + item.icon"></i>
      <span slot="title">
        {{ item.label }}
      </span>
    </el-menu-item>
    <el-submenu
      v-for="(item, index) in hasChildren"
      :index="item.label"
      :key="index"
    >
      <template slot="title">
        <i :class="'el-icon-' + item.icon"></i>
        <span slot="title">{{ item.label }}</span>
      </template>
      <el-menu-item-group>
        <el-menu-item
          v-for="(subItem, subIndex) in item.children"
          :key="subIndex"
          :index="subItem.path"
          @click="clickMenu(subItem)"
        >
        <i class="'el-icon-'+subItem.icon"></i>
        <span slot="title">{{subItem.label}}</span>
        </el-menu-item>
      </el-menu-item-group>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  computed: {
    noChildren() {
      
      return this.menu.filter((item) => item.children.length===0);
    },
    hasChildren() {
      return this.menu.filter((item) => item.children.length>0);
    },
    isCollapse() {
      return this.$store.state.tab.isCollapse;
    },
    menu() {
      return this.$store.state.tab.menu;
    },
  },
  data() {
    return {
    };
  },

  mounted() {},

  methods: {
    clickMenu(item) {
      this.$router.push({ name: item.name });
      this.$store.commit("selectMenu", item);
    },
  },
};
</script>

<style lang="scss" scoped>
.menu {
  height: 100%;
  border: none;
  h3{
      color: #ffffff;
      text-align: center;
      line-height: 60px;
  }
  .el-menu-item{
    margin-bottom: 10px;
    font-size: 20px;
  }
}
.menu:not(.el-menu--collapse){
    width: 200px;
    min-height: 400px;
}
</style>