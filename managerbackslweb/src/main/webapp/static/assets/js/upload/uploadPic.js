function uploadPic() {
	// ����jquery.form.js��Ҫ������Ϣ
	var options = {

		url : "../upload/upload.do",
		type : "post",
		dataType : "json",
		success : function(data) {
			// 1��ͼƬ����
			$("#allUrl").attr("src", data.allUrl);
			// 2������ͼƬ��url�����������ڱ��浽���ݿ�imgUrl
			$("#showImg").val(data.showImg);
		}
	}
	// ͨ��jquery.form.js�������ύ
	$("#picUpload").ajaxSubmit(options);
}