package Types;

public class Developer {
    private String firstname, lastname;

    public Developer(String firstname, String lastname){
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getName(){
        return this.firstname + " " + this.lastname;
    }
}
