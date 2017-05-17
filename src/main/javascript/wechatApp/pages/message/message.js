// pages/message/message.js
var app=getApp()
Page({
  data:{
    book_id:'',
    books:[]
  },
  onLoad:function(options){
    // 页面初始化 options为页面跳转所带来的参数
     this.setData({
      book_id:options.id
    })
    var that=this
    app.getBooks(function(books){
      that.setData({
        books:books
      })
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