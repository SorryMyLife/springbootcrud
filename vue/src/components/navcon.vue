/**
* 头部菜单
*/ 
<template>
  <el-menu class="el-menu-demo" mode="horizontal" background-color="#334157" text-color="#fff" active-text-color="#fff">
    <el-button class="buttonimg">
      <img class="showimg" :src="collapsed?imgsq:imgshow" @click="toggle(collapsed)">
    </el-button>
    <el-submenu index="2" class="submenu">
      <!-- <template slot="title">{{user.userRealName}}</template> -->
      <template slot="title">{{user}}</template>
      <el-menu-item index="2-1">设置</el-menu-item>
      <el-menu-item @click="content()" index="2-2">个人中心</el-menu-item>
      <el-menu-item @click="exit()" index="2-3">退出</el-menu-item>
    </el-submenu>
  </el-menu>
</template>
<script>
import { httpget,httppost } from "../api/userMG";
export default {
  name: 'navcon',
  data() {
    return {
      collapsed: true,
      imgshow: require('../assets/img/show.png'),
      imgsq: require('../assets/img/sq.png'),
      user: ''
    }
  },
  // 创建完毕状态(里面是操作)
  created() {
    this.checkLogin()
    
  },
  methods: {
    checkLogin(){
      if(document.cookie === '' || document.cookie === undefined){
        this.$store.commit('logout', 'false')
            this.$message({
              type: 'info',
              message: '已退出登录!'
            })
            this.$router.push({ path: '/login' })
      }
      this.user = document.cookie.split(";")[1].split("=")[1]
    },
    // 退出登录
    exit() {
      this.$confirm('退出登录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          httppost("/aa/crm/system-user/userloginOut",document.cookie).then((res)=>{
            if(res.code == 0){
              this.$message({
                type: 'info',
                message: '已退出登录!'
              })
              this.$store.commit('logout', 'false')
              this.$router.push({ path: '/login' })
              
            }
          });
          
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          })
        })
    },
    // 切换显示
    toggle(showtype) {
      this.collapsed = !showtype
      this.$root.Bus.$emit('toggle', this.collapsed)
    }
  }
}
</script>
<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  border: none;
}
.submenu {
  float: right;
}
.buttonimg {
  height: 60px;
  background-color: transparent;
  border: none;
}
.showimg {
  width: 26px;
  height: 26px;
  position: absolute;
  top: 17px;
  left: 17px;
}
.showimg:active {
  border: none;
}
</style>