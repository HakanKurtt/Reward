package reward

class CustomerController {

    static scaffold=Customer;

    def calculationsService


    def lookup(){
        //customer listi getirecek sorguyu oluşturuyoruz.
        def customerInstance = Customer.findByPhone(params.id)
        //olusturulan list'i viewe gönderiyoruz. Sagdaki viewdeki dizi ismi. Soldaki controller tarafından kullanılan ad.
        [customerInstanceList:customerInstance]
    }

    //action icerisinde logic bulunmamalı
    //view'den gelen veri service gönderilmeli ve service'den gelen result tekrar view'e gönderilmeli
    //business logic service icerisinde olmalıdır.
    def customerLookup(Customer lookupInstance){
        def (customerInstance, welcomeMessage) = calculationsService.processCheckin(lookupInstance)
        render(view:"checkin", model:[customerInstance:customerInstance, welcomeMessage: welcomeMessage])
        //Query customer by phone
        //If no costomer found
        // create a customer with given number
        // create a welcome message
        // add the award record
        // save customer
        // send welcome to the kiosk
        //If there is a customer
        // calculate the total points
        // create a welcome message
        // add the award record
        // save the customer
        // send welcome to kiosk



    }

    def checkin(){

    }

    //Scaffolding'in olusturduğu list'i override edeceğiz. Boylece bizim metodumuzu cagıracak.
    //Customer.count() db üzerindeki veri adetini sayar. Viewde bulunan degiskenlere bu degerleri gönderiyoruz.
    //params.max her sayfada gösterilecek veri adetini belirtir.
    def index() {
        params.max=10
        [customerList: Customer.list(params), customerCount: Customer.count()]
    }

    def create(){
        [customer: new Customer()]
    }

    //save diye bir view olmadığı icin show view'ine recirect yaptık ve olusturdugumuz customer'ın id sini verdik.
    def save(Customer customer){
        customer.save()
        println(customer.phone)
        redirect(action: "show", id:customer.id)
    }

    def show(Long id){
        def customer=Customer.get(id)
        customer = calculationsService.getTotalPoints(customer)
        [customer:customer]
    }

    def edit(Long id){
        def customer=Customer.get(id)
        println(customer.phone)
        [customer:customer]
    }

    //yeni musterinin ozellikleri ile eski müşterinin özelliklerini overlay yapmamız gerekir.
    //yeni musterinin özellikleri params ile gelmektedir. Eski musterinin özelliklerine Customer.get(id) ile erişilir.
    def update(Long id){
        def customer=Customer.get(id)
        customer.properties=params
        customer.save(flush:true, failOnError:true)
        println("debug:"+Customer.get(id).phone)
        redirect(action: "show", id:customer.id)
    }

    def delete(Long id){
        def customer=Customer.get(id)
        customer.delete(flush:true)
        redirect(action:"index")
    }

    def profile(){
        println("debug:"+params.phone)
        def customerInstance= Customer.findByPhone(params.id)
        [customerInstance:customerInstance]
    }

    def updateProfile(Customer customerInstance){
        customerInstance.save(flush: true, failOnError: true)
        render(view: "profile", model:[customerInstance: customerInstance])
    }
}
