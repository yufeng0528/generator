/**
 *    Copyright 2006-2017 the original author or authors.
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
package org.mybatis.generator.codegen.mybatis3.javamapper.elements.dao;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.ibatis2.dao.elements.AbstractDAOElementGenerator;
import org.mybatis.generator.codegen.ibatis2.dao.elements.UpdateByExampleParmsInnerclassGenerator;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.rules.Rules;

/**
 * DAO generator for iBatis.
 * 
 * @author Jeff Butler
 * 
 */
public class DAOGenerator extends AbstractJavaClientGenerator {

    public DAOGenerator() {
    	super(false);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString(
                "Progress.14", table.toString())); //$NON-NLS-1$
        TopLevelClass topLevelClass = getTopLevelClassShell();
        Interface interfaze = getInterfaceShell();

        addCountByExampleMethod(topLevelClass, interfaze);
        addDeleteByExampleMethod(topLevelClass, interfaze);
        addDeleteByPrimaryKeyMethod(topLevelClass, interfaze);
        addInsertMethod(topLevelClass, interfaze);
        addInsertSelectiveMethod(topLevelClass, interfaze);
        addSelectByExampleWithBLOBsMethod(topLevelClass, interfaze);
        addSelectByExampleWithoutBLOBsMethod(topLevelClass, interfaze);
        addSelectByPrimaryKeyMethod(topLevelClass, interfaze);
        addUpdateByExampleParmsInnerclass(topLevelClass, interfaze);
        addUpdateByExampleSelectiveMethod(topLevelClass, interfaze);
        addUpdateByExampleWithBLOBsMethod(topLevelClass, interfaze);
        addUpdateByExampleWithoutBLOBsMethod(topLevelClass, interfaze);
        addUpdateByPrimaryKeySelectiveMethod(topLevelClass, interfaze);
        addUpdateByPrimaryKeyWithBLOBsMethod(topLevelClass, interfaze);
        addUpdateByPrimaryKeyWithoutBLOBsMethod(topLevelClass, interfaze);

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().clientGenerated(interfaze,
                topLevelClass, introspectedTable)) {
            answer.add(topLevelClass);
            answer.add(interfaze);
        }

        return answer;
    }

    protected TopLevelClass getTopLevelClassShell() {
        FullyQualifiedJavaType interfaceType = new FullyQualifiedJavaType(
                introspectedTable.getDAOInterfaceType());
        FullyQualifiedJavaType implementationType = new FullyQualifiedJavaType(
                introspectedTable.getDAOImplementationType());


        TopLevelClass answer = new TopLevelClass(implementationType);
        answer.setVisibility(JavaVisibility.PUBLIC);

        return answer;
    }

    protected Interface getInterfaceShell() {
        Interface answer = new Interface(new FullyQualifiedJavaType(
                introspectedTable.getDAOInterfaceType()));
        answer.setVisibility(JavaVisibility.PUBLIC);

        String rootInterface = introspectedTable
                .getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        if (!stringHasValue(rootInterface)) {
            rootInterface = context.getJavaClientGeneratorConfiguration()
                    .getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        }

        if (stringHasValue(rootInterface)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(
                    rootInterface);
            answer.addSuperInterface(fqjt);
            answer.addImportedType(fqjt);
        }


        context.getCommentGenerator().addJavaFileComment(answer);

        return answer;
    }

    protected void addCountByExampleMethod(TopLevelClass topLevelClass,
            Interface interfaze) {
        if (introspectedTable.getRules().generateCountByExample()) {
        }
    }

    protected void addDeleteByExampleMethod(TopLevelClass topLevelClass,
            Interface interfaze) {
        if (introspectedTable.getRules().generateDeleteByExample()) {
        }
    }

    protected void addDeleteByPrimaryKeyMethod(TopLevelClass topLevelClass,
            Interface interfaze) {
        if (introspectedTable.getRules().generateDeleteByPrimaryKey()) {
        }
    }

    protected void addInsertMethod(TopLevelClass topLevelClass,
            Interface interfaze) {
        if (introspectedTable.getRules().generateInsert()) {
        }
    }

    protected void addInsertSelectiveMethod(TopLevelClass topLevelClass,
            Interface interfaze) {
        if (introspectedTable.getRules().generateInsertSelective()) {
        }
    }

    protected void addSelectByExampleWithBLOBsMethod(
            TopLevelClass topLevelClass, Interface interfaze) {
        if (introspectedTable.getRules().generateSelectByExampleWithBLOBs()) {
        }
    }

    protected void addSelectByExampleWithoutBLOBsMethod(
            TopLevelClass topLevelClass, Interface interfaze) {
        if (introspectedTable.getRules().generateSelectByExampleWithoutBLOBs()) {
            
        }
    }

    protected void addSelectByPrimaryKeyMethod(TopLevelClass topLevelClass,
            Interface interfaze) {
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            
        }
    }

    protected void addUpdateByExampleParmsInnerclass(
            TopLevelClass topLevelClass, Interface interfaze) {
        Rules rules = introspectedTable.getRules();
        if (rules.generateUpdateByExampleSelective()
                || rules.generateUpdateByExampleWithBLOBs()
                || rules.generateUpdateByExampleWithoutBLOBs()) {
            AbstractDAOElementGenerator methodGenerator =
                    new UpdateByExampleParmsInnerclassGenerator();
            initializeAndExecuteGenerator(methodGenerator, topLevelClass,
                    interfaze);
        }
    }

    protected void addUpdateByExampleSelectiveMethod(
            TopLevelClass topLevelClass, Interface interfaze) {
        if (introspectedTable.getRules().generateUpdateByExampleSelective()) {
            
        }
    }

    protected void addUpdateByExampleWithBLOBsMethod(
            TopLevelClass topLevelClass, Interface interfaze) {
        if (introspectedTable.getRules().generateUpdateByExampleWithBLOBs()) {
            
        }
    }

    protected void addUpdateByExampleWithoutBLOBsMethod(
            TopLevelClass topLevelClass, Interface interfaze) {
        if (introspectedTable.getRules().generateUpdateByExampleWithoutBLOBs()) {
            
        }
    }

    protected void addUpdateByPrimaryKeySelectiveMethod(
            TopLevelClass topLevelClass, Interface interfaze) {
        if (introspectedTable.getRules().generateUpdateByPrimaryKeySelective()) {
            
        }
    }

    protected void addUpdateByPrimaryKeyWithBLOBsMethod(
            TopLevelClass topLevelClass, Interface interfaze) {
        if (introspectedTable.getRules().generateUpdateByPrimaryKeyWithBLOBs()) {
            
        }
    }

    protected void addUpdateByPrimaryKeyWithoutBLOBsMethod(
            TopLevelClass topLevelClass, Interface interfaze) {
        if (introspectedTable.getRules()
                .generateUpdateByPrimaryKeyWithoutBLOBs()) {
            
        }
    }

    protected void initializeAndExecuteGenerator(
            AbstractDAOElementGenerator methodGenerator,
            TopLevelClass topLevelClass, Interface interfaze) {
      
    }

    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator() {
        // this method is not called for iBATIS2
        return null;
    }
}
