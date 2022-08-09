
public class JobDescription {
    /** 
     * declares variables in jobdescription
     * @var location - location of the job
     * @var company - company name
     * @var skillRequired - skill required for the job
     * @var yearsOfExperience - number of years of experience required
     **/
    private String _location;
    private String _company;
    private String _skillRequired;
    private int _yearsOfExperienceRequired;

    public JobDescription(String newLocation, String newCompany, String skill, int years) {
    /**
    * main entity we will be using to create a new jobDescription
    * @params newLocation - location of the new job posting
    * @params newCompany - the company name for the job posting
    * @params skillRequired - the skills required for the job posting
    * @params yearsOfExpereienceRequired - years needed in the job posting
    **/
        _location = newLocation;
        _company = newCompany;
        _skillRequired = skill;
        _yearsOfExperienceRequired = years;
    }

    public JobDescription(){
    /**
    * constructor that allows an empty object to be created and to be added to later
    * will be modified later using getters and setters / methods
    **/
        }
    
    public String toString(){
        //returns a description fo the job posting when called.
        return _location + " is the location. " + _company + " is the company hiring. " + _skillRequired + " is the skill required." + _yearsOfExperienceRequired + " is the years of experience required for this job.";
    }

    //getters method.
    public String getLocation(){
        return _location;
    }

    public String getCompany(){
        return _company;
    }

    public String getSkillRequired(){
        return _skillRequired;
    }

    public int getYearsOfExperience(){
        return _yearsOfExperienceRequired;
    }

    //setters methods
    public String setLocation(String newLocation){
        _location = newLocation;
        return _location;
    }

    public String setCompany(String newConpany){
        _company = newConpany;
        return _company;
    }

    public String setSkillRequired(String newSkillRequired){
        _skillRequired = newSkillRequired;
        return newSkillRequired;
    }

    public int setYearsOfExperience(int newYearsOfExperience){
        _yearsOfExperienceRequired = newYearsOfExperience;
        return _yearsOfExperienceRequired;
    }
}
