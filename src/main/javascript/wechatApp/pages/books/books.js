// pages/books/books.js
var app=getApp()
Page({
  data:{
    keyword:'',
    books:[], 
  },
  onLoad:function(options){
    // 页面初始化 options为页面跳转所带来的参数
    var that=this
    app.getBooks(function(books){
      that.setData({
        books:books,
      })
    })
    this.setData(
      {
        keyword:options.keyword
      }
    )
  },
  showDetail:function(e){
    var book=e.currentTarget.dataset.id
    wx.navigateTo({
      url: '../message/message?id='+book,
      success: function(res){
        // success
      },
      fail: function() {
        // fail
      },
      complete: function() {
        // complete
      }
    })
  },
  onReady:function(){
    // 页面渲染完成
  },
  onShow:function(){
    // 页面显示
  },
  onHide:function(){
    // 页面隐藏
  },
  onUnload:function(){
    // 页面关闭
  }
})