https://download.csdn.net/download/sinat_23163657/13119327
# ibatis、mybatis SQL参数拼接打印插件

在控制台选择ibatis、mybatis输出的sql语句，按F9，自动拼接参数并输出完整的SQL语句。快捷键可以通过eclipse快捷键设置功能自行调整。    

插件下载：[https://download.csdn.net/download/sinat_23163657/13119327](https://download.csdn.net/download/sinat_23163657/13119327)

注意：

1. 选择sql时，需要选中完整的两行数据  

   ```
   ibatis：
   	xxxxxxxxxxxx Preparing Statement: SELECT xxxxxxxxxxxx  
   	xxxxxxxxxxxx Parameters: [xxxxxxxxxxxx]  
   mybatis:  
   	xxxxxxxxxxxx ==>  Preparing: SELECT xxxxxxxxxxxx  
       xxxxxxxxxxxx ==> Parameters: xxxxxxxxxxxx
   ```

2. 该工具实现的原理就是通过切割“Parameters”后的参数，对于ibatis是通过字符串“, ”进行切割，mybatis是通过“\\(\\w+\\), ”正则表达式进行切割，所以如果SQL中某个参数本身也包含这种特殊字符串，最终会导致解析出来的参数个数比实际的参数个数多。出现这种情况工具最后会给出提示，此时需要人工调整


关于eclipse插件离线安装：

 1. 在eclipse根目录下新建一个links文件夹和myplugins/eclipse/plugins文件夹
 2. 在links文件夹下新建一个myplugins.link的文件，内容为path=myplugins
 3. 将插件拷贝到myplugins/eclipse/plugins文件夹下并重启eclipse
