<template>
  <div class="footer">
    <div class="footer_link">
      <a href="#" target="_blank" class="link1">详细 </a>
      <a href="http://www.shooteranke.site" target="_blank" class="link2">联系</a>
    </div>
    <div class="design_show">
      <div class="designer_info">
        <a href="http://www.shooteranke.site" target="_blank" style="color: #fff">
          <strong style="font-family: '微软雅黑'">前端ui</strong>
        </a>
        <a href="http://www.shooteranke.site" target="_blank" class="face"
          ><img id="face" src="@/pic/head.png" alt="designed by anke"
        /></a>
      </div>
    </div>
  </div>
</template>
<script>
export default {
};
</script>
<style>
.footer {
  position: fixed;
  top: auto;
  right: 0;
  bottom: 0;
  left: 0;
}
.footer_link {
  position: absolute;
  width: 141px;
  height: 78px;
  bottom: 0;
  padding: 0 12px;
  margin: 0 0 0 0;
  line-height: 25px;
  text-align: center;
  opacity: 1;
  color: #fff;
}
.design_show {
  position: fixed;
  bottom: 0;
  right: 0;
  padding: 0 12px;
  margin: 0 0 13px 0;
  line-height: 60px;
}
.designer_info {
  position: relative;
  color: #fff;
}
.link1,
.link2 {
  font-size: 14px;
  margin-right: 12px;
  color: #fff;
}
#face {
  margin: 0 0 0 10px;
  vertical-align: middle;
  border-radius: 20px;
  padding: 0;
  height: 60px;
  width: 60px;
}
</style>