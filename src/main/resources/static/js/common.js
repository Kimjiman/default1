// 레이아웃
function fn_layout(){
	var header = $("#header"),
		gnb = $("#gnb"),
		menu = $(".depth01"),
		btnMenu = header.find(".btnMenu"),
		btnClose = gnb.find(".btnClose");
	btnMenu.click(function(){
		gnb.addClass("open");
		// fn_gnb(0,0,0);  2023.02.08 주석
		menu.find("li.curr > ul").slideDown(200);
	});
	btnClose.click(function(){
		gnb.removeClass("open");
	});
	menu.find("li.curr > ul").slideDown(200);
	menu.find("li").has("ul").addClass("more");
	menu.find("li.more > a").click(function(e){
		e.preventDefault();
		if($(this).parent().hasClass("on") || $(this).parent().hasClass("curr")){
			$(this).closest("ul").find("> li").removeClass("on curr").find("> ul").slideUp(200);
		}else{
			$(this).closest("ul").find("> li").removeClass("on curr").find("> ul").slideUp(200);
			$(this).parent().addClass("on").find("> ul").slideDown(200);
		}
	});
}

// GNB 현재 페이지
function fn_gnb(depth01, depth02, depth03){
	$(".depth01 > li").eq(depth01).addClass("curr");
	$(".depth02 > li").eq(depth02).addClass("curr");
	$(".depth03 > li").eq(depth03).addClass("curr");
}

// 레이어 팝업
function fn_layer(e,t,s) {
	var pdt = $('#'+e).find('> .inner').css('padding-top').replace(/[^-\d\.]/g, ''),
		pdb = $('#'+e).find('> .inner').css('padding-bottom').replace(/[^-\d\.]/g, '');
	$('#'+e).fadeIn(200).addClass('on');
	$('body, html').css({'overflow':'hidden'});
	 $('#'+e).find('> .inner .cont').attr("tabindex",0).focus();
	$(window).resize(function(){
		$('#'+e).find('> .inner').css({'width':s+'px'});
		if($(window).width() > 767){
			$('#'+e).find('.cont').css({'max-height':$('#'+e).height()*0.9 - (Number(pdt) + Number(pdb))});
		}else{
			$('#'+e).find('.cont').css({'max-height':$('#'+e).height() - (Number(pdt) + Number(pdb))});
		}
	}).resize();
	$(t).addClass(e);
}

// 레이어 팝업 닫기
function fn_layer_close(t){
	var backFocus = $(t).closest(".layerPop").attr("id");
	$(t).closest(".inner").parent().fadeOut(200).removeClass("on");
	$("body, html").css({"overflow":"auto"});
	$("." + backFocus).focus();
}

// 이미지 업로드 시 이미지 삭제
function fn_del(t){
	$(t).hide().siblings("p").remove()
	$(t).parent().siblings(".flexB").find(".btn").removeAttr("disabled");
}

$(function() {
	/*>>>>>>>>>> 공통 <<<<<<<<<<*/
	// 레이아웃
	// fn_layout(); 2023.02.08 주석

	// 스킵네비
	$("a[href^='#']").click(function(evt){
	  var anchortarget = $(this).attr("href");
	  $(anchortarget).attr("tabindex", -1).focus();
	  $(anchortarget).removeAttr("tabindex");
	 });
	if (window.location.hash) {
		$(window.location.hash).attr("tabindex", -1).focus();
	};
	var skipNav = $("#skipNav a");
	skipNav.focus(function(){
		skipNav.removeClass("on");
		$(this).addClass("on");
	});
	skipNav.blur(function(){
		skipNav.removeClass("on");
	});

	// 데이트피커
	if($(".date").length){
		$(".date input").datepicker({
			dateFormat: 'yy-mm-dd',
			showOtherMonths: true,
			showMonthAfterYear:true,
			changeYear: true,
			changeMonth: true,
			monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
			monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			dayNamesMin: ['일','월','화','수','목','금','토'],
			dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일']
		});
	}

	// 탭 기능
	var tabWrap = $(".tabFunc");
	tabWrap.each(function(){
		var btnTab = $(this).find("> ul li a"),
			tabBox = $(this).children(".tabBox").children("div"),
			liCurr = $(this).find('> ul li.curr').index();
		tabBox.not(':eq('+ liCurr +')').hide();

		btnTab.click(function(e){
			e.preventDefault();
			var i = $(this).parent().index();
			btnTab.parent().removeClass("curr");
			$(this).parent().addClass("curr");
			tabBox.hide();
			tabBox.eq(i).show();
		});
	});

	/*>>>>>>>>>> 페이지 <<<<<<<<<<*/
});