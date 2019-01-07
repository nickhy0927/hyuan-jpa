/**
 * 选择角色信息
 */
var zTreeObj;
var setting = {
    check : {
        enable : true ,
        chkStyle : "checkbox" ,
        radioType : "all"
    } ,
    data : {
        simpleData : {
            enable : true ,
            idKey : "id" , // id编号命名
            pIdKey : "pid" , // 父id编号命名
            rootId : 0
        }
    } ,
    callback : {
        onCheck : zTreeOnCheck
    }
};

function zTreeOnCheck (event , treeId , treeNode) {
    zTreeObj.checkNode(treeNode , true);
}

function showTree() {
    $("#treeDemo").attr('style', "display:block;z-index:100;position：absolute");
}

$(function() {
	 zTreeObj = $.fn.zTree.init($("#roleTree") , setting , zNodes);
})