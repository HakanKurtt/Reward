package reward

import grails.gorm.transactions.Transactional

@Transactional
class CalculationsService {

    def serviceMethod() {

    }

    def welcome(params){
        def firstName = params.first;
        def totalPoints = params.points.toInteger();
        def welcomeMessage = "";

        switch(totalPoints){
            case 5:
                welcomeMessage = "Welcome back $firstName. This drink is on us";
                break;
            case 4:
                welcomeMessage = "Welcome back $firstName. Your next drink is free";
                break;
            case 2..3:
                welcomeMessage = "Welcome back $firstName. You have $totalPoints points.";
                break;
            default:
                welcomeMessage = "Welcome $firstName. Thank you for registering.";
                break;
        }
    }

    def getTotalPoints(customer){
        def totalAwards=0
        customer.awards.each{
            totalAwards= totalAwards+it.points
        }
        customer.totalPoints=totalAwards

        return customer
    }

    def processCheckin(lookupInstance){

        //look up for customer by phone number
        def customerInstance=Customer.findByPhone(lookupInstance.phone)
        //create a new customer if it is new one
        if(customerInstance == null){
            customerInstance=lookupInstance
            customerInstance.firstName="Customer"
        }
        //calculate the points
        def totalAwards=0
        customerInstance.awards.each{
            totalAwards= totalAwards+it.points
        }
        customerInstance.totalPoints=totalAwards

        //create a welcome message
        def welcomeMessage=""
        switch(totalAwards){
            case 5:
                welcomeMessage = "Welcome back $customerInstance.firstName. This drink is on us";
                break;
            case 4:
                welcomeMessage = "Welcome back $customerInstance.firstName. Your next drink is free";
                break;
            case 1..3:
                welcomeMessage = "Welcome back $customerInstance.firstName. You have ${totalAwards + 1} points.";
                break;
            default:
                welcomeMessage = "Welcome $customerInstance.firstName. Thank you for registering.";
                break;
        }

        //add a award
        if(totalAwards < 5){
            customerInstance.addToAwards(new Award(awardDate: new Date(), type:"Purchase", points:1))
        }else {
            customerInstance.addToAwards(new Award(awardDate: new Date(), type:"Reward", points:-5))
        }

        //save customer
        customerInstance.save(flush:true, failOnError:true)

        return [customerInstance, welcomeMessage]
    }
}
