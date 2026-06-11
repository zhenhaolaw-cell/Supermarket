/**
 * API 数据访问层
 * 通过 Ajax 请求连接 Java 后端
 */
const API_BASE = "/supermarket";

const API = {
    // ===== 商品 =====
    async getGoods() {
        return this._get("/goods/getAll");
    },

    async getGoodsById(id) {
        return this._get("/goods/getById?id=" + id);
    },

    async searchGoods(keyword) {
        const all = await this.getGoods();
        const kw = keyword.toLowerCase();
        return all.filter(g =>
            g.name.toLowerCase().includes(kw) ||
            (g.brand && g.brand.toLowerCase().includes(kw))
        );
    },

    async addGoods(data) {
        return this._post("/goods/add", data);
    },

    async updateGoods(data) {
        return this._post("/goods/update", data);
    },

    async deleteGoods(id) {
        return this._get("/goods/delete?id=" + id);
    },

    // ===== 购物车 =====
    async getCart() {
        return this._get("/cart/getList");
    },

    async addToCart(goodsId, quantity, spec) {
        // 后端通过 addOrUpdate 处理
        return this._post("/cart/add", { goodsId, quantity, spec });
    },

    async removeFromCart(id) {
        return this._get("/cart/delete?id=" + id);
    },

    async updateCartQuantity(id, quantity) {
        return this._post("/cart/updateQuantity", { id, quantity });
    },

    async clearCart() {
        return this._get("/cart/clear");
    },

    async getCartCount() {
        const cart = await this.getCart();
        return cart.reduce((sum, item) => sum + item.quantity, 0);
    },

    // ===== 订单 =====
    async getOrders() {
        return this._get("/order/getAll");
    },

    async createOrder() {
        return this._post("/order/checkout", {});
    },

    async updateOrderStatus(orderNo, status) {
        return this._post("/order/updateStatus", { orderNo, status });
    },

    // ===== 登录 =====
    async login(username, password) {
        try {
            await this._post("/admin/login", { username, password });
            localStorage.setItem("adminLoggedIn", "true");
            return true;
        } catch(e) {
            return false;
        }
    },

    isLoggedIn() {
        return localStorage.getItem("adminLoggedIn") === "true";
    },

    logout() {
        localStorage.removeItem("adminLoggedIn");
    },

    // ===== 内部方法 =====
    async _get(path) {
        const res = await fetch(API_BASE + path);
        const json = await res.json();
        if (json.code === 200) return json.data;
        else throw new Error(json.msg || "请求失败");
    },

    async _post(path, data) {
        const formBody = Object.keys(data).map(key =>
            encodeURIComponent(key) + "=" + encodeURIComponent(data[key])
        ).join("&");
        const res = await fetch(API_BASE + path, {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded;charset=utf-8" },
            body: formBody
        });
        const json = await res.json();
        if (json.code === 200) return json.data;
        else throw new Error(json.msg || "请求失败");
    }
};