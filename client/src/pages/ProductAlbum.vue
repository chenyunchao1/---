<template>
  <div class="song-list-album">
    <div class="album-slide">
      <div class="album-img">
        <img :src=attachImageUrl(product.productionTitlePicture) alt="">
      </div>
      <div class="album-info">
        <h2>简介：</h2>
        <span>
          {{product.productionText}}
        </span>
      </div>
    </div>
    <div class="album-content">
      <div class="album-title">
        <p>{{product.productionName}}</p>
      </div>
      <div>指导教师：{{productionTeacherName}}</div>
<!--      <div>{{product}}</div>-->
      <!--评分-->
      <div class="album-score">
        <div>
          <h3>评分：</h3>
          <div>
            <template>
              <div v-for="item in categoryType">
                {{item.scoreName}}
                <el-rate v-model="item.score" show-text allow-half></el-rate>
              </div>
            </template>
          </div>
        </div>
        <div>
          <el-button @click="pictureMapper()">提交评分</el-button>
        </div>
        <div>
        <el-popover
          placement="right"
          width="400"
          trigger="click">
          <el-table :data="categoryLists">
            <el-table-column width="150" property="personName" label="姓名"></el-table-column>
            <el-table-column width="100" property="scoreTypeName" label="评分类型"></el-table-column>
            <el-table-column width="300" property="score" label="评分"></el-table-column>
          </el-table>
          <el-button slot="reference" @click="categoryList">查看评分记录</el-button>
        </el-popover>
          <stu-team-list :teamId="product.teamId" :type="product.teamType"></stu-team-list>
          <el-button v-show="editPermissions" @click="editText">编辑作品其他信息</el-button>
        </div>
      </div>
