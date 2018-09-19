package reward

class OrderItem {

    Integer qty; //productun miktarı(quantity)
    Float total; //productdan kac defa siparis verildiyse toplamı
    static belongsTo = [order:OnlineOrder, product:Product];

    static constraints = {
    }
}
