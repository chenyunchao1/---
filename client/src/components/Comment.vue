<template>
  <div>
    <div class="comment">
      <h2>
        <span>评论</span>
        <span class="part__tit_desc">共 {{commentList.length}} 条评论</span>
      </h2>
      <div class="comment-msg">
        <el-input
          class="comment-input"
          type="textarea"
          placeholder="期待您的精彩评论..."
          :rows="2"
          v-model="textarea">
        </el-input>
      </div>
      <el-button type="primary" class="sub-btn" @click="postComment()">发表评论</el-button>
    </div>
    <ul class="popular" v-for="(item, index) in commentList" :key="index">
      <li>

        <div class="popular-msg">
          <ul>
            <li class="name">{{userNames[index]}}</li>
            <li class="content">{{item.evaluationText}}</li>
            <li class="time">{{item.evaluationTime}}</li>
          </ul>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import mixin from '../mixins'
import { mapGetters } from 'vuex'
import { HttpManager } from '../api/index'
import { ICON } from '../assets/icon/index'

export default {
  name: 'comment',
  mixins: [mixin],
  props: {
    playId: Number, // 作品Id
    type: Number // 歌单（1）/歌曲（0）
  },
  data () {
    return {
      commentList: [], // 存放评论内容
      userType: [], // 保存评论用户班级
      userNames: [], // 保存评论用户名字
      textarea: '', // 存放输入内容
      ZAN: ICON.ZAN
    }
  },
  computed: {
    ...mapGetters([
      'userId', // 用户ID
      'index', // 列表中的序号
      'loginIn', // 用户是否登录
      'userName',// 用户名称
      'permissions', //用户类别
    ])
  },
  watch: {
    id () {
      this.getComment()
    }
  },
  mounted () {
    this.getComment()
  },
  methods: {
    // 获取所有评论
    getComment () {
      HttpManager.getAllComment(this.playId)
        .then(res => {
          this.commentList = res
          for (let item of res) {
            let u;
            if(item.evaluationPersonType === 2){
              u = "teacher";
            }else{
              u = "student"
            }
            this.getUsers(item.evaluationPersonId,u)
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    // 获取评论用户的类型和名称
    getUsers (id,u) {
      HttpManager.getUserOfId(id,u)
        .then(res => {
          this.userType.push(u)
          this.userNames.push(res.name)
        })
        .catch(err => {
          console.log(err)
        })
    },

    // 提交评论
    postComment () {
      if (this.loginIn) {
        // 0 代表歌曲， 1 代表歌单
        let params = new URLSearchParams()
        params.append('productionId', this.playId)
        params.append('userId', this.userId)
        if(this.permissions == "teacher"){
          params.append('type', 2)
        }
        if(this.permissions == "student"){
          params.append('type', 1)
        }
        params.append('userName', this.userName)
        params.append('evaluationText', this.textarea)
        HttpManager.setComment(params)
          .then(res => {
            if (res.code === 1) {
              this.textarea = ''
              this.getComment()
              this.notify('评论成功', 'success')
            } else {
              this.notify('评论失败', 'error')
            }
          })
          .catch(err => {
            console.log(err)
          })
      } else {
        this.notify('请先登录', 'warning')
      }
    },

  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/comment.scss';
</style>
