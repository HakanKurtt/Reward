package reward

class InventoryController {

    def index() {
        render "Here is the inventory list"
    }

    def edit(){
        def productName="Apple XS - new"
        def stock="20";
        [product:productName, stock:stock]
    }

    def remove(){
        render "you've removed one item."
    }

    def list(){
        def allProducts=Product.list();
        [allProducts:allProducts];
    }
}
