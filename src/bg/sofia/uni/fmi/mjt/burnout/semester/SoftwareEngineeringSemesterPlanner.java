package bg.sofia.uni.fmi.mjt.burnout.semester;

import bg.sofia.uni.fmi.mjt.burnout.exception.CryToStudentsDepartmentException;
import bg.sofia.uni.fmi.mjt.burnout.exception.InvalidSubjectRequirementsException;
import bg.sofia.uni.fmi.mjt.burnout.plan.SemesterPlan;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;

public final class SoftwareEngineeringSemesterPlanner extends AbstractSemesterPlanner {

    public boolean checkIfCategoriesRequirementsAreMet(int[] categories) {
        for (int i = 0; i < categories.length; i++) {
            if (categories[i] != 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public UniversitySubject[] calculateSubjectList(SemesterPlan semesterPlan) throws InvalidSubjectRequirementsException {
        if (semesterPlan == null) {
            throw new IllegalArgumentException("semesterPlan cannot be null");
        }

        //get subject requirments
        int[] numOfSubjectsLeftToAttendForEachCategory = new int[semesterPlan.subjectRequirements().length];
        for (int i = 0; i < semesterPlan.subjectRequirements().length; i++) {
            for (int j = 0; j < i; j++) {
                if (semesterPlan.subjectRequirements()[i].category().equals(semesterPlan.subjectRequirements()[j].category())) {
                    throw new InvalidSubjectRequirementsException();
                }
            }
            numOfSubjectsLeftToAttendForEachCategory[i] = semesterPlan.subjectRequirements()[i].minAmountEnrolled();
        }

        //copy semesterPlan.subjects()
        UniversitySubject[] subjects = new UniversitySubject[semesterPlan.subjects().length];
        for (int i = 0; i < subjects.length; i++) {
            subjects[i] = semesterPlan.subjects()[i];
        }

        //sort by credits
        for (int i = 0; i < subjects.length - 1; i++) {
            for (int j = i + 1; j < subjects.length; j++) {
                if (subjects[i].credits() < subjects[j].credits()) {
                    UniversitySubject temp = subjects[i];
                    subjects[i] = subjects[j];
                    subjects[j] = temp;
                }
            }
        }

        UniversitySubject[] dodgedSubjects = new UniversitySubject[subjects.length];
        int dodgedSubjectsIndex = 0;

        UniversitySubject[] attendingSubjects = new UniversitySubject[subjects.length];
        int attendingSubjectsIndex = 0;

        int creditsLeftToCover = semesterPlan.minimalAmountOfCredits();

        int i = 0;
        while (creditsLeftToCover > 0 && i < subjects.length) {
            boolean attending = false;
            for (int j = 0; j < semesterPlan.subjectRequirements().length; j++) {
                if (subjects[i].category().equals(semesterPlan.subjectRequirements()[j].category())
                        && numOfSubjectsLeftToAttendForEachCategory[j] > 0) {
                    numOfSubjectsLeftToAttendForEachCategory[j]--;
                    attending = true;
                    attendingSubjects[attendingSubjectsIndex++] = subjects[i];
                    creditsLeftToCover -= subjects[i].credits();
                }
            }
            if (!attending) {
                dodgedSubjects[dodgedSubjectsIndex++] = subjects[i];
            }
            i++;
        }

        int dodgedSubjectsSize = dodgedSubjectsIndex;
        dodgedSubjectsIndex = 0;

        while (creditsLeftToCover > 0 && dodgedSubjectsIndex < dodgedSubjectsSize) {
            if (attendingSubjectsIndex >= attendingSubjects.length) {
                break;
            }

            attendingSubjects[attendingSubjectsIndex] = dodgedSubjects[dodgedSubjectsIndex];
            creditsLeftToCover -= dodgedSubjects[dodgedSubjectsIndex].credits();

            attendingSubjectsIndex++;
            dodgedSubjectsIndex++;
        }

        if (creditsLeftToCover > 0) {
            throw new CryToStudentsDepartmentException();
        }

        UniversitySubject[] finalAttendingSubjects = new UniversitySubject[attendingSubjectsIndex];

        for (int j = 0; j < attendingSubjectsIndex; j++) {
            finalAttendingSubjects[j] = attendingSubjects[j];
        }

        return finalAttendingSubjects;
    }

}
