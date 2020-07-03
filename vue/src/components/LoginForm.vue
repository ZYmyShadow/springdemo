<template>
    <div class="loginForm">
        <div>
            登录
            <i class="el-icon-user-solid"></i>
        </div>
        <div class="row">
            <el-input
                    class="input"
                    id="username"
                    type="text"
                    placeholder="请输入用户名"
                    v-model="username"
                    clearable
                    @keyup.enter.native="login"
            />
        </div>
        <div class="row">
            <el-input
                    class="input"
                    id="password"
                    type="password"
                    placeholder="请输入密码"
                    v-model="password"
                    clearable
                    @keyup.enter.native="login"
            />
        </div>
        <div class="row">
            <el-button @click="login" type="success" plain round>登录</el-button>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                username: "",
                password: ""
            };
        },
        methods: {
            /**
             * 新增
             */
            login() {
                // 判断输入数据
                if (this.username === "" || this.secretKey === "") {
                    this.warnMsgText("请正确输入数据");
                    return;
                }
                var data = {
                    username: this.ruleForm.username,
                    password: this.ruleForm.password
                }
                var result = this.postdata("/demo/sys/login",data);
                this.successMsgText("登陆成功");
                this.username = "";
                this.password = "";
                this.setUser(result.user);
                this.setToken(result.token);
                this.gotopage("index");
            }
        }
    };
</script>


<style scoped>
    .input {
        width: 300px;
        margin: 0;
        padding: 0;
    }
    .row {
        padding: 10px 0px;
    }
</style>