<!--      作品展示部分-->
      <div class="songs-body">
        <h2>作品文档</h2>
        {{product.productionDocument}}
        <el-button @click="downDocument">文档下载</el-button>
        <div align="right" v-show="editPermissions">
          <el-upload
            :show-file-list="false"
            :action="insertDocumentUrl()"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeDocumentUpload">
            <el-button type="primary">上传文档</el-button>
          </el-upload>
        </div>
        <el-divider></el-divider>
        <h2>作品图片</h2>
        <div align="right"><el-button  v-show="editPermissions" @click="editPictrue">编辑图片</el-button></div>
        <el-divider></el-divider>
        <div class="demo-image">
          <el-image v-for="item in productionPictureIds" :src=attachImageUrl(item.pictureUrl) alt="" :preview-src-list="pros"></el-image>
        </div>
        <el-divider></el-divider>
        <h2>作品视频</h2>
        <div align="right" v-show="editPermissions">
          <el-upload
            :show-file-list="false"
            :action="insertVideoUrl()"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeVideoUpload">
            <el-button type="primary">上传视频</el-button>
          </el-upload>
        </div>
        <el-divider></el-divider>
        <el-button type="text" @click="dialogVisible = true"><h2>作品视频展示</h2></el-button>
        </div>
      <div class="songs-body">
        <comment :playId="productId" :type="1"></comment>
      </div>
      </div>


      <el-dialog
      :visible.sync="dialogVisible"
      width="50%"
      >
      <span>
         <video :src='url' controls="controls" ref="player" preload="true"  width="100%" height="100%"></video>
      </span>
      <span slot="footer" class="dialog-footer">
      </span>
    </el-dialog>
      <el-dialog
      title="编辑作品图片"
      :visible.sync="editPictrueButton"
      width="30%"
      center>
      <span slot="footer" class="dialog-footer">
        <el-table :data="productionPictureIds" width="width">
      <el-table-column label="序号" type="index" align="center" width="width">
        <template slot-scope="scope">
          <span>{{scope.$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column width="width" property="pictureUrl" label="图片"></el-table-column>
      <el-table-column  label="操作" width="width">
        <template slot-scope="scope">
          <el-button  @click="deletePicture(scope.row)">删除该图片</el-button>
        </template>
      </el-table-column>
    </el-table>
   <el-upload
      align="center"
     :show-file-list="false"
      :action="insertPictureUrl()"
      :on-success="handleAvatarSuccess"
      multiple
      :before-upload="beforeAvatarUpload">
        <el-button size="small" type="primary">点击上传</el-button>
        <div class="el-upload__tip" slot="tip">只能上传jpg/png文件</div>
      </el-upload>
      </span>
    </el-dialog>
      <el-dialog
        title="编辑作品信息"
        :visible.sync="editTextButton"
        width="30%">
        <span>
          <el-form label-width="80px" :model="productText">
          <el-form-item label="作品名称">
            <el-input v-model="productText.productionName"></el-input>
          </el-form-item>
          <el-form-item label="所属队伍">
            <el-select v-model="productText.teamId" placeholder="请选择">
              <el-option
                v-for="item in teamList"
                :key="item.id"
                :label="item.teamName"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="内容介绍">
            <el-input v-model="productText.productionText"></el-input>
          </el-form-item>
          </el-form>
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="editTextButton = false">取 消</el-button>
          <el-button type="primary" @click="updateProduct">确 定</el-button>
        </span>
      </el-dialog>
    </div>
</template>

<script>
import mixin from '../mixins'
import AlbumContent from '../components/AlbumContent'
import Comment from '../components/Comment'
import { mapGetters } from 'vuex'
import { HttpManager } from '../api/index'
import StuTeamList from "../components/StuTeamList";
import {ICON} from "../assets/icon";

export default {
  name: 'product-album',
  mixins: [mixin],
  components: {
    AlbumContent,
    Comment,
    StuTeamList,
  },
  data () {
    return {
      productionPictureIds: [],   //作品图片列表
      editPermissions:false,    //编辑作品权限判断
      editPictrueButton:false,    //编辑弹窗图片按钮
      dialogVisible: false,      //视频播放弹出按钮
      editTextButton: false,    //编辑作品文字部分弹窗按钮
      product: {},    //作品信息
      pros:[],    //放大图片
      teacherId:"",   //指导教师ID
      productionTeacherName:"",   //指导教师名字
      count: 0, // 点赞数
      productId: '', // 作品ID
      value3: 0,    //评价分
      categoryType:[],    //作品评分类别
      selectValue:null,   //评分类别选项
      categoryLists:[],   //评分记录列表
      teamList:[],    //队伍列表
      productText:{
        productionName:"",
        productionText:"",
        teamId:"",
        teamName:"",
        id:"",
      },
    }
  },
  computed: {
    ...mapGetters([
      'loginIn', // 登录标识
      'tempList', // 单个歌单信息

      'userId', // 用户ID
      'permissions', // 用户权限
      'url',
    ])
  },
  watch:{
    // 监听播放还是暂停
    dialogVisible () {
      this.togglePlay()
    },
  },
  created () {
    this.productId = this.tempList.id // 给作品Id赋值
    this.product = this.tempList    //作品信息
    this.teacherId = this.tempList.productionTeacherId
    this.getTeacherName(this.teacherId)   //获得指导教师名字
    this.getPictureId() // 获取作品里面的图片ID
    this.getCategoryStyle(this.tempList.categoryId) // 获取评分类型
    this.setQuanxian()
    this.getProductionById()
    this.$store.commit('setUrl', this.$store.state.configure.HOST+this.product.productionVideo)
  },
  methods: {
    //根据作品ID获得作品信息
    getProductionById(){
      HttpManager.getProductionById(this.productId)
        .then(res => {
          this.$store.commit('setTempList', res)
          this.$store.commit('setUrl', this.$store.state.configure.HOST+res.productionVideo)
          this.product = res
        })
        .catch(err => {
          console.log(err)
        })
    },
    //上传文档
    insertDocumentUrl(){
      return `${this.$store.state.configure.HOST}/production/document?productionId=${this.product.id}`
    },
    //上传图片URL
    insertPictureUrl () {
      return `${this.$store.state.configure.HOST}/picTure/insertPic?productionId=${this.product.id}`
    },
    //上传视频URL
    insertVideoUrl(){
      return `${this.$store.state.configure.HOST}/production/video?productionId=${this.product.id}`
    },
    //视频上传之前格式校验
    beforeVideoUpload(){
    },
    //上传文档前的校验
    beforeDocumentUpload(file){
      console.log(file.type)
      const isWord = file.type === 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
      const isDocx = file.type === 'application/msword'
      const isZip = file.type === 'application/x-zip-compressed'
      if (!(isWord || isZip || isDocx)) {
        this.$message.error('上传文档只能是 zip或者word 格式!')
      }
      return isWord || isZip || isDocx
    },
    //上传之前的格式校验
    beforeAvatarUpload (file) {
      console.log(file.type)

      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 10
      if (!isJPG) {
        this.$message.error('上传图片只能是 JPG 格式!')
      }
      return isJPG && isLt2M
    },
    handleAvatarSuccess (res, file) {
      if (res.code === 1) {
        this.$message({
          message: '修改成功',
          type: 'success'
        })
        this.getProductionById()
        this.getPictureId()
      } else {
        this.notify('修改失败', 'error')
      }
    },
    editText(){
      this.editTextButton = true
      this.getTeamList()
      this.productText.productionName = this.product.productionName
      this.productText.productionText = this.product.productionText
      this.productText.teamId = this.product.teamId
    },
    updateProduct(){
      this.editTextButton = false
      let params = new URLSearchParams()
      params.append('id',this.productId)
      params.append('productionName',this.productText.productionName)
      params.append('productionText',this.productText.productionText)
      params.append('teamId',this.productText.teamId)
      HttpManager.updataProduct(params)
        .then(res => {
          if (res === "success") {
            this.newDialogVisible = false
            this.showError = false
            this.showSuccess = true
            this.getProductionById()()
            this.$notify.success({
              title: '修改成功',
              showClose: true
            })
            setTimeout(function () {
              this.$router.go(-1)
            }, 2000)
          } else {
            this.showSuccess = false
            this.showError = true
            this.$notify.error({
              title: '修改失败',
              showClose: true
            })
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    //获取队伍列表
    getTeamList(){
      HttpManager.getTeamList()
        .then(res => {
          this.teamList = res
        })
        .catch(err => {
          console.log(err)
        })
    },
    //删除该图片
    deletePicture(row){
      HttpManager.deletePicture(row.id)
        .then(res => {
          if (res =="success") {
            this.showError = false
            this.showSuccess = true
            this.getPictureId()
            this.$notify.success({
              title: '删除成功',
              showClose: true
            })
            setTimeout(function () {
              this.$router.go(-1)
            }, 2000)
          } else {
            this.showSuccess = false
            this.showError = true
            this.$notify.error({
              title: '删除失败',
              showClose: true
            })
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    // 编辑图片按钮
    editPictrue(){
      this.editPictrueButton = true
    },

    //下载文档
    downDocument(){
      let documentUrl = this.$store.state.configure.HOST + this.product.productionDocument
      let eleLink = document.createElement('a')
      eleLink.download = `aa.docx`
      eleLink.href = documentUrl
      eleLink.style.display = 'none'
      document.body.appendChild(eleLink)
      eleLink.click()
      document.body.removeChild(eleLink)
    },
    //判断是否为教师或者组内成员
    setQuanxian(){
      if(this.permissions ==="teacher"){
        this.editPermissions =true
        return
      }
      if(this.product.teamType === 2){
        return;
      }
      HttpManager.getTeamById(this.product.teamId, "student")
        .then(res => {
          for (let i = 0; i < res.length; i++) {
            if (res[i].id === this.userId) {
              this.editPermissions = true
            }
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    // 开始/暂停
    togglePlay () {
      let player = this.$refs.player
      if (this.dialogVisible) {
        player.play()
      } else {
        player.pause()
      }
    },
    //获得作品评分列表
    categoryList(){
      HttpManager.getCategoryList(this.productId)
        .then(res => {
          res.forEach(e => {
            let _this =this
            for (let i = 0; i <  _this.categoryType.length; i++) {
              if(e.scoreTypeId === _this.categoryType[i].id){
                e.scoreTypeName =_this.categoryType[i].scoreName
              }
            }
          })
          this.categoryLists = res
        })
        .catch(err => {
          console.log(err)
        })
    },

    // 获得评分类型
    getCategoryStyle(categoryId){
      HttpManager.getCategoryOfStyle(categoryId)
        .then(res => {
          this.categoryType = res
          this.categoryType.forEach(e => e.score =0)
        })
        .catch(err => {
          console.log(err)
        })
    },

    // 获得指导教师的姓名
    getTeacherName(teacherId){
      HttpManager.getTeacherName(teacherId)
          .then(res => {
            this.productionTeacherName = res.name
          })
          .catch(err => {
            console.log(err)
          })
     },
    // 作品里面的图片列表
    getPictureId () {
      HttpManager.getPictureIdList(this.tempList.id)
        .then(res => {
          // 作品里面的图片列表
          this.productionPictureIds = res
          this.productionPictureIds.forEach(e => this.pros.push(this.$store.state.configure.HOST+e))
        })
        .catch(err => {
          console.log(err)
        })
    },
    //全部频繁提交
    pictureMapper(){
      this.categoryType.forEach(e => this.pushValue(e.id,e.score))
    },
    // 提交评分
    pushValue (type,score) {
      if (this.loginIn) {
        let params = new URLSearchParams()
        params.append('productionId', this.productId)
        params.append('userId', this.userId)
        if(this.permissions == "teacher"){
          params.append('userType', 2)
        }
        if(this.permissions == "student"){
          params.append('userType', 1)
        }
        params.append('type',type)
        params.append('score', score*2)
        HttpManager.setRank(params)
          .then(res => {
            if (res.code === 1) {

              this.notify('评分成功', 'success')
            } else {
              this.notify(res.msg, 'error')
            }
          })
          .catch(err => {
            console.log(err)
          })
      } else {
        this.value3 = null
        this.notify('请先登录', 'warning')
      }
    }
  }
}
</script>

<style lang="scss">

@import '../assets/css/song-list-album.scss';
</style>
