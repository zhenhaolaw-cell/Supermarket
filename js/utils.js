var toastTimer = null;
function showToast(msg) {
    var t = document.getElementById("gt");
    if (!t) {
        t = document.createElement("div"); t.id = "gt";
        t.style.cssText = "position:fixed;top:50%;left:50%;transform:translate(-50%,-50%);background:rgba(0,0,0,.75);color:#fff;padding:12px 24px;border-radius:6px;z-index:99999;opacity:0;transition:opacity .3s;pointer-events:none;max-width:80%;text-align:center;";
        document.body.appendChild(t);
    }
    t.textContent = msg; t.style.opacity = "1";
    clearTimeout(toastTimer);
    toastTimer = setTimeout(function(){t.style.opacity="0";},2000);
}
function getUrlParams(){var p={},s=location.search.substring(1);if(!s)return p;
    s.split("&").forEach(function(kv){kv=kv.split("=");if(kv[0])p[decodeURIComponent(kv[0])]=decodeURIComponent(kv[1]||"");});return p;}
function formatPrice(p){return"¥"+(p||0).toFixed(2);}
function formatDate(ts){var d=new Date(ts);return d.getFullYear()+"-"+("0"+(d.getMonth()+1)).slice(-2)+"-"+("0"+d.getDate()).slice(-2)+" "+("0"+d.getHours()).slice(-2)+":"+("0"+d.getMinutes()).slice(-2);}
function getOrderStatusText(s){return{pending:"待发货",shipped:"已发货",completed:"已完成",cancelled:"已取消"}[s]||s;}
function getOrderStatusClass(s){return{pending:"warning",shipped:"primary",completed:"success",cancelled:"danger"}[s]||"default";}
async function updateCartCount(){var c=await API.getCartCount();
    document.querySelectorAll(".cart-count").forEach(function(e){e.textContent=c>99?"99+":c;e.style.display=c>0?"block":"none";});}