//app.js
App({
  onLaunch: function () {
    //调用API从本地缓存中获取数据
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
  },
  getUserInfo:function(cb){
    var that = this
    if(this.globalData.userInfo){
      typeof cb == "function" && cb(this.globalData.userInfo)
    }else{
      //调用登录接口
      wx.login({
        success: function () {
          wx.getUserInfo({
            success: function (res) {
              that.globalData.userInfo = res.userInfo
              typeof cb == "function" && cb(that.globalData.userInfo)
            }
          })
        }
      })
    }
  },
  getBooks:function(cb){      
         typeof cb == "function" && cb(this.globalData.books)    
  },
  getBookById:function(cb,bookId){
    if(bookId=='9787302275145'){
          var book={
            title:'软件质量保证与测试',
            id:'9787302275145',
            author:'秦航',
            image:'../../image/book3.png',
            count:3,
            remain:2,
            publisher:'清华大学出版社',
            content:'《软件质量保证与测试》全面系统地讲述了软件质量保证与测试的概念、原理和典型的方法，并介绍了相关软件项目的管理技术。《软件质量保证与测试》共15章，第1章是概述，第2章至第8章讲述了软件质量工程体系、软件配置管理和质量度量、软件可靠性度量和测试、软件质量标准、软件评审、软件全面质量管理、高质量编程，第9章至第15章分别讲述了软件测试过程、黑盒测试、白盒测试、基于缺陷模式的软件测试、集成测试、系统测试、测试管理。《软件质量保证与测试》的附录A为基本术语，附录B和附录C分别讲述了计算机软件质量保证计划规范和计算机软件测试文件编制规范，对读者深入理解软件质量测试和保证很有帮助，也是上机实习的好材料。 《软件质量保证与测试》条理清晰、语言流畅、通俗易懂，在内容组织上力求自然、合理、循序渐进，并提供了丰富的实例和实践要点，很好地把握了软件测试学科的特点，使读者更容易理解所学的理论知识、掌握软件质量保证与测试的应用之道。《软件质量保证与测试》可作为高等学校的软件工程专业、计算机应用专业和相关专业的教材，成为软件质量保证工程师和软件测试工程师的良师益友，也可作为其他各类软件工程技术人员的参考书。'
          }
          typeof cb=="function" && cb(book)
        }
  },
  globalData:{
    userInfo:null,
    books:[
        { id:'9787802058439',
        image:'../../image/book1.png',
        title:'钢铁是怎么炼成的',
        author:'尼古拉·奥斯特洛夫斯基',
        count:2,
        remain:1,
        publisher:'开明出版社',
        content:'《钢铁是怎样炼成的》是前苏联作家尼古拉·奥斯特洛夫斯基所著的一部长篇小说，于1933年写成。小说通过记叙保尔·柯察金的成长道路告诉人们，一个人只有在革命的艰难困苦中战胜敌人也战胜自己，只有在把自己的追求和祖国、人民的利益联系在一起的时候，才会创造出奇迹，才会成长为钢铁战士。',      
        relateId:['123456788',], 
      },
      {
        id:'123456788',
        image:'../../image/book2.png',
        title:'海的女儿',
        author:'安徒生',
        count:3,
        remain:1,
        publisher:'北京师范大学出版社',
        content:'《海的女儿》是安徒生的代表作之一，是最脍炙人口的名篇之一，广为流传。也被译为《人鱼公主》。该童话被多次改编为电影、木偶剧、儿童剧。海王国有一个美丽而善良的美人鱼。美人鱼爱上了陆地上英俊的王子，为了追求爱情幸福，不惜忍受巨大痛苦，脱去鱼尾，换来人腿。但王子最后却和人间的女子结了婚。巫婆告诉美人鱼，只要杀死王子，并使王子的血流到自己腿上，美人鱼就可回到海里，重新过着无忧无虑的生活。可她却为了王子的幸福，自己投入海中，化为泡沫。',
        relateId:['9787802058439',]
      }
    ]
    
  }
})