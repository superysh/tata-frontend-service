/**
 * @name 购物车相关
 * @author 494595280@qq.com
 * @url http://www.tata168.com
 */
;(function($){
    $.tata.cart = {
        settings: {
            addNum_btn: 'a.addNum',
			delNum_btn: 'a.delNum'
        },
        init: function(options){
            options && $.extend($.tata.cart.settings, options);
            $.tata.cart.cart_manager();
        },
        //购物车管理
        cart_manager: function(){
			var s = $.tata.cart.settings;
			//商品数量加
			$(document).on("click",s.addNum_btn,function(){
				var iptVal= parseInt($(this).siblings("input").val());
				$(this).siblings("input").val(iptVal+1).attr('before_num',iptVal+1);
				if(current_page=="cart"){//如果是购物车页面
					var attr_prices=$(this).parents("li").attr("data-prices"),
					 attr_id=$(this).parents("li").attr("data-id"),
					 order_totalmoney=$(".order_totalmoney").text();
					$(".order_totalmoney").text((parseFloat(order_totalmoney)+parseFloat(attr_prices)).toFixed(2));
					$.tata.cart.addnum_goods(attr_id,1);
				}
			});
			//商品数量减
			$(document).on("click",s.delNum_btn,function(){
				var iptVal= parseInt($(this).siblings("input").val());
				if (iptVal>1){
					$(this).siblings("input").val(iptVal-1).attr('before_num',iptVal-1);
				}else{
					$.tata.ui.tata_alert('最少一个',1);
					return !1;
				}
				if(current_page=="cart"){//如果是购物车页面
					var attr_prices=$(this).parents("li").attr("data-prices"),
					 attr_id=$(this).parents("li").attr("data-id"),
					 order_totalmoney=$(".order_totalmoney").text();
					$(".order_totalmoney").text((parseFloat(order_totalmoney)-parseFloat(attr_prices)).toFixed(2));
					$.tata.cart.delnum_goods(attr_id,1);
				}
			});
			//用户自己填写数量
			$(document).on("blur","input[name='num']",function(){
				var before_num=parseInt($(this).attr('before_num')),
					iptVal= $.trim($(this).val());
				if($.tata.ui.is_number(iptVal)==!1) {
					$.tata.ui.tata_alert('请输入正确的商品数量',2);
					$(this).val(before_num);
					return 1;
				}
				iptVal=parseInt($.trim($(this).val()));
				if (iptVal<1){
					$.tata.ui.tata_alert('请输入正确的商品数量',2);
					$(this).val(before_num);
					return 1;
				}
				var attr_num=parseInt($(this).val());
				if(attr_num==before_num){return !1;}
				$(this).attr('before_num',attr_num);
				if(current_page=="cart"){//如果是购物车页面
					var attr_prices=$(this).parents("li").attr("data-prices"),
					 attr_id=$(this).parents("li").attr("data-id"),
					 order_totalmoney=$(".order_totalmoney").text();
					var del_add=parseInt(attr_num-before_num);
					if(del_add < 0){
						attr_num=before_num-attr_num;
						$(".order_totalmoney").text((parseFloat(order_totalmoney)-parseFloat(attr_prices*attr_num)).toFixed(2));
						$.tata.cart.delnum_goods(attr_id,attr_num);
					}else{
						attr_num=attr_num-before_num;
						$(".order_totalmoney").text((parseFloat(order_totalmoney)+parseFloat(attr_prices*attr_num)).toFixed(2));
						$.tata.cart.addnum_goods(attr_id,attr_num);
					}
				}
			});
			//选中单个商品
			$(document).on("click","span.sel_goods",function(){
				var self=$(this);
				if(self.hasClass("on")) self.removeClass("on");
				else self.addClass("on");
			});
			//全选
			$(document).on("click","span.sel_allgoods",function(){
				var self=$(this);
				if(self.hasClass("on")){
					self.removeClass("on");
					self.children("a").html('全选');
					$('span.sel_goods').each(function() {
						if($(this).hasClass("on")) $(this).removeClass("on");
					});
				}else{
					self.addClass("on");
					self.children("a").html('全不选');
					$('span.sel_goods').each(function() {
						if(!$(this).hasClass("on")) $(this).addClass("on");
					});
				}
			});
			//删除商品
			$(document).on("click","#del_goods",function(){
				var attr_ids='',
					attr_id='',
					attr_num=0,
					attr_nums=0;
				$('span.sel_goods').each(function(){
					if($(this).hasClass("on")){
						attr_id=$(this).parent().attr("data-id");
						attr_num=parseInt($(this).siblings().find("input[name='num']").val());
						attr_ids += attr_id + ",";
						attr_nums += attr_num;
					}
				});
				attr_ids=$.trim(attr_ids.substring(0,attr_ids.length-1));
				if(attr_ids=='' || attr_ids==null){
					$.tata.ui.tata_alert('请选择要删除的商品',2);
					return !1;
				}
				layer.open({
					content: '确定要删除所选商品吗？',
					btn: ["确定", "取消"],
					yes: function(index){
						$.tata.cart.del_goods(attr_ids,attr_nums);
						layer.close(index);
					}, no: function(index){
						layer.close(index);
					}
				});
			});
			//商品详情页--加入购物车
			$(document).on("click","#add_goods",function(){
				var attr_num=parseInt($.trim($("input[name='num']").val()));
				$.tata.cart.addnum_goods(attr_id,attr_num);
			});
        },
		//去删除商品
		del_goods: function(attr_ids,attr_nums){
			var request_load=$.tata.ui.tata_load();
			$.post(TATA.root + '/?m=cart&a=del_goods',{attr_ids:attr_ids,attr_nums:attr_nums},function(result){
				layer.close(request_load);
				if(result.status==1){
					window.location.reload();
				}else{
					$.tata.ui.tata_alert(result.msg,2);
					return 1;
				}
			},'json');
        },
		//增加购物车某商品数量
		addnum_goods: function(attr_id,attr_num){
			if(attr_id=='' || attr_id==null){
				$.tata.ui.tata_alert('商品参数丢失',2);
				return 1;
			}
			if(attr_num<1){
				$.tata.ui.tata_alert('请输入正确的商品数量',2);
				return 1;
			}
			var request_load=$.tata.ui.tata_load();
			$.post(TATA.root + '/?m=cart&a=addnum_goods',{attr_id:attr_id,attr_num:attr_num},function(result){
				layer.close(request_load);
				if(result.status==1){
					var cart_goodsnum=parseInt($(".cart_goodsnum").text());
					$(".cart_goodsnum").text(parseInt(cart_goodsnum+attr_num));
					if(current_page!="cart"){$.tata.ui.tata_alert(result.msg,1);}
				}else{
					$.tata.ui.tata_alert(result.msg,2);
					t=setTimeout(function(){window.location.reload();clearTimeout(t);},2000);
				}
			});
		},
		//减少购物车某商品数量
		delnum_goods: function(attr_id,attr_num){
			if(attr_id=='' || attr_id==null){
				$.tata.ui.tata_alert('商品参数丢失',2);
				return 1;
			}
			if(attr_num<1){
				$.tata.ui.tata_alert('请输入正确的商品数量',2);
				return 1;
			}
			$.post(TATA.root + '/?m=cart&a=delnum_goods',{attr_id:attr_id,attr_num:attr_num},function(result){
				if(result.status==1){
					var cart_goodsnum=parseInt($(".cart_goodsnum").text());
					$(".cart_goodsnum").text(parseInt(cart_goodsnum-attr_num));
				}else{
					$.tata.ui.tata_alert(result.msg,2);
					t=setTimeout(function(){window.location.reload();clearTimeout(t);},2000);
				}
			});
		},
		//选择商品的规格
		attr_select: function(){
			$(document).on("click",".attr_val",function(){
				var self=$(this),
					_index=self.index();
				self.addClass("on");
				self.siblings("ol").removeClass("on");
				$(".attr_price").hide().eq(_index-1).show();
				attr_id=self.attr("data-attrid");
			});
		}
    };
    $.tata.cart.init(); //用户
})(jQuery);