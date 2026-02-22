package bg.sofia.uni.fmi.mjt.burnout.subject;

/**
 * @param name the name of the subject
 * @param credits number of credit hours for this subject
 * @param rating difficulty rating of the subject (1-5)
 * @param category the academic category this subject belongs to
 * @param neededStudyTime estimated study time in days required for this subject
 *
 * @throws IllegalArgumentException if the name of the subject is null or blank
 * @throws IllegalArgumentException if the credits are not positive
 * @throws IllegalArgumentException if the rating is not in its bounds
 * @throws IllegalArgumentException if the Category is null
 * @throws IllegalArgumentException if the neededStudy time is not positive
 */
public record UniversitySubject(String name, int credits, int rating, Category category, int neededStudyTime) {

    public UniversitySubject {
        if (name == null) {
            throw new IllegalArgumentException("Subject's name is null");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Subject's name is blank");
        }
        if (credits <= 0) {
            throw new IllegalArgumentException("Subject's credits are not positive");
        }
        if(rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Subject's rating is out of range");
        }
        if (category == null) {
            throw new IllegalArgumentException("Subject's category is null");
        }
        if (neededStudyTime <= 0) {
            throw new IllegalArgumentException("Subject's needed study time is not positive");
        }
    }

}