//index.js
//获取应用实例
var app = getApp()
Page({
  data: {
    select_title:true,
    selected:'题名',
    select_area:true,
    input_content:'',
    isdigit:false,
    
   
  },
  click_one:function(){
    var select_title=this.data.select_title;
    if(select_title==true){
      this.setData({
        select_title:false,
        select_area:false
      })
    }else{
       this.setData({
        select_title:true,
        select_area:true
      })
    }

  },
  select_one:function(e){
    if(e.target.dataset.me=='IBSN'){
      this.setData({
        isdigit:true
      })
    }else{
       this.setData({
        isdigit:false
      })
    }
    this.setData({
      selected:e.target.dataset.me,
      select_title:true,
      select_area:true
    })
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  get_search:function(e){
    this.setData({
      input_content:e.detail.value
    })
  },
  searchByKeyword:function(e){
    wx.navigateTo({
      url: '../books/books?keyword='+e.currentTarget.dataset.keyword,
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
  searchs:function(){
    var that =this
    var search_content=that.data.input_content
    if(search_content.replace(/(^\s*)|(\s*$)/g, "")==''){
      return;
    }
    wx.navigateTo({
      
      url: '../books/books?keyword='+search_content+'&tag='+that.data.selected,
      success: function(res){
        // success
        console.log("to books success")
      },
      fail: function() {
        // fail
        console.log("to books fail")
      },
      complete: function() {
        // complete
        console.log("to books complete")
      }
    })
  },
  onLoad: function () {
    console.log('onLoad')
    var that = this
    //调用应用实例的方法获取全局数据
    app.getUserInfo(function(userInfo){
      //更新数据
      that.setData({
        userInfo:userInfo
      })
    })
  }
})
