// pages/strategy/strategy-detail/strategy-detail.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    tags: ['户外', '休闲'],
    spots_nums: 5,
    days: 3
},
onStarClick: function(e) {
    // 模拟分享逻辑
    console.log('五角星按钮被点击');
    // 这里可以添加分享逻辑
    // 例如 wx.showActionSheet 或者 wx.share
},

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})