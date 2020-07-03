<template>
  <div class="index">
    <el-container>
      <el-header height="60px" class="clearfix">
        <div class="logo">
          <img class="fl" src="src/assets/images/logo.png" alt="">
          <h3 class="fl">Spring权限管理系统</h3>
        </div>
        <div class="user">
          <span class="el-icon-user-solid"></span>
          <span class="username">{{username}}</span>
          <span @click="logout" class="logout el-icon-switch-button"></span>
        </div>
      </el-header>
      <el-container class="content">
        <div>
          <el-menu
            router
            class="sidebar"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
            :collapse="isCollapse"
            :default-active="active"
            unique-opened>
          </el-menu>
        </div>
        <el-main>
          <span @click="toggleCollapse" class="toggle" :class="isCollapseClass"></span>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>

export default {
  data ()  {
    return {
      isCollapse: false,
      activeName: '1',
      token: this.getToken(),
      userId: "",
      username:""
    }
  },
  computed: {
    active () {
      return this.$route.path
    },
    isCollapseClass () {
      return this.isCollapse ? "el-icon-s-unfold" : "el-icon-s-fold"
    }
  },
  mounted() {
    this.username = this.$route.params.username;
    this.userId = this.$route.params.userId;
  },
  methods: {
    toggleCollapse () {
      this.isCollapse = !this.isCollapse
    },
    logout () {
      this.$confirm('是否要退出本系统?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios({
          method: "post",
          url: "/demo/sys/logout",
          headers:{
            token:this.token
          },
          data: {}
        }).then(result => {
          if (result.status === 200) {
            var data = result.data;
            if (data.status === 200) {
              this.$message({
                message: data.msg,
                type: "success",
                duration: "600"
              });
              this.$router.push('/')
            } else {
              this.$message({
                message: "退出失败",
                type: "error",
                duration: "3000"
              });
            }
          } else {
            this.$message({
              message: "退出失败",
              type: "error",
              duration: "3000"
            });
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    }
  }
}
</script>

<style lang="less">
.index,.el-container {
  height: 100%;
}

.el-header {
  line-height: 50px;
  padding: 5px 20px !important;
  background-color: #409EFF;
  color: #fff;

  .logo {
    float: left;

    h3 {
      font-size: 20px;
    }
  }

  .user {
    float: right;
    margin-right: 20px;
    font-size: 20px;
    span {
      margin-right: 20px;
    }
    .logout {
      cursor: pointer;
    }
  }
}

.sidebar:not(.el-menu--collapse) {
  width: 200px;
  min-height: 100%;
}
.sidebar.el-menu--collapse {
  min-height: 100%;
}
.el-menu {
  border-right: none;
}

.el-main {
  position: relative;
  padding: 30px;

  .toggle {
    position: absolute;
    top: 0;
    left: 0;
    font-size: 20px;
    cursor: pointer;
  }
}
</style>