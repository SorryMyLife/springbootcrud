/**
 * 系统管理 用户管理
 */
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="formInline" class="user-search">
      <el-form-item label="搜索：">
        <el-select
          size="small"
          v-model="formInline.isLock"
          placeholder="请选择"
        >
          <el-option label="全部" value=""></el-option>
          <el-option label="正常" value="N"></el-option>
          <el-option label="已锁定" value="Y"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="">
        <el-input
          size="small"
          v-model="formInline.userName"
          placeholder="输入用户名"
        ></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input
          size="small"
          v-model="formInline.userMobile"
          placeholder="输入手机号"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button
          size="small"
          type="primary"
          icon="el-icon-search"
          @click="search"
          >搜索</el-button
        >
        <el-button
          size="small"
          type="primary"
          icon="el-icon-plus"
          @click="handleEdit()"
          >添加</el-button
        >
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table
      size="small"
      @selection-change="selectChange"
      :data="userData"
      highlight-current-row
      v-loading="loading"
      border
      element-loading-text="拼命加载中"
      style="width: 100%"
    >
      <el-table-column align="center" type="selection" width="50">
      </el-table-column>
      <el-table-column
        align="center"
        sortable
        prop="deptname"
        label="公司"
        width="120"
      >
      </el-table-column>
      <el-table-column
        align="center"
        sortable
        prop="username"
        label="用户名"
        width="120"
      >
      </el-table-column>
      <el-table-column
        align="center"
        sortable
        prop="userrealname"
        label="用户等级"
        width="120"
      >
      </el-table-column>
      <el-table-column
        align="center"
        sortable
        prop="usermobile"
        label="手机号"
        width="120"
      >
      </el-table-column>
      <el-table-column
        align="center"
        sortable
        prop="usersex"
        label="性别"
        min-width="50"
      >
      </el-table-column>
      <el-table-column
        align="center"
        sortable
        prop="useremail"
        label="邮件"
        min-width="120"
      >
      </el-table-column>
      <el-table-column
        align="center"
        sortable
        prop="edittime"
        label="修改时间"
        min-width="120"
      >
      </el-table-column>
      <el-table-column
        align="center"
        sortable
        prop="islock"
        label="状态"
        min-width="50"
      >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.islock == 0 ? nshow : fshow"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="editType(scope.$index, scope.row)"
          >
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" min-width="300">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)"
            >编辑</el-button
          >
          <el-button
            size="mini"
            type="danger"
            @click="deleteUser(scope.$index, scope.row)"
            >删除</el-button
          >
          <el-button
            size="mini"
            type="success"
            @click="resetpwd(scope.$index, scope.row)"
            >重置密码</el-button
          >

          <el-button
            size="mini"
            type="success"
            @click="offlineUser(scope.$index, scope.row)"
          >
            <div v-if="scope.row.isonline == 0">
              {{ (isonlinebuttontext = "上线") }}
            </div>
            <div v-if="scope.row.isonline == 1">
              {{ (isonlinebuttontext = "下线") }}
            </div>
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination
      v-bind:child-msg="pageparm"
      @callFather="callFather"
    ></Pagination>
    <!-- 编辑界面 -->
    <el-dialog
      :title="title"
      :visible.sync="editFormVisible"
      width="30%"
      @click="closeDialog('edit')"
    >
      <el-form
        label-width="80px"
        ref="editForm"
        :model="editForm"
        :rules="rules"
      >
      <el-form-item label="用户ID" prop="userid">
          <el-input
            size="small"
            v-model="editForm.userid"
            auto-complete="off"
            placeholder="请输入用户ID"
          ></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input
            size="small"
            v-model="editForm.username"
            auto-complete="off"
            placeholder="请输入用户名"
          ></el-input>
        </el-form-item>
        <el-form-item label="公司名称" prop="deptname">
          <!-- <el-input size="small" v-model="editForm.deptname" auto-complete="off" placeholder="请输入姓名"></el-input> -->
          <!-- <el-autocomplete
          v-model="autocompleteDeptName"
          :fetch-suggestions="autocompleteQuerySearchAsync"
          placeholder="请输入内容"
          @select="autocompleteHandleSelect"
        ></el-autocomplete> -->
          <el-select
            size="small"
            v-model="editForm.deptid"
            placeholder="请选择"
            class="userRole"
          >
            <el-option
              v-for="(dept, index) in depts"
              :key="index"
              :label="dept.deptname"
              :value="dept.deptid"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="角色" prop="roleid">
          <el-select
            size="small"
            v-model="editForm.roleid"
            placeholder="请选择"
            class="userRole"
          >
            <el-option
              v-for="(rolea, index) in roles"
              :key="index"
              :label="rolea.userrealname"
              :value="rolea.roleid"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="手机号" prop="usermobile">
          <el-input
            size="small"
            v-model="editForm.usermobile"
            placeholder="请输入手机号"
          ></el-input>
        </el-form-item>
        <el-form-item label="邮件" prop="useremail">
          <el-input
            size="small"
            v-model="editForm.useremail"
            placeholder="请输入邮箱地址"
          ></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="usersex">
          <el-radio v-model="editForm.usersex" label="男">男</el-radio>
          <el-radio v-model="editForm.usersex" label="女">女</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="closeDialog('edit')">取消</el-button>
        <el-button
          size="small"
          type="primary"
          :loading="loading"
          class="title"
          @click="submitForm('editForm')"
          >保存</el-button
        >
      </div>
    </el-dialog>

    <!-- 密码修改界面 -->
    <el-dialog
      :title="title"
      :visible.sync="editPasswdFormVisible"
      width="30%"
      @click="closeDialog('resetpwd')"
    >
      <el-form
        label-width="80px"
        ref="editPasswdForm"
        :model="editForm"
        :rules="rules"
      >
        <el-form-item label="用户密码" prop="passwd">
          <el-input
            size="small"
            v-model="editPasswdForm.passwd"
            auto-complete="off"
            placeholder="请输入用户密码"
          ></el-input>
        </el-form-item>
        
        <el-form-item label="确认用户密码" prop="passwd2">
          <el-input
            size="small"
            v-model="editPasswdForm.passwd2"
            placeholder="请确认用户密码"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="closeDialog('resetpwd')">取消</el-button>
        <el-button
          size="small"
          type="primary"
          :loading="loading"
          class="title"
          @click="subresetpwd('editPasswdForm')"
          >确认修改</el-button
        >
      </div>
    </el-dialog>


  </div>
