/**
 * 
 */
var services = angular.module('objectApp.services', []);
services.factory('LayerService', [function () {
    return {
    	openLayer: function (title, url, w, h) {
    		var w = w ? w : "800px",h = h ? h : "450px";
    		var title = title ? title : "新增";
    		var index = layer.open({
    			type: 2,
    			title: title,
    			move: false,
    			shadeClose: true,
    	        shade: 0.6,
    			area: [w, h],
    			content: url
    		});
        }
    }
}]);
