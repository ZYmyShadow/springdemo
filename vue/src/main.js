import Vue from 'vue';
import App from './App.vue';
import router from './router';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios';
import md5 from 'js-md5';

Vue.prototype.$axios = axios;
Vue.use(ElementUI);

Vue.config.productionTip = false;

Vue.prototype.errorMsgText = function(str){
  this.$message({
    message: str,
    duration: "1000",
    type: "error"
  });
}
Vue.prototype.successMsgText = function(str){
  this.$message({
    message: str,
    duration: "1000",
    type: "success"
  });
}
Vue.prototype.warnMsgText = function(str){
  this.$message({
    message: str,
    duration: "1000",
    type: "warning"
  });
}
Vue.prototype.getToken = function() {
  const token = localStorage.getItem("token");
  if (token === "" || token == null) {
    this.$message({
      showClose: true,
      message: "请先登录",
      type: "error",
      duration: "3000"
    });
    this.$router.push({ path: "/" });
  }
  return token;
}
Vue.prototype.setToken = function(token) {
  localStorage.setItem("token", token);
}
Vue.prototype.getUser = function(){
  const user = localStorage.getItem("user");
  if(user === "" || user == null){
    this.$message({
      showClose: true,
      message: "请先登录",
      type: "error",
      duration: "3000"
    });
    this.$router.push({ path: "/" });
  }
  return user;
}
Vue.prototype.setUser = function(user){
  localStorage.setItem("user", user);
}
Vue.prototype.postdata = function (url,data) {
    this.$axios({
      method: 'post',
      url: url,
      data: data,
      headers: {
        token: this.getToken()
      }
    }).then(result => {
        if (result.status === 200) {
          var data = result.data;
          if(data.status === 200){
            return data;
          } else {
            this.errorMsgText(data.msg);
          }
        } else {
          this.errorMsgText("网络错误，请联系管理员");
        }
    });
}
Vue.prototype.gotopage = function(name){
  this.$router.push({ path: "/"+name });
}
Vue.prototype.md5encrypt = function(str){
  return md5(str);
}
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