</template>

<script>
// 导入请求方法
import { httpget, httppost } from "../../api/userMG";
import Pagination from "../../components/Pagination";
import { timestampToTime } from "../../utils/util";
export default {
  data() {
    return {
      isonlinebuttontext: "",
      autocompleteDeptName: "",
      depts: [],
      roles: [],
      nshow: true, //switch开启
      fshow: false, //switch关闭
      loading: false, //是显示加载
      title: "添加用户",
      editFormVisible: false, //控制编辑页面显示与隐藏
      dataAccessshow: false, //控制数据权限显示与隐藏
      unitAccessshow: false, //控制所属单位隐藏与显示
      editPasswdFormVisible: false,
      // 编辑与添加
      editForm: {
        userid: "",
        username: "",
        deptid: "",
        roleid: "",
        usermobile: "",
        useremail: "",
        usersex: "",
        uoldid: "",
        token: localStorage.getItem("logintoken"),
      },

      editPasswdForm: {
        userid: "",
        passwd: "",
        passwd2: ""
      },

      // 部门参数
      unitparm: {
        userIds: "",
        deptId: "",
        deptName: "",
      },
      // 选择数据
      selectdata: [],
      // rules表单验证
      rules: {
        userName: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        userId: [
          { required: true, message: "请输入用户ID", trigger: "blur" },
        ],
        userRealName: [
          { required: true, message: "请输入姓名", trigger: "blur" },
        ],
        roleId: [{ required: true, message: "请选择角色", trigger: "blur" }],
        userMobile: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          {
            pattern: /^1(3\d|47|5((?!4)\d)|7(0|1|[6-8])|8\d)\d{8,8}$/,
            required: true,
            message: "请输入正确的手机号",
            trigger: "blur",
          },
        ],
        userEmail: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          {
            pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
            required: true,
            message: "请输入正确的邮箱",
            trigger: "blur",
          },
        ],
        userSex: [{ required: true, message: "请选择性别", trigger: "blur" }],
      },
      // 删除用户
      seletedata: {
        ids: "",
        token: localStorage.getItem("logintoken"),
      },
      // 重置密码
      resetpsd: {
        userId: "",
        token: localStorage.getItem("logintoken"),
      },
      // 用户下线
      offline: {
        token: localStorage.getItem("logintoken"),
      },
      // 请求数据参数
      formInline: {
        page: 1,
        limit: 10,
        deptId: "",
        userName: "",
        userMobile: "",
        isLock: "",
      },
      //用户数据
      userData: [],
      //参数role
      saveroleId: "",
      // 分页参数
      pageparm: {
        currentPage: 1,
        pageSize: 10,
        total: 10,
      },
    };
  },
  // 注册组件
  components: {
    Pagination,
  },

  /**
   * 数据发生改变
   */
  watch: {},

  /**
   * 创建完毕
   */
  created() {
    this.getdata(this.formInline);
  },

  /**
   * 里面的方法只有被调用才会执行
   */
  methods: {
    autocompleteQuerySearchAsync(queryString, cb) {
      console.log("queryString ::: ", queryString);
      var restaurants = this.depts;
      var results = queryString
        ? restaurants.filter(this.createStateFilter(queryString))
        : restaurants;
      console.log("results ::: ", results);
      // clearTimeout(this.timeout);
      // this.timeout = setTimeout(() => {
      //   cb(results);
      // }, 1000 * Math.random());
    },
    createStateFilter(queryString) {
      return (autocompleteDeptName) => {
        return autocompleteDeptName.value.indexOf(queryString) === 0;
      };
    },
    autocompleteHandleSelect(item) {
      console.log(item);
    },

    testdataget(parm) {
      return new Promise((resolve, reject) => {
        var geturl =
          "/aa/crm/system-user-manage/getuserlist?starts=" +
          parm.page +
          "&limit=" +
          parm.limit;
        if (parm.isLock != "" || parm.userName != "" || parm.userMobile != "") {
          geturl =
            "/aa/crm/system-user-manage/searchuserlist?starts=" +
            parm.page +
            "&limit=" +
            parm.limit +
            "&islock=" +
            parm.isLock +
            "&username=" +
            parm.userName +
            "&usermobile=" +
            parm.userMobile;
        }

        // console.log("geturl :::: " ,geturl);
        httpget(geturl).then((res) => {
          resolve(res);
        }, reject);
      });
    },

    getDepts(id, limit, starts) {
      return new Promise((resolve, reject) => {
        var geturl =
          id > -1
            ? "/aa/crm/system-dept/getdept?deptid=" + id
            : "/aa/crm/system-dept/getdepts";
        if (limit > -1 && starts > -1) {
          geturl =
            "/aa/crm/system-dept/getdeptlist?limit=" +
            limit +
            "&starts=" +
            starts;
        }

        console.log("geturl :::: ", geturl);
        httpget(geturl).then((res) => {
          //console.log("promise ::: ",res);
          resolve(res);
        }, reject);
      });
    },

    getRoles(id) {
      return new Promise((resolve, reject) => {
        var geturl =
          id > -1
            ? "/aa/crm/system-role/getrole?roleid=" + id
            : "/aa/crm/system-role/getroles";
        // console.log("geturl :::: " ,geturl);
        httpget(geturl).then((res) => {
          //console.log("promise ::: ",res);
          resolve(res);
        }, reject);
      });
    },

    // 获取数据方法
    async getdata(parameter) {
      this.loading = true;
      try {
        // console.log("parameter :::: " ,parameter)
        const data = await this.testdataget(parameter);
        console.log("getdata ::: " + data);
        if (data.code == 0) {
          // 模拟数据开始
          this.loading = false;
          // console.log("data.data  ::: ", data.data);
          this.userData = data.data;
          console.log("userdata ::: " ,this.userData);
          // 分页赋值
          this.pageparm.currentPage = this.formInline.page;
          this.pageparm.pageSize = this.formInline.limit;
          this.pageparm.total = data.total;
          // 模拟数据结束
        } else {
          this.$message({
            type: "info",
            message: data.msg,
          });
        }
        this.loading = false;
      } catch (error) {
        // 处理接口错误
      }
    },
    // 分页插件事件
    callFather(parm) {
      this.formInline.page = parm.currentPage;
      this.formInline.limit = parm.pageSize;
      this.getdata(this.formInline);
    },
    //搜索事件
    search() {
      this.getdata(this.formInline);
      // console.log("this.formInline ::: ",this.formInline);
    },
    // 修改type
    editType: function (index, row) {
      this.loading = true;
      var parm = {
        lock: "",
        userid: "",
      };
      console.log("row ::: ", row);
      parm.userid = row.userid;
      parm.lock = row.islock == 0 ? 1 : 0;
      parm = JSON.stringify(parm);
      console.log("parm ::: ", parm);

      // 修改状态
      httppost("/aa/crm/system-user-manage/changelock", parm).then((res) => {
        console.log("res ::: ", res);
        if (res.code != 0) {
          this.$message({
            type: "info",
            message: res.msg,
          });
        } else {
          this.$message({
            type: "success",
            message: "状态修改成功",
          });
          this.getdata(this.formInline);
        }
      });
      this.loading = false;
    },
    //显示编辑界面
    handleEdit: async function (index, row) {
      try {
        const a = await this.getRoles(-1);
        const b = await this.getDepts(-1, -1, -1);
        this.roles = a.roles;
        this.depts = b.depts;
      } catch (err) {
        console.log(" try error ::: ", err);
      }
      //this.$forceUpdate()
      console.log("roles ::: ", this.roles);
      console.log("index ::: ",index , " -- row ::: ",row);

      this.editFormVisible = true;
      if (row != undefined && row != "undefined") {
        this.title = "修改用户";
        this.editForm.userid = row.userid;
        this.editForm.username = row.username;
        this.editForm.deptid = row.deptid;
        this.editForm.roleid = row.roleid;
        this.editForm.usermobile = row.usermobile;
        this.editForm.useremail = row.useremail;
        this.editForm.usersex = row.usersex;
        this.editForm.uoldid = row.uoldid;
      } else {
        this.title = "添加用户";
        this.editForm.userid = "";
        this.editForm.username = "";
        this.editForm.deptid = "";
        this.editForm.roleid = "";
        this.editForm.usermobile = "";
        this.editForm.useremail = "";
        this.editForm.usersex = "";
      }
    },
    // 编辑、添加提交方法
    submitForm(editData) {
      this.$refs[editData].validate((valid) => {
        if (valid) {
          // 请求方法
          console.log("this.editForm 1::: ", this.editForm);

          this.editForm.usersex = this.editForm.usersex === "男" ? 1 : 0;
          const bbbb = JSON.stringify(this.editForm);
          console.log("bbbb::: ", bbbb);
          httppost("aa/crm/system-user-manage/changeuser", bbbb).then((r) => {
            this.editFormVisible = false;
            this.loading = false;
            console.log("r ::: ", r);
            if (r.code == 0) {
              this.getdata(this.formInline);
              this.$message({
                type: "success",
                message: "数据保存成功！",
              });
            } else {
              this.$message({
                type: "info",
                message: r.msg,
              });
            }
          });
        } else {
          return false;
        }
      });
    },

    // 选择复选框事件
    selectChange(val) {
      this.selectdata = val;
    },
    // 关闭编辑、增加弹出框
    closeDialog(dialog) {
      if (dialog == "edit") {
        this.editFormVisible = false;
      } else if (dialog == "perm") {
        this.dataAccessshow = false;
      } else if (dialog == "unit") {
        this.unitAccessshow = false;
      }else if(dialog == "resetpwd"){
        this.editPasswdFormVisible = false;
      }
    },
    // 删除用户
    deleteUser(index, row) {
      this.$confirm("确定要删除吗?", "信息", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // 删除
          var parm = {
            userid: row.userid,
          };
          parm = JSON.stringify(parm);
          httppost("/aa/crm/system-user-manage/deluser", parm)
            .then((r) => {
              if (r.code == 0) {
                this.$message({
                  type: "success",
                  message: "数据已删除!",
                });
                this.getdata(this.formInline);
              } else {
                this.$message({
                  type: "info",
                  message: res.msg,
                });
              }
            })
            .catch((err) => {
              this.loading = false;
              this.$message.error("数据删除失败，请稍后再试！");
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除！",
          });
        });
    },

    subresetpwd(editPasswdForm) {
      this.$refs[editPasswdForm].validate((valid) => {
        if (valid) {
          // 请求方法
          console.log("this.editPasswdForm 1::: ", this.editPasswdForm);
          const bbbb = JSON.stringify(this.editPasswdForm);
          console.log("bbbb::: ", bbbb);
          httppost("aa/crm/system-user-manage/changeuserpwd", bbbb).then((r) => {
            this.editPasswdFormVisible = false;
            this.loading = false;
            console.log("r ::: ", r);
            if (r.code == 0) {
              this.getdata(this.formInline);
              this.$message({
                type: "success",
                message: "用户密码修改成功！",
              });
            } else {
              this.$message({
                type: "info",
                message: r.msg,
              });
            }
          });
        } else {
          return false;
        }
      });
    },

    // 重置密码
    resetpwd: async function (index, row) {
      this.editPasswdFormVisible = true;
      this.editPasswdForm.userid = row.userid;
      this.editPasswdForm.passwd = "";
      this.editPasswdForm.passwd2 = "";
    },
    // 下线用户
    offlineUser(index, row) {
      var onlinetext = this.isonlinebuttontext;
      this.$confirm(
        "确定要让 [ " + row.username + " ] 用户" + onlinetext + "吗?",
        "信息",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          var parm = {
            userid: row.userid,
            online: row.isonline == 0 ? 1 : 0,
          };
          parm = JSON.stringify(parm);
          httppost("/aa/crm/system-user-manage/changeonline", parm)
            .then((res) => {
              if (res.code == 0) {
                this.$message({
                  type: "success",
                  message:
                    "用户 [ " +
                    row.username +
                    " ] 强制" +
                    onlinetext +
                    "成功！",
                });
                this.getdata(this.formInline);
              } else {
                this.$message({
                  type: "info",
                  message: res.msg,
                });
              }
            })
            .catch((err) => {
              this.loading = false;
              this.$message.error(
                "用户 [ " + row.username + " ] 失败，请稍后再试！",
                err
              );
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
        });
    },
  },
};
</script>

<style scoped>
.user-search {
  margin-top: 20px;
}
.userRole {
  width: 100%;
}
</style>

 