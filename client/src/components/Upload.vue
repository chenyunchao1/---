<template>
  <div class="upload">
    <p class="title">上传作品</p>
    <hr/>
    <div class="section">
      <el-form label-width="80px" :model="product" :rules="rules">
        <el-form-item label="作品名称" :required="true" prop="productionName">
          <el-col :span="6">
          <el-input v-model="product.productionName" ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="类别" :required="true" prop="categoryId">
          <el-select v-model="product.categoryId" placeholder="请选择">
            <el-option
              v-for="item in categoryList"
              :key="item.id"
              :label="item.categoryName"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="团队类型" :required="true" v-show="this.permissions == 'teacher'">
          <el-select v-model="product.teamType" placeholder="请选择">
          <el-option
            key=1
            label="学生"
            value=1>
          </el-option>
            <el-option
              key=2
              label="教师"
              value=2>
            </el-option>
        </el-select>
        </el-form-item>
        <el-form-item label="团队名称" :required="true">
          <el-select v-model="product.teamId" placeholder="请选择">
            <el-option
              v-for="item in teamList"
              :key="item.id"
              :label="item.teamName"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="指导教师" :required="true" prop="productionTeacherId">
          <el-select v-model="product.productionTeacherId" placeholder="请选择">
            <el-option
              v-for="item in teacherList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="作品说明" >
          <el-col :span="6">
            <el-input v-model="product.productionText" placeholder="作品说明"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="作品视频">
          <el-col :span="6">
            <el-upload
              class="upload-demo"
              ref="uploadVideo"
              :action="insertVideoUrl()"
              :before-upload="beforeVideoUpload"
              :on-success="videoUploadSuccess"
              :auto-upload="false">
              <el-button slot="trigger" size="small" type="primary">选择视频</el-button>
            </el-upload>
          </el-col>
        </el-form-item>
        <el-form-item label="作品封面" :required="true">
          <el-col :span="6">
            <el-upload
              class="upload-demo"
              ref="uploadTitlePicture"
              :action="insertTitlePictureUrl()"
              :before-upload="beforeTitlePictureUpload"
              :on-success="titlePictureUploadSuccess"
              :auto-upload="false">
              <el-button slot="trigger" size="small" type="primary">选取封面</el-button>
            </el-upload>
          </el-col>
        </el-form-item>
        <el-form-item label="作品文档" :required="true">
          <el-col :span="6">

            <el-upload
              class="upload-demo"
              ref="upload"
              :action="insertDocumentUrl()"
              :before-upload="beforeDocumentUpload"
              :on-success="documentUploadSuccess"
              :auto-upload="false">
              <el-button slot="trigger" size="small" type="primary">选取文档</el-button>
              <div slot="tip" class="el-upload__tip">只能上传docx/doc文件或者压缩文件zip</div>
            </el-upload>
          </el-col>
        </el-form-item>
        <el-form-item label="作品图片">
          <el-col :span="6">
            <el-upload
              class="upload-demo"
              ref="uploadPicture"
              :action="insertPictureUrl()"
              :before-upload="handleAvatarSuccess"
              multiple
              :on-success="beforeAvatarUpload"
              :auto-upload="false">
              <el-button slot="trigger" size="small" type="primary">选取图片</el-button>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件</div>
            </el-upload>
          </el-col>
        </el-form-item>
        <el-form-item>
          <el-col :span="6">
            <el-button type="primary" @click="insertOneProduct">提交</el-button>
          </el-col>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import mixin from '../mixins'
import { mapGetters } from 'vuex'
import {HttpManager} from "../api";

export default {
  name: 'upload',
  mixins: [mixin],
  data () {
    return {
      productId:-1,
      imageUrl: '',
      product:{
        productionName:"",
        categoryId:"",
        teamId:"",
        teamType:"1",
        productionTeacherId:"",
        productionText:"",
        productionVideo:"",
        productionTitlePicture:"",
        productionDocument:"",
        productPicList:[],
      },
      categoryList:[],  //作品类别列表
      teamList:[],    //队伍列表
      teacherList:[],
      rules: {
        productionName: [
          { required: true, message: '请输入作品名称', trigger: 'blur' },
        ],
        categoryId: [
          { required: true, message: '请选择作品类型', trigger: 'change' }
        ],
        productionTeacherId: [
          { required: true, message: '请选择指导教师', trigger: 'change' }
        ],
        productionDocument: [
          { required: true, message: '请选择作品文档', trigger: 'blur' },
        ],
      }
    }
  },
  computed: {
    ...mapGetters([
      'userId',
      'permissions',
    ])
  },
  created () {
    this.quanxian()
    this.getProductCategoryList()
    this.getTeacherList()
  },
  watch:{
    // 监听播放还是暂停
    productId () {
      this.submitStart()
    },
  },
  methods: {
    //提交按钮
    submitStart(){
      this.submitUpload()
    },
    //文档上传按钮
    submitUpload() {
      this.$refs.upload.submit();
    },
    //视频上传按钮
    submitVideoUpload() {
      this.$refs.uploadVideo.submit();
    },
    //封面上传按钮
    submitTitlePictureUpload() {
      this.$refs.uploadTitlePicture.submit();
    },
    //图片上传按钮
    submitPictureUpload() {
      this.$refs.uploadPicture.submit();
    },
    //上传文档前的校验
    beforeDocumentUpload(file){
      if(this.product.productionName == null){
        this.$message({
          message: '作品名称不能为空',
          type: 'error'
        })
        return false
      }
      if(this.product.productionTeacherId == null){
        this.$message({
          message: '指导教师不能为空',
          type: 'error'
        })
        return false
      }
      if(this.product.categoryId == null){
        this.$message({
          message: '作品类型不能为空',
          type: 'error'
        })
        return false
      }
      if(this.product.productionDocument == null){
        this.$message({
          message: '上传作品不能为空',
          type: 'error'
        })
        return false
      }
      const isWord = file.type === 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
      const isDocx = file.type === 'application/msword'
      const isZip = file.type === 'application/x-zip-compressed'
      if (!(isWord || isZip || isDocx)) {
        this.$message.error('上传文档只能是 zip或者word 格式!')
      }
      return isWord || isZip || isDocx
    },
    //视频上传前的校验
    beforeVideoUpload(file){
      return file!=null
    },
    //封面上传前的校验
    beforeTitlePictureUpload(file){
      const isJPG = file.type === 'image/jpeg'
      if (!isJPG) {
        this.$message.error('上传图片只能是 JPG 格式!')
      }
      return isJPG
    },
    //图片上传之前的格式校验
    beforeAvatarUpload (file) {
      console.log(file.type)

      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 10
      if (!isJPG) {
        this.$message.error('上传图片只能是 JPG 格式!')
      }
      return isJPG && isLt2M
    },
    //上传文档
    insertDocumentUrl(){
      return `${this.$store.state.configure.HOST}/production/document?productionId=${this.productId}`
    },
    //上传视频
    insertVideoUrl(){
      return `${this.$store.state.configure.HOST}/production/video?productionId=${this.productId}`
    },
    //上传封面
    insertTitlePictureUrl(){
      return `${this.$store.state.configure.HOST}/production/titlePicture?productionId=${this.productId}`
    },
    //上传图片URL
    insertPictureUrl () {
      return `${this.$store.state.configure.HOST}/picTure/insertPic?productionId=${this.product.id}`
    },
    //文档上传成功
    documentUploadSuccess(){
      this.submitVideoUpload()
    },
    //视频上传成功
    videoUploadSuccess(){
      this.submitTitlePictureUpload()
    },
    //封面上传成功
    titlePictureUploadSuccess(){
      this.submitPictureUpload()
    },
    quanxian(){
      if(this.permissions === 'teacher'){
        this.getTeamList()
      }else {
        this.getStuTeamById(this.userId,this.permissions)
      }
    },
    //作品完成上传
    handleAvatarSuccess (res, file) {
      if (res.code === 1) {
        this.imageUrl = URL.createObjectURL(file.raw)
        this.$message({
          message: '作品上传完成',
          type: 'success'
        })
      } else {
        this.notify('修改失败', 'error')
      }
    },
    //获得全部作品类别
    getProductCategoryList() {
      let params = new URLSearchParams()
      HttpManager.getCategoryTypeList(params)
        .then(res => {
          this.categoryList = res
        })
        .catch(err => {
          console.log(err)
        })
    },
    //当前登陆人为学生时，根据学生ID查询到相应已加入分组
    getStuTeamById (id,permissions) {
      HttpManager.getTeamOfId(id,permissions)
        .then(res => {
          this.teamList = res.stuTeam;
        })
        .catch(err => {
          console.log(err)
        })
    },
    //当前登陆人为教师时，查询获得全部组队信息
    getTeamList(){
      let params = new URLSearchParams()
      HttpManager.getFindTeam(params)
        .then(res => {
          this.teamList = res
        })
        .catch(err => {
          console.log(err)
        })
    },
    //获得指导教师名单
    getTeacherList(){
      let params = new URLSearchParams()
      HttpManager.getTeacherList(params)
        .then(res => {
          this.teacherList = res
        })
        .catch(err => {
          console.log(err)
        })
    },
    //新建作品
    insertOneProduct(){
      let params = new URLSearchParams()
      if(this.product.productionName == null){
        this.$message({
          message: '作品名称不能为空',
          type: 'error'
        })
        return
      }
      if(this.product.productionTeacherId == null){
        this.$message({
          message: '指导教师不能为空',
          type: 'error'
        })
        return
      }
      if(this.product.categoryId == null){
        this.$message({
          message: '作品类型不能为空',
          type: 'error'
        })
        return
      }
      if(this.product.productionDocument == null){
        this.$message({
          message: '上传作品不能为空',
          type: 'error'
        })
        return
      }
      params.append("productionName",this.product.productionName)
      params.append('productionTeacherId',this.product.productionTeacherId)
      params.append('categoryId',this.product.categoryId)
      params.append('teamId',this.product.teamId)
      params.append('teamType',1)
      HttpManager.insertProduct(params)
        .then(res => {
          this.productId = res.id
          return false
        })
        .catch(err => {
          console.log(err)
        })
    },
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/info.scss';
</style>
