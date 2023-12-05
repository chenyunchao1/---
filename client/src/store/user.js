const user = {
  state: {
    userId: '',
    userName: '',
    permissions: ''
  },
  getters: {
    userId: state => {
      let userId = state.userId
      if (!userId) {
        userId = JSON.parse(window.localStorage.getItem('userId') || null)
      }
      return userId
    },
    userName: state => {
      let userName = state.userName
      if (!userName) {
        userName = JSON.parse(window.localStorage.getItem('userName') || null)
      }
      return userName
    },
    permissions: state => {
      let permissions = state.permissions
      if (!permissions) {
        permissions = JSON.parse(window.localStorage.getItem('permissions') || null)
      }
      return permissions
    }
  },
  mutations: {
    setUserId: (state, userId) => {
      state.userId = userId
      window.localStorage.setItem('userId', JSON.stringify(userId))
    },
    setUserName: (state, userName) => {
      state.userName = userName
      window.localStorage.setItem('userName', JSON.stringify(userName))
    },
    setPermissions: (state, permissions) => {
      state.permissions = permissions
      window.localStorage.setItem('permissions', JSON.stringify(permissions))
    }
  },
  actions: {}
}

export default user
