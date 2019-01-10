<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="basePath" />
<hy:extends name="title">系统性能分析</hy:extends>
<hy:extends name="css">
	<style type="text/css">
		#container {
			min-width: 310px;
			height: 400px;
			margin: 0 auto
		}
	</style>
</hy:extends>
<hy:extends name="javascript">
	<script src="${basePath}/assets/lib/Highcharts-7.0.1/highcharts.js"></script>
	<script src="${basePath}/assets/lib/Highcharts-7.0.1/modules/series-label.js"></script>
	<script src="${basePath}/assets/lib/Highcharts-7.0.1/modules/exporting.js"></script>
	<script src="${basePath}/assets/lib/Highcharts-7.0.1/modules/export-data.js"></script>
	<script type="text/javascript">
		console.log(Highcharts)
		$(function () {
			layui.use(['form', 'tree'], function () {
				$.ajax({
	            	type: "GET",
	                url: "${basePath}/platform/access/menu/create.json",
	                contentType: "application/json; charset=utf-8",
	                data: {},
			    	loadSuccess: function(res) {
	     	         	layui.tree({
	                        elem: "#classtree",
	                        nodes: res.data['menuTrees'],
	                        click: function (node) {
	                        	var $select = $($(this)[0].elem).parents(".layui-form-select");
	                            $select.removeClass("layui-form-selected").find(".layui-select-title span").html(node.name).end().find("input:hidden[name='parentId']").val(node.id);
	                       }
	                    });
	                 },
	                 error: function (message) {
	                 }
	            })
			});
			
			Highcharts.chart('container', {
				chart: {
					marginRight: 5,
				},
			    title: {
			        text: '链接性能监控'
			    },
			    subtitle: {
			        text: 'Source: thesolarfoundation.com'
			    },
			    yAxis: {
			        title: {
			            text: '用户人数'
			        }
			    },
			    legend: {
			        layout: 'vertical',
			        align: 'left',
			        verticalAlign: 'middle'
			    },
			    plotOptions: {
			        series: {
			            label: {
			                connectorAllowed: false
			            },
			            pointStart: 2010
			        }
			    },
			   
			    exporting: {
					buttons: {
						contextButton: {
							/* menuItems: [{
								text: '导出为png',
								onclick: function () {
									this.exportChart();
								},
								separator: false
							}] */
							 enabled: false
						}
					}
				},
			    series: [{
			        name: '安装数量',
			        data: [43934, 52503, 57177, 69658, 97031, 119931, 137133, 154175]
			    }, {
			        name: 'Manufacturing',
			        data: [24916, 24064, 29742, 29851, 32490, 30282, 38121, 40434]
			    }, {
			        name: 'Sales & Distribution',
			        data: [11744, 17722, 16005, 19771, 20185, 24377, 32147, 39387]
			    }, {
			        name: 'Project Development',
			        data: [null, null, 7988, 12169, 15112, 22452, 34400, 34227]
			    }, {
			        name: 'Other',
			        data: [12908, 5948, 8105, 11248, 8989, 11816, 18274, 18111]
			    }],
		
			    responsive: {
			        rules: [{
			            condition: {
			                maxWidth: 500
			            },
			            chartOptions: {
			                legend: {
			                    layout: 'horizontal',
			                    align: 'center',
			                    verticalAlign: 'bottom'
			                }
			            }
			        }]
			    }
		
			});
		})
	</script>
</hy:extends>
<hy:extends name="body">
	<div class="coun-row" style="display: flex;flex-direction: row;">
		<div class="tree-row" style="width: 199px;border-right: 1px solid red;">
			<dl class="layui-anim layui-anim-upbit">
		        <dd>
		            <ul id="classtree"></ul>
		        </dd>
		    </dl>	
		</div>
		<div class="count-row-r" style="float: left;width: calc(100% - 200px)">
			<div id="container" style="width:100%;height:400px"></div>
		</div>
	</div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />

