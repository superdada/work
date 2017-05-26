<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/20
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<%@ page isELIgnored="false"%>
<%--<c:set var="ctx" value="${pageContext.request.contextPath}" /> --%>

<script src="js/jquery.cookie.js"></script>
<div id="conditionDiv" class="conditionDiv">
	<div class="my-switch-box" style="width: 1180px;">
		<div class="my3-switch-item">
			<div class="conditions" style="width:430px;">
				<div class="my3-switch-text">
					<span>维&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度 :</span>
				</div>
				<ul class="my3-switch-options" id="WDSelected">
					<%--<li class="my-options-on" type="allNet">全网</li>
                    <li class="my-options-off" type="cityName">地市</li>
                    <li class="my-options-off" type="dataCenter">数据中心</li>
                    <li class="my-options-off" type="ICP">ICP</li>
                    <li class="my-options-off" type="server">服务器</li>
                    <li class="my-options-off" type="domain">域名</li>--%>
				</ul>
			</div>
			<div id="searchCondition"
				class="conditions layui-form layui-form-pane"
				style="width:720px;height: 38px;">
				<div class="layui-form-item" style="margin-bottom:0px;">
					<div class="floatDiv" id="cityName_div"  style="display: none">
						<label class="layui-form-label">地市</label>

						<div class="layui-input-block" style="width: 120px;">
							<select id="cityName" lay-filter="cityName">
								<option value=""></option>
							</select>
						</div>
					</div>
					<div class="floatDiv" id="dataCenter_div" style="display: none">
						<label class="layui-form-label">数据中心</label>

						<div class="layui-input-block" style="width: 120px;">
							<select id="dataCenter" lay-filter="dataCenter">
								<option value=""></option>
							</select>
						</div>
					</div>
					<div class="floatDiv" id="ICP_div" style="display: none">
						<label class="layui-form-label">ICP</label>

						<div class="layui-input-block" style="width: 120px;">
							<select id="ICP" class="lay-search-sync" lay-search
								lay-search-sync>
								<%--<option value=""></option>--%>
								<option value="02326">YOUKU</option>
							</select>
						</div>
					</div>
					<div class="floatDiv" id="server_div" style="display: none">
						<label class="layui-form-label">服务器</label>

						<div class="layui-input-block" style="width: 120px;">
							<select class="lay-search-sync" id="server" lay-search
								lay-search-sync>
								<option value=""></option>
							</select>
						</div>
					</div>
				</div>
			</div>
			<!-- 添加类型(Cache/CDN/ALL)查询条件 -->
			<div class="conditions" style="width:430px;text-align:left;">
				<div class="floatDiv">
					<div class="my3-switch-text">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:</div>
					<ul class="my3-switch-options" id="cdn_cache_all">
						<li class="my-options-on">ALL</li>
						<li class="my-options-off">CACHE</li>
						<li class="my-options-off">CDN</li>
					</ul>
				</div>
			</div>
			
			<div id="cdnCondition" class="conditions layui-form layui-form-pane"
				style="width:720px;height: 38px;"></div>

			<div class="conditions" style="width: 430px;">
				<div>
					<div class="my3-switch-text">时间粒度 :</div>
					<ul class="my3-switch-options" id="timeTypeSelected">
						<li class="my-options-on" value="60" type="2">小时</li>
						<li class="my-options-off" value="1440" type="3">天</li>
						<li class="my-options-off" value="10080" type="4">周</li>
						<li class="my-options-off" value="44640" type="5">月</li>
					</ul>
				</div>
			</div>
			<div class="layui-form-pane floatDiv timeCondition">
				<div class="layui-form-item">
					<label class="layui-form-label" id="timeLable">时间范围</label>

					<div class="layui-input-inline">
						<input class="layui-input" placeholder="开始时间" id="timeStart">
					</div>
					<div class="layui-input-inline" id="dateEnd">
						<input class="layui-input" placeholder="结束时间" id="timeEnd">
					</div>
				</div>
			</div>
			<div class="searchBtn btn_sbm_search">
				<button onclick="loadChartData(true)"
					class="layui-btn layui-btn-normal">
					<span style="width: 16px;height: 28px"></span>查询
				</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	//给CDN/CACHE/ALL查询条件绑定单击事件
	/* $("#cdn_cache_all").find("li").each(function(){
			
	}); */

	$(function() {
		//给cdn_cache_all绑定单击事件
		choiceTypeQueryCondition();
	});
</script>
<script src="js/commonCondition.js"></script>
