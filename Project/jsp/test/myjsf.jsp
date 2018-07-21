<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : myjsf
    Created on : Dec 30, 2007, 7:04:18 PM
    Author     : Ketut Kartika T
-->
<jsp:root version="1.2" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:ui="http://www.sun.com/web/ui">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <ui:page binding="#{test$myjsf.page1}" id="page1">
            <ui:html binding="#{test$myjsf.html1}" id="html1">
                <ui:head binding="#{test$myjsf.head1}" id="head1">
                    <ui:link binding="#{test$myjsf.link1}" id="link1" url="/resources/stylesheet.css"/>
                </ui:head>
                <ui:body binding="#{test$myjsf.body1}" id="body1" style="-rave-layout: grid">
                    <ui:form binding="#{test$myjsf.form1}" id="form1">
                        <ui:label binding="#{test$myjsf.label2}" id="label2" style="position: absolute; left: 24px; top: 24px" text="Name"/>
                        <ui:textField binding="#{test$myjsf.textField1}" id="textField1" style="left: 72px; top: 24px; position: absolute"/>
                        <ui:table augmentTitle="false" binding="#{test$myjsf.table1}" id="table1"
                            style="left: 72px; top: 72px; position: absolute; width: 450px" title="Item" width="450">
                            <ui:tableRowGroup binding="#{test$myjsf.tableRowGroup1}" id="tableRowGroup1" rows="10"
                                sourceData="#{test$myjsf.defaultTableDataProvider}" sourceVar="currentRow">
                                <ui:tableColumn binding="#{test$myjsf.tableColumn1}" headerText="Product" id="tableColumn1"
                                        sort="column1">I
                                    <ui:staticText binding="#{test$myjsf.staticText1}" id="staticText1" text="#{currentRow.value['column1']}"/>
                                </ui:tableColumn>
                                <ui:tableColumn binding="#{test$myjsf.tableColumn2}" headerText="HPP" id="tableColumn2" sort="column2">
                                    <ui:staticText binding="#{test$myjsf.staticText2}" id="staticText2" text="#{currentRow.value['column2']}"/>
                                </ui:tableColumn>
                                <ui:tableColumn binding="#{test$myjsf.tableColumn3}" headerText="Buying Price" id="tableColumn3" sort="column3">
                                    <ui:staticText binding="#{test$myjsf.staticText3}" id="staticText3" text="#{currentRow.value['column3']}"/>
                                </ui:tableColumn>
                            </ui:tableRowGroup>
                        </ui:table>
                    </ui:form>
                </ui:body>
            </ui:html>
        </ui:page>
    </f:view>
</jsp:root>
