##我自己的xml配置

分支release_1.3.6.1

###MySQLPaginationPlugin

分页配置

###RenameExampleClassPlugin

把Example命名为Criteria

<code>

	<generatorConfiguration>
		  <classPathEntry location="jar包的路径/mysql-connector-java-5.1.17.jar" />
		  <context id="DB2Tables" targetRuntime="MyBatis3">
			  <property name="javaFileEncoding" value="UTF-8"/> 

			  <plugin type="org.mybatis.generator.plugins.MySQLPaginationPlugin" />
			  <plugin type="org.mybatis.generator.plugins.RenameExampleClassPlugin" >
			      <property name="searchString" value="Example$"/>
			      <property name="replaceString" value="Criteria"/>
			    </plugin>
			  <commentGenerator type="org.mybatis.generator.plugins.DefaultCommentGenerator">
			    <property name="suppressDate" value="true"/>
			    <property name="suppressAllComments" value="false"/>
			    <property name="addRemarkComments" value="true"/>
			  </commentGenerator>
			
			    <jdbcConnection driverClass="com.mysql.jdbc.Driver"
			        connectionURL="jdbc:mysql://127.0.0.1:3306/market?useUnicode=true"
			        userId="root"
			        password="yiguokeji01db">
			        <property name="useInformationSchema"  value="true"/>
			    </jdbcConnection>
			
			    <javaTypeResolver >
			      <property name="forceBigDecimals" value="true" />
			    </javaTypeResolver>
			
			    <javaModelGenerator targetPackage="yike.obj" targetProject="自己的文件目录">
			      <property name="enableSubPackages" value="true" />
			      <property name="trimStrings" value="false" />
			    </javaModelGenerator>
			
			    <sqlMapGenerator targetPackage="yike.dao"  targetProject="自己的文件目录">
			      <property name="enableSubPackages" value="true" />
			    </sqlMapGenerator>
				
				<javaClientGenerator type="XMLMAPPER" targetPackage="yike.mapper" targetProject="自己的文件目录">  
				  <property name="enableSubPackages" value="true" />
				</javaClientGenerator>  

				<javaClientDaoGenerator targetPackage="yike.dao" targetProject="自己的文件目录">  
				  <property name="enableSubPackages" value="true" />
				</javaClientDaoGenerator> 
				
			    <table tableName="promotion_info" domainObjectName="PromotionInfo" enableCountByExample="true" enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="true" selectByExampleQueryId="true">
			      <generatedKey column="id" sqlStatement="JDBC"/>
			    </table>
		
		   
		  </context>
	  
	</generatorConfiguration>
</code>
