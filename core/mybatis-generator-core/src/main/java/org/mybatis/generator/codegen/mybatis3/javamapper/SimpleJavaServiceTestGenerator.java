/**
 *    Copyright 2006-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.codegen.mybatis3.javamapper;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.SimpleXMLMapperGenerator;

public class SimpleJavaServiceTestGenerator extends AbstractJavaClientGenerator {
	
	private FullyQualifiedJavaType serviceType;

	public SimpleJavaServiceTestGenerator() {
        super(true);
    }

    public SimpleJavaServiceTestGenerator(boolean requiresMatchedXMLGenerator) {
        super(requiresMatchedXMLGenerator);
    }
    
    private String getServiceName(){
    	String myBatis3JavaServiceType = serviceType.getFullyQualifiedName();
    	if(myBatis3JavaServiceType.indexOf(".") == -1)
    		return myBatis3JavaServiceType;
    	int start = myBatis3JavaServiceType.lastIndexOf(".") + 1;
    	return myBatis3JavaServiceType.substring(start, start + 1).toLowerCase() + 
    			myBatis3JavaServiceType.substring(start + 1, myBatis3JavaServiceType.length());
    }
    
    @Override
    public List<CompilationUnit> getCompilationUnits() {
    	Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
    	
        progressCallback.startTask(getString("Progress.20", //$NON-NLS-1$
                introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getMyBatis3JavaServiceTestType());
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        
        serviceType = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaServiceType());
        importedTypes.add(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        importedTypes.add(serviceType);
        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(serviceType);
        field.setName(getServiceName()); //$NON-NLS-1$
        field.addAnnotation("@Autowired");
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);
        
        
        addInsertMethood(topLevelClass, importedTypes);
        addUpdateByPrimaryKeyMethod(topLevelClass, importedTypes);
        
        topLevelClass.addImportedTypes(importedTypes);

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().modelExampleClassGenerated(
                topLevelClass, introspectedTable)) {
            answer.add(topLevelClass);
        }

        return answer;
    }
    
    protected void addInsertMethood(TopLevelClass topLevelClass, Set<FullyQualifiedJavaType> importedTypes) {
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            Method method = new Method();
            method.addAnnotation("@Test");
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName("testAdd"); //$NON-NLS-1$
            
            FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
            importedTypes.add(type);
            method.addParameter(new Parameter(type, "record"));
            method.addBodyLine(getServiceName() + ".insert(record);"); 
            topLevelClass.addMethod(method);
        }
    }
    
    protected void addUpdateByPrimaryKeyMethod(TopLevelClass topLevelClass, Set<FullyQualifiedJavaType> importedTypes) {
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            Method method = new Method();
            method.addAnnotation("@Test");
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName("update"); //$NON-NLS-1$
            
            FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
            importedTypes.add(type);
            method.addParameter(new Parameter(type, "record"));
            method.addBodyLine(getServiceName() + ".updateByPrimaryKey(record);");
            topLevelClass.addMethod(method);
        }
    }

	@Override
	public AbstractXmlGenerator getMatchedXMLGenerator() {
		 return new SimpleXMLMapperGenerator();
	}

}
