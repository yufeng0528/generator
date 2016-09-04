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
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.SimpleXMLMapperGenerator;

public class SimpleJavaServiceGenerator extends AbstractJavaClientGenerator {
	
	private FullyQualifiedJavaType mapperType;

	public SimpleJavaServiceGenerator() {
        super(true);
    }

    public SimpleJavaServiceGenerator(boolean requiresMatchedXMLGenerator) {
        super(requiresMatchedXMLGenerator);
    }
    
    private String getMapperName(){
    	String myBatis3JavaMapperType = mapperType.getFullyQualifiedName();
    	if(myBatis3JavaMapperType.indexOf(".") == -1)
    		return myBatis3JavaMapperType;
    	int start = myBatis3JavaMapperType.lastIndexOf(".") + 1;
    	return myBatis3JavaMapperType.substring(start, start + 1).toLowerCase() + 
    			myBatis3JavaMapperType.substring(start + 1, myBatis3JavaMapperType.length());
    }
    
    @Override
    public List<CompilationUnit> getCompilationUnits() {
    	Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
    	
        progressCallback.startTask(getString("Progress.19", //$NON-NLS-1$
                introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();

        importedTypes.add(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getMyBatis3JavaServiceType());
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addAnnotation("@Service");
        commentGenerator.addJavaFileComment(topLevelClass);
        
        mapperType = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
        importedTypes.add(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        importedTypes.add(mapperType);
        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(mapperType);
        field.setName(getMapperName()); //$NON-NLS-1$
        field.addAnnotation("@Autowired");
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);
        
        
        addSelectByPrimaryKeyMethod(topLevelClass, importedTypes);
        addInsertMethood(topLevelClass, importedTypes);
        addUpdateByPrimaryKeyMethod(topLevelClass, importedTypes);
        addDeleteByPrimaryKeyMethod(topLevelClass, importedTypes);
        
        topLevelClass.addImportedTypes(importedTypes);

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().modelExampleClassGenerated(
                topLevelClass, introspectedTable)) {
            answer.add(topLevelClass);
        }

        return answer;
    }
    
    protected void addSelectByPrimaryKeyMethod(TopLevelClass topLevelClass, Set<FullyQualifiedJavaType> importedTypes) {
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
        	importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        	
            Method method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName("getById"); //$NON-NLS-1$
            method.setReturnType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
            List<IntrospectedColumn> introspectedColumns = introspectedTable
                    .getPrimaryKeyColumns();
            StringBuilder sb = new StringBuilder();
            for (IntrospectedColumn introspectedColumn : introspectedColumns) {
                FullyQualifiedJavaType type = introspectedColumn
                        .getFullyQualifiedJavaType();
                importedTypes.add(type);
                Parameter parameter = new Parameter(type, introspectedColumn
                        .getJavaProperty());
                method.addParameter(parameter);
                sb.append(",").append(parameter.getName());
            }
            sb.deleteCharAt(0);
            method.addBodyLine("return " + getMapperName() + ".selectByPrimary(" + sb.toString() + ");"); //$NON-NLS-1$
            topLevelClass.addMethod(method);
        }
    }
    
    protected void addInsertMethood(TopLevelClass topLevelClass, Set<FullyQualifiedJavaType> importedTypes) {
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            Method method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName("add"); //$NON-NLS-1$
            method.setReturnType(PrimitiveTypeWrapper.getStringInstance());
            method.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), "record"));
            method.addBodyLine(getMapperName() + ".insert(record);"); 
            method.addBodyLine("return null;"); //$NON-NLS-1$
            topLevelClass.addMethod(method);
        }
    }
    
    protected void addUpdateByPrimaryKeyMethod(TopLevelClass topLevelClass, Set<FullyQualifiedJavaType> importedTypes) {
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            Method method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName("update"); //$NON-NLS-1$
            method.setReturnType(PrimitiveTypeWrapper.getStringInstance());
            method.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), "record"));
            method.addBodyLine(getMapperName() + ".updateByPrimaryKey(record);");
            method.addBodyLine("return null;"); //$NON-NLS-1$
            topLevelClass.addMethod(method);
        }
    }
    
    protected void addDeleteByPrimaryKeyMethod(TopLevelClass topLevelClass, Set<FullyQualifiedJavaType> importedTypes) {
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            Method method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName("deleteById"); //$NON-NLS-1$
            method.setReturnType(PrimitiveTypeWrapper.getStringInstance());
            
            List<IntrospectedColumn> introspectedColumns = introspectedTable
                    .getPrimaryKeyColumns();
            StringBuilder sb = new StringBuilder();
            for (IntrospectedColumn introspectedColumn : introspectedColumns) {
                FullyQualifiedJavaType type = introspectedColumn
                        .getFullyQualifiedJavaType();
                importedTypes.add(type);
                Parameter parameter = new Parameter(type, introspectedColumn
                        .getJavaProperty());
                method.addParameter(parameter);
                sb.append(",").append(parameter.getName());
            }
            sb.deleteCharAt(0);
            method.addBodyLine(getMapperName() + ".deleteByPrimaryKey(" + sb.toString() + ");");
            method.addBodyLine("return null;"); //$NON-NLS-1$
            topLevelClass.addMethod(method);
        }
    }

	@Override
	public AbstractXmlGenerator getMatchedXMLGenerator() {
		 return new SimpleXMLMapperGenerator();
	}

}
