
public class MatchingSystem {
    /** 
     * matching system with parameters:
     * @var jobs - jobs
     * @var applicants - applicants to the job
     * @var numberOfJobs - number of jobs available.
     * @var maximumNumberOfJobs - max number of jobs we will match with
     * @var maximumNumberofApplicants - max number of applicants for jobs.
     **/

    private JobDescription[] _jobs;
    private Person[] _applicants;
    private int _maximumNumberOfJobs;
    private int _maximumNumberOfApplicants;

    public MatchingSystem(){
    /**
     * constructor that initialises variables.
     * @var maximumNumbeOfBobs - initialises the max number of jobs
     * @var maximumNumberOfApplicants - initialises the max number of applicants
     * @var jobs - initialises the array with the max number of jobs as the length
     * @var appliacnts - initialises the array with max number of applicants
     * 
    **/
        _maximumNumberOfJobs = 3;
        _maximumNumberOfApplicants = 6;
        _jobs = new JobDescription[_maximumNumberOfJobs];
        _applicants = new Person[_maximumNumberOfApplicants];
    }

    public void addJob(JobDescription jobDesc) {
    /** 
    * This method receives as input a job description and adds it as the next
    * element to the job array. If the job array is not full, and the new job is
    * successfully added, an acknowledgement message is printed on the screen
    * along with the total number of jobs in the system.
    * @params jobDesc - job description
    **/

    if (_jobs.length <= _maximumNumberOfJobs){
        for (int i = 0 ; i <= _jobs.length; i++){
            if ( i == _jobs.length){
                System.out.println("Unfortunately, you are not currently able to add another job. The list is now full, Please purchase premium for a longer list size.\n" + jobDesc.getCompany() + " looking for skill(" + jobDesc.getSkillRequired() + ") will be unfortunate for the moment.\n\n");
                break;
            }

            if (_jobs[i] == null){
                _jobs[i] = jobDesc;
                System.out.println("The new company "+ jobDesc.getCompany() + " looking for people with " + jobDesc.getYearsOfExperience() + " years of experience in " + jobDesc.getSkillRequired() + " has successfully been added to the jobs pool.");
                int jobsLength = 0;
                for (JobDescription job : _jobs) {
                    if (job != null){
                        jobsLength += 1;
                    }
                }
                System.out.println("The number of jobs in the pool is " + jobsLength + "\n\n");
                break;
            }
        }
    }

    }

    public void addApplicant(Person personProfile){
    /**
     * This method receives as input an applicant person profile and adds it as the
     * next element to the applicants array. if the person array is not full and the new
     * Person could be successfully added, and acknowledgement message is printed on
     * the screen along with the total number of applicants in the system.
     * @param personProfile - a person profile
    **/
    if (_applicants.length <= _maximumNumberOfApplicants){
        for (int i = 0 ; i <= _applicants.length; i++){
            if (i == _applicants.length) {
                System.out.println("Unfortunately, you are not currently able to add another applicant. They are full.");
                break;
            }
            if (_applicants[i] == null){
                _applicants[i] = personProfile;
                System.out.println("The new person: " + personProfile.getName() + ", located in " + personProfile.getLocation() + "... has successfully been added to the applicants pool.");
                
                int applicantLength = 0;
                for (Person person : _applicants) {
                    if (person != null){
                        applicantLength += 1;
                    }
                }
                System.out.println("The number of applicants in the pool is " + applicantLength +".\n");
                break;
            }
        }
    }
    }

    public int listSuitableAssignments(){
    /**
     * This method lists all (job description - applicant) matches.
     * In order for a person to be matched to a job, the following
     * must be satisfied: (job and person must be in the same city) AND (the 
     * person's skill must match the skill required by the job ) AND ( the person
     * must meet the minimum years of experience ).
     * @return the total number of suitable assignments.
    **/
    int numSuitableAssignments = 0;

    for (JobDescription job : _jobs) {
        boolean isSomeoneSuitable = false;
        for (Person person : _applicants) {
            if (person == null){
                continue;
            } else if (job.getLocation() == person.getLocation() && job.getSkillRequired() == person.getSkill() && person.getYearsOfExperience() >= job.getYearsOfExperience()){
                numSuitableAssignments += 1;
                System.out.println("Found a match for " + job.getCompany() + " in " + job.getLocation() + ". The person who is a match is " + person.getName() + ", from " + person.getLocation() + " with experience in " + person.getSkill() + ".\n");
                isSomeoneSuitable = true;
                break;
            }
        }

        if (!isSomeoneSuitable){
            System.out.println("There is no match for company " + job.getCompany() + ". There are no suitable people with the years of expereience required in " + job.getSkillRequired() + " who are also from " + job.getLocation() + ".\n");
        }

    }

    return numSuitableAssignments;

    }

}
