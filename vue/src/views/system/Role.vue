/**
 * 系统管理  角色管理
 */
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>角色管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="formInline" class="user-search">
      <el-form-item label="搜索：">
        <el-input
          size="small"
          v-model="formInline.rolename"
          placeholder="输入角色名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input
          size="small"
          v-model="formInline.roleid"
          placeholder="输入角色代码"
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
      :data="listData"
      highlight-current-row
      v-loading="loading"
      border
      element-loading-text="拼命加载中"
      style="width: 100%"
    >
      <el-table-column align="center" type="selection" width="60">
      </el-table-column>
      <el-table-column
        sortable
        prop="userrealname"
        label="角色名称"
        width="300"
      >
      </el-table-column>
      <el-table-column sortable prop="roleid" label="角色代码" width="300">
      </el-table-column>
      <el-table-column sortable prop="edittime" label="修改时间" width="300">
      </el-table-column>
      <el-table-column sortable prop="edituser" label="修改人" width="300">
      </el-table-column>
      <el-table-column align="center" label="操作" min-width="300">
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
        label-width="120px"
        :model="editForm"
        ref="editForm"
        :rules="rules"
      >
        <el-form-item label="角色名称" prop="rolename">
          <el-input
            size="small"
            v-model="editForm.rolename"
            auto-complete="off"
            placeholder="请输入角色名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="角色代码" prop="roleid">
          <el-input
            size="small"
            v-model="editForm.roleid"
            auto-complete="off"
            placeholder="请输入角色代码"
          ></el-input>
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
  </div>
</template>

<script>
import { httpget, httppost } from "../../api/userMG";
import Pagination from "../../components/Pagination";
export default {
  data() {
    return {
      nshow: true, //switch开启
      fshow: false, //switch关闭
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      menuAccessshow: false, //控制数据权限显示与隐藏
      title: "添加",
      editForm: {
        roleid: "",
        rolename: "",
        token: localStorage.getItem("logintoken"),
      },
      // rules 表单验证
      rules: {
        roleNo: [
          { required: true, message: "请输入角色代码", trigger: "blur" },
        ],
        roleName: [
          { required: true, message: "请输入角色名称", trigger: "blur" },
        ],
      },
      formInline: {
        page: 1,
        limit: 10,
        rolename: "",
        roleid: "",
        token: localStorage.getItem("logintoken"),
      },
      // 删除
      seletedata: {
        ids: "",
        token: localStorage.getItem("logintoken"),
      },
      userparm: [], //搜索权限
      listData: [], //用户数据
      // 数据权限
      RoleRight: [],
      RoleRightProps: {
        children: "children",
        label: "name",
      },
      // 选中
      checkmenu: [],
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

    // 获取角色列表
    getdata(parameter) {
      /***
       * 调用接口，注释上面模拟数据 取消下面注释
       */
      //console.log("parameter ::: " ,parameter);
      var geturl =
        parameter.rolename === "" && parameter.roleid === ""
          ? "/aa/crm/system-role/getroles?limit=" +
            parameter.limit +
            "&page=" +
            parameter.page
          : "/aa/crm/system-role/searchrolelist?limit=" +
            parameter.limit +
            "&page=" +
            parameter.page +
            "&rolename=" +
            parameter.rolename +
            "&roleid=" +
            parameter.roleid;
      //console.log("geturl ::: ",geturl);
      httpget(geturl)
        .then((res) => {
          this.loading = false;
          if (res.code == 0) {
            this.$message({
              type: "success",
              message: res.msg,
            });
          } else {
            this.$message({
              type: "error",
              message: res.msg,
            });
            this.formInline.page = 1;
          }
          this.listData = res.roles;
          // 分页赋值
          this.pageparm.currentPage = this.formInline.page;
          this.pageparm.pageSize = this.formInline.limit;
          this.pageparm.total = res.total;
        })
        .catch((err) => {
          this.loading = false;
          this.$message.error("获取角色列表失败，请稍后再试！");
        });
    },
    // 分页插件事件
    callFather(parm) {
      this.formInline.page = parm.currentPage;
      this.formInline.limit = parm.pageSize;
      this.getdata(this.formInline);
    },
    // 搜索事件
    search() {
      this.getdata(this.formInline);
    },
    //显示编辑界面
    handleEdit: function (index, row) {
      this.editFormVisible = true;
      if (row != undefined && row != "undefined") {
        this.title = "修改";
        this.editForm.roleid = row.roleid;
        this.editForm.rolename = row.userrealname;
      } else {
        this.title = "添加";
        this.editForm.roleid = "";
        this.editForm.rolename = "";
      }
    },
    // 编辑、增加页面保存方法
    submitForm(editData) {
      this.$refs[editData].validate((valid) => {
        if (valid) {
          var parm = JSON.stringify(this.editForm);
          console.log("editData :::: ", parm);
          httppost("/aa/crm/system-role/changerole", parm)
            .then((res) => {
              this.editFormVisible = false;
              this.loading = false;
              if (res.code == 0) {
                this.getdata(this.formInline);
                this.$message({
                  type: "success",
                  message: "角色保存成功！",
                });
              } else {
                this.$message({
                  type: "info",
                  message: res.msg,
                });
              }
            })
            .catch((err) => {
              this.editFormVisible = false;
              this.loading = false;
              this.$message.error("角色保存失败，请稍后再试！");
            });
        } else {
          return false;
        }
      });
    },
    // 删除角色
    deleteUser(index, row) {
      this.$confirm("确定要删除吗?", "信息", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          httppost("/aa/crm/system-role/delrole", row.roleid)
            .then((res) => {
              if (res.code == 0) {
                this.$message({
                  type: "success",
                  message: "角色已删除！",
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
              this.$message.error("角色删除失败，请稍后再试！");
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },

    // 选中菜单
    changemenu(arr) {
      let change = [];
      for (let i = 0; i < arr.length; i++) {
        if (arr[i].checked) {
          change.push(arr[i].id);
        }
      }
      this.checkmenu = change;
    },

    // 关闭编辑、增加弹出框
    closeDialog(dialog) {
      if (dialog == "edit") {
        this.editFormVisible = false;
      } else if (dialog == "perm") {
        this.menuAccessshow = false;
      }
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

 