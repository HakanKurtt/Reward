package reward

class BootStrap {

    def init = { servletContext ->
        new reward.Product(name:"Morning Bleed", sku:"MB01", price: 14.95).save();
        new reward.Product(name:"Dark Roast", sku:"DR45", price: 12.95).save();
    }
    def destroy = {
    }
}
