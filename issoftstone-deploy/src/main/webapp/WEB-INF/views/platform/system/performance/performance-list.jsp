<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="basePath" />
<hy:extends name="title">系统性能分析</hy:extends>
<hy:extends name="css">
	<style type="text/css">
		#container {
			height: 400px;
		}
	</style>
</hy:extends>
<hy:extends name="javascript">
	<script src="${basePath}/assets/lib/Highcharts-7.0.1/highcharts.js"></script>
	<script src="${basePath}/assets/lib/Highcharts-7.0.1/modules/series-label.js"></script>
	<script src="${basePath}/assets/lib/Highcharts-7.0.1/modules/exporting.js"></script>
	<script src="${basePath}/assets/lib/Highcharts-7.0.1/modules/export-data.js"></script>
	<script type="text/javascript">
		$(function () {
			var datas = eval('(${datas})')
			var dayList = eval('(${dayList})')
			Highcharts.chart('container', {
				chart: {
					marginRight: 5,
					colorCount: 100000
				},
			    title: {
			        text: '系统请求响应时间'
			    },
			    subtitle: {
			        text: '性能监控'
			    },
			    xAxis: {
			        categories: dayList
			    },
			    yAxis: {
			        title: {
			            text: '请求平均响应时间'
			        }
			    },
			    legend: {
			        layout: 'vertical',
			        align: 'right',
			        verticalAlign: 'middle'
			    },
			    plotOptions: {
			        series: {
			            label: {
			                connectorAllowed: false
			            },
			            cursor: 'pointer',
			            events: {
			                click: function (e) {
			                    console.log(datas[e.point.colorIndex]);
			                    $.openWindow({
									title: '查看请求响应时间列表',
									height: '95%',
									width: '99%',
									url: '${basePath}/platform/system/performance/list.do?alias=' + datas[e.point.colorIndex]['alias']
								})
			                }
			            }
			        },
			        line: {
			            dataLabels: {
			                enabled: true
			            },
			            enableMouseTracking: true
			        }
			    },
			    exporting: {
					buttons: {
						contextButton: {
							menuItems: [{
								text: '导出为png',
								onclick: function () {
									this.exportChart();
								},
								separator: false
							}],
						 	enabled: false
						}
					}
				},
			    series: datas,
			    responsive: {
			        rules: [{
			            condition: {
			                maxWidth: 1200
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
		<!-- <div class="tree-row" style="width: 199px;border-right: 1px solid red;">
			<dl class="layui-anim layui-anim-upbit">
		        <dd>
		            <ul id="classtree"></ul>
		        </dd>
		    </dl>	
		</div> -->
		<div class="count-row-r" style="float: left;width: 100%;/* width: calc(100% - 200px) */">
			<div id="container" style="width:100%;height:500px"></div>
		</div>
	</div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />

