<template>
    <el-row>
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="filters">
                <el-form-item>
                    <el-input v-model="filters.keyword" placeholder="姓名"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" class="el-icon-search" :loading="selLoading" v-on:click="getUsers">查询
                    </el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="success" class="el-icon-plus" @click.native="showDialogForm">新增</el-button>
                </el-form-item>
                <!-- <el-form-item>
                <el-button type="error" class="el-icon-edit" v-on:click="editUser">编辑</el-button>
                </el-form-item> -->
                <el-form-item>
                    <el-button type="danger" class="el-icon-delete" @click="delUser">删除</el-button>
                </el-form-item>
            </el-form>
        </el-col>

        <!--列表-->
        <el-col>
            <el-table :data="users" v-loading="listLoading" element-loading-text="拼命加载中"
                      @selection-change="handleSelectionChange" style="width: 100%;">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <!-- <el-table-column prop="id" label="id"  sortable>
                </el-table-column> -->
                <el-table-column prop="userName" label="姓名" sortable>
                </el-table-column>
                <el-table-column prop="email" label="邮箱" sortable>
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间" sortable>
                    <template slot-scope="scope">
                        <span>{{scope.row.createTime}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="lastLoginTime" label="上次登录时间" sortable>
                    <template slot-scope="scope">
                        <span>{{scope.row.lastLoginTime}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="role" label="角色" sortable>
                </el-table-column>
                <el-table-column
                        fixed="right"
                        label="允许登陆" width="120">
                    <template slot-scope="scope">
                        <el-switch
                                @change="switchChange(scope.row.status,scope.row.id)"
                                v-model="scope.row.status"
                                active-color="#13ce66"
                                inactive-color="#ff4949"
                                :active-value="active"
                                :inactive-value="inactive">
                        </el-switch>
                    </template>
                </el-table-column>
            </el-table>
        </el-col>
        <el-col>
            <div class="block" style="float: right;margin-right: 10px;margin-top: 10px;">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="startPage"
                        :page-sizes="pageSizes"
                        :page-size="pageSize"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="total">
                </el-pagination>
            </div>
        </el-col>
        <el-col :span="2">
            <el-dialog title="新增用户" :visible.sync="dialogFormVisible">
                <div style="width:80%;margin: 0 auto">
                    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" :inline="false"
                             label-width="90px" class="demo-ruleForm">
                        <el-form-item label="昵称" prop="name">
                            <el-input type="text" placeholder="用户昵称" auto-complete="off"
                                      v-model="ruleForm.name"></el-input>
                        </el-form-item>
                        <el-form-item label="邮箱" prop="email">
                            <el-input type="text" placeholder="用户邮箱" auto-complete="off"
                                      v-model="ruleForm.email"></el-input>
                        </el-form-item>
                        <el-form-item label="密码" prop="pass1">
                            <el-input type="password" placeholder="密码以字母开头，长度在6~18之间，只能包含字符、数字和下划线" auto-complete="off"
                                      v-model="ruleForm.pass1"></el-input>
                        </el-form-item>
                        <el-form-item label="确认密码" prop="pass2">
                            <el-input type="password" placeholder="确认密码" auto-complete="off"
                                      v-model="ruleForm.pass2"></el-input>
                        </el-form-item>
                        <el-form-item label="角色" prop="role">
                            <el-select v-model="ruleForm.role" filterable placeholder="请选择">
                                <el-option
                                        v-for="item in roles"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="是否启用">
                            <el-switch
                                    v-model="ruleForm.switchValue"
                                    active-color="#13ce66"
                                    inactive-color="#ff4949">
                            </el-switch>
                        </el-form-item>
                    </el-form>
                </div>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                    <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
                </div>
            </el-dialog>
        </el-col>
    </el-row>
</template>

<script>
    export default {
        data() {
// 检测两次密码是否一致
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'))
                } else if (value !== this.ruleForm.pass1) {
                    callback(new Error('两次输入密码不一致!'))
                } else {
                    callback();
                }
            }
            return {
                filters: {
                    keyword: ''
                },
                ruleForm: {
                    name: '',
                    pass1: '',
                    pass2: '',
                    email: '',
                    switchValue: true,
                    role: ''
                },
                pageSizes: [30, 50, 80, 100],
                startPage: 1,
                pageSize: 30,
                total: 0,
                users: [],
                page: 1,
                listLoading: false,
                selLoading: false,
                dialogFormVisible: false,
                formLabelWidth: '120px',
                userIds: [], // 用户ids
                roles: [], // 角色
                inactive: 0,
                active: 1,
// 表单验证
                rules: {
                    name: [
                        {required: true, message: '请输入用户昵称'},
                        {min: 2, max: 10, message: '长度在 5 到 12 个字符'}
                    ],
                    email: [
                        {required: true, message: '请输入邮箱地址'},
                        {type: 'email', message: '请输入正确的邮箱地址'}
                    ],
                    pass1: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {validator: 'regexp', pattern: /^[a-zA-Z]\w{5,17}$/, message: '密码格式不正确', trigger: 'change,blur'}
                    ],
                    pass2: [
                        {required: true, message: '请再次输入密码', trigger: 'blur'},
                        {validator: validatePass2, trigger: 'blur'}
                    ],
                    role: [
                        {required: true, message: '请选择用户角色', trigger: 'change'}
                    ]
                }
            }
        },
        methods: {
// 查询用户
            async getUsers() {
                
                this.listLoading = true
                let params = {
                    startPage: this.startPage,
                    pageSize: this.pageSize,
                    keyword: this.filters.keyword
                }
                let data = this.postdata('UserApi/v1/findUserByPage', params)
                if (!data.data) {
                    this.listLoading = false
                    return
                }
                if (data.data && data.data.status === 200) {
                    this.users = data.data.data
                    this.total = data.data.total
                } else {
                    this.message(true, data.data.msg, 'error')
                    this.users = []
                }
                this.listLoading = false
            },
// 表单提交
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.addUser()
                    } else {
                        console.log('error submit!!');
                        return false
                    }
                })
            },
// 重置
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
// 显示添加用户窗口
            showDialogForm() {
                this.dialogFormVisible = true
                this.getCboData()
            },
// 添加用户
            async addUser() {
                
                let params = {
                    name: this.ruleForm.name,
                    pass: this.md5encrypt(this.ruleForm.pass1),
                    email: this.ruleForm.email,
                    status: this.ruleForm.switchValue,
                    role: this.ruleForm.role
                }
                let data = this.postdata('UserApi/v1/addUser', params)

                if (!data.data) {
                    return
                }

                if (data.data.status === 200) {
                    this.message(true, data.data.msg, 'success')
                    this.resetForm('ruleForm')
                    this.dialogFormVisible = false
                } else {
                    this.message(true, data.data.msg, 'error')
                }
                this.getUsers()
            },
// 删除用户
            delUser() {
                if (this.userIds.length === 0) {
                    this.message(true, '请选择需要删除的用户', 'error')
                    return
                }
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'error'
                }).then(() => {
                    this.delUsers()
                }).catch(() => {
                    this.message(true, '已取消删除', 'error')
                })
            },
// 删除用户
            async delUsers() {
                let params = {
                    ids: this.userIds
                }
                let data = this.postdata('UserApi/v1/delUsers', params)
                if (!data.data) {
                    return
                }
                if (data.data.status === 200) {
                    this.message(true, data.data.msg, 'success')
                } else {
                    this.message(true, data.data.msg, 'error')
                }
                this.getUsers()
            },
// 查询菜单
            async getCboData() {
                
                let data = this.postdata('RoleApi/v1/findAllRoles')
                if (!data.data) {
                    return
                }
                if (data.data.status === 200) {
                    this.roles = data.data.data
                } else {
                    this.message(true, data.data.msg, 'error')
                    this.types = []
                }
            },
// 获取选中集
            handleSelectionChange(val) {
                this.userIds = []
                if (val) {
                    val.forEach(row => {
                        this.userIds.push(row.id)
                    });
                }
            },
// 每页大小改变时触发
            handleSizeChange(val) {
                this.pageSize = val
                this.getUsers()
            },
// 当前页码改变时触发
            handleCurrentChange(val) {
                this.startPage = val
                this.getUsers()
            },
            switchChange(status, id) {
                this.editUserStatus(status, id)
            },
// 编辑用户状态
            async editUserStatus(status, id) {
                
                let params = {
                    id: id,
                    type: status
                }
                let data = this.postdata('UserApi/v1/editUserStatus', params)

                if (!data.data) {
                    return
                }

                if (data.data.status === 200) {
                    this.message(true, data.data.msg, 'success')
                } else {
                    this.message(true, data.data.msg, 'error')
                }
                this.getUsers()
            },
            /**
             * ifshow: true/false msg: message  type: error/error/success
             */
            message(ifshow, msg, type) {
                this.$message({
                    showClose: ifshow,
                    message: msg,
                    type: type
                })
            }
        },
        mounted() {
            this.getUsers()
        }
    }

</script>

<style scoped>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }

    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>
