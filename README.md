# MainUI
Android项目常见的UI主框架


常见的应用框架

框架一：多个tab+Fragment，点击不同的tab加载不同的Fragment，不能滑动切换只能点击切换；

框架二：多个tab+ViewPager+FragmentPagerAdapter，点击不同的tab加载ViewPager对应的item，可以滑动切换，经过改进，可以实现"强制刷新"！

框架三：多个tab+ViewPager+FragmentPagerAdapter+SlidingMenu，左右滑菜单

其他:

上有标题栏，标题栏可以是在Fragment或ViewPager中的(如QQ，每个页面的标题栏都不一样)或者和tab同级(如微信，所有页面的标题栏都一样)。


![](http://images2015.cnblogs.com/blog/795730/201606/795730-20160627205657484-825714889.jpg)
