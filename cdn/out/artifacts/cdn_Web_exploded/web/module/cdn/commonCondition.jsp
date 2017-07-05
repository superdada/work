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
	<div class="my3-switch-box" style="width: 1180px;">
		<div class="my3-switch-item">
			<!-- 添加类型(Cache/CDN/ALL)查询条件 -->
			<div class="conditions" style="width:430px;text-align:left;">
				<div class="floatDiv">
					<div class="my3-switch-text">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:</div>
					<ul class="my3-switch-options" id="cdn_cache_all">
						<li class="my-options-on">CDN</li>
						<li class="my-options-off">CACHE</li>
					</ul>
				</div>
			</div>
			<div id="cdnCondition" class="conditions  layui-form-pane"
				 style="width:720px;height: 38px;"></div>
		</div>
		<div class="my3-switch-item">
			<div class="conditions" style="width:640px;">
				<div class="my3-switch-text">
					<span>维&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度 :</span>
				</div>
				<ul class="my3-switch-options" id="WDSelected">
				</ul>
			</div>
			<form id="searchCondition"
				class="conditions layui-form layui-form-pane"
				style="width:520px;height: 38px;">
				<div class="layui-form-item" style="margin-bottom:0px;">
					<div class="floatDiv" id="cityName1_div"  style="display: none">
						<label class="layui-form-label">地市</label>

						<div class="layui-input-block" style="width: 150px;">
							<select id="cityName1" lay-filter="cityName1" class="lay-search-sync" lay-search-sync lay-search>
								<option value=""></option>
							</select>
						</div>
					</div>
					<div class="floatDiv" id="cityName2_div"  style="display: none">
						<label class="layui-form-label">地市</label>

						<div class="layui-input-block" style="width: 150px;">
							<select id="cityName2" lay-filter="cityName2" class="lay-search-sync" lay-search-sync lay-search>
								<option value=""></option>
							</select>
						</div>
					</div>
					<div class="floatDiv" id="dataCenter_div" style="display: none">
						<label class="layui-form-label">内容中心</label>
						<div class="layui-input-block" style="width: 150px;">
							<select id="dataCenter" lay-filter="dataCenter" class="lay-search-sync" lay-search-sync lay-search>
								<option value=""></option>
							</select>
						</div>
					</div>
					<div class="floatDiv" id="ICP1_div" style="display: none">
						<label class="layui-form-label">ICP</label>
						<div class="layui-input-block" style="width: 150px;">
							<select id="ICP1" class="lay-search-sync" lay-filter="ICP1"  lay-search-sync lay-search>
								<option value=""></option>
							</select>
						</div>
					</div>
					<div class="floatDiv" id="ICP2_div" style="display: none">
						<label class="layui-form-label">ICP</label>
						<div class="layui-input-block" style="width: 150px;">
							<input id="ICP2" type="text"  name="title" required  lay-verify="required"  autocomplete="off" class="layui-input lay-search-sync" lay-search-sync>
							<div class=" multip-input" style="display: none;">
								<table class="layui-table multi_table" lay-skin="nob" >
									<thead>
									<tr>
										<th><input type="checkbox" name="" lay-skin="primary" lay-filter="allChoose"></th>
										<th>全选</th>
									</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="floatDiv" id="domain1_div" style="display: none">
						<label class="layui-form-label">域名</label>
						<div class="layui-input-block" style="width: 150px;">
							<select id="domain1" lay-filter="domain1" class="lay-search-sync" lay-search lay-search-sync >
								<option value=""></option>
							</select>
						</div>
					</div>
					<div class="floatDiv" id="domain2_div" style="display: none">
						<label class="layui-form-label">域名</label>
						<div class="layui-input-block" style="width: 150px;">
							<input id="domain2" type="text"  name="title" required  lay-verify="required"  autocomplete="off" class="layui-input lay-search-sync" lay-search-sync>
							<div  class="multip-input" style="display: none;">
								<table class="layui-table multi_table" lay-skin="nob" >
									<thead>
									<tr>
										<th><input type="checkbox" name="" lay-skin="primary" lay-filter="allChoose"></th>
										<th>全选</th>
									</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="floatDiv" id="serviceTypeCategory_div" style="display: none">
						<label class="layui-form-label">业务分类</label>
						<div class="layui-input-block" style="width: 150px;">
							<select id="serviceTypeCategory" lay-filter="serviceTypeCategory" class="lay-search-sync" lay-search lay-search-sync>
								<option value="自有业务">自有业务</option>
								<option value="腾讯业务">腾讯业务</option>
							</select>
						</div>
					</div>
					<div class="floatDiv" id="serviceNameCategory_div" style="display: none">
						<label class="layui-form-label">业务名称</label>
						<div class="layui-input-block" style="width: 150px;">
							<select id="serviceNameCategory" lay-filter="serviceNameCategory" class="lay-search-sync"  lay-search lay-search-sync>
								<option value="咪咕视频">咪咕视频</option>
								<option value="腾讯图片">腾讯图片</option>
							</select>
						</div>
					</div>
					<div class="floatDiv" id="server1_div" style="display: none">
						<label class="layui-form-label">服务器</label>
						<div class="layui-input-block" style="width: 150px;">
							<select class="lay-search-sync" id="server1"  lay-filter="server1"  lay-search lay-search-sync>
								<option value=""></option>
							</select>
						</div>
					</div>
					<div class="floatDiv" id="server2_div" style="display: none">
						<label class="layui-form-label">服务器</label>
						<div class="layui-input-block" style="width: 150px;">
							<input id="server2"  type="text"  name="title" required  lay-verify="required"  autocomplete="off" class="layui-input lay-search-sync" lay-search-sync>
							<div class="multip-input" style="display: none;">
								<table class="layui-table multi_table" lay-skin="nob" >
									<thead>
									<tr>
										<th><input type="checkbox" name="" lay-skin="primary" lay-filter="allChoose"></th>
										<th>全选</th>
									</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="my3-switch-item" style="width: 1180px">
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
		//choiceTypeQueryCondition();
		/* layui.use('form', function() {
			var form = layui.form();
		});*/
	});
</script>
<script src="js/commonCondition.js"></script>
