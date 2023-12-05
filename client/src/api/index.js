import { get, post } from './request'

const HttpManager = {
  // =======================> 用户 API
  // 登录
  loginIn: (params) => post(`user/login/status`, params),
  // 注册
  SignUp: (params) => post(`user/add`, params),
  // 更新用户信息
  updateUserMsg: (params,permissions) => post(permissions+`/update`, params),
  // 返回指定ID的用户
  getUserOfId: (id,permissions) => post(permissions+`/findOne?id=${id}`),
  //获得指导教师名称列表
  getTeacherList: (params) => post(`/teacher/find`,params),
  //返回学生列表
  getStudentList: (params) => post(`student/find`,params),
  //删除学生
  deleteStun: (params) => post(`/student/delete`,params),

  //========================    队伍分组API ==============
  //查询队伍
  getFindTeam:(params) => post(`/team/findTeam`,params),
  //根据队伍内属性查找队伍
  getTeamList: (params) => post(`/team/find`,params),
  //返回某个队伍的人员列表
  getTeamById: (teamId,permissions) => get(permissions+`/stuIdByTeam?teamId=${teamId}`),
  //返回已加入的队伍列表
  getTeamOfId: (id,permissions) => get(permissions+`/stuIdOfTeam?id=${id}`),
  // 删除某个分组里的人
  deleteStudentTeam: (params,permissions) => post(permissions+`/deleteStudentTeam`, params),
  //添加分组中的成员
  insertStuTeam: (params) => post(`/studentTeam/insertStudentTeam`,params),
  //新建分组
  insertTeam: (params) => post(`/team/insertOne`,params),


  // =======================> 作品 API
  //获取指导教师的名字
  getTeacherName: (teacherId) => post(`/teacher/findOne?id=${teacherId}`),
  // 获取单个作品图片列表
  getPictureIdList: (pictureId) => get(`/picTure/findByPictureID?pictureId=${pictureId}`),
  // 获取全部作品列表
  getProductList: (params) => post(`production/find`,params),
  // 返回某一作品根据Id
  getProductionById: (productId) => post(`/production/findOne?productId=${productId}`),
  //删除作品图片中的某一张
  deletePicture: (id) => post(`/picTure/deletePicture?id=${id}`),
  //更新作品信息
  updataProduct: (params) => post(`/production/update`,params),
  //添加作品
  insertProduct: (params) => post(`/production/insertOne`,params),

  // =======================> 班级 API
  // 返回所有班级
  getClassList: (params) => post('/stuClass/find',params),
  // 通过性别对歌手分类
  getSingerOfSex: (sex) => get(`singer/sex/detail?sex=${sex}`),

  // =======================> 作品类别 API
  //添加作品评分类别
  addProductScoreCategory: (params) => post(`productionScoreCategory/insertOne`,params),
  // 获取作品类别
  getCategoryTypeList: (params) => post(`productionCategory/find`,params),
  // 返回的指定用户ID的收藏列表
  getCollectionOfUser: (userId) => get(`collection/detail?userId=${userId}`),
  // 删除作品类别
  deleteProCate: (params) => post(`productionCategory/delete`, params),
  //删除作品类别绑定的评分类别
  deleteProScoreCate: (params) => post(`productionScoreCategory/delete`, params),
  //添加作品类别
  newCategoryType: (params) => post(`productionCategory/insertOne`,params),
  //更改作品类别名称
  updateCategoryName: (params) => post(`/productionCategory/update`,params),
  //下载文档
  downDocument: (url) => get(url,{},'blob'),

  // =======================> 评分 API
//评分汇总
  getProductScoreSort: (categoryId) => post(`productionScore/sort?categoryId=${categoryId}`),
  //获取全部评分类型
  getCategoryStyleAll: (params) => post(`/scoreCategory/find`,params),
  // 获取评分类型
  getCategoryOfStyle: (style) => post(`/productionScoreCategory/find?style=${style}`),
  // 提交评分
  setRank: (params) => post(`productionScore/insertOne`, params),
  // 获取指定作品的评分
  getCategoryList: (productId) => post(`productionScore/find?productId=${productId}`),
  //删除评分类型
  deleCategoryType: (scoreCategoryId) => post(`scoreCategory/delete?id=${scoreCategoryId}`),
  //更新评分类型名称
  updateCategoryType: (params) => post(`scoreCategory/update`,params),
  //添加作品评分类型
  insertCategoryType: (params) => post(`scoreCategory/insertOne`,params),

  // =======================> 评论 API
  // 添加评论
  setComment: (params) => post(`evaluation/add`, params),

  // 返回所有评论
  getAllComment: (productionId) => post(`/evaluation/findAll?productionId=${productionId}`),

  // =======================> 赛事 API
  // 返回赛事
  getCompetition: (params) => post(`/competition/find`,params),
  // 返回指定赛事的获奖作品列表
  getProductByCompetitionId: (params) => post(`/productionCompetition/find`,params),
  // 删除获奖赛事信息
  deleteProductCompetition: (params) => post(`productionCompetition/delete`,params),
  //添加获奖赛事信息
  addProductCompetition: (productionCompetition) => post(`productionCompetition/insertOne`,productionCompetition),
  //添加赛事信息
  addCompetition: (params) => post('/competition/insertOne',params),
  //删除赛事信息
  deleteCompetition: (params) => post(`/competition/delete?id=${params}`),
  //更新赛事信息
  updateCompetition: (params) => post(`/competition/update`,params),
  // 下载音乐
  downloadMusic: (url) => get(url, {}, 'blob')
}

export { HttpManager }
