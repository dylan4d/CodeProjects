
public class Person {
    /** 
     * person class with variables:
     * @var name - persons name
     * @var gender - persons gender
     * @var location - persons location
     * @var skill - persons skill
     * @var yearsOfExperience - persons years of experience
     **/

    private Gender _pGender;
    private String _name;
    private String _location;
    private String _skill;
    private int _yearsOfExperience;

    private enum Gender {
        male("male"),
        female("female"),
        prefersNotToSay("prefers not to say");

        final String _gender;
        
        private Gender(String gender){
            this._gender = gender;
        }

        public String toString(){
            return _gender;
        }

    }

    public Person(String newName, String newGender, String newLocation) {
    /** 
     * constructor that sets the parameters equal to class variables.
     * @param name - name of the person
     * @param gender - gender of the person
     * @param location - location of the person
     * @param skill -skill of the person
     * @yearsOfExperience - the number of years of experience each person has.
    **/

        _name = newName;
        _location = newLocation;
        
        switch (newGender){
            case "male":
                _pGender = Gender.male;
                break;
            case "female":
                _pGender = Gender.female;
                break;
            case "pefers not to say":
                _pGender = Gender.prefersNotToSay;
                break;
        }
    }

    @Override
    public String toString(){
    //returns the object as a string.
        return _name;
    }

    public boolean equals(Person other){
    /**
     * assesses whether or not the objects are equal.
     * @param other - other person object
    **/
        
        if (_name == other._name && this._pGender == other._pGender){
            return true;
        } else {
            return false;
        }
    }

    public void addSkill(String newSkill, int years, boolean isOutputON) {
    /** 
    * This method adds a skill to a person. In this program, a person need not have
    * a skill. A new skill overrides the previous skill.
    * 
    * @param newSkill - a new skill
    * @param years - years of experience of the applicant for the given skill. Negative values are not accepted.
    * @param isOutputOn - if this parameter is set to true, the added skill is printed on the screen.
    **/
        _skill = newSkill;
        _yearsOfExperience = years;
        if (isOutputON){
            System.out.println(_name + " who has a gender of {" +  _pGender +  "}" +" has a new skill(" + _skill + ") with " + years + " years of experience.\n");
        }
    }

    //getters methods
    public String getName(){
        return _name;
    }

    public Gender getGender(){
        return _pGender;
    }

    public String getLocation(){
        return _location;
    }

    public String getSkill(){
        return _skill;
    }

    public int getYearsOfExperience(){
        return _yearsOfExperience;
    }

    //setters methods
    public String setName(String newName){
        _name = newName;
        return _name;
    }

    public Gender setGender(Gender newGender){
        _pGender = newGender;
        return _pGender;
    }

    public String setLocation(String newLocationString){
        _location = newLocationString;
        return _location;
    }

    public String setSkill(String newSkill){
        _skill = newSkill;
        return _skill;
    }

    public int setYearsOfExperience(int newYearsOfExperience){
        _yearsOfExperience = newYearsOfExperience;
        return _yearsOfExperience;
    }


}
