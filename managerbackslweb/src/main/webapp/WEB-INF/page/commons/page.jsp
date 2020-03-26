<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="row DTTTFooter" style="text-align: right;padding-top: 10px;">
	<div class="col-sm-12">
		<ul class="pagination">
			<li id="firstPageLi"><a href="javascript:firstPage()">首页</a></li>
			<li id="beforePageLi"><a href="javascript:beforePage()">上一页</a></li>
			<li id="nextPageLi"><a href="javascript:nextPage()">下一页</a></li>
			<li id="lastPageLi"><a href="javascript:lastPage()">末页</a></li>
			<li class="disabled">
				<a href="javascript:void(0)">&nbsp;&nbsp;当前第<c:if test="${pagination.page==0}">1</c:if><c:if test="${pagination.page!=0}">${pagination.page}</c:if>页/共${pagination.pages }页,每页${pagination.limit}条记录</a>
			</li>
			<li><a href="javascript:void(0)" style="padding-top: 1px;padding-left: 3px;padding-right: 3px;padding-bottom: 1px;">
				<input type="hidden" id="page" name="page" value="${pagination.page }"/>
				<input type="text" id="tempPage" style="width: 30px;height: 28px;" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  
                                    onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</a></li>
			<li><a href="javascript:goToPage()">GO</a></li>
		</ul>
	</div>
</div>
<script type="text/javascript">
    var currentPage;
	<c:if test="${pagination.page==0}">
    currentPage = '1'; //当前页
	</c:if>
    <c:if test="${pagination.page!=0}">
    currentPage = '${pagination.page}'; //当前页
    </c:if>
	var totalPage = '${pagination.pages }';	//总页数
	if(parseInt(currentPage) == 1 || parseInt(totalPage) == 0){
		$("#firstPageLi").addClass("disabled");
 		$("#beforePageLi").addClass("disabled");
	}
	if(parseInt(currentPage) == parseInt(totalPage)){
		$("#nextPageLi").addClass("disabled");
		$("#lastPageLi").addClass("disabled");
	}

	function pageSubmit(){
		$("#submit-form").submit();
	}

	function goToPage(){
		var tempPage = $("#tempPage").val();
		if(tempPage!='' && totalPage != 0) {
           if (parseInt(tempPage) >= parseInt(totalPage)) {
                tempPage = totalPage
            }
            $("#page").val(parseInt(tempPage));
            pageSubmit();
        }
	}
	//首页
	function firstPage(){
        if(parseInt(totalPage) > 1) {
            $("#page").val(1);
            pageSubmit();
        }
	}
	//上一页
	function beforePage(){
		if(parseInt(currentPage) > 1){
			$("#page").val(parseInt(currentPage) - 1);
			pageSubmit();
		}
	}
	//下一页
	function nextPage(){
		if(parseInt(totalPage) > parseInt(currentPage)){
			$("#page").val(parseInt(currentPage) + 1);
			pageSubmit();
		}
	}
	//末页
	function lastPage(){
		if(parseInt(totalPage) > 1){
			$("#page").val(parseInt(totalPage));
			pageSubmit();
		}
	}

</script>