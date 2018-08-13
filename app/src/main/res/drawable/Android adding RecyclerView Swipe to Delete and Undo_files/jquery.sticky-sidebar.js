/*!
 * Theia Sticky Sidebar v1.4.0
 * https://github.com/WeCodePixels/theia-sticky-sidebar
 *
 * Glues your website's sidebars, making them permanently visible while scrolling.
 *
 * Copyright 2013-2016 WeCodePixels and other contributors
 * Released under the MIT license
 */

(function(d){d.fn.theiaStickySidebar=function(c){function h(g,c){if(!0===g.initialized)return!0;if(d("body").width()<g.minWidth)return!1;n(g,c);return!0}function n(g,c){g.initialized=!0;d("head").append(d('<style>.theiaStickySidebar:after {content: ""; display: table; clear: both;}</style>'));c.each(function(){function c(){b.fixedScrollTop=0;b.sidebar.css({"min-height":"1px"});b.stickySidebar.css({position:"static",width:""})}function p(a){var b=a.height();a.children().each(function(){b=Math.max(b,
d(this).height())});return b}var b={};b.sidebar=d(this);b.options=g||{};b.container=d(b.options.containerSelector);0==b.container.size()&&(b.container=b.sidebar.parent());b.sidebar.parents().css("-webkit-transform","none");b.sidebar.css({position:"relative",overflow:"visible","-webkit-box-sizing":"border-box","-moz-box-sizing":"border-box","box-sizing":"border-box"});b.stickySidebar=b.sidebar.find(".theiaStickySidebar");0==b.stickySidebar.length&&(b.sidebar.find("script").remove(),b.stickySidebar=
d("<div>").addClass("theiaStickySidebar").append(b.sidebar.children()),b.sidebar.append(b.stickySidebar));b.marginTop=parseInt(b.sidebar.css("margin-top"));b.marginBottom=parseInt(b.sidebar.css("margin-bottom"));b.paddingTop=parseInt(b.sidebar.css("padding-top"));b.paddingBottom=parseInt(b.sidebar.css("padding-bottom"));var h=b.stickySidebar.offset().top,e=b.stickySidebar.outerHeight();b.stickySidebar.css("padding-top",1);b.stickySidebar.css("padding-bottom",1);h-=b.stickySidebar.offset().top;e=b.stickySidebar.outerHeight()-
e-h;0==h?(b.stickySidebar.css("padding-top",0),b.stickySidebarPaddingTop=0):b.stickySidebarPaddingTop=1;0==e?(b.stickySidebar.css("padding-bottom",0),b.stickySidebarPaddingBottom=0):b.stickySidebarPaddingBottom=1;b.previousScrollTop=null;b.fixedScrollTop=0;c();b.onScroll=function(a){if(a.stickySidebar.is(":visible"))if(d("body").width()<a.options.minWidth)c();else if(a.options.disableOnResponsiveLayouts&&a.sidebar.outerWidth("none"==a.sidebar.css("float"))+50>a.container.width())c();else{var b=d(document).scrollTop(),
l="static";if(b>=a.container.offset().top+(a.paddingTop+a.marginTop-a.options.additionalMarginTop)){var k=a.paddingTop+a.marginTop+g.additionalMarginTop,h=a.paddingBottom+a.marginBottom+g.additionalMarginBottom,e=a.container.offset().top,f=a.container.offset().top+p(a.container),l=0+g.additionalMarginTop,k=a.stickySidebar.outerHeight()+k+h<d(window).height()?l+a.stickySidebar.outerHeight():d(window).height()-a.marginBottom-a.paddingBottom-g.additionalMarginBottom,e=e-b+a.paddingTop+a.marginTop,h=
f-b-a.paddingBottom-a.marginBottom,f=a.stickySidebar.offset().top-b,m=a.previousScrollTop-b;"fixed"==a.stickySidebar.css("position")&&"modern"==a.options.sidebarBehavior&&(f+=m);"stick-to-top"==a.options.sidebarBehavior&&(f=g.additionalMarginTop);"stick-to-bottom"==a.options.sidebarBehavior&&(f=k-a.stickySidebar.outerHeight());f=0<m?Math.min(f,l):Math.max(f,k-a.stickySidebar.outerHeight());f=Math.max(f,e);f=Math.min(f,h-a.stickySidebar.outerHeight());l=(e=a.container.height()==a.stickySidebar.outerHeight())||
f!=l?e||f!=k-a.stickySidebar.outerHeight()?b+f-a.sidebar.offset().top-a.paddingTop<=g.additionalMarginTop?"static":"absolute":"fixed":"fixed"}"fixed"==l?a.stickySidebar.css({position:"fixed",width:a.sidebar.width(),top:f,left:a.sidebar.offset().left+parseInt(a.sidebar.css("padding-left"))}):"absolute"==l?(k={},"absolute"!=a.stickySidebar.css("position")&&(k.position="absolute",k.top=b+f-a.sidebar.offset().top-a.stickySidebarPaddingTop-a.stickySidebarPaddingBottom),k.width=a.sidebar.width(),k.left=
"",a.stickySidebar.css(k)):"static"==l&&c();"static"!=l&&1==a.options.updateSidebarHeight&&a.sidebar.css({"min-height":a.stickySidebar.outerHeight()+a.stickySidebar.offset().top-a.sidebar.offset().top+a.paddingBottom});a.previousScrollTop=b}};b.onScroll(b);d(document).scroll(function(a){return function(){a.onScroll(a)}}(b));d(window).resize(function(a){return function(){a.stickySidebar.css({position:"static"});a.onScroll(a)}}(b))})}c=d.extend({containerSelector:"",additionalMarginTop:0,additionalMarginBottom:0,
updateSidebarHeight:!0,minWidth:0,disableOnResponsiveLayouts:!0,sidebarBehavior:"modern"},c);c.additionalMarginTop=parseInt(c.additionalMarginTop)||0;c.additionalMarginBottom=parseInt(c.additionalMarginBottom)||0;(function(c,e){h(c,e)||(console.log("TST: Body width smaller than options.minWidth. Init is delayed."),d(document).scroll(function(c,e){return function(b){h(c,e)&&d(this).unbind(b)}}(c,e)),d(window).resize(function(c,e){return function(b){h(c,e)&&d(this).unbind(b)}}(c,e)))})(c,this)}})(jQuery);