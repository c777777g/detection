var UITree = function () {

    return {
        //main function to initiate the module
        init: function (data) {
        	var DataSource = function (options) {
        		this._formatter = options.formatter;
        		this._columns = options.columns;
        		this._data = options.data;
        		};
        		
        		var cont = 0;
        		DataSource.prototype = {
        			
        			columns: function () {
        				return this._columns;
        			},

        			data: function (options, callback) {

        				var self = this;
        				if (options.search) {
        					callback({ data: self._data, start: start, end: end, count: count, pages: pages, page: page });
        		        } else if (options.data) { 
        					callback({ data: options.data, start: 0, end: 0, count: 0, pages: 0, page: 0 });
        				} else if (cont==0){
        					callback({ data: self._data, start: 0, end: 0, count: 0, pages: 0, page: 0 });
        				}else {
        					callback({ data: 0, start: 0, end: 0, count: 0, pages: 0, page: 0 });
        				}
        				cont++;
        			}
        		};

        		var treeDataSource = new DataSource({
        		  data: data,
        					delay: 400
        				});

        				$('#MyTree').tree({dataSource: treeDataSource});

        				$('#tree-selected-items').on('click', function () {
        					console.log("selected items: ", $('#MyTree').tree('selectedItems'));
        				});

        				$('#MyTree').on('loaded', function (evt, data) {
        					console.log('tree content loaded');
        				});

        				$('#MyTree').on('opened', function (evt, data) {
        					console.log('sub-folder opened: ', data);
        				});

        				$('#MyTree').on('closed', function (evt, data) {
        					console.log('sub-folder closed: ', data);
        				});


        }

    };

}();