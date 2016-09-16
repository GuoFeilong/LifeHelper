**越来越多的APP用到了地图API,所以本人依赖百度地图提供的API做了一个简单的项目,希望大家多多支持和star一下!!**


**SoHOT链接和star地址:**[SoHoT链接下载地址,最后有github开源地址,别错过](http://blog.csdn.net/givemeacondom/article/details/50526518)
**如果您还没有去给SoHOT一颗star而直接看这个项目,那简直是有点损失,希望您点上面的链接,去star和下载体验一下SoHoT,捡起那个大西瓜再来捡这个小芝麻项目,你懂的!!**



 - 首先我们来看下项目预览
   先来一张项目结构图
   ![这里写图片描述](http://img.blog.csdn.net/20160405151922783)
   
   **声明一下,代码没有重新整理,有点略乱,如果你想运行项目到你AS中请一定看仔细下面几张图,红色框框中的apk是可以直接安装到真机上的,模拟器请自重,我没试过模拟器....**

下面来看下你需要注册和修改的几个部分

 1. 自己去百度开发者中心注册一个百度地图的key
 
	 ![这里写图片描述](http://img.blog.csdn.net/20160405152259394)

2 . 如果你想用侧边栏who那个界面的内容,请从聚合数据官网注册,关于笑话api的key,

![这里写图片描述](http://img.blog.csdn.net/20160405152448801)

如果只想看地图部分功能,这个接口可以忽略.

3 .项目功能截图预览
 
   ![这里写图片描述](http://img.blog.csdn.net/20160405152611114)

 **这里是start界面,预览,这里你可以找到Android属性动画和Rxjava
  延时的知识点,大神勿喷,**


![这里写图片描述](http://img.blog.csdn.net/20160405152728161)


**这是主界面这里你可以找到一个自定义的view 包含几种属性和几种状态以及透出接口和百度地图的回调,就是图中正方形的小icon 其实三种不同的icon用的是同一个View** 

![这里写图片描述](http://img.blog.csdn.net/20160405152921755)

**这里是侧边栏的drawer,你可以找到改变drawer距离顶端的距离代码,以及用recylerview 替代原来的menu的功能,实现更自由的侧边栏menu!这块有一个邀请您体验一键回家的功能,忘记做了,不做了,有兴趣的同学可以拿到源码,这这里加入点击事件去地图选点存入数据库或者sp里面,来实现,路线规划都已经实现了,你需要的做的就是保存你家的location!!**

![这里写图片描述](http://img.blog.csdn.net/20160405153153163)

**这里是路线规划的activity,这里你可以找到,activity里面动态加入fragment的知识,以及数据库存储路线路径的,还有fragment和activity的传值,activity和fragment的传值,等知识,这里略微小乱,请耐心看.**

![这里写图片描述](http://img.blog.csdn.net/20160405153316710)

![这里写图片描述](http://img.blog.csdn.net/20160405153329210)

![这里写图片描述](http://img.blog.csdn.net/20160405153345523)

![这里写图片描述](http://img.blog.csdn.net/20160405153358132)

**这里几张分别展示了路线规划的不同结果fragment,虽然是不同的路线却用的同一个fragment,因为百度地图对fragment的支持在连续的fragment里面都加入百度mapview的话会有黑边,笔者也被坑了很长时间,最后曲线救国了,具体可以看我博客关于百度地图和fragment黑边的问题,[fragment遇到百度地图黑边问题](http://blog.csdn.net/givemeacondom/article/details/50926433),还可以找到一个自定义view 就是我们路线详情里面的支持自定义颜色和开始结束点的PointView 目测使用起来还行,需要说明的是both point 笔者没有去实现,有兴趣的可以去实现下,在ondraw里面修改下绘制的坐标即可.还有布局里面路线的 item_layout 里面为了简单直接复制粘贴了N多个imageview 这里其实可以改用代码动态的add ,然后我比较懒没有那样做,有强迫症的建议去自己修改下这部分布局和代码!这里主要为了学习下layoutanimation的动画应用!**

![这里写图片描述](http://img.blog.csdn.net/20160405154122338)
![这里写图片描述](http://img.blog.csdn.net/20160405154134870)
最后一部分就是娱乐笑话板块,从侧边栏点击who进入可以浏览最新的高效图片和文字笑话,你自己可以按照右上角切换内容,还支持下拉刷新和加载更多,貌似有点小bug 自己去发现和修改.毕竟是个Demo,

最最最后,还有一个bottomsheet控件的使用,么有封装好,就是把里面的popwindow和事件用接口的形式透漏出来和activity交互,

直接上图![这里写图片描述](http://img.blog.csdn.net/20160405154403683)

bottomsheet view...
![这里写图片描述](http://img.blog.csdn.net/20160405154423449)

**到此我们的项目预览全部结束,以及大概的知识点也介绍完毕了,重要的就要来了那就是项目写的比较仓促有冗余代码和没封装好的比如baseModel baseView basepresenter等都没比较理想的封装,**

大家都凑乎下吧,

**源码在github上面,地址如下,希望你在下载的同时star一下,能fork下更好,谢谢各位!!**


GitHub地址:[源码地址](https://github.com/GuoFeilong/LifeHelper)
一定要star一下,以后会陆续开源其他项目,比如我的青年区域.....THX!!!
