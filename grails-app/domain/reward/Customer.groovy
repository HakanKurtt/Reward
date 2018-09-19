package reward

class Customer {

    String fistName;
    String lastName;
    Long phone;
    String email;
    Integer totalPoints;
    static hasMany=[awards:Award, orders:OnlineOrder];

    static constraints = {
    }
}
