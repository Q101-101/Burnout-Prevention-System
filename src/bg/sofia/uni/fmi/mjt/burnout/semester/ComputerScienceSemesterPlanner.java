package bg.sofia.uni.fmi.mjt.burnout.semester;

import bg.sofia.uni.fmi.mjt.burnout.exception.CryToStudentsDepartmentException;
import bg.sofia.uni.fmi.mjt.burnout.exception.InvalidSubjectRequirementsException;
import bg.sofia.uni.fmi.mjt.burnout.plan.SemesterPlan;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;

public final class ComputerScienceSemesterPlanner extends AbstractSemesterPlanner {

    @Override
    public UniversitySubject[] calculateSubjectList(SemesterPlan semesterPlan) throws InvalidSubjectRequirementsException {
        if (semesterPlan == null) {
            throw new IllegalArgumentException("semesterPlan cannot be null");
        }

        for (int i = 0; i < semesterPlan.subjectRequirements().length; i++) {
            for (int j = 0; j < i; j++) {
                if (semesterPlan.subjectRequirements()[i].category().equals(semesterPlan.subjectRequirements()[j].category())) {
                    throw new InvalidSubjectRequirementsException();
                }
            }
        }

        int creditsLeftToCover = semesterPlan.minimalAmountOfCredits();
        UniversitySubject[] subjects = new UniversitySubject[semesterPlan.subjects().length];
        int numAttendingSubjects = 0;
        for (int i = 0; i < subjects.length; i++) {
            subjects[i] = semesterPlan.subjects()[i];
        }

        for (int i = 0; i < subjects.length - 1; i++) {
            for (int j = i + 1; j < subjects.length; j++) {
                if (subjects[i].rating() < subjects[j].rating()) {
                    UniversitySubject temp = subjects[i];
                    subjects[i] = subjects[j];
                    subjects[j] = temp;
                }
            }
        }

        int i = 0;
        while (creditsLeftToCover > 0 && i < subjects.length) {
            creditsLeftToCover -= subjects[i].credits();
            numAttendingSubjects++;
            i++;
        }

        if (creditsLeftToCover > 0) {
            throw new CryToStudentsDepartmentException();
        }

        UniversitySubject[] attendingSubjects = new UniversitySubject[numAttendingSubjects];

        System.arraycopy(subjects, 0, attendingSubjects, 0, numAttendingSubjects);

        return attendingSubjects;
    }

}
