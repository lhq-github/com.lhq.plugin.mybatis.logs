# ibatis、mybatis SQL参数拼接打印插件

选择控制台ibatis、mybatis输出的sql语句，按F9，自动拼接参数并输出完整的SQL语句<br>
[插件jar下载](https://github.com/lhq-github/com.lhq.plugin.mybatis.logs/blob/master/jar/com.lhq.plugin.mybatis.logs_1.0.0.20201112.jar) <br>
注意：选择sql时，需要选中完整的三行数据<br>
	ibatis：<br>
		xxxxxxxxxxxx Preparing Statement: SELECT xxxxxxxxxxxx<br>
		xxxxxxxxxxxx Parameters: [xxxxxxxxxxxx]<br>
		xxxxxxxxxxxx Types: [xxxxxxxxxxxx]<br>
	mybatis:<br>
		xxxxxxxxxxxx ==>  Preparing: SELECT xxxxxxxxxxxx<br>
    	xxxxxxxxxxxx ==> Parameters: xxxxxxxxxxxx<br>
    	xxxxxxxxxxxx <==      Total: xxxxxxxxxxxx<br>