// pages/borrow/borrow.js
var app=getApp()
Page({
  data:{
    books:[],
    borrowcount:0,
    sanCode:''
  },
 
  onLoad:function(options){
    // 页面初始化 options为页面跳转所带来的参数
    var that=this
    app.getBooks(function(books){
      that.setData({
        books:books,
       borrowcount:books.length
      })
    })
    if(this.data.borrowcount==0){
      wx.showModal({
        title: '借书栏为空',
        content: '您暂时没有借书，可以点击借书按钮',
      })
    }
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
  scanCode:function(){
    var that=this
    wx.scanCode({
      success: function(res){
        that.setData({
          sanCode:res
        })
        var bookId=res
        var books=that.data.books
        app.getBookById(function(book){
           books.add(book)
           that.setData({
             books:books,
             borrowcount:borrowcount+1,
           })
        },bookId)
       
       
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