<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="bjui-pageContent">
    <div class="bs-example">
        <form action="rolelisteditSave.action" class="datagrid-edit-form" data-toggle="validate" data-data-type="jsonp">
            <div class="bjui-row col-2">
                <label class="row-label">机器码:</label>
                <div class="row-input required">
                	<input type="hidden" name="machineCode" value="${machineCode }"></input>
                    ${machineCode }
                </div>
                <label class="row-label">授权码:</label>
                <div class="row-input required">
                	<input type="hidden" name="machineCode" value="${machineCode }"></input>
                    ${machineCode }
                </div>
                <label class="row-label">授权商:</label>
                <div class="row-input required">
                	<input type="hidden" name="machineCode" value="${machineCode }"></input>
                    ${machineCode }
                </div>
            </div>
        </form>
    </div>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="submit" class="btn-default" data-icon="save">保存</button></li>
    </ul>
</div>
</body>
</html>