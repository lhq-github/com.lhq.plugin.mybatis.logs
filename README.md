# ibatis、mybatis SQL参数拼接打印插件

在控制台选择ibatis、mybatis输出的sql语句，按F9，自动拼接参数并输出完整的SQL语句  

链接: https://pan.baidu.com/s/1ri-sxcgq3nSsnqvmbgSmJA 提取码: 3ati    

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

2. 该工具实现的原理就是通过切割“Parameters”后的参数，对于ibatis是通过字符串“, ”进行切割，mybatis是通过“\\(\\w+\\), ”正则表达式进行切割，所以如果SQL某个参数本身也包含这种特殊字符串，最终会导致解析出来的参数个数比实际的参数个数多。出现这种数据工具最后会给出提示，此时需要人工调整

