Page({
  data: {
    guideId: null, // 攻略ID
    guide: null, // 攻略详情
    isOwner: false, // 是否是攻略所有者
    isEditing: false, // 是否处于编辑模式
  },

  // 页面加载时获取攻略详情
  onLoad(options) {
    const { id } = options;
    if (!id) {
      wx.showToast({
        title: '缺少攻略ID',
        icon: 'none',
      });
      return;
    }
    this.setData({ guideId: id });
    this.fetchGuideDetail(id);
  },

  // 获取攻略详情
  fetchGuideDetail(guideId) {
    wx.request({
      url: `http://localhost:8080/api/v1/guides/${guideId}`, // 本地后端地址
      method: 'GET',
      success: (res) => {
        if (res.statusCode === 200) {
          const guide = res.data;
          const isOwner = this.checkOwner(guide); // 检查是否是攻略所有者
          this.setData({ guide, isOwner });
        } else {
          wx.showToast({
            title: '获取攻略详情失败',
            icon: 'none',
          });
        }
      },
      fail: (err) => {
        console.error('网络请求失败', err);
        wx.showToast({
          title: '网络请求失败',
          icon: 'none',
        });
      },
    });
  },

  // 检查是否是攻略所有者
  checkOwner(guide) {
    const currentUserId = wx.getStorageSync('userId'); // 假设当前用户的 ID 存储在本地
    return guide.ownerId === currentUserId;
  },

  // 进入编辑模式
  onEditGuide() {
    this.setData({ isEditing: true });
  },

  // 保存编辑后的攻略
  onSaveGuide() {
    const { guideId, guide } = this.data;
    wx.request({
      url: `http://localhost:8080/api/v1/guides/${guideId}/edit`, // 本地后端地址
      method: 'PUT',
      data: guide,
      success: (res) => {
        if (res.statusCode === 200) {
          wx.showToast({
            title: '保存成功',
            icon: 'success',
          });
          this.setData({ isEditing: false }); // 退出编辑模式
          this.fetchGuideDetail(guideId); // 重新获取攻略详情
        } else {
          wx.showToast({
            title: '保存失败',
            icon: 'none',
          });
        }
      },
      fail: (err) => {
        console.error('网络请求失败', err);
        wx.showToast({
          title: '网络请求失败',
          icon: 'none',
        });
      },
    });
  },

  // 增加一天
  onAddDay() {
    const { guideId } = this.data;
    wx.request({
      url: `http://localhost:8080/api/v1/guides/${guideId}/addDay`, // 本地后端地址
      method: 'POST',
      success: (res) => {
        if (res.statusCode === 200) {
          wx.showToast({
            title: '增加一天成功',
            icon: 'success',
          });
          // 重新获取攻略详情
          this.fetchGuideDetail(guideId);
        } else {
          wx.showToast({
            title: '增加一天失败',
            icon: 'none',
          });
        }
      },
      fail: () => {
        wx.showToast({
          title: '网络请求失败',
          icon: 'none',
        });
      },
    });
  },

  // 取消编辑
  onCancelEdit() {
    this.setData({ isEditing: false });
  },

  // 监听标题输入
  onTitleInput(e) {
    const title = e.detail.value;
    this.setData({
      guide: { ...this.data.guide, title },
    });
  },

  // 监听描述输入
  onDescriptionInput(e) {
    const description = e.detail.value;
    this.setData({
      guide: { ...this.data.guide, description },
    });
  },
});